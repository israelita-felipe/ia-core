package com.ia.core.owl.service.tool.dataproperty;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de subpropriedade de dado OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar subpropriedades de dado
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa subpropriedade de dado (U₁ ⊑ U₂).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * SubDataPropertyOf é um axioma que especifica que uma propriedade de dado é subpropriedade de outra.
 * Se U₁(x, v) então U₂(x, v), onde U₁ é subpropriedade de U₂. Permite construir hierarquias de propriedades de dado.
 * <p>
 * <b>Sintaxe Manchester:</b> SubDataPropertyOf(:SubPropriedade :SuperPropriedade)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>temNomeCompleto é subpropriedade de temNome:
 *       SubDataPropertyOf(:temNomeCompleto :temNome)</li>
 *   <li>temPrimeiroNome é subpropriedade de temNome:
 *       SubDataPropertyOf(:temPrimeiroNome :temNome)</li>
 *   <li>temSobrenome é subpropriedade de temNome:
 *       SubDataPropertyOf(:temSobrenome :temNome)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class SubDataPropertyOfTool extends OwlConstructorTool {

  public SubDataPropertyOfTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma subpropriedade de dado na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param subProperty Propriedade de dado subpropriedade
   * @param superProperty Propriedade de dado superpropriedade
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma subpropriedade de dado OWL 2 DL na ontologia da sessão. " +
                     "Representa subpropriedade de dado (U₁ ⊑ U₂). " +
                     "Especifica que uma propriedade de dado é subpropriedade de outra. " +
                     "Exemplos: " +
                     "1) temNomeCompleto é subpropriedade de temNome → SubDataPropertyOf(:temNomeCompleto :temNome). " +
                     "2) temPrimeiroNome é subpropriedade de temNome → SubDataPropertyOf(:temPrimeiroNome :temNome). " +
                     "3) temSobrenome é subpropriedade de temNome → SubDataPropertyOf(:temSobrenome :temNome).")
  public String createSubDataPropertyOf(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Propriedade de dado subpropriedade", required = true) String subProperty,
      @ToolParam(description = "Propriedade de dado superpropriedade", required = true) String superProperty) {

    log.debug("Criando SubDataPropertyOf: {} ⊑ {}", subProperty, superProperty);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SubDataPropertyOf: " + subProperty + " " + superProperty;

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de SubDataPropertyOf: {}", result);
    return result;
  }
}
