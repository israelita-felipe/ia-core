package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de axiomas EquivalentClasses OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar classes equivalentes
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa classes equivalentes (sinônimos ou idênticas).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * EquivalentClasses é um axioma que declara que duas ou mais classes têm a mesma extensão,
 * ou seja, contêm exatamente as mesmas instâncias. É usado para definir sinônimos,
 * classes idênticas ou diferentes nomes para o mesmo conceito.
 * <p>
 * <b>Sintaxe Manchester:</b> EquivalentClasses(:Classe1 :Classe2 ...)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>Homem e MachoHumano são a mesma coisa:
 *       EquivalentClasses(:Homem :MachoHumano)</li>
 *   <li>VeículoAutomotor e Automóvel são equivalentes:
 *       EquivalentClasses(:VeiculoAutomotor :Automovel)</li>
 *   <li>ClienteVIP e ClientePremium representam o mesmo conceito:
 *       EquivalentClasses(:ClienteVIP :ClientePremium)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class EquivalentClassesTool extends OwlConstructorTool {

  public EquivalentClassesTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria um axioma EquivalentClasses na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param class1 Primeira classe
   * @param class2 Segunda classe (equivalente à primeira)
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria um axioma EquivalentClasses OWL 2 DL na ontologia da sessão. " +
                     "Declara que duas ou mais classes são equivalentes (têm a mesma extensão/instâncias). " +
                     "É usado para definir sinônimos, classes idênticas ou diferentes nomes para o mesmo conceito. " +
                     "Exemplos: " +
                     "1) Homem e MachoHumano são a mesma coisa → EquivalentClasses(:Homem :MachoHumano). " +
                     "2) VeículoAutomotor e Automóvel são equivalentes → EquivalentClasses(:VeiculoAutomotor :Automovel). " +
                     "3) ClienteVIP e ClientePremium representam o mesmo conceito → EquivalentClasses(:ClienteVIP :ClientePremium). " +
                     "Útil para definir sinônimos ou classes idênticas em ontologias.")
  public String createEquivalentClasses(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Primeira classe", required = true) String class1,
      @ToolParam(description = "Segunda classe (equivalente à primeira)", required = true) String class2) {

    log.debug("Criando EquivalentClasses: {} equivalente a {}", class1, class2);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "EquivalentClasses(" + class1 + " " + class2 + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de EquivalentClasses: {}", result);
    return result;
  }
}
