package com.ia.core.owl.service.tool.dataproperty;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de axiomas EquivalentDataProperties OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar propriedades de dado equivalentes
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa propriedades de dado equivalentes (sinônimos ou idênticas).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * EquivalentDataProperties é um axioma que declara que duas propriedades de dado têm a mesma
 * extensão, ou seja, relacionam as mesmas instâncias aos mesmos valores literais. É usado para
 * definir sinônimos, propriedades idênticas ou diferentes nomes para o mesmo conceito de atributo.
 * <p>
 * <b>Sintaxe Manchester:</b> EquivalentDataProperties(:Propriedade1 :Propriedade2)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>email e enderecoEmail são equivalentes:
 *       EquivalentDataProperties(:email :enderecoEmail)</li>
 *   <li>idadeEmAnos e idade são equivalentes:
 *       EquivalentDataProperties(:idadeEmAnos :idade)</li>
 *   <li>nomeCompleto e nome representam o mesmo conceito:
 *       EquivalentDataProperties(:nomeCompleto :nome)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class EquivalentDataPropertiesTool extends OwlConstructorTool {

  public EquivalentDataPropertiesTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria um axioma EquivalentDataProperties na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param property1 Primeira propriedade de dado
   * @param property2 Segunda propriedade de dado (equivalente à primeira)
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria um axioma EquivalentDataProperties OWL 2 DL na ontologia da sessão. " +
                     "Declara que duas propriedades de dado são equivalentes (têm a mesma extensão/relacionam as mesmas instâncias aos mesmos valores). " +
                     "É usado para definir sinônimos, propriedades idênticas ou diferentes nomes para o mesmo conceito de atributo. " +
                     "Exemplos: " +
                     "1) email e enderecoEmail são equivalentes → EquivalentDataProperties(:email :enderecoEmail). " +
                     "2) idadeEmAnos e idade são equivalentes → EquivalentDataProperties(:idadeEmAnos :idade). " +
                     "3) nomeCompleto e nome representam o mesmo conceito → EquivalentDataProperties(:nomeCompleto :nome). " +
                     "Útil para definir sinônimos ou propriedades de dado idênticas.")
  public String createEquivalentDataProperties(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Primeira propriedade de dado", required = true) String property1,
      @ToolParam(description = "Segunda propriedade de dado (equivalente à primeira)", required = true) String property2) {

    log.debug("Criando EquivalentDataProperties: {} equivalente a {}", property1, property2);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "EquivalentDataProperties(" + property1 + " " + property2 + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de EquivalentDataProperties: {}", result);
    return result;
  }
}
