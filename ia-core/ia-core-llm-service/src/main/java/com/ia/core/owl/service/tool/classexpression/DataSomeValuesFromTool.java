package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de restrição existencial de dado OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar restrições existenciais de dado
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa restrição existencial sobre dados (∃U.DR).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * DataSomeValuesFrom é um construtor de restrição de classe que indica que existe pelo menos um valor
 * de uma propriedade de dado para instâncias da classe que pertence a um tipo de dado especificado.
 * É uma restrição existencial sobre dados que pode incluir facetas de restrição.
 * <p>
 * <b>Sintaxe Manchester:</b> SubClassOf(:Classe DataSomeValuesFrom(:Propriedade xsd:tipo[faceta]))
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>Adulto é quem tem alguma idade maior ou igual a 18:
 *       EquivalentTo(:Adulto ObjectIntersectionOf(:Pessoa DataSomeValuesFrom(:temIdade xsd:integer[>= 18])))</li>
 *   <li>Obeso é quem possui algum peso acima de 100 kg:
 *       SubClassOf(:Obeso DataSomeValuesFrom(:temPeso xsd:decimal[> 100.0]))</li>
 *   <li>Aluno nota A tem alguma nota igual a 'A':
 *       SubClassOf(:AlunoNotaA DataSomeValuesFrom(:temNota xsd:string[= "A"]))</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class DataSomeValuesFromTool extends OwlConstructorTool {

  public DataSomeValuesFromTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma restrição existencial de dado na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe que tem a restrição
   * @param property Propriedade de dado
   * @param datatype Tipo de dado (ex: xsd:integer)
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma restrição existencial de dado OWL 2 DL na ontologia da sessão. " +
                     "Representa restrição existencial sobre dados (∃U.DR). " +
                     "Indica que existe pelo menos um valor de uma propriedade de dado para instâncias da classe que pertence a um tipo de dado especificado. " +
                     "Exemplos: " +
                     "1) Adulto é quem tem alguma idade maior ou igual a 18 → EquivalentTo(:Adulto ObjectIntersectionOf(:Pessoa DataSomeValuesFrom(:temIdade xsd:integer[>= 18]))). " +
                     "2) Obeso é quem possui algum peso acima de 100 kg → SubClassOf(:Obeso DataSomeValuesFrom(:temPeso xsd:decimal[> 100.0])). " +
                     "3) Aluno nota A tem alguma nota igual a 'A' → SubClassOf(:AlunoNotaA DataSomeValuesFrom(:temNota xsd:string[= \"A\")).")
  public String createDataSomeValuesFrom(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe que tem a restrição", required = true) String className,
      @ToolParam(description = "Propriedade de dado", required = true) String property,
      @ToolParam(description = "Tipo de dado (ex: xsd:integer, xsd:string)", required = true) String datatype) {

    log.debug("Criando DataSomeValuesFrom: {} ∃{}.{}", className, property, datatype);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SubClassOf: " + className + " DataSomeValuesFrom(" + property + " " + datatype + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de DataSomeValuesFrom: {}", result);
    return result;
  }
}
