package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Tool para criação de classes OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar classes
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * Declaração de classe OWL (Class(C)). Uma classe representa um conjunto de indivíduos.
 * Permite especificar superclasses, classes equivalentes e classes disjuntas em uma única declaração.
 * <p>
 * <b>Sintaxe Manchester:</b> Class: :Classe SubClassOf: :SuperClasse EquivalentTo: :ClasseEq DisjointWith: :ClasseDisj
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>Cria classe Pessoa com superclasse Animal:
 *       Class: :Pessoa SubClassOf: :Animal</li>
 *   <li>Cria classe Estudante com superclasse Pessoa e equivalente a Aluno:
 *       Class: :Estudante SubClassOf: :Pessoa EquivalentTo: :Aluno</li>
 *   <li>Cria classe Masculino disjunta de Feminino:
 *       Class: :Masculino DisjointWith: :Feminino</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class OwlClassCreatorTool extends OwlConstructorTool {

  public OwlClassCreatorTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma classe OWL 2 DL na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe a ser criada
   * @param superClasses Lista de superclasses (opcional)
   * @param equivalentClasses Lista de classes equivalentes (opcional)
   * @param disjointClasses Lista de classes disjuntas (opcional)
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma classe OWL 2 DL na ontologia da sessão. " +
                     "Declaração de classe OWL (Class(C)). Permite especificar superclasses, classes equivalentes e classes disjuntas. " +
                     "Exemplos: " +
                     "1) Cria classe Pessoa com superclasse Animal → Class: :Pessoa SubClassOf: :Animal. " +
                     "2) Cria classe Estudante com superclasse Pessoa e equivalente a Aluno → Class: :Estudante SubClassOf: :Pessoa EquivalentTo: :Aluno. " +
                     "3) Cria classe Masculino disjunta de Feminino → Class: :Masculino DisjointWith: :Feminino.")
  public String createClass(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe a ser criada", required = true) String className,
      @ToolParam(description = "Lista de superclasses", required = false) List<String> superClasses,
      @ToolParam(description = "Lista de classes equivalentes", required = false) List<String> equivalentClasses,
      @ToolParam(description = "Lista de classes disjuntas", required = false) List<String> disjointClasses) {

    log.debug("Criando classe OWL: {}", className);

    // Constrói o axioma em Manchester OWL Syntax
    StringBuilder manchesterAxiom = new StringBuilder("Class: " + className);

    if (superClasses != null && !superClasses.isEmpty()) {
      manchesterAxiom.append(" SubClassOf: ");
      manchesterAxiom.append(String.join(" and ", superClasses));
    }

    if (equivalentClasses != null && !equivalentClasses.isEmpty()) {
      manchesterAxiom.append(" EquivalentTo: ");
      manchesterAxiom.append(String.join(" and ", equivalentClasses));
    }

    if (disjointClasses != null && !disjointClasses.isEmpty()) {
      manchesterAxiom.append(" DisjointWith: ");
      manchesterAxiom.append(String.join(" ", disjointClasses));
    }

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom.toString());

    log.debug("Resultado da criação de classe: {}", result);
    return result;
  }
}
