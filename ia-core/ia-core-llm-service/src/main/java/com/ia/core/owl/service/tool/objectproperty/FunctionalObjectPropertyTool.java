package com.ia.core.owl.service.tool.objectproperty;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de propriedade de objeto funcional OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar propriedades de objeto funcionais
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa propriedade de objeto funcional (Func(R)).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * FunctionalObjectProperty é um axioma que especifica que uma propriedade de objeto é funcional.
 * Cada indivíduo x pode estar relacionado a no máximo um indivíduo y pela propriedade R(x, y). Permite modelar relacionamentos de cardinalidade máxima 1.
 * <p>
 * <b>Sintaxe Manchester:</b> FunctionalObjectProperty(:Propriedade)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>temMae é funcional (cada pessoa tem no máximo uma mãe):
 *       FunctionalObjectProperty(:temMae)</li>
 *   <li>temPaiBiologico é funcional:
 *       FunctionalObjectProperty(:temPaiBiologico)</li>
 *   <li>temEsposa é funcional (monogamia):
 *       FunctionalObjectProperty(:temEsposa)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class FunctionalObjectPropertyTool extends OwlConstructorTool {

  public FunctionalObjectPropertyTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma propriedade de objeto funcional na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param property Propriedade de objeto
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma propriedade de objeto funcional OWL 2 DL na ontologia da sessão. " +
                     "Representa propriedade de objeto funcional (Func(R)). " +
                     "Especifica que cada indivíduo pode estar relacionado a no máximo um indivíduo pela propriedade. " +
                     "Exemplos: " +
                     "1) temMae é funcional (cada pessoa tem no máximo uma mãe) → FunctionalObjectProperty(:temMae). " +
                     "2) temPaiBiologico é funcional → FunctionalObjectProperty(:temPaiBiologico). " +
                     "3) temEsposa é funcional (monogamia) → FunctionalObjectProperty(:temEsposa).")
  public String createFunctionalObjectProperty(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Propriedade de objeto", required = true) String property) {

    log.debug("Criando FunctionalObjectProperty: Func({})", property);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "FunctionalObjectProperty: " + property;

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de FunctionalObjectProperty: {}", result);
    return result;
  }
}
