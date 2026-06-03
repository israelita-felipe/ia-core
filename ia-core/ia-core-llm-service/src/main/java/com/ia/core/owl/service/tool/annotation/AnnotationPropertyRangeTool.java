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
 * Tool para gerar axiomas AnnotationPropertyRange.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class AnnotationPropertyRangeTool extends AbstractOWLTool {

  private static final String CONSTRUCTOR_NAME = "AnnotationPropertyRange";

  private static final String PROMPT_TEMPLATE = """
      Você é um especialista em ontologias OWL 2 DL.
      Sua tarefa é converter descrições em linguagem natural em axiomas AnnotationPropertyRange.
      Construtor: AnnotationPropertyRange
      Descrição: Declara o range de uma propriedade de anotação.
      Sintaxe Manchester: AnnotationPropertyRange(<propriedade> <tipo_dado>)
      Exemplos:
      - "autor tem range xsd:string" → AnnotationPropertyRange(:autor xsd:string)
      Contexto ontológico atual: {context}
      Descrição a converter: {description}
      Retorne APENAS o axioma em sintaxe Manchester.
      """;

  public AnnotationPropertyRangeTool(ChatModel chatModel,
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
  public String getDescription() { return "Declara range de propriedade de anotação"; }

  @Override
  public List<String> getExamples() {
    return List.of("autor tem range xsd:string", "dataCriacao tem range xsd:dateTime");
  }
}
