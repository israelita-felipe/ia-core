package com.ia.core.owl.service.tool.objectproperty;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de propriedade de objeto transitiva OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar propriedades de objeto transitivas
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa propriedade de objeto transitiva (Trans(R)).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * TransitiveObjectProperty é um axioma que especifica que uma propriedade de objeto é transitiva.
 * Se R(x, y) e R(y, z) então R(x, z). Permite inferir relacionamentos transitivos como ancestralidade e mereologia.
 * <p>
 * <b>Sintaxe Manchester:</b> TransitiveObjectProperty(:Propriedade)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>éAncestralDe é transitivo:
 *       TransitiveObjectProperty(:éAncestralDe)</li>
 *   <li>parteDe é transitivo (mereologia):
 *       TransitiveObjectProperty(:parteDe)</li>
 *   <li>éMaiorQue é transitivo:
 *       TransitiveObjectProperty(:éMaiorQue)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class TransitiveObjectPropertyTool extends OwlConstructorTool {

  public TransitiveObjectPropertyTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma propriedade de objeto transitiva na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param property Propriedade de objeto
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma propriedade de objeto transitiva OWL 2 DL na ontologia da sessão. " +
                     "Representa propriedade de objeto transitiva (Trans(R)). " +
                     "Especifica que se R(x, y) e R(y, z) então R(x, z). " +
                     "Exemplos: " +
                     "1) éAncestralDe é transitivo → TransitiveObjectProperty(:éAncestralDe). " +
                     "2) parteDe é transitivo (mereologia) → TransitiveObjectProperty(:parteDe). " +
                     "3) éMaiorQue é transitivo → TransitiveObjectProperty(:éMaiorQue).")
  public String createTransitiveObjectProperty(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Propriedade de objeto", required = true) String property) {

    log.debug("Criando TransitiveObjectProperty: Trans({})", property);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "TransitiveObjectProperty: " + property;

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de TransitiveObjectProperty: {}", result);
    return result;
  }
}
