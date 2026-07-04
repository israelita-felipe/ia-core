package com.ia.core.owl.service.tool.individual;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de asserção de classe OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owl*service para criar asserções de classe
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa asserção de classe (C(a)).
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class ClassAssertionTool extends OwlConstructorTool {

  public ClassAssertionTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma asserção de classe na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param individual Indivíduo
   * @param cls Classe
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma asserção de classe OWL 2 DL na ontologia da sessão. " +
                     "Representa asserção de classe (C(a)). " +
                     "Exemplo: João é uma Pessoa → ClassAssertion(:João :Pessoa).")
  public String createClassAssertion(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Indivíduo", required = true) String individual,
      @ToolParam(description = "Classe", required = true) String cls) {

    log.debug("Criando ClassAssertion: {} ∈ {}", individual, cls);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "ClassAssertion: " + cls + " " + individual;

    // Usa OwlConstructorTool.createAxiom para adicionar via owl*service
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de ClassAssertion: {}", result);
    return result;
  }
}
