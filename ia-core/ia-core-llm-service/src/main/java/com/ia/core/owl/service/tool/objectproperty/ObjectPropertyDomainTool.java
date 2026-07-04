package com.ia.core.owl.service.tool.objectproperty;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de domínio de propriedade de objeto OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar domínios de propriedades de objeto
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa domínio de propriedade de objeto (Domain(R) ⊆ C).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * ObjectPropertyDomain é um axioma que especifica que se uma propriedade de objeto R relaciona um indivíduo x a algum indivíduo,
 * então x deve ser uma instância da classe C. Restringe o domínio da propriedade a uma classe específica.
 * <p>
 * <b>Sintaxe Manchester:</b> ObjectPropertyDomain(:Propriedade :Classe)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>temFilho é uma propriedade entre pessoas:
 *       ObjectPropertyDomain(:temFilho :Pessoa)</li>
 *   <li>conhece é uma propriedade social entre agentes:
 *       ObjectPropertyDomain(:conhece :Agente)</li>
 *   <li>parteDe é uma propriedade mereológica entre objetos:
 *       ObjectPropertyDomain(:parteDe :Objeto)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class ObjectPropertyDomainTool extends OwlConstructorTool {

  public ObjectPropertyDomainTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria um domínio de propriedade de objeto na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param property Propriedade de objeto
   * @param domain Classe domínio
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria um domínio de propriedade de objeto OWL 2 DL na ontologia da sessão. " +
                     "Representa domínio de propriedade de objeto (Domain(R) ⊆ C). " +
                     "Especifica que se uma propriedade relaciona um indivíduo x a algum indivíduo, então x deve ser uma instância da classe especificada. " +
                     "Exemplos: " +
                     "1) temFilho é uma propriedade entre pessoas → ObjectPropertyDomain(:temFilho :Pessoa). " +
                     "2) conhece é uma propriedade social entre agentes → ObjectPropertyDomain(:conhece :Agente). " +
                     "3) parteDe é uma propriedade mereológica entre objetos → ObjectPropertyDomain(:parteDe :Objeto).")
  public String createObjectPropertyDomain(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Propriedade de objeto", required = true) String property,
      @ToolParam(description = "Classe domínio", required = true) String domain) {

    log.debug("Criando ObjectPropertyDomain: Domain({}) ⊆ {}", property, domain);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "ObjectPropertyDomain: " + property + " " + domain;

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de ObjectPropertyDomain: {}", result);
    return result;
  }
}
