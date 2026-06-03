package com.ia.core.owl.service.tool.base;

import com.ia.core.llm.service.ferramenta.FerramentaService;
import com.ia.core.llm.service.model.ferramenta.FerramentaDTO;
import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.llm.service.template.TemplateService;
import com.ia.core.owl.service.DefaultOwlService;
import com.ia.core.owl.service.LLMCommunicator;
import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementação abstrata que fornece funcionalidade comum
 * para todas as tools OWL 2 DL.
 * <p>
 * Gerencia automaticamente a criação e carregamento de ferramentas e templates.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
public abstract class AbstractOWLTool implements OWLTool {

  protected FerramentaDTO tool;
  protected TemplateDTO template;
  protected final ChatModel chatModel;
  protected final LLMCommunicator llmCommunicator;
  protected final DefaultOwlService owlService;
  protected final TemplateService templateService;
  protected final FerramentaService ferramentaService;

  /**
   * Construtor para inicialização.
   * Configura os metadados da ferramenta automaticamente.
   */
  public AbstractOWLTool(ChatModel chatModel,
                        LLMCommunicator llmCommunicator,
                        DefaultOwlService owlService,
                        TemplateService templateService,
                        FerramentaService ferramentaService) {
    this.chatModel = chatModel;
    this.llmCommunicator = llmCommunicator;
    this.owlService = owlService;
    this.templateService = templateService;
    this.ferramentaService = ferramentaService;

    // Carregar ou criar a ferramenta e o template
    loadOrCreateToolAndTemplate();
  }

  private void loadOrCreateToolAndTemplate() {
    String identificador = getConstructorName();

    // Carregar ou criar template
    Optional<TemplateDTO> existingTemplate = templateService.loadById(identificador);

    if (existingTemplate.isPresent()) {
      this.template = existingTemplate.get();
      log.debug("Template existente carregado: {}", identificador);
    } else {
      this.template = buildTemplate();
      templateService.save(this.template);
      log.debug("Novo template criado e persistido: {}", identificador);
    }

    // Carregar ou criar ferramenta
    Optional<FerramentaDTO> existingTool = ferramentaService.listAvailable().stream()
        .filter(f -> f.getIdentificador().equals(identificador))
        .findFirst();

    if (existingTool.isPresent()) {
      this.tool = existingTool.get();
      log.debug("Ferramenta existente carregada: {}", identificador);
    } else {
      this.tool = buildTool();
      ferramentaService.save(this.tool);
      log.debug("Nova ferramenta criada e persistida: {}", identificador);
    }
  }

  /**
   * Implementação padrão para buildTemplate.
   * Subclasses podem sobrescrever se necessário.
   */
  protected TemplateDTO buildTemplate() {
    return TemplateDTO.builder()
        .titulo(getConstructorName())
        .identificador(getConstructorName())
        .conteudo(getPromptTemplate())
        .exigeContexto(true)
        .build();
  }

  /**
   * Implementação padrão para buildTool.
   * Subclasses podem sobrescrever se necessário.
   */
  protected FerramentaDTO buildTool() {
    return FerramentaDTO.builder()
        .titulo(getConstructorName())
        .descricao(getDescription())
        .identificador(getConstructorName())
        .tipo(com.ia.core.llm.model.ferramenta.TipoFerramentaEnum.TOOL_SPRING)
        .moduloOrigem("ia-core-llm-service")
        .ativo(true)
        .descobertaAutomatica(true)
        .build();
  }

  @Override
  public List<AxiomaDTO> generateAxioms(String naturalLanguageDescription,
                                        OntologyContext context) {
    log.debug("Gerando axiomas para construtor: {}, descrição: {}",
              getConstructorName(), naturalLanguageDescription);

    String prompt = buildPrompt(naturalLanguageDescription, context);
    String response = llmCommunicator.sendPrompt(chatModel, prompt);

    log.debug("Resposta do LLM: {}", response);

    return parseResponse(response);
  }

  protected String buildPrompt(String description, OntologyContext context) {
    Map<String, Object> params = new HashMap<>();
    params.put("description", description);
    params.put("constructor", getConstructorName());
    params.put("examples", getExamples());
    if (context != null) {
      params.put("context", context.toManchesterSyntax());
    }
    return templateService.processTemplate(template, params);
  }

  /**
   * Implementação padrão para parseResponse com logging adequado.
   * Subclasses podem sobrescrever se necessitarem de lógica customizada.
   */
  protected List<AxiomaDTO> parseResponse(String response) {
    String cleaned = cleanResponse(response);
    log.debug("Resposta limpa para construtor {}: {}", getConstructorName(), cleaned);

    try {
      AxiomaDTO axiom = owlService.criarAxioma(cleaned);
      log.debug("Axioma criado com sucesso para construtor {}", getConstructorName());
      return List.of(axiom);
    } catch (Exception e) {
      log.error("Erro ao parsear resposta para construtor {}: {}", getConstructorName(), cleaned, e);
      return List.of();
    }
  }

  /**
   * Implementação padrão para validateAxiom.
   * Subclasses podem sobrescrever se necessitarem de lógica customizada.
   */
  @Override
  public boolean validateAxiom(AxiomaDTO axiom) {
    try {
      owlService.addAxioms(() -> List.of(axiom));
      log.debug("Axioma validado com sucesso para construtor {}", getConstructorName());
      return true;
    } catch (Exception e) {
      log.warn("Axioma inválido para construtor {}: {}", getConstructorName(), axiom, e);
      return false;
    }
  }

  protected String cleanResponse(String response) {
    return response
        .replaceAll("```", "")
        .replaceAll("(?i)axiom:", "")
        .replaceAll("(?i)axioma:", "")
        .trim();
  }

}
