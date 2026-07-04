package com.ia.core.owl.service.tool.classexpression;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Tool para criação de axiomas DisjointUnion OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar uniões disjuntas de classes
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa uma classe que é a união disjunta (partição) de outras classes.
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * DisjointUnion é um axioma que declara que uma classe é a união disjunta (partição)
 * de duas ou mais classes. Isso significa que: (1) a classe é a união das subclasses,
 * (2) as subclasses são mutuamente exclusivas (disjuntas), e (3) a união cobre toda a classe.
 * É útil para definir partições completas de um conceito.
 * <p>
 * <b>Sintaxe Manchester:</b> DisjointUnion(:Classe :SubClasse1 :SubClasse2 ...)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>Animal é união disjunta de Gato e Cachorro:
 *       DisjointUnion(:Animal :Gato :Cachorro)</li>
 *   <li>SexoBiológico é união disjunta de Masculino e Feminino:
 *       DisjointUnion(:SexoBiologico :Masculino :Feminino)</li>
 *   <li>EstadoCivil é união disjunta de Solteiro, Casado, Divorciado e Viúvo:
 *       DisjointUnion(:EstadoCivil :Solteiro :Casado :Divorciado :Viuvo)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class DisjointUnionTool extends OwlConstructorTool {

  public DisjointUnionTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria um axioma DisjointUnion na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param className Nome da classe que é a união disjunta
   * @param classes Lista de classes que compõem a união disjunta
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria um axioma DisjointUnion OWL 2 DL na ontologia da sessão. " +
                     "Declara que uma classe é a união disjunta (partição completa) de outras classes. " +
                     "Isso significa que a classe é a união das subclasses, as subclasses são mutuamente exclusivas, e a união cobre toda a classe. " +
                     "Exemplos: " +
                     "1) Animal é união disjunta de Gato e Cachorro → DisjointUnion(:Animal :Gato :Cachorro). " +
                     "2) SexoBiológico é união disjunta de Masculino e Feminino → DisjointUnion(:SexoBiologico :Masculino :Feminino). " +
                     "3) EstadoCivil é união disjunta de Solteiro, Casado, Divorciado e Viúvo → DisjointUnion(:EstadoCivil :Solteiro :Casado :Divorciado :Viuvo). " +
                     "Útil para definir classes como partição completa de outras classes.")
  public String createDisjointUnion(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Nome da classe que é a união disjunta", required = true) String className,
      @ToolParam(description = "Lista de classes que compõem a união disjunta", required = true) List<String> classes) {

    log.debug("Criando DisjointUnion: {} é união disjunta de {}", className, classes);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "DisjointUnion(" + className + " " + String.join(" ", classes) + ")";

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de DisjointUnion: {}", result);
    return result;
  }
}
