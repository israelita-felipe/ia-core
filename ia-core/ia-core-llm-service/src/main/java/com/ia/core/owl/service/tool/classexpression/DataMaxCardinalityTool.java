package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de cardinalidade máxima de dado OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar cardinalidades máximas de dado
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa cardinalidade máxima não qualificada sobre dados (≤ n U).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * DataMaxCardinality é um construtor de restrição de classe que indica que instâncias da classe
 * devem ter no máximo n valores de uma propriedade de dado. É uma restrição de cardinalidade
 * que limita o número máximo de valores de dados.
 * <p>
 * <b>Sintaxe Manchester:</b> SubClassOf(:Classe DataMaxCardinality(n :Propriedade))
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>Perfil básico tem no máximo 3 apelidos:
 *       SubClassOf(:PerfilBasico DataMaxCardinality(3 :temApelido))</li>
 *   <li>Segurado comum pode ter no máximo 5 dependentes:
 *       EquivalentTo(:SeguradoComum ObjectIntersectionOf(:Segurado DataMaxCardinality(5 :temDependente)))</li>
 *   <li>Viajante frequente possui no máximo 1 número de passaporte:
 *       SubClassOf(:ViajanteFrequente DataMaxCardinality(1 :temPassaporte))</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class DataMaxCardinalityTool extends OwlConstructorTool {

  public DataMaxCardinalityTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma cardinalidade máxima não qualificada de dado na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe que tem a restrição
   * @param cardinality Cardinalidade máxima (n)
   * @param property Propriedade de dado
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma cardinalidade máxima não qualificada de dado OWL 2 DL na ontologia da sessão. " +
                     "Representa cardinalidade máxima não qualificada sobre dados (≤ n U). " +
                     "Indica que instâncias da classe devem ter no máximo n valores de uma propriedade de dado. " +
                     "Exemplos: " +
                     "1) Perfil básico tem no máximo 3 apelidos → SubClassOf(:PerfilBasico DataMaxCardinality(3 :temApelido)). " +
                     "2) Segurado comum pode ter no máximo 5 dependentes → EquivalentTo(:SeguradoComum ObjectIntersectionOf(:Segurado DataMaxCardinality(5 :temDependente))). " +
                     "3) Viajante frequente possui no máximo 1 número de passaporte → SubClassOf(:ViajanteFrequente DataMaxCardinality(1 :temPassaporte)).")
  public String createDataMaxCardinality(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe que tem a restrição", required = true) String className,
      @ToolParam(description = "Cardinalidade máxima (n)", required = true) int cardinality,
      @ToolParam(description = "Propriedade de dado", required = true) String property) {

    log.debug("Criando DataMaxCardinality: {} ≤ {} {}", className, cardinality, property);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SubClassOf: " + className + " DataMaxCardinality(" + cardinality + " " + property + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de DataMaxCardinality: {}", result);
    return result;
  }

  /**
   * Cria uma cardinalidade máxima qualificada de dado na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe que tem a restrição
   * @param cardinality Cardinalidade máxima (n)
   * @param property Propriedade de dado
   * @param datatype Tipo de dado alvo da restrição
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma cardinalidade máxima qualificada de dado OWL 2 DL na ontologia da sessão. " +
                     "Representa cardinalidade máxima qualificada sobre dados (≤ n U.DR). " +
                     "Exemplo: Pessoa tem no máximo 1 CPF que é string → SubClassOf(:Pessoa DataMaxCardinality(1 :temCPF xsd:string)).")
  public String createDataMaxCardinalityQualified(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe que tem a restrição", required = true) String className,
      @ToolParam(description = "Cardinalidade máxima (n)", required = true) int cardinality,
      @ToolParam(description = "Propriedade de dado", required = true) String property,
      @ToolParam(description = "Tipo de dado alvo da restrição", required = true) String datatype) {

    log.debug("Criando DataMaxCardinality qualificado: {} ≤ {} {}.{}", className, cardinality, property, datatype);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SubClassOf: " + className + " DataMaxCardinality(" + cardinality + " " + property + " " + datatype + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de DataMaxCardinality qualificado: {}", result);
    return result;
  }
}
