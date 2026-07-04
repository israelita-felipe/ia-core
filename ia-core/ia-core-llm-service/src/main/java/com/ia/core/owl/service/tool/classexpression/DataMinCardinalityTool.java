package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de cardinalidade mínima de dado OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar cardinalidades mínimas de dado
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa cardinalidade mínima não qualificada sobre dados (≥ n U).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * DataMinCardinality é um construtor de restrição de classe que indica que instâncias da classe
 * devem ter pelo menos n valores de uma propriedade de dado. É uma restrição de cardinalidade
 * que garante um número mínimo de valores de dados.
 * <p>
 * <b>Sintaxe Manchester:</b> SubClassOf(:Classe DataMinCardinality(n :Propriedade))
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>Usuário com e-mail tem pelo menos 1 e-mail:
 *       SubClassOf(:UsuarioComEmail DataMinCardinality(1 :temEmail))</li>
 *   <li>Pessoa contatável possui no mínimo 2 números de telefone:
 *       EquivalentTo(:PessoaContatavel ObjectIntersectionOf(:Pessoa DataMinCardinality(2 :temTelefone)))</li>
 *   <li>Candidato a emprego forneceu pelo menos 3 referências:
 *       SubClassOf(:CandidatoComReferencias DataMinCardinality(3 :temReferencia))</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class DataMinCardinalityTool extends OwlConstructorTool {

  public DataMinCardinalityTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma cardinalidade mínima não qualificada de dado na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe que tem a restrição
   * @param cardinality Cardinalidade mínima (n)
   * @param property Propriedade de dado
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma cardinalidade mínima não qualificada de dado OWL 2 DL na ontologia da sessão. " +
                     "Representa cardinalidade mínima não qualificada sobre dados (≥ n U). " +
                     "Indica que instâncias da classe devem ter pelo menos n valores de uma propriedade de dado. " +
                     "Exemplos: " +
                     "1) Usuário com e-mail tem pelo menos 1 e-mail → SubClassOf(:UsuarioComEmail DataMinCardinality(1 :temEmail)). " +
                     "2) Pessoa contatável possui no mínimo 2 números de telefone → EquivalentTo(:PessoaContatavel ObjectIntersectionOf(:Pessoa DataMinCardinality(2 :temTelefone))). " +
                     "3) Candidato a emprego forneceu pelo menos 3 referências → SubClassOf(:CandidatoComReferencias DataMinCardinality(3 :temReferencia)).")
  public String createDataMinCardinality(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe que tem a restrição", required = true) String className,
      @ToolParam(description = "Cardinalidade mínima (n)", required = true) int cardinality,
      @ToolParam(description = "Propriedade de dado", required = true) String property) {

    log.debug("Criando DataMinCardinality: {} ≥ {} {}", className, cardinality, property);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SubClassOf: " + className + " DataMinCardinality(" + cardinality + " " + property + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de DataMinCardinality: {}", result);
    return result;
  }

  /**
   * Cria uma cardinalidade mínima qualificada de dado na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe que tem a restrição
   * @param cardinality Cardinalidade mínima (n)
   * @param property Propriedade de dado
   * @param datatype Tipo de dado alvo da restrição
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma cardinalidade mínima qualificada de dado OWL 2 DL na ontologia da sessão. " +
                     "Representa cardinalidade mínima qualificada sobre dados (≥ n U.DR). " +
                     "Exemplo: Pessoa tem pelo menos 1 nome que é string → SubClassOf(:Pessoa DataMinCardinality(1 :temNome xsd:string)).")
  public String createDataMinCardinalityQualified(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe que tem a restrição", required = true) String className,
      @ToolParam(description = "Cardinalidade mínima (n)", required = true) int cardinality,
      @ToolParam(description = "Propriedade de dado", required = true) String property,
      @ToolParam(description = "Tipo de dado alvo da restrição", required = true) String datatype) {

    log.debug("Criando DataMinCardinality qualificado: {} ≥ {} {}.{}", className, cardinality, property, datatype);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SubClassOf: " + className + " DataMinCardinality(" + cardinality + " " + property + " " + datatype + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de DataMinCardinality qualificado: {}", result);
    return result;
  }
}
