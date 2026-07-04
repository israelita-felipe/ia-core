package com.ia.core.owl.service.tool.objectproperty;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de propriedade inversa OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar propriedades inversas
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa propriedade inversa (R⁻¹).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * ObjectInverseOf é um axioma que especifica que uma propriedade de objeto é a inversa de outra.
 * Se R(x, y) então S(y, x), onde S é a inversa de R. Permite modelar relacionamentos bidirecionais.
 * <p>
 * <b>Sintaxe Manchester:</b> InverseObjectProperties(:Propriedade :PropriedadeInversa)
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
public class ObjectInverseOfTool extends OwlConstructorTool {

  public ObjectInverseOfTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma propriedade inversa na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param property Propriedade de objeto
   * @param inverseProperty Propriedade inversa
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma propriedade inversa OWL 2 DL na ontologia da sessão. " +
                     "Representa propriedade inversa (R⁻¹). " +
                     "Especifica que uma propriedade de objeto é a inversa de outra. " +
                     "Exemplos: " +
                     "1) temPai é a inversa de temFilho → InverseObjectProperties(:temPai :temFilho). " +
                     "2) éChefeDe é a inversa de temChefe → InverseObjectProperties(:eChefeDe :temChefe). " +
                     "3) éParteDe é a inversa de temParte → InverseObjectProperties(:eParteDe :temParte).")
  public String createObjectInverseOf(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Propriedade de objeto", required = true) String property,
      @ToolParam(description = "Propriedade inversa", required = true) String inverseProperty) {

    log.debug("Criando ObjectInverseOf: {}⁻¹ = {}", property, inverseProperty);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "InverseObjectProperties: " + property + " " + inverseProperty;

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de ObjectInverseOf: {}", result);
    return result;
  }
}
