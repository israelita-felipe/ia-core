package com.ia.core.owl.service.tool.individual;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de asserção negativa de propriedade de objeto OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owl*service para criar asserções negativas de propriedade de objeto
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa asserção negativa de propriedade de objeto (¬R(a, b)).
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class NegativeObjectPropertyAssertionTool extends OwlConstructorTool {

  public NegativeObjectPropertyAssertionTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma asserção negativa de propriedade de objeto na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param individual1 Primeiro indivíduo
   * @param property Propriedade de objeto
   * @param individual2 Segundo indivíduo
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma asserção negativa de propriedade de objeto OWL 2 DL na ontologia da sessão. " +
                     "Representa asserção negativa de propriedade de objeto (¬R(a, b)). " +
                     "Exemplo: João não tem filho Maria → NegativeObjectPropertyAssertion(:temFilho :João :Maria).")
  public String createNegativeObjectPropertyAssertion(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Primeiro indivíduo", required = true) String individual1,
      @ToolParam(description = "Propriedade de objeto", required = true) String property,
      @ToolParam(description = "Segundo indivíduo", required = true) String individual2) {

    log.debug("Criando NegativeObjectPropertyAssertion: ¬{} {} {}", property, individual1, individual2);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "NegativeObjectPropertyAssertion: " + property + " " + individual1 + " " + individual2;

    // Usa OwlConstructorTool.createAxiom para adicionar via owl*service
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de NegativeObjectPropertyAssertion: {}", result);
    return result;
  }
}
