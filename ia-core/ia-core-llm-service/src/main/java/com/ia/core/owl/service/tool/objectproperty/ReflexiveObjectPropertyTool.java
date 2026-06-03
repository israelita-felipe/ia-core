package com.ia.core.owl.service.tool.objectproperty;

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
 * Tool para gerar axiomas ReflexiveObjectProperty.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class ReflexiveObjectPropertyTool extends AbstractOWLTool {

  private static final String CONSTRUCTOR_NAME = "ReflexiveObjectProperty";

  public ReflexiveObjectPropertyTool(ChatModel chatModel,
                                   LLMCommunicator llmCommunicator,
                                   DefaultOwlService owlService,
                                   TemplateService templateService,
                                   FerramentaService ferramentaService) {
    super(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Override
  public String getConstructorName() { return CONSTRUCTOR_NAME; }

  @Override
  public String getDescription() { return "Declara propriedade de objeto reflexiva"; }

  private static final String PROMPT_TEMPLATE = """
      Você é um especialista em ontologias OWL 2 DL.
      Sua tarefa é converter descrições em linguagem natural em axiomas ReflexiveObjectProperty.
      Construtor: ReflexiveObjectProperty
      Descrição: Declara que a propriedade é reflexiva (todo indivíduo se relaciona consigo mesmo).
      Sintaxe Manchester: ReflexiveObjectProperty(<propriedade>)
      Exemplos:
      - "conhece é reflexiva" → ReflexiveObjectProperty(:conhece)
      Contexto ontológico atual: {context}
      Descrição a converter: {description}
      Retorne APENAS o axioma em sintaxe Manchester.
      """;

  @Override
  public String getPromptTemplate() {
    return PROMPT_TEMPLATE;
  }

  @Override
  public List<String> getExamples() {
    return List.of("conhece é reflexiva", "temRelacaoConsigoMesmo é reflexiva");
  }
}
