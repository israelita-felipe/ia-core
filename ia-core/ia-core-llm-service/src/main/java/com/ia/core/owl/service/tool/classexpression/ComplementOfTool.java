package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de axiomas ComplementOf OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar complementos de classes
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa classes negativas ou complementares (contém todos os elementos que não pertencem à outra classe).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * ObjectComplementOf é um construtor de expressão de classe que representa o complemento
 * (negação lógica NOT) de uma expressão de classe. Uma instância pertence ao complemento
 * se e somente se NÃO pertencer à classe original.
 * <p>
 * <b>Sintaxe Manchester:</b> EquivalentClasses(:Classe ComplementOf(:OutraClasse))
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>Ser inanimado é tudo que não está vivo:
 *       EquivalentClasses(:Inanimado ComplementOf(:Vivo))</li>
 *   <li>Animal não mamífero é animal que não é mamífero:
 *       EquivalentClasses(:AnimalNaoMamifero IntersectionOf(:Animal ComplementOf(:Mamifero)))</li>
 *   <li>Material seguro é material que não é inflamável:
 *       SubClassOf(:MaterialSeguro ComplementOf(:Inflamavel))</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class ComplementOfTool extends OwlConstructorTool {

  public ComplementOfTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria um axioma ComplementOf na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe que é o complemento
   * @param otherClass Nome da classe original (cujo complemento está sendo definido)
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria um axioma ComplementOf OWL 2 DL na ontologia da sessão. " +
                     "Declara que uma classe é o complemento (negação lógica NOT) de outra classe. " +
                     "Uma instância pertence ao complemento se e somente se NÃO pertencer à classe original. " +
                     "Exemplos: " +
                     "1) Ser inanimado é tudo que não está vivo → EquivalentClasses(:Inanimado ComplementOf(:Vivo)). " +
                     "2) Animal não mamífero é animal que não é mamífero → EquivalentClasses(:AnimalNaoMamifero IntersectionOf(:Animal ComplementOf(:Mamifero))). " +
                     "3) Material seguro é material que não é inflamável → SubClassOf(:MaterialSeguro ComplementOf(:Inflamavel)). " +
                     "Útil para definir classes negativas ou complementares.")
  public String createComplementOf(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe que é o complemento", required = true) String className,
      @ToolParam(description = "Nome da classe original (cujo complemento está sendo definido)", required = true) String otherClass) {

    log.debug("Criando ComplementOf: {} é complemento de {}", className, otherClass);

    // Constrói o axioma em Manchester OWL Syntax
    String complementExpression = "ComplementOf(" + otherClass + ")";
    String manchesterAxiom = "EquivalentClasses(" + className + " " + complementExpression + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de ComplementOf: {}", result);
    return result;
  }
}
