package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de complemento de classe OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owl*service para criar complementos
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa o complemento (NÃO) de uma classe.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class ObjectComplementOfTool extends OwlConstructorTool {

  public ObjectComplementOfTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria um complemento de classe na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param resultClassName Nome da classe resultante do complemento
   * @param className Classe a ser complementada
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria um complemento de classe OWL 2 DL na ontologia da sessão. " +
                     "Representa o complemento (NÃO) de uma classe. " +
                     "Exemplo: NaoAdulto = NOT Adulto.")
  public String createObjectComplementOf(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe resultante do complemento", required = true) String resultClassName,
      @ToolParam(description = "Classe a ser complementada", required = true) String className) {

    log.debug("Criando ObjectComplementOf: {} = NOT {}", resultClassName, className);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "EquivalentClasses: " + resultClassName + " NOT " + className;

    // Usa OwlConstructorTool.createAxiom para adicionar via owl*service
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de ObjectComplementOf: {}", result);
    return result;
  }
}
