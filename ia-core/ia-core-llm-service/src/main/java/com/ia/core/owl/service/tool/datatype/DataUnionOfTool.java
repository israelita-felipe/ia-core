package com.ia.core.owl.service.tool.datatype;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Tool para criação de união de tipos de dados OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owl*service para criar uniões de tipos de dados
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa união de tipos de dados (DR₁ ∪ DR₂ ∪ ... ∪ DRₙ).
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class DataUnionOfTool extends OwlConstructorTool {

  public DataUnionOfTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma união de tipos de dados na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param resultDatatype Nome do tipo de dados resultante
   * @param datatypes Lista de tipos de dados a serem unidos
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma união de tipos de dados OWL 2 DL na ontologia da sessão. " +
                     "Representa união de tipos de dados (DR₁ ∪ DR₂ ∪ ... ∪ DRₙ). " +
                     "Exemplo: StringOuInteiro = string ∪ integer → Datatype: StringOuInteiro equivalentTo DataUnionOf(xsd:string xsd:integer).")
  public String createDataUnionOf(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome do tipo de dados resultante", required = true) String resultDatatype,
      @ToolParam(description = "Lista de tipos de dados a serem unidos (mínimo 2)", required = true) List<String> datatypes) {

    log.debug("Criando DataUnionOf: {} = {}", resultDatatype, String.join(" ∪ ", datatypes));

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "Datatype: " + resultDatatype + " equivalentTo DataUnionOf(" + String.join(" ", datatypes) + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owl*service
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de DataUnionOf: {}", result);
    return result;
  }
}
