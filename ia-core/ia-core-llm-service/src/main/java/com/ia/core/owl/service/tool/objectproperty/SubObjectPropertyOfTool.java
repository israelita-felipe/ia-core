package com.ia.core.owl.service.tool.objectproperty;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de subpropriedade de objeto OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar subpropriedades de objeto
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa subpropriedade de objeto (R₁ ⊑ R₂).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * SubObjectPropertyOf é um axioma que especifica que uma propriedade de objeto é subpropriedade de outra.
 * Se R₁(x, y) então R₂(x, y), onde R₁ é subpropriedade de R₂. Permite construir hierarquias de propriedades.
 * <p>
 * <b>Sintaxe Manchester:</b> SubObjectPropertyOf(:SubPropriedade :SuperPropriedade)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>temFilho é subpropriedade de temParente:
 *       SubObjectPropertyOf(:temFilho :temParente)</li>
 *   <li>temPai é subpropriedade de temParente:
 *       SubObjectPropertyOf(:temPai :temParente)</li>
 *   <li>temEsposa é subpropriedade de temCônjuge:
 *       SubObjectPropertyOf(:temEsposa :temConjuge)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class SubObjectPropertyOfTool extends OwlConstructorTool {

  public SubObjectPropertyOfTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma subpropriedade de objeto na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param subProperty Propriedade de objeto subpropriedade
   * @param superProperty Propriedade de objeto superpropriedade
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma subpropriedade de objeto OWL 2 DL na ontologia da sessão. " +
                     "Representa subpropriedade de objeto (R₁ ⊑ R₂). " +
                     "Especifica que uma propriedade de objeto é subpropriedade de outra. " +
                     "Exemplos: " +
                     "1) temFilho é subpropriedade de temParente → SubObjectPropertyOf(:temFilho :temParente). " +
                     "2) temPai é subpropriedade de temParente → SubObjectPropertyOf(:temPai :temParente). " +
                     "3) temEsposa é subpropriedade de temCônjuge → SubObjectPropertyOf(:temEsposa :temConjuge).")
  public String createSubObjectPropertyOf(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Propriedade de objeto subpropriedade", required = true) String subProperty,
      @ToolParam(description = "Propriedade de objeto superpropriedade", required = true) String superProperty) {

    log.debug("Criando SubObjectPropertyOf: {} ⊑ {}", subProperty, superProperty);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SubObjectPropertyOf: " + subProperty + " " + superProperty;

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de SubObjectPropertyOf: {}", result);
    return result;
  }
}
