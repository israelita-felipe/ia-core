package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de cardinalidade exata OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar cardinalidades exatas
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa cardinalidade exata não qualificada (= n R).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * ObjectExactCardinality é um construtor de restrição de classe que indica que instâncias da classe
 * devem ter exatamente n valores de uma propriedade de objeto. É uma restrição de cardinalidade
 * que especifica um número exato de relacionamentos.
 * <p>
 * <b>Sintaxe Manchester:</b> SubClassOf(:Classe ObjectExactCardinality(n :Propriedade))
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>Ser humano tem exatamente 2 pais biológicos:
 *       SubClassOf(:SerHumano ObjectExactCardinality(2 :temPaisBiologicos))</li>
 *   <li>Carro comum tem exatamente 4 rodas:
 *       EquivalentTo(:CarroComum ObjectIntersectionOf(:Carro ObjectExactCardinality(4 :temRoda)))</li>
 *   <li>Cidadão brasileiro reside em exatamente 1 país (Brasil):
 *       SubClassOf(:CidadaoBrasileiro ObjectExactCardinality(1 :resideEm))</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class ObjectExactCardinalityTool extends OwlConstructorTool {

  public ObjectExactCardinalityTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma cardinalidade exata não qualificada na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe que tem a restrição
   * @param cardinality Cardinalidade exata (n)
   * @param property Propriedade de objeto
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma cardinalidade exata não qualificada OWL 2 DL na ontologia da sessão. " +
                     "Representa cardinalidade exata não qualificada (= n R). " +
                     "Indica que instâncias da classe devem ter exatamente n valores de uma propriedade de objeto. " +
                     "Exemplos: " +
                     "1) Ser humano tem exatamente 2 pais biológicos → SubClassOf(:SerHumano ObjectExactCardinality(2 :temPaisBiologicos)). " +
                     "2) Carro comum tem exatamente 4 rodas → EquivalentTo(:CarroComum ObjectIntersectionOf(:Carro ObjectExactCardinality(4 :temRoda))). " +
                     "3) Cidadão brasileiro reside em exatamente 1 país (Brasil) → SubClassOf(:CidadaoBrasileiro ObjectExactCardinality(1 :resideEm)).")
  public String createObjectExactCardinality(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe que tem a restrição", required = true) String className,
      @ToolParam(description = "Cardinalidade exata (n)", required = true) int cardinality,
      @ToolParam(description = "Propriedade de objeto", required = true) String property) {

    log.debug("Criando ObjectExactCardinality: {} = {} {}", className, cardinality, property);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SubClassOf: " + className + " ObjectExactCardinality(" + cardinality + " " + property + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de ObjectExactCardinality: {}", result);
    return result;
  }

  /**
   * Cria uma cardinalidade exata qualificada na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe que tem a restrição
   * @param cardinality Cardinalidade exata (n)
   * @param property Propriedade de objeto
   * @param targetClass Classe alvo da restrição
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma cardinalidade exata qualificada OWL 2 DL na ontologia da sessão. " +
                     "Representa cardinalidade exata qualificada (= n R.C). " +
                     "Exemplo: Casal tem exatamente 2 membros que são Pessoa → SubClassOf(:Casal ObjectExactCardinality(2 :temMembro :Pessoa)).")
  public String createObjectExactCardinalityQualified(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe que tem a restrição", required = true) String className,
      @ToolParam(description = "Cardinalidade exata (n)", required = true) int cardinality,
      @ToolParam(description = "Propriedade de objeto", required = true) String property,
      @ToolParam(description = "Classe alvo da restrição", required = true) String targetClass) {

    log.debug("Criando ObjectExactCardinality qualificado: {} = {} {}.{}", className, cardinality, property, targetClass);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SubClassOf: " + className + " ObjectExactCardinality(" + cardinality + " " + property + " " + targetClass + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de ObjectExactCardinality qualificado: {}", result);
    return result;
  }
}
