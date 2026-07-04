package com.ia.core.owl.service.tool.objectproperty;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de axiomas EquivalentObjectProperties OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar propriedades equivalentes
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa propriedades de objeto equivalentes (sinônimos ou idênticas).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * EquivalentObjectProperties é um axioma que declara que duas propriedades de objeto têm a mesma
 * extensão, ou seja, relacionam os mesmos pares de instâncias. É usado para definir sinônimos,
 * propriedades idênticas ou diferentes nomes para o mesmo conceito de relação.
 * <p>
 * <b>Sintaxe Manchester:</b> EquivalentObjectProperties(:Propriedade1 :Propriedade2)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>esposa e mulher são equivalentes:
 *       EquivalentObjectProperties(:esposa :mulher)</li>
 *   <li>trabalhaPara e éEmpregadoDe são equivalentes:
 *       EquivalentObjectProperties(:trabalhaPara :eEmpregadoDe)</li>
 *   <li>resideEm e moraEm representam o mesmo conceito:
 *       EquivalentObjectProperties(:resideEm :moraEm)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class EquivalentObjectPropertiesTool extends OwlConstructorTool {

  public EquivalentObjectPropertiesTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria um axioma EquivalentObjectProperties na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param property1 Primeira propriedade de objeto
   * @param property2 Segunda propriedade de objeto (equivalente à primeira)
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria um axioma EquivalentObjectProperties OWL 2 DL na ontologia da sessão. " +
                     "Declara que duas propriedades de objeto são equivalentes (têm a mesma extensão/relacionam os mesmos pares de instâncias). " +
                     "É usado para definir sinônimos, propriedades idênticas ou diferentes nomes para o mesmo conceito de relação. " +
                     "Exemplos: " +
                     "1) esposa e mulher são equivalentes → EquivalentObjectProperties(:esposa :mulher). " +
                     "2) trabalhaPara e éEmpregadoDe são equivalentes → EquivalentObjectProperties(:trabalhaPara :eEmpregadoDe). " +
                     "3) resideEm e moraEm representam o mesmo conceito → EquivalentObjectProperties(:resideEm :moraEm). " +
                     "Útil para definir sinônimos ou propriedades idênticas.")
  public String createEquivalentObjectProperties(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Primeira propriedade de objeto", required = true) String property1,
      @ToolParam(description = "Segunda propriedade de objeto (equivalente à primeira)", required = true) String property2) {

    log.debug("Criando EquivalentObjectProperties: {} equivalente a {}", property1, property2);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "EquivalentObjectProperties(" + property1 + " " + property2 + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de EquivalentObjectProperties: {}", result);
    return result;
  }
}
