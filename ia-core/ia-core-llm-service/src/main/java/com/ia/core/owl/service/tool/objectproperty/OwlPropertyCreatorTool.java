package com.ia.core.owl.service.tool.objectproperty;


import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Tool para criação de propriedades OWL 2 DL.
 * <p>
 * Extende OwlConstructorTool e usa owlService para criar propriedades
 * em Manchester OWL Syntax com validação automática de consistência.
 * <p>
 * <b>Definição Formal OWL 2 DL:</b>
 * Declaração de propriedade OWL (ObjectProperty(R) ou DataProperty(U)).
 * Uma propriedade de objeto relaciona indivíduos a indivíduos, enquanto uma propriedade de dado relaciona indivíduos a valores literais.
 * Permite especificar domínio, range e propriedade inversa em uma única declaração.
 * <p>
 * <b>Sintaxe Manchester:</b> ObjectProperty: :Propriedade Domain: :Classe Range: :Classe InverseOf: :PropriedadeInversa
 * <p>
 * <b>Exemplos:</b>
 * <ul>
 *   <li>Cria propriedade de objeto temFilho com domínio Pessoa e range Pessoa:
 *       ObjectProperty: :temFilho Domain: :Pessoa Range: :Pessoa</li>
 *   <li>Cria propriedade de objeto temPai com inversa temFilho:
 *       ObjectProperty: :temPai InverseOf: :temFilho</li>
 *   <li>Cria propriedade de dado idade com domínio Pessoa e range xsd:integer:
 *       DataProperty: :idade Domain: :Pessoa Range: xsd:integer</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class OwlPropertyCreatorTool extends OwlConstructorTool {

  public OwlPropertyCreatorTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria uma propriedade OWL 2 DL (object property ou data property) na ontologia da sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @param propertyType Tipo de propriedade: 'object' para OWLObjectProperty ou 'data' para OWLDataProperty
   * @param propertyName Nome da propriedade
   * @param domain Domínio da propriedade (classe OWL)
   * @param range Range da propriedade (classe OWL ou datatype)
   * @param inverseProperty Propriedade inversa (opcional)
   * @return resultado da operação com feedback sobre consistência
   */
  @Tool(description = "Cria uma propriedade OWL 2 DL (object property ou data property) na ontologia da sessão. " +
                     "Declaração de propriedade OWL (ObjectProperty(R) ou DataProperty(U)). " +
                     "Permite especificar domínio, range e propriedade inversa. " +
                     "Exemplos: " +
                     "1) Cria propriedade de objeto temFilho com domínio Pessoa e range Pessoa → ObjectProperty: :temFilho Domain: :Pessoa Range: :Pessoa. " +
                     "2) Cria propriedade de objeto temPai com inversa temFilho → ObjectProperty: :temPai InverseOf: :temFilho. " +
                     "3) Cria propriedade de dado idade com domínio Pessoa e range xsd:integer → DataProperty: :idade Domain: :Pessoa Range: xsd:integer.")
  public String createProperty(
      @ToolParam(description = "ID da sessão de conversação", required = true) String sessionId,
      @ToolParam(description = "Tipo de propriedade: 'object' para OWLObjectProperty ou 'data' para OWLDataProperty", required = true) String propertyType,
      @ToolParam(description = "Nome da propriedade", required = true) String propertyName,
      @ToolParam(description = "Domínio da propriedade (classe OWL)", required = false) String domain,
      @ToolParam(description = "Range da propriedade (classe OWL ou datatype)", required = false) String range,
      @ToolParam(description = "Propriedade inversa", required = false) String inverseProperty) {

    log.debug("Criando propriedade OWL: {} tipo: {}", propertyName, propertyType);

    // Usa owlService para criar a propriedade em Manchester OWL Syntax
    String manchesterAxiom;
    StringBuilder result = new StringBuilder();

    if ("object".equals(propertyType)) {
      manchesterAxiom = "ObjectProperty: " + propertyName;
      if (domain != null) {
        manchesterAxiom += " Domain: " + domain;
      }
      if (range != null) {
        manchesterAxiom += " Range: " + range;
      }
      if (inverseProperty != null) {
        manchesterAxiom += " InverseOf: " + inverseProperty;
      }
      result.append("ObjectProperty criada: ").append(propertyName).append("\n");
    } else {
      manchesterAxiom = "DataProperty: " + propertyName;
      if (domain != null) {
        manchesterAxiom += " Domain: " + domain;
      }
      if (range != null) {
        manchesterAxiom += " Range: " + range;
      }
      result.append("DataProperty criada: ").append(propertyName).append("\n");
    }

    // Usa OwlConstructorTool.createAxiom para adicionar via owlService
    result.append(createAxiom(sessionId, manchesterAxiom));

    log.debug("Resultado da criação de propriedade: {}", result);
    return result.toString();
  }
}
