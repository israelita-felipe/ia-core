package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de restrição universal de dado OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar restrições universais de dado
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa restrição universal sobre dados (∀U.DR).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * DataAllValuesFrom é um construtor de restrição de classe que indica que todos os valores
 * de uma propriedade de dados para instâncias da classe devem pertencer a um intervalo de dados
 * especificado. É uma restrição universal (∀) que garante que todos os valores satisfazem a condição.
 * <p>
 * <b>Sintaxe Manchester:</b> SubClassOf(:Classe DataAllValuesFrom(:Propriedade TipoDeDado))
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>Medição válida de temperatura tem todos os valores acima do zero absoluto:
 *       SubClassOf(:MedicaoTemperaturaValida DataAllValuesFrom(:temTemperatura xsd:decimal[>= -273.15]))</li>
 *   <li>Cadastro de CPF válido possui todas as strings com exatamente 11 dígitos:
 *       EquivalentClasses(:CadastroCPFValido DataAllValuesFrom(:temCPF xsd:string[length 11]))</li>
 *   <li>Produto de código positivo tem todos os códigos como inteiros não negativos:
 *       SubClassOf(:ProdutoCodigoPositivo DataAllValuesFrom(:temCodigoProduto xsd:integer[>= 0]))</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class DataAllValuesFromTool extends OwlConstructorTool {

  public DataAllValuesFromTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma restrição universal de dado na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe que tem a restrição
   * @param property Propriedade de dado
   * @param datatype Tipo de dado (ex: xsd:integer)
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma restrição universal de dado OWL 2 DL na ontologia da sessão. " +
                     "Representa restrição universal sobre dados (∀U.DR). " +
                     "Indica que todos os valores de uma propriedade de dados para instâncias da classe devem pertencer a um intervalo de dados especificado. " +
                     "Exemplos: " +
                     "1) Medição válida de temperatura tem todos os valores acima do zero absoluto → SubClassOf(:MedicaoTemperaturaValida DataAllValuesFrom(:temTemperatura xsd:decimal[>= -273.15])). " +
                     "2) Cadastro de CPF válido possui todas as strings com exatamente 11 dígitos → EquivalentClasses(:CadastroCPFValido DataAllValuesFrom(:temCPF xsd:string[length 11])). " +
                     "3) Produto de código positivo tem todos os códigos como inteiros não negativos → SubClassOf(:ProdutoCodigoPositivo DataAllValuesFrom(:temCodigoProduto xsd:integer[>= 0])).")
  public String createDataAllValuesFrom(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe que tem a restrição", required = true) String className,
      @ToolParam(description = "Propriedade de dado", required = true) String property,
      @ToolParam(description = "Tipo de dado (ex: xsd:integer, xsd:string)", required = true) String datatype) {

    log.debug("Criando DataAllValuesFrom: {} ∀{}.{}", className, property, datatype);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SubClassOf: " + className + " DataAllValuesFrom(" + property + " " + datatype + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de DataAllValuesFrom: {}", result);
    return result;
  }
}
