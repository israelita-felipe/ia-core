package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de restrição de valor específico de dado OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar restrições de valor de dado
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa restrição de valor de dado específico (∃U.{v}).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * DataHasValue é um construtor de restrição de classe que indica que existe pelo menos um valor
 * de uma propriedade de dado para instâncias da classe que é um valor literal específico.
 * É uma restrição existencial que garante que a propriedade tem um valor específico.
 * <p>
 * <b>Sintaxe Manchester:</b> SubClassOf(:Classe DataHasValue(:Propriedade valor))
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>Família Silva é quem tem sobrenome igual a 'Silva':
 *       EquivalentTo(:FamiliaSilva DataHasValue(:temSobrenome "Silva"))</li>
 *   <li>Nascido em 2000 possui ano de nascimento exatamente 2000:
 *       SubClassOf(:Nascido2000 DataHasValue(:anoNascimento 2000))</li>
 *   <li>Portador do CPF 123.456.789-00 tem esse documento:
 *       EquivalentTo(:PortadorCPF123 DataHasValue(:temCPF "123.456.789-00"))</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class DataHasValueTool extends OwlConstructorTool {

  public DataHasValueTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma restrição de valor específico de dado na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe que tem a restrição
   * @param property Propriedade de dado
   * @param value Valor específico (ex: 18, "texto")
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma restrição de valor específico de dado OWL 2 DL na ontologia da sessão. " +
                     "Representa restrição de valor de dado específico (∃U.{v}). " +
                     "Indica que existe pelo menos um valor de uma propriedade de dado para instâncias da classe que é um valor literal específico. " +
                     "Exemplos: " +
                     "1) Família Silva é quem tem sobrenome igual a 'Silva' → EquivalentTo(:FamiliaSilva DataHasValue(:temSobrenome \"Silva\")). " +
                     "2) Nascido em 2000 possui ano de nascimento exatamente 2000 → SubClassOf(:Nascido2000 DataHasValue(:anoNascimento 2000)). " +
                     "3) Portador do CPF 123.456.789-00 tem esse documento → EquivalentTo(:PortadorCPF123 DataHasValue(:temCPF \"123.456.789-00\")).")
  public String createDataHasValue(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe que tem a restrição", required = true) String className,
      @ToolParam(description = "Propriedade de dado", required = true) String property,
      @ToolParam(description = "Valor específico (ex: 18, \"texto\")", required = true) String value) {

    log.debug("Criando DataHasValue: {} ∃{}.{{{}}}", className, property, value);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SubClassOf: " + className + " DataHasValue(" + property + " " + value + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de DataHasValue: {}", result);
    return result;
  }
}
