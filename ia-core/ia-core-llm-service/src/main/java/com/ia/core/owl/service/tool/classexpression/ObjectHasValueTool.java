package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de restrição de valor específico OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar restrições de valor
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa restrição de valor (∃R.{a}) para um indivíduo específico.
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * ObjectHasValue é um construtor de restrição de classe que indica que existe pelo menos um valor
 * de uma propriedade de objeto para instâncias da classe que é um indivíduo específico.
 * É uma restrição existencial que garante que a propriedade tem um valor específico.
 * <p>
 * <b>Sintaxe Manchester:</b> SubClassOf(:Classe ObjectHasValue(:Propriedade :Individuo))
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>Filho de João é quem tem João como pai:
 *       EquivalentTo(:FilhoDeJoao ObjectHasValue(:temPai :Joao))</li>
 *   <li>Residente em Paris mora exatamente na cidade de Paris:
 *       EquivalentTo(:ResidenteParis ObjectHasValue(:moraEm :Paris))</li>
 *   <li>Subordinado de Maria tem chefe igual a Maria:
 *       EquivalentTo(:SubordinadoDeMaria ObjectHasValue(:temChefe :Maria))</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class ObjectHasValueTool extends OwlConstructorTool {

  public ObjectHasValueTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma restrição de valor específico na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe que tem a restrição
   * @param property Propriedade de objeto
   * @param individual Individuo específico
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma restrição de valor específico OWL 2 DL na ontologia da sessão. " +
                     "Representa restrição de valor (∃R.{a}) para um indivíduo específico. " +
                     "Indica que existe pelo menos um valor de uma propriedade de objeto para instâncias da classe que é um indivíduo específico. " +
                     "Exemplos: " +
                     "1) Filho de João é quem tem João como pai → EquivalentTo(:FilhoDeJoao ObjectHasValue(:temPai :Joao)). " +
                     "2) Residente em Paris mora exatamente na cidade de Paris → EquivalentTo(:ResidenteParis ObjectHasValue(:moraEm :Paris)). " +
                     "3) Subordinado de Maria tem chefe igual a Maria → EquivalentTo(:SubordinadoDeMaria ObjectHasValue(:temChefe :Maria)).")
  public String createObjectHasValue(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe que tem a restrição", required = true) String className,
      @ToolParam(description = "Propriedade de objeto", required = true) String property,
      @ToolParam(description = "Individuo específico", required = true) String individual) {

    log.debug("Criando ObjectHasValue: {} ∃{}.{{{}}}", className, property, individual);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SubClassOf: " + className + " ObjectHasValue(" + property + " " + individual + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de ObjectHasValue: {}", result);
    return result;
  }
}
