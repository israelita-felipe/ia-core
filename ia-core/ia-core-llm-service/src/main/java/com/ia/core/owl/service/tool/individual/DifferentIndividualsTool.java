package com.ia.core.owl.service.tool.individual;

import com.ia.core.llm.service.ferramenta.FerramentaService;
import com.ia.core.llm.service.template.TemplateService;
import com.ia.core.owl.service.DefaultOwlService;
import com.ia.core.owl.service.LLMCommunicator;
import com.ia.core.owl.service.tool.base.AbstractOWLTool;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DifferentIndividualsTool extends AbstractOWLTool {

  public DifferentIndividualsTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService,
                               TemplateService templateService, FerramentaService ferramentaService) {
    super(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Override
  public String getConstructorName() { return "DifferentIndividuals"; }

  @Override
  public String getDescription() { return "Declara que dois indivíduos são diferentes"; }

  private static final String PROMPT_TEMPLATE = """
      Você é um especialista em ontologias OWL 2 DL.
      Sua tarefa é converter descrições em linguagem natural em axiomas DifferentIndividuals.
      Construtor: DifferentIndividuals
      Descrição: Declara que dois indivíduos são diferentes.
      Sintaxe Manchester: DifferentIndividuals(<individuo1> <individuo2>)
      Exemplos:
      - "Tom e Jerry são diferentes" → DifferentIndividuals(:Tom :Jerry)
      Contexto ontológico atual: {context}
      Descrição a converter: {description}
      Retorne APENAS o axioma em sintaxe Manchester.
      """;

  @Override
  public String getPromptTemplate() {
    return PROMPT_TEMPLATE;
  }

  @Override
  public List<String> getExamples() { return List.of("Tom e Jerry são diferentes", "João e Maria são diferentes"); }
}
