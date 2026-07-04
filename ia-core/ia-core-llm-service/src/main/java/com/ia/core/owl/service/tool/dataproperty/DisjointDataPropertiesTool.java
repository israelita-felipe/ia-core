package com.ia.core.owl.service.tool.dataproperty;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de axiomas DisjointDataProperties OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar propriedades de dado disjuntas
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa propriedades de dado mutuamente exclusivas (não podem ter valores em comum).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * DisjointDataProperties é um axioma que declara que duas ou mais propriedades de dado são
 * mutuamente exclusivas, ou seja, não podem ter o mesmo valor literal para a mesma instância.
 * Se uma instância x tem valor v via propriedade P, não pode ter valor v via propriedade Q.
 * <p>
 * <b>Sintaxe Manchester:</b> DisjointDataProperties(:Propriedade1 :Propriedade2 ...)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>idade e dataNascimento são disjuntas (uma pessoa não pode ter o mesmo valor para ambos):
 *       DisjointDataProperties(:idade :dataNascimento)</li>
 *   <li>salarioMensal e salarioAnual são disjuntas:
 *       DisjointDataProperties(:salarioMensal :salarioAnual)</li>
 *   <li>pesoKg e alturaMetros são disjuntas:
 *       DisjointDataProperties(:pesoKg :alturaMetros)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class DisjointDataPropertiesTool extends OwlConstructorTool {

  public DisjointDataPropertiesTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria um axioma DisjointDataProperties na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param property1 Primeira propriedade de dado
   * @param property2 Segunda propriedade de dado (disjunta da primeira)
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria um axioma DisjointDataProperties OWL 2 DL na ontologia da sessão. " +
                     "Declara que duas ou mais propriedades de dado são disjuntas (mutuamente exclusivas). " +
                     "Não podem ter o mesmo valor literal para a mesma instância. " +
                     "Exemplos: " +
                     "1) idade e dataNascimento são disjuntas → DisjointDataProperties(:idade :dataNascimento). " +
                     "2) salarioMensal e salarioAnual são disjuntas → DisjointDataProperties(:salarioMensal :salarioAnual). " +
                     "3) pesoKg e alturaMetros são disjuntas → DisjointDataProperties(:pesoKg :alturaMetros). " +
                     "Útil para definir propriedades de dado que não podem ter valores em comum.")
  public String createDisjointDataProperties(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Primeira propriedade de dado", required = true) String property1,
      @ToolParam(description = "Segunda propriedade de dado (disjunta da primeira)", required = true) String property2) {

    log.debug("Criando DisjointDataProperties: {} disjunta de {}", property1, property2);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "DisjointDataProperties(" + property1 + " " + property2 + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de DisjointDataProperties: {}", result);
    return result;
  }
}
