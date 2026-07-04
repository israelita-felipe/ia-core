package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de cardinalidade exata de dado OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar cardinalidades exatas de dado
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa cardinalidade exata não qualificada sobre dados (= n U).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * DataExactCardinality é um construtor de restrição de classe que indica que instâncias da classe
 * devem ter exatamente n valores de uma propriedade de dado. É uma restrição de cardinalidade
 * que especifica um número exato de valores de dados.
 * <p>
 * <b>Sintaxe Manchester:</b> SubClassOf(:Classe DataExactCardinality(n :Propriedade))
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>Pessoa física tem exatamente 1 data de nascimento:
 *       SubClassOf(:PessoaFisica DataExactCardinality(1 :dataNascimento))</li>
 *   <li>Nome composto brasileiro possui exatamente 2 sobrenomes:
 *       EquivalentTo(:NomeCompostoBrasileiro DataExactCardinality(2 :temSobrenome))</li>
 *   <li>Produto com código EAN-13 tem exatamente 13 dígitos no código de barras:
 *       SubClassOf(:ProdutoEAN13 DataExactCardinality(13 :temCodigoBarras))</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class DataExactCardinalityTool extends OwlConstructorTool {

  public DataExactCardinalityTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma cardinalidade exata não qualificada de dado na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe que tem a restrição
   * @param cardinality Cardinalidade exata (n)
   * @param property Propriedade de dado
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma cardinalidade exata não qualificada de dado OWL 2 DL na ontologia da sessão. " +
                     "Representa cardinalidade exata não qualificada sobre dados (= n U). " +
                     "Indica que instâncias da classe devem ter exatamente n valores de uma propriedade de dado. " +
                     "Exemplos: " +
                     "1) Pessoa física tem exatamente 1 data de nascimento → SubClassOf(:PessoaFisica DataExactCardinality(1 :dataNascimento)). " +
                     "2) Nome composto brasileiro possui exatamente 2 sobrenomes → EquivalentTo(:NomeCompostoBrasileiro DataExactCardinality(2 :temSobrenome)). " +
                     "3) Produto com código EAN-13 tem exatamente 13 dígitos no código de barras → SubClassOf(:ProdutoEAN13 DataExactCardinality(13 :temCodigoBarras)).")
  public String createDataExactCardinality(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe que tem a restrição", required = true) String className,
      @ToolParam(description = "Cardinalidade exata (n)", required = true) int cardinality,
      @ToolParam(description = "Propriedade de dado", required = true) String property) {

    log.debug("Criando DataExactCardinality: {} = {} {}", className, cardinality, property);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SubClassOf: " + className + " DataExactCardinality(" + cardinality + " " + property + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de DataExactCardinality: {}", result);
    return result;
  }

  /**
   * Cria uma cardinalidade exata qualificada de dado na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe que tem a restrição
   * @param cardinality Cardinalidade exata (n)
   * @param property Propriedade de dado
   * @param datatype Tipo de dado alvo da restrição
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma cardinalidade exata qualificada de dado OWL 2 DL na ontologia da sessão. " +
                     "Representa cardinalidade exata qualificada sobre dados (= n U.DR). " +
                     "Exemplo: Pessoa tem exatamente 1 email que é string → SubClassOf(:Pessoa DataExactCardinality(1 :temEmail xsd:string)).")
  public String createDataExactCardinalityQualified(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe que tem a restrição", required = true) String className,
      @ToolParam(description = "Cardinalidade exata (n)", required = true) int cardinality,
      @ToolParam(description = "Propriedade de dado", required = true) String property,
      @ToolParam(description = "Tipo de dado alvo da restrição", required = true) String datatype) {

    log.debug("Criando DataExactCardinality qualificado: {} = {} {}.{}", className, cardinality, property, datatype);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SubClassOf: " + className + " DataExactCardinality(" + cardinality + " " + property + " " + datatype + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de DataExactCardinality qualificado: {}", result);
    return result;
  }
}
