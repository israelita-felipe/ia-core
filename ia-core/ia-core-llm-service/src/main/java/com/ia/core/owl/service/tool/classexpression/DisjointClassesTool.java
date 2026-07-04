package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de axiomas DisjointClasses OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar classes disjuntas
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa classes mutuamente exclusivas (não têm membros em comum).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * DisjointClasses é um axioma que declara que duas ou mais classes são mutuamente
 * exclusivas, ou seja, não têm nenhuma instância em comum. Se uma instância pertence
 * a uma das classes, não pode pertencer a nenhuma das outras.
 * <p>
 * <b>Sintaxe Manchester:</b> DisjointClasses(:Classe1 :Classe2 ...)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>Gato e Cachorro são disjuntos (um animal não pode ser ambos):
 *       DisjointClasses(:Gato :Cachorro)</li>
 *   <li>Homem e Mulher são disjuntos:
 *       DisjointClasses(:Homem :Mulher)</li>
 *   <li>VeículoTerrestre e VeículoAéreo são disjuntos:
 *       DisjointClasses(:VeiculoTerrestre :VeiculoAereo)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class DisjointClassesTool extends OwlConstructorTool {

  public DisjointClassesTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria um axioma DisjointClasses na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param class1 Primeira classe
   * @param class2 Segunda classe (disjunta da primeira)
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria um axioma DisjointClasses OWL 2 DL na ontologia da sessão. " +
                     "Declara que duas ou mais classes são disjuntas (mutuamente exclusivas, não têm membros em comum). " +
                     "Se uma instância pertence a uma das classes, não pode pertencer a nenhuma das outras. " +
                     "Exemplos: " +
                     "1) Gato e Cachorro são disjuntos → DisjointClasses(:Gato :Cachorro). " +
                     "2) Homem e Mulher são disjuntos → DisjointClasses(:Homem :Mulher). " +
                     "3) VeículoTerrestre e VeículoAéreo são disjuntos → DisjointClasses(:VeiculoTerrestre :VeiculoAereo). " +
                     "Útil para definir classes mutuamente exclusivas em ontologias.")
  public String createDisjointClasses(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Primeira classe", required = true) String class1,
      @ToolParam(description = "Segunda classe (disjunta da primeira)", required = true) String class2) {

    log.debug("Criando DisjointClasses: {} disjunta de {}", class1, class2);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "DisjointClasses(" + class1 + " " + class2 + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de DisjointClasses: {}", result);
    return result;
  }
}
