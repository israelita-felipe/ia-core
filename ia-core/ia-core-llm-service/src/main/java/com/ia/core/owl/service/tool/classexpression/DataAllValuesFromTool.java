package com.ia.core.owl.service.tool.classexpression;

import com.ia.core.llm.service.ferramenta.FerramentaService;
import com.ia.core.llm.service.template.TemplateService;
import com.ia.core.owl.service.DefaultOwlService;
import com.ia.core.owl.service.LLMCommunicator;
import com.ia.core.owl.service.tool.base.AbstractOWLTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Tool para gerar axiomas DataAllValuesFrom.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class DataAllValuesFromTool extends AbstractOWLTool {

  private static final String CONSTRUCTOR_NAME = "DataAllValuesFrom";

  private static final String PROMPT_TEMPLATE = """
      Você é um especialista em ontologias OWL 2 DL.
      Sua tarefa é converter descrições em linguagem natural em axiomas DataAllValuesFrom.
      Construtor: DataAllValuesFrom
      Descrição: Declara que todos os valores da propriedade pertencem a um tipo de dado.
      Sintaxe Manchester: DataAllValuesFrom(<propriedade> <tipo_dado>)
      Exemplos:
      - "Adulto tem apenas idade que é integer >= 18" → SubClassOf(:Adulto DataAllValuesFrom(:temIdade xsd:integer[>=18]))
      Contexto ontológico atual: {context}
      Descrição a converter: {description}
      Retorne APENAS o axioma em sintaxe Manchester.
      """;

  public DataAllValuesFromTool(ChatModel chatModel,
                               LLMCommunicator llmCommunicator,
                               DefaultOwlService owlService,
                               TemplateService templateService,
                               FerramentaService ferramentaService) {
    super(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Override
  public String getPromptTemplate() {
    return PROMPT_TEMPLATE;
  }

  @Override
  public String getConstructorName() { return CONSTRUCTOR_NAME; }

  @Override
  public String getDescription() { return "Declara restrição universal de dado"; }

  @Override
  public List<String> getExamples() {
    return List.of("Adulto tem apenas idade que é integer >= 18", "Produto tem apenas preço que é decimal >= 0");
  }
}
