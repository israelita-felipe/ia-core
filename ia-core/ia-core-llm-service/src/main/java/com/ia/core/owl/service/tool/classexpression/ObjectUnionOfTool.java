package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Tool para criação de união de classes OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owl*service para criar uniões
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa a união (OU) de duas ou mais classes.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class ObjectUnionOfTool extends OwlConstructorTool {

  public ObjectUnionOfTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma união de classes na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param resultClassName Nome da classe resultante da união
   * @param classes Lista de classes a serem unidas
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma união de classes OWL 2 DL na ontologia da sessão. " +
                     "Representa a união (OU) de duas ou mais classes. " +
                     "Exemplo: Veiculo = Carro OR Moto OR Caminhao.")
  public String createObjectUnionOf(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe resultante da união", required = true) String resultClassName,
      @ToolParam(description = "Lista de classes a serem unidas (mínimo 2)", required = true) List<String> classes) {

    log.debug("Criando ObjectUnionOf: {} = {}", resultClassName, String.join(" or ", classes));

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "EquivalentClasses: " + resultClassName + " " + String.join(" or ", classes);

    // Usa OwlConstructorTool.createAxiom para adicionar via owl*service
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de ObjectUnionOf: {}", result);
    return result;
  }
}
