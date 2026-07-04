package com.ia.core.owl.service.tool.datatype;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de complemento de tipo de dado OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owl*service para criar complementos de tipos de dados
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa complemento de tipo de dado (¬DR).
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class DataComplementOfTool extends OwlConstructorTool {

  public DataComplementOfTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria um complemento de tipo de dado na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param resultDatatype Nome do tipo de dados resultante
   * @param datatype Tipo de dados a ser complementado
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria um complemento de tipo de dado OWL 2 DL na ontologia da sessão. " +
                     "Representa complemento de tipo de dado (¬DR). " +
                     "Exemplo: NaoString é complemento de string → Datatype: NaoString equivalentTo DataComplementOf(xsd:string).")
  public String createDataComplementOf(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome do tipo de dados resultante", required = true) String resultDatatype,
      @ToolParam(description = "Tipo de dados a ser complementado", required = true) String datatype) {

    log.debug("Criando DataComplementOf: {} = ¬{}", resultDatatype, datatype);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "Datatype: " + resultDatatype + " equivalentTo DataComplementOf(" + datatype + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owl*service
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de DataComplementOf: {}", result);
    return result;
  }
}
