package com.ia.core.owl.service.tool.objectproperty;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de axiomas InverseObjectProperties OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar propriedades inversas
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa propriedades inversas onde se xRy então yRx.
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * InverseObjectProperties é um axioma que declara que duas propriedades de objeto são inversas
 * uma da outra. Se a propriedade P é inversa de Q, então para quaisquer instâncias x e y,
 * se x está relacionado com y via P, então y está relacionado com x via Q (xPy ↔ yQx).
 * <p>
 * <b>Sintaxe Manchester:</b> InverseObjectProperties(:Propriedade1 :Propriedade2)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>temPai é a inversa de temFilho:
 *       InverseObjectProperties(:temPai :temFilho)</li>
 *   <li>éChefeDe é a inversa de temChefe:
 *       InverseObjectProperties(:eChefeDe :temChefe)</li>
 *   <li>éParteDe é a inversa de temParte:
 *       InverseObjectProperties(:eParteDe :temParte)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class InverseObjectPropertiesTool extends OwlConstructorTool {

  public InverseObjectPropertiesTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria um axioma InverseObjectProperties na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param property1 Primeira propriedade de objeto
   * @param property2 Segunda propriedade de objeto (inversa da primeira)
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria um axioma InverseObjectProperties OWL 2 DL na ontologia da sessão. " +
                     "Declara que duas propriedades de objeto são inversas uma da outra. " +
                     "Se a propriedade P é inversa de Q, então para quaisquer instâncias x e y, se xPy então yQx. " +
                     "Exemplos: " +
                     "1) temPai é a inversa de temFilho → InverseObjectProperties(:temPai :temFilho). " +
                     "2) éChefeDe é a inversa de temChefe → InverseObjectProperties(:eChefeDe :temChefe). " +
                     "3) éParteDe é a inversa de temParte → InverseObjectProperties(:eParteDe :temParte). " +
                     "Útil para definir propriedades inversas onde se xRy então yRx.")
  public String createInverseObjectProperties(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Primeira propriedade de objeto", required = true) String property1,
      @ToolParam(description = "Segunda propriedade de objeto (inversa da primeira)", required = true) String property2) {

    log.debug("Criando InverseObjectProperties: {} inverso de {}", property1, property2);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "InverseObjectProperties(" + property1 + " " + property2 + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de InverseObjectProperties: {}", result);
    return result;
  }
}
