package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Tool para criação de axiomas IntersectionOf OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar interseções de classes
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa uma classe que é a interseção de outras classes (conjunção lógica).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * ObjectIntersectionOf é um construtor de expressão de classe que representa a interseção
 * (conjunção lógica AND) de duas ou mais expressões de classe. Uma instância pertence à
 * interseção se e somente se pertencer a todas as classes envolvidas.
 * <p>
 * <b>Sintaxe Manchester:</b> EquivalentClasses(:Classe IntersectionOf(:Classe1 :Classe2 ...))
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>Mãe trabalhadora é toda pessoa que é mãe e também trabalhadora:
 *       EquivalentClasses(:MaeTrabalhadora IntersectionOf(:Mae :Trabalhadora))</li>
 *   <li>Mamífero aquático é exatamente um animal que é mamífero e aquático:
 *       EquivalentClasses(:MamiferoAquatico IntersectionOf(:Mamifero :Aquatico))</li>
 *   <li>Professor pesquisador é um docente que também exerce atividade de pesquisa:
 *       SubClassOf(:ProfessorPesquisador IntersectionOf(:Professor :Pesquisador))</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class IntersectionOfTool extends OwlConstructorTool {

  public IntersectionOfTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria um axioma IntersectionOf na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe que é a interseção
   * @param classes Lista de classes que compõem a interseção
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria um axioma IntersectionOf OWL 2 DL na ontologia da sessão. " +
                     "Declara que uma classe é a interseção (conjunção lógica AND) de outras classes. " +
                     "Uma instância pertence à interseção se e somente se pertencer a todas as classes envolvidas. " +
                     "Exemplos: " +
                     "1) Mãe trabalhadora é toda pessoa que é mãe e também trabalhadora → EquivalentClasses(:MaeTrabalhadora IntersectionOf(:Mae :Trabalhadora)). " +
                     "2) Mamífero aquático é exatamente um animal que é mamífero e aquático → EquivalentClasses(:MamiferoAquatico IntersectionOf(:Mamifero :Aquatico)). " +
                     "3) Professor pesquisador é um docente que também exerce atividade de pesquisa → SubClassOf(:ProfessorPesquisador IntersectionOf(:Professor :Pesquisador)). " +
                     "Útil para definir classes que satisfazem múltiplas condições simultaneamente.")
  public String createIntersectionOf(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe que é a interseção", required = true) String className,
      @ToolParam(description = "Lista de classes que compõem a interseção", required = true) List<String> classes) {

    log.debug("Criando IntersectionOf: {} é interseção de {}", className, classes);

    // Constrói o axioma em Manchester OWL Syntax
    String intersectionExpression = "IntersectionOf(" + String.join(" ", classes) + ")";
    String manchesterAxiom = "EquivalentClasses(" + className + " " + intersectionExpression + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de IntersectionOf: {}", result);
    return result;
  }
}
