package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de cardinalidade mínima OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar cardinalidades mínimas
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa cardinalidade mínima não qualificada (≥ n R).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * ObjectMinCardinality é um construtor de restrição de classe que indica que instâncias da classe
 * devem ter pelo menos n valores de uma propriedade de objeto. É uma restrição de cardinalidade
 * que garante um número mínimo de relacionamentos.
 * <p>
 * <b>Sintaxe Manchester:</b> SubClassOf(:Classe ObjectMinCardinality(n :Propriedade))
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>Pai de família grande tem pelo menos 2 filhos:
 *       SubClassOf(:PaiFamiliaGrande ObjectMinCardinality(2 :temFilho))</li>
 *   <li>Pessoa com múltiplos endereços possui no mínimo 3 endereços:
 *       EquivalentTo(:PessoaMultiEndereco ObjectIntersectionOf(:Pessoa ObjectMinCardinality(3 :temEndereco)))</li>
 *   <li>Empresa com muitos funcionários tem no mínimo 10 funcionários:
 *       SubClassOf(:EmpresaMuitosFuncionarios ObjectMinCardinality(10 :temFuncionario))</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class ObjectMinCardinalityTool extends OwlConstructorTool {

  public ObjectMinCardinalityTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma cardinalidade mínima não qualificada na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe que tem a restrição
   * @param cardinality Cardinalidade mínima (n)
   * @param property Propriedade de objeto
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma cardinalidade mínima não qualificada OWL 2 DL na ontologia da sessão. " +
                     "Representa cardinalidade mínima não qualificada (≥ n R). " +
                     "Indica que instâncias da classe devem ter pelo menos n valores de uma propriedade de objeto. " +
                     "Exemplos: " +
                     "1) Pai de família grande tem pelo menos 2 filhos → SubClassOf(:PaiFamiliaGrande ObjectMinCardinality(2 :temFilho)). " +
                     "2) Pessoa com múltiplos endereços possui no mínimo 3 endereços → EquivalentTo(:PessoaMultiEndereco ObjectIntersectionOf(:Pessoa ObjectMinCardinality(3 :temEndereco))). " +
                     "3) Empresa com muitos funcionários tem no mínimo 10 funcionários → SubClassOf(:EmpresaMuitosFuncionarios ObjectMinCardinality(10 :temFuncionario)).")
  public String createObjectMinCardinality(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe que tem a restrição", required = true) String className,
      @ToolParam(description = "Cardinalidade mínima (n)", required = true) int cardinality,
      @ToolParam(description = "Propriedade de objeto", required = true) String property) {

    log.debug("Criando ObjectMinCardinality: {} ≥ {} {}", className, cardinality, property);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SubClassOf: " + className + " ObjectMinCardinality(" + cardinality + " " + property + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de ObjectMinCardinality: {}", result);
    return result;
  }

  /**
   * Cria uma cardinalidade mínima qualificada na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe que tem a restrição
   * @param cardinality Cardinalidade mínima (n)
   * @param property Propriedade de objeto
   * @param targetClass Classe alvo da restrição
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma cardinalidade mínima qualificada OWL 2 DL na ontologia da sessão. " +
                     "Representa cardinalidade mínima qualificada (≥ n R.C). " +
                     "Exemplo: Pessoa tem pelo menos 1 filho que é Pessoa → SubClassOf(:Pessoa ObjectMinCardinality(1 :temFilho :Pessoa)).")
  public String createObjectMinCardinalityQualified(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe que tem a restrição", required = true) String className,
      @ToolParam(description = "Cardinalidade mínima (n)", required = true) int cardinality,
      @ToolParam(description = "Propriedade de objeto", required = true) String property,
      @ToolParam(description = "Classe alvo da restrição", required = true) String targetClass) {

    log.debug("Criando ObjectMinCardinality qualificado: {} ≥ {} {}.{}", className, cardinality, property, targetClass);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SubClassOf: " + className + " ObjectMinCardinality(" + cardinality + " " + property + " " + targetClass + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de ObjectMinCardinality qualificado: {}", result);
    return result;
  }
}
