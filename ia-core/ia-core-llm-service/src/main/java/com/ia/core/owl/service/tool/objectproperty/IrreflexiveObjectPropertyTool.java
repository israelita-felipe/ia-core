package com.ia.core.owl.service.tool.objectproperty;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de propriedade de objeto irreflexiva OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar propriedades de objeto irreflexivas
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa propriedade de objeto irreflexiva (Irref(R)).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * IrreflexiveObjectProperty é um axioma que especifica que uma propriedade de objeto é irreflexiva.
 * Nenhum indivíduo x pode estar relacionado consigo mesmo pela propriedade ¬R(x, x). Permite modelar relacionamentos que não podem ser auto-relacionados.
 * <p>
 * <b>Sintaxe Manchester:</b> IrreflexiveObjectProperty(:Propriedade)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>paiDe é irreflexiva (ninguém é pai de si mesmo):
 *       IrreflexiveObjectProperty(:paiDe)</li>
 *   <li>éMaiorQue é irreflexiva:
 *       IrreflexiveObjectProperty(:éMaiorQue)</li>
 *   <li>éAnteriorA é irreflexiva:
 *       IrreflexiveObjectProperty(:éAnteriorA)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class IrreflexiveObjectPropertyTool extends OwlConstructorTool {

  public IrreflexiveObjectPropertyTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma propriedade de objeto irreflexiva na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param property Propriedade de objeto
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma propriedade de objeto irreflexiva OWL 2 DL na ontologia da sessão. " +
                     "Representa propriedade de objeto irreflexiva (Irref(R)). " +
                     "Especifica que nenhum indivíduo pode estar relacionado consigo mesmo pela propriedade. " +
                     "Exemplos: " +
                     "1) paiDe é irreflexiva (ninguém é pai de si mesmo) → IrreflexiveObjectProperty(:paiDe). " +
                     "2) éMaiorQue é irreflexiva → IrreflexiveObjectProperty(:éMaiorQue). " +
                     "3) éAnteriorA é irreflexiva → IrreflexiveObjectProperty(:éAnteriorA).")
  public String createIrreflexiveObjectProperty(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Propriedade de objeto", required = true) String property) {

    log.debug("Criando IrreflexiveObjectProperty: Irref({})", property);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "IrreflexiveObjectProperty: " + property;

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de IrreflexiveObjectProperty: {}", result);
    return result;
  }
}
