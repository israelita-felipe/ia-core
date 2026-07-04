package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de axiomas HasSelf OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar restrições de auto-referência
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa classes onde uma propriedade se refere ao próprio indivíduo.
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * ObjectHasSelf é um construtor de restrição de classe que indica que uma propriedade
 * deve ter o próprio indivíduo como valor (auto-referência). Uma instância pertence à classe
 * se a propriedade se relaciona consigo mesma.
 * <p>
 * <b>Sintaxe Manchester:</b> SubClassOf(:Classe ObjectHasSelf(:Propriedade))
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>Narcisista é quem ama a si mesmo:
 *       EquivalentClasses(:Narcisista ObjectHasSelf(:ama))</li>
 *   <li>Autoconfiante é quem conhece a si mesmo:
 *       SubClassOf(:Autoconfiante ObjectHasSelf(:conhece))</li>
 *   <li>Pessoa autocontrolada é aquela que controla a si mesma:
 *       EquivalentClasses(:Autocontrolado ObjectHasSelf(:controla))</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class HasSelfTool extends OwlConstructorTool {

  public HasSelfTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria um axioma HasSelf na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe que tem a restrição
   * @param property Propriedade de objeto que se refere ao próprio indivíduo
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria um axioma HasSelf OWL 2 DL na ontologia da sessão. " +
                     "Declara que uma propriedade se refere ao próprio indivíduo (auto-referência). " +
                     "Uma instância pertence à classe se a propriedade se relaciona consigo mesma. " +
                     "Exemplos: " +
                     "1) Narcisista é quem ama a si mesmo → EquivalentClasses(:Narcisista ObjectHasSelf(:ama)). " +
                     "2) Autoconfiante é quem conhece a si mesmo → SubClassOf(:Autoconfiante ObjectHasSelf(:conhece)). " +
                     "3) Pessoa autocontrolada é aquela que controla a si mesma → EquivalentClasses(:Autocontrolado ObjectHasSelf(:controla)). " +
                     "Útil para definir classes onde uma propriedade tem auto-referência.")
  public String createHasSelf(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe que tem a restrição", required = true) String className,
      @ToolParam(description = "Propriedade de objeto que se refere ao próprio indivíduo", required = true) String property) {

    log.debug("Criando HasSelf: {} tem auto-referência via {}", className, property);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SubClassOf: " + className + " ObjectHasSelf(" + property + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de HasSelf: {}", result);
    return result;
  }
}
