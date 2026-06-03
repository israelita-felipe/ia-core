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
 * Tool para gerar axiomas ObjectPropertyChain.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class ObjectPropertyChainTool extends AbstractOWLTool {

  private static final String CONSTRUCTOR_NAME = "ObjectPropertyChain";

  private static final String PROMPT_TEMPLATE = """
      Você é um especialista em ontologias OWL 2 DL.
      Sua tarefa é converter descrições em linguagem natural em axiomas ObjectPropertyChain.
      Construtor: ObjectPropertyChain
      Descrição: Declara que uma propriedade é a composição de uma cadeia de propriedades.
      Sintaxe Manchester: SubObjectPropertyOf(ObjectPropertyChain(<propriedade1> <propriedade2>) <propriedade_resultante>)
      Exemplos:
      - "avoDe é cadeia de temPai e temPai" → SubObjectPropertyOf(ObjectPropertyChain(:temPai :temPai) :avoDe)
      Contexto ontológico atual: {context}
      Descrição a converter: {description}
      Retorne APENAS o axioma em sintaxe Manchester.
      """;

  public ObjectPropertyChainTool(ChatModel chatModel,
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
  public String getDescription() { return "Declara cadeia de propriedades de objeto"; }

  @Override
  public List<String> getExamples() {
    return List.of("avoDe é cadeia de temPai e temPai", "tioPaterno é cadeia de temPai e temIrmao");
  }
}
