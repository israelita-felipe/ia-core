package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de auto-restrição OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owl*service para criar auto-restrições
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa auto-restrição (∃R.Self), p. ex. conhece Self.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class ObjectHasSelfTool extends OwlConstructorTool {

  public ObjectHasSelfTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma auto-restrição na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe que tem a auto-restrição
   * @param property Propriedade de objeto
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma auto-restrição OWL 2 DL na ontologia da sessão. " +
                     "Representa auto-restrição (∃R.Self). " +
                     "Exemplo: Pessoa que conhece a si mesma → SubClassOf(:PessoaQueSeConhece ObjectHasSelf(:conhece)).")
  public String createObjectHasSelf(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe que tem a auto-restrição", required = true) String className,
      @ToolParam(description = "Propriedade de objeto", required = true) String property) {

    log.debug("Criando ObjectHasSelf: {} ∃{}.Self", className, property);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "SubClassOf: " + className + " ObjectHasSelf(" + property + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owl*service
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de ObjectHasSelf: {}", result);
    return result;
  }
}
