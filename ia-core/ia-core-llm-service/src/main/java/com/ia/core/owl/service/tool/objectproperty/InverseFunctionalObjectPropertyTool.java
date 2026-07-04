package com.ia.core.owl.service.tool.objectproperty;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de propriedade de objeto inversamente funcional OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar propriedades de objeto inversamente funcionais
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa propriedade de objeto inversamente funcional (InvFunc(R)).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * InverseFunctionalObjectProperty é um axioma que especifica que uma propriedade de objeto é inversamente funcional.
 * Cada indivíduo y pode estar relacionado por no máximo um indivíduo x pela propriedade R(x, y). Permite modelar relacionamentos de identidade única.
 * <p>
 * <b>Sintaxe Manchester:</b> InverseFunctionalObjectProperty(:Propriedade)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>temFilho é inversamente funcional (cada pessoa tem no máximo um pai biológico):
 *       InverseFunctionalObjectProperty(:temFilho)</li>
 *   <li>temCPF é inversamente funcional (CPF é único):
 *       InverseFunctionalObjectProperty(:temCPF)</li>
 *   <li>temRG é inversamente funcional (RG é único):
 *       InverseFunctionalObjectProperty(:temRG)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class InverseFunctionalObjectPropertyTool extends OwlConstructorTool {

  public InverseFunctionalObjectPropertyTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma propriedade de objeto inversamente funcional na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param property Propriedade de objeto
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma propriedade de objeto inversamente funcional OWL 2 DL na ontologia da sessão. " +
                     "Representa propriedade de objeto inversamente funcional (InvFunc(R)). " +
                     "Especifica que cada indivíduo pode estar relacionado por no máximo um indivíduo pela propriedade. " +
                     "Exemplos: " +
                     "1) temFilho é inversamente funcional (cada pessoa tem no máximo um pai biológico) → InverseFunctionalObjectProperty(:temFilho). " +
                     "2) temCPF é inversamente funcional (CPF é único) → InverseFunctionalObjectProperty(:temCPF). " +
                     "3) temRG é inversamente funcional (RG é único) → InverseFunctionalObjectProperty(:temRG).")
  public String createInverseFunctionalObjectProperty(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Propriedade de objeto", required = true) String property) {

    log.debug("Criando InverseFunctionalObjectProperty: InvFunc({})", property);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "InverseFunctionalObjectProperty: " + property;

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de InverseFunctionalObjectProperty: {}", result);
    return result;
  }
}
