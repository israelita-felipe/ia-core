package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Tool para criação de axiomas OneOf OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar enumerações de indivíduos
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa uma classe que é uma enumeração de indivíduos específicos.
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * ObjectOneOf é um construtor de expressão de classe que representa uma enumeração
 * explícita de indivíduos. Uma classe definida por OneOf contém exatamente os indivíduos
 * listados e nenhum outro. É útil para definir classes finitas e conhecidas.
 * <p>
 * <b>Sintaxe Manchester:</b> EquivalentClasses(:Classe OneOf(:Individuo1 :Individuo2 ...))
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>Cor primária é exatamente vermelho, amarelo ou azul:
 *       EquivalentClasses(:CorPrimaria OneOf({:Vermelho :Amarelo :Azul}))</li>
 *   <li>Planeta interior do sistema solar são Mercúrio e Vênus:
 *       EquivalentClasses(:PlanetaInterior OneOf({:Mercurio :Venus}))</li>
 *   <li>Equipe gestora é composta por João, Maria e Pedro:
 *       EquivalentClasses(:EquipeGestora OneOf({:Joao :Maria :Pedro}))</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class OneOfTool extends OwlConstructorTool {

  public OneOfTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria um axioma OneOf na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe que é a enumeração
   * @param individuals Lista de indivíduos que compõem a enumeração
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria um axioma OneOf OWL 2 DL na ontologia da sessão. " +
                     "Declara que uma classe é uma enumeração explícita de indivíduos específicos. " +
                     "Uma classe definida por OneOf contém exatamente os indivíduos listados e nenhum outro. " +
                     "Exemplos: " +
                     "1) Cor primária é exatamente vermelho, amarelo ou azul → EquivalentClasses(:CorPrimaria OneOf({:Vermelho :Amarelo :Azul})). " +
                     "2) Planeta interior do sistema solar são Mercúrio e Vênus → EquivalentClasses(:PlanetaInterior OneOf({:Mercurio :Venus})). " +
                     "3) Equipe gestora é composta por João, Maria e Pedro → EquivalentClasses(:EquipeGestora OneOf({:Joao :Maria :Pedro})). " +
                     "Útil para definir classes finitas e conhecidas como enumerações.")
  public String createOneOf(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe que é a enumeração", required = true) String className,
      @ToolParam(description = "Lista de indivíduos que compõem a enumeração", required = true) List<String> individuals) {

    log.debug("Criando OneOf: {} é enumeração de {}", className, individuals);

    // Constrói o axioma em Manchester OWL Syntax
    String oneOfExpression = "OneOf(" + String.join(" ", individuals) + ")";
    String manchesterAxiom = "EquivalentClasses(" + className + " " + oneOfExpression + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de OneOf: {}", result);
    return result;
  }
}
