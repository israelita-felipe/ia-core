package com.ia.core.owl.service.tool.individual;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de asserção negativa de propriedade de dado OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owl*service para criar asserções negativas de propriedade de dado
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa asserção negativa de propriedade de dado (¬U(a, v)).
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class NegativeDataPropertyAssertionTool extends OwlConstructorTool {

  public NegativeDataPropertyAssertionTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma asserção negativa de propriedade de dado na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param individual Indivíduo
   * @param property Propriedade de dado
   * @param value Valor
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma asserção negativa de propriedade de dado OWL 2 DL na ontologia da sessão. " +
                     "Representa asserção negativa de propriedade de dado (¬U(a, v)). " +
                     "Exemplo: João não tem idade 10 → NegativeDataPropertyAssertion(:temIdade :João 10).")
  public String createNegativeDataPropertyAssertion(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Indivíduo", required = true) String individual,
      @ToolParam(description = "Propriedade de dado", required = true) String property,
      @ToolParam(description = "Valor", required = true) String value) {

    log.debug("Criando NegativeDataPropertyAssertion: ¬{} {} {}", property, individual, value);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "NegativeDataPropertyAssertion: " + property + " " + individual + " " + value;

    // Usa OwlConstructorTool.createAxiom para adicionar via owl*service
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de NegativeDataPropertyAssertion: {}", result);
    return result;
  }
}
