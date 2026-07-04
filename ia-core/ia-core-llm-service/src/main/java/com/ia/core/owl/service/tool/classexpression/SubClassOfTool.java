package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de axiomas SubClassOf.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar axiomas SubClassOf
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * SubClassOf é um axioma que especifica que todas as instâncias de uma classe são também instâncias de outra classe.
 * É usado para construir hierarquias de classes e expressar relações de especialização/generalização.
 * <p>
 * <b>Sintaxe Manchester:</b> SubClassOf(:SubClasse :SuperClasse)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>Todo cachorro é um mamífero:
 *       SubClassOf(:Cachorro :Mamifero)</li>
 *   <li>Todo estudante é uma pessoa:
 *       SubClassOf(:Estudante :Pessoa)</li>
 *   <li>Todo carro é um veículo:
 *       SubClassOf(:Carro :Veiculo)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class SubClassOfTool extends OwlConstructorTool {

  public SubClassOfTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria um axioma SubClassOf na ontologia da sessão.
   * <p>
   * Especifica que uma classe é subclasse de outra.
   *
   * @param sessionId ID da sessão de conversação
   * @param subClass Classe subclasse
   * @param superClass Classe superclasse
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria um axioma SubClassOf na ontologia da sessão. " +
                     "Especifica que todas as instâncias de uma classe são também instâncias de outra classe. " +
                     "Útil para construir hierarquias de classes e expressar relações de especialização/generalização. " +
                     "Exemplos: " +
                     "1) Todo cachorro é um mamífero → SubClassOf(:Cachorro :Mamifero). " +
                     "2) Todo estudante é uma pessoa → SubClassOf(:Estudante :Pessoa). " +
                     "3) Todo carro é um veículo → SubClassOf(:Carro :Veiculo).")
  public String createSubClassOf(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Classe subclasse", required = true) String subClass,
      @ToolParam(description = "Classe superclasse", required = true) String superClass) {

    log.debug("Criando axioma SubClassOf: {} SubClassOf: {}", subClass, superClass);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SubClassOf: " + subClass + " " + superClass;

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de SubClassOf: {}", result);
    return result;
  }
}
