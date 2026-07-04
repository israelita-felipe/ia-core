package com.ia.core.owl.service.tool.datatype;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Tool para criação de interseção de tipos de dados OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owl*service para criar interseções de tipos de dados
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa interseção de tipos de dados (DR₁ ∩ DR₂ ∩ ... ∩ DRₙ).
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class DataIntersectionOfTool extends OwlConstructorTool {

  public DataIntersectionOfTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma interseção de tipos de dados na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param resultDatatype Nome do tipo de dados resultante
   * @param datatypes Lista de tipos de dados a serem intersectados
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma interseção de tipos de dados OWL 2 DL na ontologia da sessão. " +
                     "Representa interseção de tipos de dados (DR₁ ∩ DR₂ ∩ ... ∩ DRₙ). " +
                     "Exemplo: StringOuInteiro = string ∩ integer → Datatype: StringOuInteiro equivalentTo DataIntersectionOf(xsd:string xsd:integer).")
  public String createDataIntersectionOf(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome do tipo de dados resultante", required = true) String resultDatatype,
      @ToolParam(description = "Lista de tipos de dados a serem intersectados (mínimo 2)", required = true) List<String> datatypes) {

    log.debug("Criando DataIntersectionOf: {} = {}", resultDatatype, String.join(" ∩ ", datatypes));

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "Datatype: " + resultDatatype + " equivalentTo DataIntersectionOf(" + String.join(" ", datatypes) + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owl*service
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de DataIntersectionOf: {}", result);
    return result;
  }
}
