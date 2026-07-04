package com.ia.core.owl.service.tool.datatype;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de restrição de tipo de dado OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owl*service para criar restrições de tipos de dados
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa restrição de tipo de dado com facetas (DR[facet]).
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class DatatypeRestrictionTool extends OwlConstructorTool {

  public DatatypeRestrictionTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma restrição de tipo de dado na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param resultDatatype Nome do tipo de dados resultante
   * @param baseDatatype Tipo de dados base
   * @param facet Faceta da restrição (ex: >=, <=, minInclusive, maxInclusive)
   * @param value Valor da restrição
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma restrição de tipo de dado OWL 2 DL na ontologia da sessão. " +
                     "Representa restrição de tipo de dado com facetas (DR[facet]). " +
                     "Exemplo: IdadeAdulto é integer >= 18 → Datatype: IdadeAdulto equivalentTo xsd:integer[>= 18].")
  public String createDatatypeRestriction(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome do tipo de dados resultante", required = true) String resultDatatype,
      @ToolParam(description = "Tipo de dados base", required = true) String baseDatatype,
      @ToolParam(description = "Faceta da restrição (ex: >=, <=, minInclusive, maxInclusive)", required = true) String facet,
      @ToolParam(description = "Valor da restrição", required = true) String value) {

    log.debug("Criando DatatypeRestriction: {} = {}[{} {}]", resultDatatype, baseDatatype, facet, value);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "Datatype: " + resultDatatype + " equivalentTo " + baseDatatype + "[" + facet + " " + value + "]";

    // Usa OwlConstructorTool.createAxiom para adicionar via owl*service
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de DatatypeRestriction: {}", result);
    return result;
  }
}
