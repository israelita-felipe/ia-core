package com.ia.core.owl.service.tool.annotation;

import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de anotação OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar anotações
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa anotação sobre uma entidade (Ann(entity, value)).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * AnnotationAssertion é um axioma que associa um valor a uma entidade através de uma propriedade de anotação.
 * Permite adicionar metadados e documentação a classes, propriedades e indivíduos sem afetar a lógica da ontologia.
 * <p>
 * <b>Sintaxe Manchester:</b> AnnotationAssertion(:Propriedade :Entidade "Valor")
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>Pessoa tem rdfs:label 'Pessoa':
 *       AnnotationAssertion(rdfs:label :Pessoa "Pessoa")</li>
 *   <li>Pessoa tem rdfs:comment 'Representa uma pessoa física':
 *       AnnotationAssertion(rdfs:comment :Pessoa "Representa uma pessoa física")</li>
 *   <li>temPai tem rdfs:label 'tem pai':
 *       AnnotationAssertion(rdfs:label :temPai "tem pai")</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class AnnotationAssertionTool extends OwlConstructorTool {

  public AnnotationAssertionTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma anotação na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param annotationProperty Propriedade de anotação (ex: rdfs:label, rdfs:comment)
   * @param entity Entidade a ser anotada
   * @param value Valor da anotação
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma anotação OWL 2 DL na ontologia da sessão. " +
                     "Representa anotação sobre uma entidade (Ann(entity, value)). " +
                     "Permite adicionar metadados e documentação a classes, propriedades e indivíduos sem afetar a lógica da ontologia. " +
                     "Exemplos: " +
                     "1) Pessoa tem rdfs:label 'Pessoa' → AnnotationAssertion(rdfs:label :Pessoa \"Pessoa\"). " +
                     "2) Pessoa tem rdfs:comment 'Representa uma pessoa física' → AnnotationAssertion(rdfs:comment :Pessoa \"Representa uma pessoa física\"). " +
                     "3) temPai tem rdfs:label 'tem pai' → AnnotationAssertion(rdfs:label :temPai \"tem pai\").")
  public String createAnnotationAssertion(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Propriedade de anotação (ex: rdfs:label, rdfs:comment)", required = true) String annotationProperty,
      @ToolParam(description = "Entidade a ser anotada", required = true) String entity,
      @ToolParam(description = "Valor da anotação", required = true) String value) {

    log.debug("Criando AnnotationAssertion: {}({}, {})", annotationProperty, entity, value);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "AnnotationAssertion: " + annotationProperty + " " + entity + " " + value;

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de AnnotationAssertion: {}", result);
    return result;
  }
}
