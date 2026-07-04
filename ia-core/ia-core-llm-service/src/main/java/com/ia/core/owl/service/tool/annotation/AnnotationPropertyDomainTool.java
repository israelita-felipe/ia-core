package com.ia.core.owl.service.tool.annotation;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de domínio de propriedade de anotação OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar domínios de propriedades de anotação
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa domínio de propriedade de anotação (Domain(AP) ⊆ C).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * AnnotationPropertyDomain é um axioma que especifica que se uma propriedade de anotação AP é usada para anotar uma entidade,
 * então essa entidade deve ser uma instância da classe C. Restringe o domínio da propriedade de anotação a uma classe específica.
 * <p>
 * <b>Sintaxe Manchester:</b> AnnotationPropertyDomain(:Propriedade :Classe)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>autor tem domínio Pessoa:
 *       AnnotationPropertyDomain(:autor :Pessoa)</li>
 *   <li>criadoPor tem domínio Pessoa:
 *       AnnotationPropertyDomain(:criadoPor :Pessoa)</li>
 *   <li>modificadoPor tem domínio Pessoa:
 *       AnnotationPropertyDomain(:modificadoPor :Pessoa)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class AnnotationPropertyDomainTool extends OwlConstructorTool {

  public AnnotationPropertyDomainTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria um domínio de propriedade de anotação na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param annotationProperty Propriedade de anotação
   * @param domain Classe domínio
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria um domínio de propriedade de anotação OWL 2 DL na ontologia da sessão. " +
                     "Representa domínio de propriedade de anotação (Domain(AP) ⊆ C). " +
                     "Especifica que se uma propriedade de anotação é usada para anotar uma entidade, então essa entidade deve ser uma instância da classe especificada. " +
                     "Exemplos: " +
                     "1) autor tem domínio Pessoa → AnnotationPropertyDomain(:autor :Pessoa). " +
                     "2) criadoPor tem domínio Pessoa → AnnotationPropertyDomain(:criadoPor :Pessoa). " +
                     "3) modificadoPor tem domínio Pessoa → AnnotationPropertyDomain(:modificadoPor :Pessoa).")
  public String createAnnotationPropertyDomain(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Propriedade de anotação", required = true) String annotationProperty,
      @ToolParam(description = "Classe domínio", required = true) String domain) {

    log.debug("Criando AnnotationPropertyDomain: Domain({}) ⊆ {}", annotationProperty, domain);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "AnnotationPropertyDomain: " + annotationProperty + " " + domain;

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de AnnotationPropertyDomain: {}", result);
    return result;
  }
}
