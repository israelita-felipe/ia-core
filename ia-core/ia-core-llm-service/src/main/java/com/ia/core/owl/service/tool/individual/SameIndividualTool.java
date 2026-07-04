package com.ia.core.owl.service.tool.individual;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de indivíduos iguais OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owl*service para criar axiomas de indivíduos iguais
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa indivíduos iguais (a₁ = a₂).
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class SameIndividualTool extends OwlConstructorTool {

  public SameIndividualTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria um axioma de indivíduos iguais na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param individual1 Primeiro indivíduo
   * @param individual2 Segundo indivíduo
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria um axioma de indivíduos iguais OWL 2 DL na ontologia da sessão. " +
                     "Representa indivíduos iguais (a₁ = a₂). " +
                     "Exemplo: João é o mesmo que John → SameIndividual(:João :John).")
  public String createSameIndividual(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Primeiro indivíduo", required = true) String individual1,
      @ToolParam(description = "Segundo indivíduo", required = true) String individual2) {

    log.debug("Criando SameIndividual: {} = {}", individual1, individual2);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SameIndividual: " + individual1 + " " + individual2;

    // Usa OwlConstructorTool.createAxiom para adicionar via owl*service
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de SameIndividual: {}", result);
    return result;
  }
}
