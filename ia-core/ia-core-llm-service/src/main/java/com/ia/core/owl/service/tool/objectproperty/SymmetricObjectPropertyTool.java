package com.ia.core.owl.service.tool.objectproperty;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de propriedade de objeto simétrica OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar propriedades de objeto simétricas
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa propriedade de objeto simétrica (Sym(R)).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * SymmetricObjectProperty é um axioma que especifica que uma propriedade de objeto é simétrica.
 * Se R(x, y) então R(y, x). Permite modelar relacionamentos bidirecionais como cônjuges e irmãos.
 * <p>
 * <b>Sintaxe Manchester:</b> SymmetricObjectProperty(:Propriedade)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>éCônjugeDe é simétrico:
 *       SymmetricObjectProperty(:éCônjugeDe)</li>
 *   <li>éIrmãoDe é simétrico:
 *       SymmetricObjectProperty(:éIrmãoDe)</li>
 *   <li>éColegaDe é simétrico:
 *       SymmetricObjectProperty(:éColegaDe)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class SymmetricObjectPropertyTool extends OwlConstructorTool {

  public SymmetricObjectPropertyTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma propriedade de objeto simétrica na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param property Propriedade de objeto
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma propriedade de objeto simétrica OWL 2 DL na ontologia da sessão. " +
                     "Representa propriedade de objeto simétrica (Sym(R)). " +
                     "Especifica que se R(x, y) então R(y, x). " +
                     "Exemplos: " +
                     "1) éCônjugeDe é simétrico → SymmetricObjectProperty(:éCônjugeDe). " +
                     "2) éIrmãoDe é simétrico → SymmetricObjectProperty(:éIrmãoDe). " +
                     "3) éColegaDe é simétrico → SymmetricObjectProperty(:éColegaDe).")
  public String createSymmetricObjectProperty(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Propriedade de objeto", required = true) String property) {

    log.debug("Criando SymmetricObjectProperty: Sym({})", property);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SymmetricObjectProperty: " + property;

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de SymmetricObjectProperty: {}", result);
    return result;
  }
}
