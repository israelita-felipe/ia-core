package com.ia.core.owl.service.tool.individual;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de asserção de propriedade de dado OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owl*service para criar asserções de propriedade de dado
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa asserção de propriedade de dado (U(a, v)).
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class DataPropertyAssertionTool extends OwlConstructorTool {

  public DataPropertyAssertionTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma asserção de propriedade de dado na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param individual Indivíduo
   * @param property Propriedade de dado
   * @param value Valor
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma asserção de propriedade de dado OWL 2 DL na ontologia da sessão. " +
                     "Representa asserção de propriedade de dado (U(a, v)). " +
                     "Exemplo: João tem idade 30 → DataPropertyAssertion(:João :idade 30).")
  public String createDataPropertyAssertion(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Indivíduo", required = true) String individual,
      @ToolParam(description = "Propriedade de dado", required = true) String property,
      @ToolParam(description = "Valor", required = true) String value) {

    log.debug("Criando DataPropertyAssertion: {} {} {}", individual, property, value);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "DataPropertyAssertion: " + property + " " + individual + " " + value;

    // Usa OwlConstructorTool.createAxiom para adicionar via owl*service
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de DataPropertyAssertion: {}", result);
    return result;
  }
}
