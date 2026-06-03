package com.ia.core.owl.service.tool.annotation;

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
 * Tool para gerar axiomas AnnotationPropertyDomain.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class AnnotationPropertyDomainTool extends AbstractOWLTool {

  private static final String CONSTRUCTOR_NAME = "AnnotationPropertyDomain";

  private static final String PROMPT_TEMPLATE = """
      Você é um especialista em ontologias OWL 2 DL.
      Sua tarefa é converter descrições em linguagem natural em axiomas AnnotationPropertyDomain.
      Construtor: AnnotationPropertyDomain
      Descrição: Declara o domínio de uma propriedade de anotação.
      Sintaxe Manchester: AnnotationPropertyDomain(<propriedade> <classe>)
      Exemplos:
      - "autor tem domínio Pessoa" → AnnotationPropertyDomain(:autor :Pessoa)
      Contexto ontológico atual: {context}
      Descrição a converter: {description}
      Retorne APENAS o axioma em sintaxe Manchester.
      """;

  public AnnotationPropertyDomainTool(ChatModel chatModel,
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
  public String getDescription() { return "Declara domínio de propriedade de anotação"; }

  @Override
  public List<String> getExamples() {
    return List.of("autor tem domínio Pessoa", "dataCriacao tem domínio Entidade");
  }
}
