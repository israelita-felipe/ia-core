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
 * Tool para gerar axiomas ObjectExactCardinality.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class ObjectExactCardinalityTool extends AbstractOWLTool {

  private static final String CONSTRUCTOR_NAME = "ObjectExactCardinality";

  private static final String PROMPT_TEMPLATE = """
      Você é um especialista em ontologias OWL 2 DL.
      Sua tarefa é converter descrições em linguagem natural em axiomas ObjectExactCardinality.
      Construtor: ObjectExactCardinality
      Descrição: Declara que uma propriedade tem exatamente N valores.
      Sintaxe Manchester: ObjectExactCardinality(<n> <propriedade> <classe>)
      Exemplos:
      - "Casal tem exatamente 2 membros" → SubClassOf(:Casal ObjectExactCardinality(2 :temMembro :Pessoa))
      Contexto ontológico atual: {context}
      Descrição a converter: {description}
      Retorne APENAS o axioma em sintaxe Manchester.
      """;

  public ObjectExactCardinalityTool(ChatModel chatModel,
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
  public String getDescription() { return "Declara cardinalidade exata para propriedade de objeto"; }

  @Override
  public List<String> getExamples() {
    return List.of("Casal tem exatamente 2 membros", "Bicicleta tem exatamente 2 rodas");
  }
}
