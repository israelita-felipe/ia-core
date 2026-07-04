package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Tool para criação de interseção de classes OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owl*service para criar interseções
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa a interseção (E) de duas ou mais classes.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class ObjectIntersectionOfTool extends OwlConstructorTool {

  public ObjectIntersectionOfTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma interseção de classes na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param resultClassName Nome da classe resultante da interseção
   * @param classes Lista de classes a serem intersectadas
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma interseção de classes OWL 2 DL na ontologia da sessão. " +
                     "Representa a interseção (E) de duas ou mais classes. " +
                     "Exemplo: Adulto = Pessoa AND (temIdade >= 18).")
  public String createObjectIntersectionOf(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe resultante da interseção", required = true) String resultClassName,
      @ToolParam(description = "Lista de classes a serem intersectadas (mínimo 2)", required = true) List<String> classes) {

    log.debug("Criando ObjectIntersectionOf: {} = {}", resultClassName, String.join(" and ", classes));

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "EquivalentClasses: " + resultClassName + " " + String.join(" and ", classes);

    // Usa OwlConstructorTool.createAxiom para adicionar via owl*service
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de ObjectIntersectionOf: {}", result);
    return result;
  }
}
