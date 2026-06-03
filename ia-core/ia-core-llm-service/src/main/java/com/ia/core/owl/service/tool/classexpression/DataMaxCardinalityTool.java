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
 * Tool para gerar axiomas DataMaxCardinality.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class DataMaxCardinalityTool extends AbstractOWLTool {

  private static final String CONSTRUCTOR_NAME = "DataMaxCardinality";

  private static final String PROMPT_TEMPLATE = """
      Você é um especialista em ontologias OWL 2 DL.
      Sua tarefa é converter descrições em linguagem natural em axiomas DataMaxCardinality.
      Construtor: DataMaxCardinality
      Descrição: Declara que uma propriedade de dado tem no máximo N valores.
      Sintaxe Manchester: DataMaxCardinality(<n> <propriedade> <tipo_dado>)
      Exemplos:
      - "Pessoa tem no máximo 1 CPF" → SubClassOf(:Pessoa DataMaxCardinality(1 :temCPF xsd:string))
      Contexto ontológico atual: {context}
      Descrição a converter: {description}
      Retorne APENAS o axioma em sintaxe Manchester.
      """;

  public DataMaxCardinalityTool(ChatModel chatModel,
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
  public String getDescription() { return "Declara cardinalidade máxima para propriedade de dado"; }

  @Override
  public List<String> getExamples() {
    return List.of("Pessoa tem no máximo 1 CPF", "Produto tem no máximo 1 código");
  }
}
