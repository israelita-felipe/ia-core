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
public class ClassAssertionTool extends AbstractOWLTool {

  public ClassAssertionTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService,
                           TemplateService templateService, FerramentaService ferramentaService) {
    super(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Override
  public String getConstructorName() { return "ClassAssertion"; }

  @Override
  public String getDescription() { return "Declara que um indivíduo é instância de uma classe"; }

  private static final String PROMPT_TEMPLATE = """
      Você é um especialista em ontologias OWL 2 DL.
      Sua tarefa é converter descrições em linguagem natural em axiomas ClassAssertion.
      Construtor: ClassAssertion
      Descrição: Declara que um indivíduo é instância de uma classe.
      Sintaxe Manchester: ClassAssertion(<individuo> <classe>)
      Exemplos:
      - "João é uma Pessoa" → ClassAssertion(:João :Pessoa)
      Contexto ontológico atual: {context}
      Descrição a converter: {description}
      Retorne APENAS o axioma em sintaxe Manchester.
      """;

  @Override
  public String getPromptTemplate() {
    return PROMPT_TEMPLATE;
  }

  @Override
  public List<String> getExamples() { return List.of("João é uma Pessoa", "Maria é uma Pessoa"); }
}
