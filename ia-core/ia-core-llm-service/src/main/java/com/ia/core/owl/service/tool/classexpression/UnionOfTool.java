package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Tool para criação de axiomas UnionOf OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar uniões de classes
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa uma classe que é a união de outras classes (disjunção lógica).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * ObjectUnionOf é um construtor de expressão de classe que representa a união
 * (disjunção lógica OR) de duas ou mais expressões de classe. Uma instância pertence à
 * união se pertencer a pelo menos uma das classes envolvidas.
 * <p>
 * <b>Sintaxe Manchester:</b> EquivalentClasses(:Classe UnionOf(:Classe1 :Classe2 ...))
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>Profissional de saúde é médico ou enfermeiro:
 *       EquivalentClasses(:ProfissionalSaude UnionOf(:Medico :Enfermeiro))</li>
 *   <li>Veículo leve é carro ou moto:
 *       SubClassOf(:VeiculoLeve UnionOf(:Carro :Moto))</li>
 *   <li>Fruta cítrica é laranja, limão ou tangerina:
 *       EquivalentClasses(:FrutaCitrica UnionOf(:Laranja :Limao :Tangerina))</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class UnionOfTool extends OwlConstructorTool {

  public UnionOfTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria um axioma UnionOf na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe que é a união
   * @param classes Lista de classes que compõem a união
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria um axioma UnionOf OWL 2 DL na ontologia da sessão. " +
                     "Declara que uma classe é a união (disjunção lógica OR) de outras classes. " +
                     "Uma instância pertence à união se pertencer a pelo menos uma das classes envolvidas. " +
                     "Exemplos: " +
                     "1) Profissional de saúde é médico ou enfermeiro → EquivalentClasses(:ProfissionalSaude UnionOf(:Medico :Enfermeiro)). " +
                     "2) Veículo leve é carro ou moto → SubClassOf(:VeiculoLeve UnionOf(:Carro :Moto)). " +
                     "3) Fruta cítrica é laranja, limão ou tangerina → EquivalentClasses(:FrutaCitrica UnionOf(:Laranja :Limao :Tangerina)). " +
                     "Útil para definir classes compostas por outras classes (disjunção lógica).")
  public String createUnionOf(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe que é a união", required = true) String className,
      @ToolParam(description = "Lista de classes que compõem a união", required = true) List<String> classes) {

    log.debug("Criando UnionOf: {} é união de {}", className, classes);

    // Constrói o axioma em Manchester OWL Syntax
    String unionExpression = "UnionOf(" + String.join(" ", classes) + ")";
    String manchesterAxiom = "EquivalentClasses(" + className + " " + unionExpression + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de UnionOf: {}", result);
    return result;
  }
}
