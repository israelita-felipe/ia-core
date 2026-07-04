package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de restrição universal OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar restrições universais
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa restrição universal (∀R.C) sobre propriedade de objeto.
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * ObjectAllValuesFrom é um construtor de restrição de classe que indica que todos os valores
 * de uma propriedade de objeto para instâncias da classe devem pertencer a uma classe especificada.
 * É uma restrição universal (∀) que garante que todos os valores satisfazem a condição.
 * <p>
 * <b>Sintaxe Manchester:</b> SubClassOf(:Classe ObjectAllValuesFrom(:Propriedade :ClasseAlvo))
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>Mulher tem apenas filhos que são Pessoas:
 *       SubClassOf(:Mulher ObjectAllValuesFrom(:temFilho :Pessoa))</li>
 *   <li>Departamento de TI tem apenas funcionários que são Engenheiros:
 *       SubClassOf(:DepartamentoTI ObjectAllValuesFrom(:temFuncionario :Engenheiro))</li>
 *   <li>Veículo elétrico tem apenas motores que são elétricos:
 *       SubClassOf(:VeiculoEletrico ObjectAllValuesFrom(:temMotor :MotorEletrico))</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class ObjectAllValuesFromTool extends OwlConstructorTool {

  public ObjectAllValuesFromTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma restrição universal na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe que tem a restrição
   * @param property Propriedade de objeto
   * @param targetClass Classe alvo da restrição
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma restrição universal OWL 2 DL na ontologia da sessão. " +
                     "Representa restrição universal (∀R.C) sobre propriedade de objeto. " +
                     "Indica que todos os valores de uma propriedade de objeto para instâncias da classe devem pertencer a uma classe especificada. " +
                     "Exemplos: " +
                     "1) Mulher tem apenas filhos que são Pessoas → SubClassOf(:Mulher ObjectAllValuesFrom(:temFilho :Pessoa)). " +
                     "2) Departamento de TI tem apenas funcionários que são Engenheiros → SubClassOf(:DepartamentoTI ObjectAllValuesFrom(:temFuncionario :Engenheiro)). " +
                     "3) Veículo elétrico tem apenas motores que são elétricos → SubClassOf(:VeiculoEletrico ObjectAllValuesFrom(:temMotor :MotorEletrico)).")
  public String createObjectAllValuesFrom(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe que tem a restrição", required = true) String className,
      @ToolParam(description = "Propriedade de objeto", required = true) String property,
      @ToolParam(description = "Classe alvo da restrição", required = true) String targetClass) {

    log.debug("Criando ObjectAllValuesFrom: {} ∀{}.{}", className, property, targetClass);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SubClassOf: " + className + " ObjectAllValuesFrom(" + property + " " + targetClass + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de ObjectAllValuesFrom: {}", result);
    return result;
  }
}
