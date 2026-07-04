package com.ia.core.owl.service.tool.dataproperty;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de propriedade de dado funcional OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar propriedades de dado funcionais
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa propriedade de dado funcional (Func(U)).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * FunctionalDataProperty é um axioma que especifica que uma propriedade de dado é funcional.
 * Cada indivíduo x pode estar relacionado a no máximo um valor pela propriedade U(x, v). Permite modelar atributos únicos.
 * <p>
 * <b>Sintaxe Manchester:</b> FunctionalDataProperty(:Propriedade)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>CPF é funcional (cada pessoa tem no máximo um CPF):
 *       FunctionalDataProperty(:CPF)</li>
 *   <li>RG é funcional (cada pessoa tem no máximo um RG):
 *       FunctionalDataProperty(:RG)</li>
 *   <li>dataNascimento é funcional:
 *       FunctionalDataProperty(:dataNascimento)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class FunctionalDataPropertyTool extends OwlConstructorTool {

  public FunctionalDataPropertyTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma propriedade de dado funcional na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param property Propriedade de dado
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma propriedade de dado funcional OWL 2 DL na ontologia da sessão. " +
                     "Representa propriedade de dado funcional (Func(U)). " +
                     "Especifica que cada indivíduo pode estar relacionado a no máximo um valor pela propriedade. " +
                     "Exemplos: " +
                     "1) CPF é funcional (cada pessoa tem no máximo um CPF) → FunctionalDataProperty(:CPF). " +
                     "2) RG é funcional (cada pessoa tem no máximo um RG) → FunctionalDataProperty(:RG). " +
                     "3) dataNascimento é funcional → FunctionalDataProperty(:dataNascimento).")
  public String createFunctionalDataProperty(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Propriedade de dado", required = true) String property) {

    log.debug("Criando FunctionalDataProperty: Func({})", property);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "FunctionalDataProperty: " + property;

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de FunctionalDataProperty: {}", result);
    return result;
  }
}
