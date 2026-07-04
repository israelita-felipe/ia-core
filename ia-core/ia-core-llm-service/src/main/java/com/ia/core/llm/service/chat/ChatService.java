package com.ia.core.llm.service.chat;

import com.google.gson.Gson;
import com.ia.core.llm.model.ferramenta.Ferramenta;
import com.ia.core.llm.model.prompt.FinalidadePromptEnum;
import com.ia.core.llm.service.prompt.PromptRepository;
import com.ia.core.llm.service.template.PromptTemplateService;
import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient.CallResponseSpec;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Serviço principal de Chat que orquestra as operações de chat.
 * <p>
 * Responsável pela coordenação entre os serviços especializados.
 * Integra funcionalidades de ChatApplicationService para gerenciamento de prompts.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class ChatService {

  /**
   * ID da sessão padrão quando não especificado.
   */
  private static final String DEFAULT_SESSION_ID = "default";

  /** Serviço de Templates de Prompt */
  private final PromptTemplateService promptTemplateService;

  /** Serviço de Sessão de Chat */
  private final ChatSessionService chatSessionService;

  /** Comunicador LLM para criação de ChatClient */
  private final com.ia.core.owl.service.LLMCommunicator llmCommunicator;

  /** Repositório de Prompts para gerenciamento de templates */
  private final PromptRepository promptRepository;

  /**
   * Realiza uma pergunta simples ao modelo.
   *
   * @param document o documento base
   * @param text a pergunta/texto
   * @param chatSessionId identificador da sessão de chat
   * @return resposta do modelo
   */
  public String ask(String document, String text, String chatSessionId) {
    Objects.requireNonNull(text, "Texto da pergunta não pode ser null");
    Objects.requireNonNull(chatSessionId, "ID da sessão de chat não pode ser null");

    Prompt prompt = promptTemplateService.createSimplePrompt(document, text);
    CallResponseSpec response = call(prompt, chatSessionId, null, null);
    return response.content();
  }

  /**
   * Realiza uma pergunta ao modelo com template de sistema.
   *
   * @param document o documento base
   * @param text a pergunta/texto
   * @param systemTemplate o template do sistema
   * @param finalidade o tipo de retorno esperado
   * @param exigeContexto se deve usar contexto do vector store
   * @param params parâmetros adicionais
   * @param chatSessionId identificador da sessão de chat
   * @return resposta do modelo
   */
  public String ask(String document, String text, String systemTemplate,
      FinalidadePromptEnum finalidade, boolean exigeContexto, Map<String, Object> params, String chatSessionId) {
    Objects.requireNonNull(text, "Texto da pergunta não pode ser null");
    Objects.requireNonNull(chatSessionId, "ID da sessão de chat não pode ser null");
    Objects.requireNonNull(finalidade, "Finalidade não pode ser null");

    Prompt prompt = promptTemplateService.createSystemPrompt(
        document, text, systemTemplate, finalidade, params);
    CallResponseSpec response = call(prompt, chatSessionId, null, null);
    ParameterizedTypeReference<?> returnType = finalidade.getReturnType();
    if (returnType == null) {
      return response.content();
    }
    Object entity = response.entity(returnType);
    return new Gson().toJson(entity);
  }

  /**
   * Realiza uma pergunta ao modelo usando um template do banco de dados.
   *
   * @param templateId identificador do template no banco
   * @param params mapa de parâmetros para o template
   * @param chatSessionId identificador da sessão de chat
   * @param tools array de tools a serem registradas
   * @return resposta do modelo
   */
  public String ask(String templateId, Map<String, Object> params, String chatSessionId, Object...tools) {
    Objects.requireNonNull(templateId, "ID do template não pode ser null");
    Objects.requireNonNull(chatSessionId, "ID da sessão de chat não pode ser null");

    String promptText = promptTemplateService.processTemplate(templateId, params);
    Prompt prompt = promptTemplateService.createSimplePrompt("", promptText);
    return call(prompt, chatSessionId, null, tools).content();
  }

  /**
   * Realiza uma pergunta ao modelo usando um template do banco de dados com skills e tools.
   *
   * @param templateId identificador do template no banco
   * @param params mapa de parâmetros para o template
   * @param chatSessionId identificador da sessão de chat
   * @param skills lista de skills (Ferramenta com tipo SKILL)
   * @param tools lista de tools a serem registradas
   * @return resposta do modelo
   */
  public String ask(String templateId, Map<String, Object> params, String chatSessionId, List<Ferramenta> skills, List<Object> tools) {
    Objects.requireNonNull(templateId, "ID do template não pode ser null");
    Objects.requireNonNull(chatSessionId, "ID da sessão de chat não pode ser null");

    String promptText = promptTemplateService.processTemplate(templateId, params);
    Prompt prompt = promptTemplateService.createSimplePrompt("", promptText);
    return call(prompt, chatSessionId, skills, tools).content();
  }

  /**
   * Realiza uma pergunta ao modelo usando um template do banco de dados com skills e tools varargs.
   *
   * @param templateId identificador do template no banco
   * @param params mapa de parâmetros para o template
   * @param chatSessionId identificador da sessão de chat
   * @param skills lista de skills (Ferramenta com tipo SKILL)
   * @param tools array de tools a serem registradas
   * @return resposta do modelo
   */
  public String ask(String templateId, Map<String, Object> params, String chatSessionId, List<Ferramenta> skills, Object...tools) {
    Objects.requireNonNull(templateId, "ID do template não pode ser null");
    Objects.requireNonNull(chatSessionId, "ID da sessão de chat não pode ser null");

    String promptText = promptTemplateService.processTemplate(templateId, params);
    Prompt prompt = promptTemplateService.createSimplePrompt("", promptText);
    return call(prompt, chatSessionId, skills, tools).content();
  }

  /**
   * Executa uma chamada ao modelo com advisor de vector store.
   *
   * @param prompt o prompt a ser enviado
   * @param chatSessionId identificador da sessão de chat
   * @param skills lista de skills (Ferramenta com tipo SKILL)
   * @param tools array de tools a serem registradas
   * @return resposta da chamada
   */
  protected CallResponseSpec call(Prompt prompt, String chatSessionId, List<Ferramenta> skills, Object...tools) {
      if (!chatSessionService.isSessionActive(chatSessionId)) {
          chatSessionService.createSession(chatSessionId);
      }
    return llmCommunicator.sendPrompt(prompt, chatSessionId, skills, tools);
  }

  /**
   * Realiza uma pergunta ao modelo com suporte a prompts do banco de dados.
   * <p>
   * Método integrado de ChatApplicationService com suporte a resiliência.
   *
   * @param dto DTO contendo a requisição de chat
   * @return resposta do modelo
   */
  @Resilient(ResilienceProfile.LLM_SERVICE)
  public String ask(com.ia.core.llm.service.model.chat.ChatRequestDTO dto) {
    return ask(dto, null);
  }

  /**
   * Realiza uma pergunta ao modelo com suporte a prompts do banco de dados e override de system prompt.
   * <p>
   * Método integrado de ChatApplicationService com suporte a resiliência.
   *
   * @param dto DTO contendo a requisição de chat
   * @param systemPromptOverride override do prompt de sistema
   * @return resposta do modelo
   */
  @Resilient(ResilienceProfile.LLM_SERVICE)
  public String ask(com.ia.core.llm.service.model.chat.ChatRequestDTO dto, String systemPromptOverride) {
    ChatRequestParams params = prepareChatRequestParams(dto, systemPromptOverride);

    if (params.text() == null || params.text().isBlank()) {
      return "";
    }

    if (params.promptId() != null) {
      return askWithPromptFromDatabase(params);
    }

    if (systemPromptOverride != null) {
      return ask("", params.text(), systemPromptOverride, FinalidadePromptEnum.RESPOSTA_TEXTUAL, false,
          Map.of(), params.sessionId());
    }

    return ask("", params.text(), params.sessionId());
  }

  /**
   * Prepara os parâmetros da requisição de chat a partir do DTO.
   *
   * <p>Extrai e normaliza os campos do DTO, aplicando valores padrão quando necessário.
   *
   * @param dto DTO da requisição de chat
   * @param systemPromptOverride Override do prompt de sistema
   * @return Parâmetros preparados para a requisição
   */
  private ChatRequestParams prepareChatRequestParams(com.ia.core.llm.service.model.chat.ChatRequestDTO dto,
                                                     String systemPromptOverride) {
    String text = dto.getRequest() != null ? dto.getRequest() : dto.getText();
    String sessionId = dto.getSessionId() != null ? dto.getSessionId() : DEFAULT_SESSION_ID;
    return new ChatRequestParams(dto.getPromptId(), text, sessionId, systemPromptOverride);
  }

  /**
   * Executa a pergunta usando um prompt do banco de dados.
   *
   * @param params Parâmetros da requisição preparados
   * @return resposta do modelo
   */
  private String askWithPromptFromDatabase(ChatRequestParams params) {
    promptRepository.findById(params.promptId()).ifPresent(prompt -> {
      if (prompt.getTemplate() != null) {
        String system = params.systemPromptOverride() != null
            ? params.systemPromptOverride()
            : prompt.getTemplate().getConteudo();
        ask("", params.text(), system, prompt.getFinalidade(), prompt.getTemplate().isExigeContexto(),
            Collections.emptyMap(), params.sessionId());
      }
    });
    return "";
  }

  /**
   * Record para encapsular os parâmetros preparados de uma requisição de chat.
   *
   * @param promptId ID do prompt no banco de dados
   * @param text Texto da pergunta/requisição
   * @param sessionId ID da sessão de chat
   * @param systemPromptOverride Override do prompt de sistema
   */
  private record ChatRequestParams(Long promptId, String text, String sessionId, String systemPromptOverride) {}
}
