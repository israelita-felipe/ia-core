package com.ia.core.owl.service.tool.objectproperty;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Tool para criação de cadeia de propriedades OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar cadeias de propriedades
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa cadeia de propriedades (R₁ ∘ R₂ ∘ ... ∘ Rₙ).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * ObjectPropertyChain é um axioma que especifica que uma propriedade de objeto é subpropriedade de uma cadeia de propriedades.
 * Se R(x, y) e S(y, z) então T(x, z), onde T é subpropriedade de R ∘ S. Permite inferir relacionamentos transitivos compostos.
 * <p>
 * <b>Sintaxe Manchester:</b> SubObjectPropertyOf(ObjectPropertyChain(:R₁ :R₂ ... :Rₙ) :Propriedade)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>Se alguém tem pai que tem irmão, então essa pessoa tem um tio:
 *       SubObjectPropertyOf(ObjectPropertyChain(:temPai :temIrmao) :temTio)</li>
 *   <li>Se alguém reside em uma cidade que está localizada em um país, então reside nesse país:
 *       SubObjectPropertyOf(ObjectPropertyChain(:resideEm :localizadoEm) :resideNoPais)</li>
 *   <li>Se um produto tem componente que tem fornecedor, então o produto tem fornecedor indireto:
 *       SubObjectPropertyOf(ObjectPropertyChain(:temComponente :temFornecedor) :temFornecedorIndireto)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class ObjectPropertyChainTool extends OwlConstructorTool {

  public ObjectPropertyChainTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma cadeia de propriedades na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param resultProperty Propriedade resultante da cadeia
   * @param chainProperties Lista de propriedades na cadeia (em ordem)
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma cadeia de propriedades OWL 2 DL na ontologia da sessão. " +
                     "Representa cadeia de propriedades (R₁ ∘ R₂ ∘ ... ∘ Rₙ). " +
                     "Especifica que uma propriedade de objeto é subpropriedade de uma cadeia de propriedades. " +
                     "Exemplos: " +
                     "1) Se alguém tem pai que tem irmão, então essa pessoa tem um tio → SubObjectPropertyOf(ObjectPropertyChain(:temPai :temIrmao) :temTio). " +
                     "2) Se alguém reside em uma cidade que está localizada em um país, então reside nesse país → SubObjectPropertyOf(ObjectPropertyChain(:resideEm :localizadoEm) :resideNoPais). " +
                     "3) Se um produto tem componente que tem fornecedor, então o produto tem fornecedor indireto → SubObjectPropertyOf(ObjectPropertyChain(:temComponente :temFornecedor) :temFornecedorIndireto).")
  public String createObjectPropertyChain(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Propriedade resultante da cadeia", required = true) String resultProperty,
      @ToolParam(description = "Lista de propriedades na cadeia (em ordem)", required = true) List<String> chainProperties) {

    log.debug("Criando ObjectPropertyChain: {} = {}", resultProperty, String.join(" ∘ ", chainProperties));

    // Constrói o axioma em Manchester OWL Syntax
    String chain = "ObjectPropertyChain(" + String.join(" ", chainProperties) + ")";
    String manchesterAxiom = "SubObjectPropertyOf: " + chain + " " + resultProperty;

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de ObjectPropertyChain: {}", result);
    return result;
  }
}
