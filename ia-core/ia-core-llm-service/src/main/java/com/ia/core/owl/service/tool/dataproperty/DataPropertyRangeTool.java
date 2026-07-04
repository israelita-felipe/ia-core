package com.ia.core.owl.service.tool.dataproperty;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de range de propriedade de dado OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar ranges de propriedades de dado
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa range de propriedade de dado (Range(U) ⊆ DR).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * DataPropertyRange é um axioma que especifica que se uma propriedade de dado U relaciona um indivíduo x a um valor v,
 * então v deve ser uma instância do tipo de dado DR. Restringe o range da propriedade de dado a um tipo de dado específico.
 * <p>
 * <b>Sintaxe Manchester:</b> DataPropertyRange(:Propriedade :TipoDado)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>idade é xsd:integer:
 *       DataPropertyRange(:idade xsd:integer)</li>
 *   <li>nome é xsd:string:
 *       DataPropertyRange(:nome xsd:string)</li>
 *   <li>preço é xsd:decimal:
 *       DataPropertyRange(:preço xsd:decimal)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class DataPropertyRangeTool extends OwlConstructorTool {

  public DataPropertyRangeTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria um range de propriedade de dado na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param property Propriedade de dado
   * @param range Tipo de dado range
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria um range de propriedade de dado OWL 2 DL na ontologia da sessão. " +
                     "Representa range de propriedade de dado (Range(U) ⊆ DR). " +
                     "Especifica que se uma propriedade de dado relaciona um indivíduo x a um valor v, então v deve ser uma instância do tipo de dado especificado. " +
                     "Exemplos: " +
                     "1) idade é xsd:integer → DataPropertyRange(:idade xsd:integer). " +
                     "2) nome é xsd:string → DataPropertyRange(:nome xsd:string). " +
                     "3) preço é xsd:decimal → DataPropertyRange(:preço xsd:decimal).")
  public String createDataPropertyRange(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Propriedade de dado", required = true) String property,
      @ToolParam(description = "Tipo de dado range", required = true) String range) {

    log.debug("Criando DataPropertyRange: Range({}) ⊆ {}", property, range);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "DataPropertyRange: " + property + " " + range;

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de DataPropertyRange: {}", result);
    return result;
  }
}
