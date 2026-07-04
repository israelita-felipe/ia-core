package com.ia.core.owl.service.tool.objectproperty;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de propriedade de objeto reflexiva OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar propriedades de objeto reflexivas
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa propriedade de objeto reflexiva (Ref(R)).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * ReflexiveObjectProperty é um axioma que especifica que uma propriedade de objeto é reflexiva.
 * Todo indivíduo x deve estar relacionado consigo mesmo pela propriedade R(x, x). Permite modelar relacionamentos de auto-relação.
 * <p>
 * <b>Sintaxe Manchester:</b> ReflexiveObjectProperty(:Propriedade)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>conhece é reflexiva (todos se conhecem):
 *       ReflexiveObjectProperty(:conhece)</li>
 *   <li>éRelacionadoCom é reflexiva:
 *       ReflexiveObjectProperty(:éRelacionadoCom)</li>
 *   <li>temMesmoEstado é reflexiva:
 *       ReflexiveObjectProperty(:temMesmoEstado)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class ReflexiveObjectPropertyTool extends OwlConstructorTool {

  public ReflexiveObjectPropertyTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma propriedade de objeto reflexiva na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param property Propriedade de objeto
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma propriedade de objeto reflexiva OWL 2 DL na ontologia da sessão. " +
                     "Representa propriedade de objeto reflexiva (Ref(R)). " +
                     "Especifica que todo indivíduo deve estar relacionado consigo mesmo pela propriedade. " +
                     "Exemplos: " +
                     "1) conhece é reflexiva (todos se conhecem) → ReflexiveObjectProperty(:conhece). " +
                     "2) éRelacionadoCom é reflexiva → ReflexiveObjectProperty(:éRelacionadoCom). " +
                     "3) temMesmoEstado é reflexiva → ReflexiveObjectProperty(:temMesmoEstado).")
  public String createReflexiveObjectProperty(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Propriedade de objeto", required = true) String property) {

    log.debug("Criando ReflexiveObjectProperty: Ref({})", property);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "ReflexiveObjectProperty: " + property;

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de ReflexiveObjectProperty: {}", result);
    return result;
  }
}
