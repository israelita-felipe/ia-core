package com.ia.core.owl.service.tool.objectproperty;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de axiomas DisjointObjectProperties OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar propriedades disjuntas
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa propriedades de objeto mutuamente exclusivas (não podem ter valores em comum).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * DisjointObjectProperties é um axioma que declara que duas ou mais propriedades de objeto são
 * mutuamente exclusivas, ou seja, não podem ter o mesmo valor (instância) para o mesmo sujeito.
 * Se uma instância x está relacionada com y via propriedade P, não pode estar relacionada com y via Q.
 * <p>
 * <b>Sintaxe Manchester:</b> DisjointObjectProperties(:Propriedade1 :Propriedade2 ...)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>esposa e irmã são disjuntas (uma pessoa não pode ser esposa e irmã da mesma pessoa):
 *       DisjointObjectProperties(:esposa :irma)</li>
 *   <li>trabalhaPara e éClienteDe são disjuntas:
 *       DisjointObjectProperties(:trabalhaPara :eClienteDe)</li>
 *   <li>temPai e temMãe são disjuntas:
 *       DisjointObjectProperties(:temPai :temMae)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class DisjointObjectPropertiesTool extends OwlConstructorTool {

  public DisjointObjectPropertiesTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria um axioma DisjointObjectProperties na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param property1 Primeira propriedade de objeto
   * @param property2 Segunda propriedade de objeto (disjunta da primeira)
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria um axioma DisjointObjectProperties OWL 2 DL na ontologia da sessão. " +
                     "Declara que duas ou mais propriedades de objeto são disjuntas (mutuamente exclusivas). " +
                     "Não podem ter o mesmo valor (instância) para o mesmo sujeito. " +
                     "Exemplos: " +
                     "1) esposa e irmã são disjuntas → DisjointObjectProperties(:esposa :irma). " +
                     "2) trabalhaPara e éClienteDe são disjuntas → DisjointObjectProperties(:trabalhaPara :eClienteDe). " +
                     "3) temPai e temMãe são disjuntas → DisjointObjectProperties(:temPai :temMae). " +
                     "Útil para definir propriedades de objeto que não podem ter valores em comum.")
  public String createDisjointObjectProperties(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Primeira propriedade de objeto", required = true) String property1,
      @ToolParam(description = "Segunda propriedade de objeto (disjunta da primeira)", required = true) String property2) {

    log.debug("Criando DisjointObjectProperties: {} disjunta de {}", property1, property2);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "DisjointObjectProperties(" + property1 + " " + property2 + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de DisjointObjectProperties: {}", result);
    return result;
  }
}
