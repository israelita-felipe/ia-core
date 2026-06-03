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
 * Tool para gerar axiomas DataMinCardinality.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class DataMinCardinalityTool extends AbstractOWLTool {

  private static final String CONSTRUCTOR_NAME = "DataMinCardinality";

  private static final String PROMPT_TEMPLATE = """
      Você é um especialista em ontologias OWL 2 DL.
      Sua tarefa é converter descrições em linguagem natural em axiomas DataMinCardinality.
      Construtor: DataMinCardinality
      Descrição: Declara que uma propriedade de dado tem pelo menos N valores.
      Sintaxe Manchester: DataMinCardinality(<n> <propriedade> <tipo_dado>)
      Exemplos:
      - "Pessoa tem pelo menos 1 nome" → SubClassOf(:Pessoa DataMinCardinality(1 :temNome xsd:string))
      Contexto ontológico atual: {context}
      Descrição a converter: {description}
      Retorne APENAS o axioma em sintaxe Manchester.
      """;

  public DataMinCardinalityTool(ChatModel chatModel,
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
  public String getDescription() { return "Declara cardinalidade mínima para propriedade de dado"; }

  @Override
  public List<String> getExamples() {
    return List.of("Pessoa tem pelo menos 1 nome", "Produto tem pelo menos 1 preço");
  }
}
