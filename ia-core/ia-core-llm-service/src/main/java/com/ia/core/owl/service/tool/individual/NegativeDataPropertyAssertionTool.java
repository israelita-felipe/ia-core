package com.ia.core.owl.service.tool.individual;

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
 * Tool para gerar axiomas NegativeDataPropertyAssertion.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class NegativeDataPropertyAssertionTool extends AbstractOWLTool {

  private static final String CONSTRUCTOR_NAME = "NegativeDataPropertyAssertion";

  private static final String PROMPT_TEMPLATE = """
      Você é um especialista em ontologias OWL 2 DL.
      Sua tarefa é converter descrições em linguagem natural em axiomas NegativeDataPropertyAssertion.
      Construtor: NegativeDataPropertyAssertion
      Descrição: Declara que um indivíduo NÃO tem uma propriedade com um valor específico.
      Sintaxe Manchester: NegativeDataPropertyAssertion(<propriedade> <individuo> <valor>)
      Exemplos:
      - "João não tem idade 10" → NegativeDataPropertyAssertion(:temIdade :Joao 10)
      Contexto ontológico atual: {context}
      Descrição a converter: {description}
      Retorne APENAS o axioma em sintaxe Manchester.
      """;

  public NegativeDataPropertyAssertionTool(ChatModel chatModel,
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
  public String getDescription() { return "Declara negação de propriedade de dado"; }

  @Override
  public List<String> getExamples() {
    return List.of("João não tem idade 10", "Produto não tem preço 0");
  }
}
