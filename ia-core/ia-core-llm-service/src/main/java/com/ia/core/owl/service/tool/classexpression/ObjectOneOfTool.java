package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Tool para criação de enumeração de indivíduos OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owl*service para criar enumerações
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa uma enumeração de indivíduos (nominal), p. ex. {João, Maria}.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class ObjectOneOfTool extends OwlConstructorTool {

  public ObjectOneOfTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma enumeração de indivíduos na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe resultante da enumeração
   * @param individuals Lista de indivíduos da enumeração
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma enumeração de indivíduos OWL 2 DL na ontologia da sessão. " +
                     "Representa uma enumeração de indivíduos (nominal). " +
                     "Exemplo: DiasDaSemana = {Segunda, Terca, Quarta, Quinta, Sexta, Sabado, Domingo}.")
  public String createObjectOneOf(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe resultante da enumeração", required = true) String className,
      @ToolParam(description = "Lista de indivíduos da enumeração (mínimo 1)", required = true) List<String> individuals) {

    log.debug("Criando ObjectOneOf: {} = {{{}}}", className, String.join(", ", individuals));

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "EquivalentClasses: " + className + " {" + String.join(", ", individuals) + "}";

    // Usa OwlConstructorTool.createAxiom para adicionar via owl*service
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de ObjectOneOf: {}", result);
    return result;
  }
}
