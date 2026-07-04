package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de cardinalidade máxima OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar cardinalidades máximas
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa cardinalidade máxima não qualificada (≤ n R).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * ObjectMaxCardinality é um construtor de restrição de classe que indica que instâncias da classe
 * devem ter no máximo n valores de uma propriedade de objeto. É uma restrição de cardinalidade
 * que limita o número máximo de relacionamentos.
 * <p>
 * <b>Sintaxe Manchester:</b> SubClassOf(:Classe ObjectMaxCardinality(n :Propriedade))
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>Pessoa monogâmica tem no máximo 1 cônjuge:
 *       SubClassOf(:PessoaMonogamica ObjectMaxCardinality(1 :temConjuge))</li>
 *   <li>Gerente de equipe pequena gerencia no máximo 10 funcionários:
 *       EquivalentTo(:GerenteEquipePequena ObjectIntersectionOf(:Gerente ObjectMaxCardinality(10 :gerencia)))</li>
 *   <li>Motorista particular possui no máximo 2 carros:
 *       SubClassOf(:MotoristaParticular ObjectMaxCardinality(2 :possuiCarro))</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class ObjectMaxCardinalityTool extends OwlConstructorTool {

  public ObjectMaxCardinalityTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma cardinalidade máxima não qualificada na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe que tem a restrição
   * @param cardinality Cardinalidade máxima (n)
   * @param property Propriedade de objeto
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma cardinalidade máxima não qualificada OWL 2 DL na ontologia da sessão. " +
                     "Representa cardinalidade máxima não qualificada (≤ n R). " +
                     "Indica que instâncias da classe devem ter no máximo n valores de uma propriedade de objeto. " +
                     "Exemplos: " +
                     "1) Pessoa monogâmica tem no máximo 1 cônjuge → SubClassOf(:PessoaMonogamica ObjectMaxCardinality(1 :temConjuge)). " +
                     "2) Gerente de equipe pequena gerencia no máximo 10 funcionários → EquivalentTo(:GerenteEquipePequena ObjectIntersectionOf(:Gerente ObjectMaxCardinality(10 :gerencia))). " +
                     "3) Motorista particular possui no máximo 2 carros → SubClassOf(:MotoristaParticular ObjectMaxCardinality(2 :possuiCarro)).")
  public String createObjectMaxCardinality(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe que tem a restrição", required = true) String className,
      @ToolParam(description = "Cardinalidade máxima (n)", required = true) int cardinality,
      @ToolParam(description = "Propriedade de objeto", required = true) String property) {

    log.debug("Criando ObjectMaxCardinality: {} ≤ {} {}", className, cardinality, property);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SubClassOf: " + className + " ObjectMaxCardinality(" + cardinality + " " + property + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de ObjectMaxCardinality: {}", result);
    return result;
  }

  /**
   * Cria uma cardinalidade máxima qualificada na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe que tem a restrição
   * @param cardinality Cardinalidade máxima (n)
   * @param property Propriedade de objeto
   * @param targetClass Classe alvo da restrição
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma cardinalidade máxima qualificada OWL 2 DL na ontologia da sessão. " +
                     "Representa cardinalidade máxima qualificada (≤ n R.C). " +
                     "Exemplo: Pessoa tem no máximo 5 amigos que são Pessoa → SubClassOf(:Pessoa ObjectMaxCardinality(5 :temAmigo :Pessoa)).")
  public String createObjectMaxCardinalityQualified(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe que tem a restrição", required = true) String className,
      @ToolParam(description = "Cardinalidade máxima (n)", required = true) int cardinality,
      @ToolParam(description = "Propriedade de objeto", required = true) String property,
      @ToolParam(description = "Classe alvo da restrição", required = true) String targetClass) {

    log.debug("Criando ObjectMaxCardinality qualificado: {} ≤ {} {}.{}", className, cardinality, property, targetClass);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SubClassOf: " + className + " ObjectMaxCardinality(" + cardinality + " " + property + " " + targetClass + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de ObjectMaxCardinality qualificado: {}", result);
    return result;
  }
}
