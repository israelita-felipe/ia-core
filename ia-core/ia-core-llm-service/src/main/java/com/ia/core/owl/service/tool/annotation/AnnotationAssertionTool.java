package com.ia.core.owl.service.tool.annotation;

import com.ia.core.llm.service.ferramenta.FerramentaService;
import com.ia.core.llm.service.template.TemplateService;
import com.ia.core.owl.service.DefaultOwlService;
import com.ia.core.owl.service.LLMCommunicator;
import com.ia.core.owl.service.tool.base.AbstractOWLTool;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnnotationAssertionTool extends AbstractOWLTool {

  private static final String PROMPT_TEMPLATE = """
      Você é um especialista em ontologias OWL 2 DL.
      Sua tarefa é converter descrições em linguagem natural em axiomas AnnotationAssertion.
      Construtor: AnnotationAssertion
      Descrição: Declara uma anotação sobre uma entidade.
      Sintaxe Manchester: AnnotationAssertion(<propriedade_anotacao> <entidade> <valor>)
      Exemplos:
      - "Pessoa tem rdfs:label 'Pessoa'" → AnnotationAssertion(rdfs:label :Pessoa "Pessoa")
      Contexto ontológico atual: {context}
      Descrição a converter: {description}
      Retorne APENAS o axioma em sintaxe Manchester.
      """;

  public AnnotationAssertionTool(ChatModel chatModel, LLMCommunicator llmCommunicator, DefaultOwlService owlService,
                                 TemplateService templateService, FerramentaService ferramentaService) {
    super(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Override
  public String getPromptTemplate() {
    return PROMPT_TEMPLATE;
  }

  @Override
  public String getConstructorName() { return "AnnotationAssertion"; }

  @Override
  public String getDescription() { return "Gera axiomas AnnotationAssertion"; }

  @Override
  public List<String> getExamples() { return List.of("Pessoa tem rdfs:label 'Pessoa'", "temFilho tem rdfs:comment 'Filho de'"); }
}
