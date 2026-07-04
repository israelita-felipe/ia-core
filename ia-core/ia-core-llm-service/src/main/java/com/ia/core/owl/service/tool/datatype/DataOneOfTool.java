package com.ia.core.owl.service.tool.datatype;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Tool para criação de enumeração de valores de dados OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owl*service para criar enumerações de valores
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa enumeração de valores ({v₁, v₂, ..., vₙ}).
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class DataOneOfTool extends OwlConstructorTool {

  public DataOneOfTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma enumeração de valores de dados na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param resultDatatype Nome do tipo de dados resultante
   * @param values Lista de valores da enumeração
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma enumeração de valores de dados OWL 2 DL na ontologia da sessão. " +
                     "Representa enumeração de valores ({v₁, v₂, ..., vₙ}). " +
                     "Exemplo: DiasDaSemana = {Segunda, Terca, Quarta, Quinta, Sexta, Sabado, Domingo} → Datatype: DiasDaSemana equivalentTo DataOneOf(\"Segunda\" \"Terca\" \"Quarta\" \"Quinta\" \"Sexta\" \"Sabado\" \"Domingo\").")
  public String createDataOneOf(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome do tipo de dados resultante", required = true) String resultDatatype,
      @ToolParam(description = "Lista de valores da enumeração (mínimo 1)", required = true) List<String> values) {

    log.debug("Criando DataOneOf: {} = {{{}}}", resultDatatype, String.join(", ", values));

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "Datatype: " + resultDatatype + " equivalentTo DataOneOf(" + String.join(" ", values) + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owl*service
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de DataOneOf: {}", result);
    return result;
  }
}
