package com.ia.core.owl.service.tool.annotation;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de range de propriedade de anotação OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar ranges de propriedades de anotação
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa range de propriedade de anotação (Range(AP) ⊆ DR).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * AnnotationPropertyRange é um axioma que especifica que se uma propriedade de anotação AP é usada para anotar uma entidade com um valor,
 * então esse valor deve ser uma instância do tipo de dado DR. Restringe o range da propriedade de anotação a um tipo de dado específico.
 * <p>
 * <b>Sintaxe Manchester:</b> AnnotationPropertyRange(:Propriedade :TipoDado)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>autor tem range xsd:string:
 *       AnnotationPropertyRange(:autor xsd:string)</li>
 *   <li>dataCriacao tem range xsd:dateTime:
 *       AnnotationPropertyRange(:dataCriacao xsd:dateTime)</li>
 *   <li>versao tem range xsd:integer:
 *       AnnotationPropertyRange(:versao xsd:integer)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class AnnotationPropertyRangeTool extends OwlConstructorTool {

  public AnnotationPropertyRangeTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria um range de propriedade de anotação na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param annotationProperty Propriedade de anotação
   * @param range Tipo de dado range
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria um range de propriedade de anotação OWL 2 DL na ontologia da sessão. " +
                     "Representa range de propriedade de anotação (Range(AP) ⊆ DR). " +
                     "Especifica que se uma propriedade de anotação é usada para anotar uma entidade com um valor, então esse valor deve ser uma instância do tipo de dado especificado. " +
                     "Exemplos: " +
                     "1) autor tem range xsd:string → AnnotationPropertyRange(:autor xsd:string). " +
                     "2) dataCriacao tem range xsd:dateTime → AnnotationPropertyRange(:dataCriacao xsd:dateTime). " +
                     "3) versao tem range xsd:integer → AnnotationPropertyRange(:versao xsd:integer).")
  public String createAnnotationPropertyRange(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Propriedade de anotação", required = true) String annotationProperty,
      @ToolParam(description = "Tipo de dado range", required = true) String range) {

    log.debug("Criando AnnotationPropertyRange: Range({}) ⊆ {}", annotationProperty, range);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "AnnotationPropertyRange: " + annotationProperty + " " + range;

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de AnnotationPropertyRange: {}", result);
    return result;
  }
}
