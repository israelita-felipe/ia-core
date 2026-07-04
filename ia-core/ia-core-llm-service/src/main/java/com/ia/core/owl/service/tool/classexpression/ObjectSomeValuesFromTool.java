package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de restrição existencial OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar restrições existenciais
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa restrição existencial (∃R.C) sobre propriedade de objeto.
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * ObjectSomeValuesFrom é um construtor de restrição de classe que indica que existe pelo menos um
 * valor de uma propriedade de objeto para instâncias da classe que pertence a uma classe especificada.
 * É uma restrição existencial (∃) que garante que pelo menos um valor satisfaz a condição.
 * <p>
 * <b>Sintaxe Manchester:</b> SubClassOf(:Classe ObjectSomeValuesFrom(:Propriedade :ClasseAlvo))
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>Pai tem algum filho que é Pessoa:
 *       SubClassOf(:Pai ObjectSomeValuesFrom(:temFilho :Pessoa))</li>
 *   <li>Departamento tem algum funcionário que é Gerente:
 *       SubClassOf(:Departamento ObjectSomeValuesFrom(:temFuncionario :Gerente))</li>
 *   <li>Empresa tem algum cliente que é VIP:
 *       SubClassOf(:Empresa ObjectSomeValuesFrom(:temCliente :VIP))</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class ObjectSomeValuesFromTool extends OwlConstructorTool {

  public ObjectSomeValuesFromTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma restrição existencial na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe que tem a restrição
   * @param property Propriedade de objeto
   * @param targetClass Classe alvo da restrição
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma restrição existencial OWL 2 DL na ontologia da sessão. " +
                     "Representa restrição existencial (∃R.C) sobre propriedade de objeto. " +
                     "Indica que existe pelo menos um valor de uma propriedade de objeto para instâncias da classe que pertence a uma classe especificada. " +
                     "Exemplos: " +
                     "1) Pai tem algum filho que é Pessoa → SubClassOf(:Pai ObjectSomeValuesFrom(:temFilho :Pessoa)). " +
                     "2) Departamento tem algum funcionário que é Gerente → SubClassOf(:Departamento ObjectSomeValuesFrom(:temFuncionario :Gerente)). " +
                     "3) Empresa tem algum cliente que é VIP → SubClassOf(:Empresa ObjectSomeValuesFrom(:temCliente :VIP)).")
  public String createObjectSomeValuesFrom(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe que tem a restrição", required = true) String className,
      @ToolParam(description = "Propriedade de objeto", required = true) String property,
      @ToolParam(description = "Classe alvo da restrição", required = true) String targetClass) {

    log.debug("Criando ObjectSomeValuesFrom: {} ∃{}.{}", className, property, targetClass);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SubClassOf: " + className + " ObjectSomeValuesFrom(" + property + " " + targetClass + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de ObjectSomeValuesFrom: {}", result);
    return result;
  }
}
