package com.ia.core.owl.service.tool.individual;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de asserção de propriedade de objeto OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owl*service para criar asserções de propriedade de objeto
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa asserção de propriedade de objeto (R(a, b)).
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class ObjectPropertyAssertionTool extends OwlConstructorTool {

  public ObjectPropertyAssertionTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma asserção de propriedade de objeto na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param individual1 Primeiro indivíduo
   * @param property Propriedade de objeto
   * @param individual2 Segundo indivíduo
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma asserção de propriedade de objeto OWL 2 DL na ontologia da sessão. " +
                     "Representa asserção de propriedade de objeto (R(a, b)). " +
                     "Exemplo: João é pai de Maria → ObjectPropertyAssertion(:João :temPai :Maria).")
  public String createObjectPropertyAssertion(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Primeiro indivíduo", required = true) String individual1,
      @ToolParam(description = "Propriedade de objeto", required = true) String property,
      @ToolParam(description = "Segundo indivíduo", required = true) String individual2) {

    log.debug("Criando ObjectPropertyAssertion: {} {} {}", individual1, property, individual2);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "ObjectPropertyAssertion: " + property + " " + individual1 + " " + individual2;

    // Usa OwlConstructorTool.createAxiom para adicionar via owl*service
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de ObjectPropertyAssertion: {}", result);
    return result;
  }
}
