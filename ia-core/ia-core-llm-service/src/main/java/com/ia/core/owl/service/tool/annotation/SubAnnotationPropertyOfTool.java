package com.ia.core.owl.service.tool.annotation;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de subpropriedade de anotação OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar subpropriedades de anotação
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa subpropriedade de anotação (AP₁ ⊑ AP₂).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * SubAnnotationPropertyOf é um axioma que especifica que uma propriedade de anotação é subpropriedade de outra.
 * Se AP₁(x, v) então AP₂(x, v), onde AP₁ é subpropriedade de AP₂. Permite construir hierarquias de propriedades de anotação.
 * <p>
 * <b>Sintaxe Manchester:</b> SubAnnotationPropertyOf(:SubPropriedade :SuperPropriedade)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>autor é subpropriedade de rdfs:label:
 *       SubAnnotationPropertyOf(:autor rdfs:label)</li>
 *   <li>criadoPor é subpropriedade de rdfs:comment:
 *       SubAnnotationPropertyOf(:criadoPor rdfs:comment)</li>
 *   <li>modificadoPor é subpropriedade de rdfs:comment:
 *       SubAnnotationPropertyOf(:modificadoPor rdfs:comment)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class SubAnnotationPropertyOfTool extends OwlConstructorTool {

  public SubAnnotationPropertyOfTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma subpropriedade de anotação na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param subProperty Propriedade de anotação subpropriedade
   * @param superProperty Propriedade de anotação superpropriedade
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma subpropriedade de anotação OWL 2 DL na ontologia da sessão. " +
                     "Representa subpropriedade de anotação (AP₁ ⊑ AP₂). " +
                     "Especifica que uma propriedade de anotação é subpropriedade de outra. " +
                     "Exemplos: " +
                     "1) autor é subpropriedade de rdfs:label → SubAnnotationPropertyOf(:autor rdfs:label). " +
                     "2) criadoPor é subpropriedade de rdfs:comment → SubAnnotationPropertyOf(:criadoPor rdfs:comment). " +
                     "3) modificadoPor é subpropriedade de rdfs:comment → SubAnnotationPropertyOf(:modificadoPor rdfs:comment).")
  public String createSubAnnotationPropertyOf(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Propriedade de anotação subpropriedade", required = true) String subProperty,
      @ToolParam(description = "Propriedade de anotação superpropriedade", required = true) String superProperty) {

    log.debug("Criando SubAnnotationPropertyOf: {} ⊑ {}", subProperty, superProperty);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SubAnnotationPropertyOf: " + subProperty + " " + superProperty;

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de SubAnnotationPropertyOf: {}", result);
    return result;
  }
}
