package com.ia.core.owl.service.tool.dataproperty;

import com.ia.core.llm.service.ferramenta.FerramentaService;
import com.ia.core.llm.service.template.TemplateService;
import com.ia.core.owl.service.DefaultOwlService;
import com.ia.core.owl.service.LLMCommunicator;
import com.ia.core.owl.service.tool.base.AbstractOWLTool;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FunctionalDataPropertyTool extends AbstractOWLTool {

  public FunctionalDataPropertyTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService,
                                     TemplateService templateService, FerramentaService ferramentaService) {
    super(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Override
  public String getConstructorName() { return "FunctionalDataProperty"; }

  @Override
  public String getDescription() { return "Gera axiomas FunctionalDataProperty"; }

  private static final String PROMPT_TEMPLATE = """
      Você é um especialista em ontologias OWL 2 DL.
      Sua tarefa é converter descrições em linguagem natural em axiomas FunctionalDataProperty.
      Construtor: FunctionalDataProperty
      Descrição: Declara que uma propriedade de dados é funcional.
      Sintaxe Manchester: FunctionalDataProperty(<propriedade>)
      Exemplos:
      - "CPF é funcional" → FunctionalDataProperty(:CPF)
      Contexto ontológico atual: {context}
      Descrição a converter: {description}
      Retorne APENAS o axioma em sintaxe Manchester.
      """;

  @Override
  public String getPromptTemplate() {
    return PROMPT_TEMPLATE;
  }

  @Override
  public List<String> getExamples() { return List.of("CPF é funcional", "RG é funcional"); }
}
