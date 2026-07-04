package com.ia.core.owl.service.tool.dataproperty;


import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de domínio de propriedade de dado OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar domínios de propriedades de dado
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * Representa domínio de propriedade de dado (Domain(U) ⊆ C).
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * DataPropertyDomain é um axioma que especifica que se uma propriedade de dado U relaciona um indivíduo x a algum valor,
 * então x deve ser uma instância da classe C. Restringe o domínio da propriedade de dado a uma classe específica.
 * <p>
 * <b>Sintaxe Manchester:</b> DataPropertyDomain(:Propriedade :Classe)
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>idade se aplica a Pessoa:
 *       DataPropertyDomain(:idade :Pessoa)</li>
 *   <li>nome se aplica a Pessoa:
 *       DataPropertyDomain(:nome :Pessoa)</li>
 *   <li>preço se aplica a Produto:
 *       DataPropertyDomain(:preço :Produto)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class DataPropertyDomainTool extends OwlConstructorTool {

  public DataPropertyDomainTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria um domínio de propriedade de dado na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param property Propriedade de dado
   * @param domain Classe domínio
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria um domínio de propriedade de dado OWL 2 DL na ontologia da sessão. " +
                     "Representa domínio de propriedade de dado (Domain(U) ⊆ C). " +
                     "Especifica que se uma propriedade de dado relaciona um indivíduo x a algum valor, então x deve ser uma instância da classe especificada. " +
                     "Exemplos: " +
                     "1) idade se aplica a Pessoa → DataPropertyDomain(:idade :Pessoa). " +
                     "2) nome se aplica a Pessoa → DataPropertyDomain(:nome :Pessoa). " +
                     "3) preço se aplica a Produto → DataPropertyDomain(:preço :Produto).")
  public String createDataPropertyDomain(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Propriedade de dado", required = true) String property,
      @ToolParam(description = "Classe domínio", required = true) String domain) {

    log.debug("Criando DataPropertyDomain: Domain({}) ⊆ {}", property, domain);

    // Constrói o axioma em Manchester OWL Syntax
    String manchesterAxiom = "DataPropertyDomain: " + property + " " + domain;

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    String result = createAxiom(sessionId, manchesterAxiom);

    log.debug("Resultado da criação de DataPropertyDomain: {}", result);
    return result;
  }
}
