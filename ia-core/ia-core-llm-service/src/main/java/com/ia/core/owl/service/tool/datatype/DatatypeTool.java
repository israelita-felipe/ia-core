package com.ia.core.owl.service.tool.datatype;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para declaração de tipos de dados OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owl*service para declarar tipos de dados
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa declaração de tipo de dados (Datatype).
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class DatatypeTool extends OwlConstructorTool {

  public DatatypeTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Declara um tipo de dados na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param datatypeName Nome do tipo de dados
   * @param baseDatatype Tipo de dados base (ex: xsd:string, xsd:integer)
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Declara um tipo de dados OWL 2 DL na ontologia da sessão. " +
                     "Representa declaração de tipo de dados (Datatype). " +
                     "Exemplo: IdadeInteiro é um tipo de dado baseado em xsd:integer → Datatype: IdadeInteiro equivalentTo xsd:integer.")
  public String declareDatatype(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome do tipo de dados", required = true) String datatypeName,
      @ToolParam(description = "Tipo de dados base (ex: xsd:string, xsd:integer)", required = true) String baseDatatype) {

    log.debug("Declarando Datatype: {} = {}", datatypeName, baseDatatype);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "Datatype: " + datatypeName + " equivalentTo " + baseDatatype;

    // Usa OwlConstructorTool.createAxiom para adicionar via owl*service
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da declaração de Datatype: {}", result);
    return result;
  }
}
