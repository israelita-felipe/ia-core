package com.ia.core.owl.service.tool.objectproperty;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de propriedade de objeto assimétrica OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar propriedades de objeto assimétricas
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa propriedade de objeto assimétrica (Asym(R)).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * AsymmetricObjectProperty é um axioma que especifica que uma propriedade de objeto é assimétrica.
 * Se R(x, y) então ¬R(y, x). Permite modelar relacionamentos direcionais que não podem ser recíprocos.
 * <p>
 * <b>Sintaxe Manchester:</b> AsymmetricObjectProperty(:Propriedade)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>paiDe é assimétrica (se x é pai de y, y não pode ser pai de x):
 *       AsymmetricObjectProperty(:paiDe)</li>
 *   <li>éMaiorQue é assimétrica:
 *       AsymmetricObjectProperty(:éMaiorQue)</li>
 *   <li>éAnteriorA é assimétrica:
 *       AsymmetricObjectProperty(:éAnteriorA)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class AsymmetricObjectPropertyTool extends OwlConstructorTool {

  public AsymmetricObjectPropertyTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma propriedade de objeto assimétrica na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param property Propriedade de objeto
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma propriedade de objeto assimétrica OWL 2 DL na ontologia da sessão. " +
                     "Representa propriedade de objeto assimétrica (Asym(R)). " +
                     "Especifica que se R(x, y) então ¬R(y, x). " +
                     "Exemplos: " +
                     "1) paiDe é assimétrica (se x é pai de y, y não pode ser pai de x) → AsymmetricObjectProperty(:paiDe). " +
                     "2) éMaiorQue é assimétrica → AsymmetricObjectProperty(:éMaiorQue). " +
                     "3) éAnteriorA é assimétrica → AsymmetricObjectProperty(:éAnteriorA).")
  public String createAsymmetricObjectProperty(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Propriedade de objeto", required = true) String property) {

    log.debug("Criando AsymmetricObjectProperty: Asym({})", property);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "AsymmetricObjectProperty: " + property;

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de AsymmetricObjectProperty: {}", result);
    return result;
  }
}
