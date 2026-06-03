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
 * Tool para gerar axiomas ObjectPropertyRange.
 * <p>
 * Exemplo: "temPai leva a Pessoa" → ObjectPropertyRange(:temPai :Pessoa)
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class ObjectPropertyRangeTool extends AbstractOWLTool {

  private static final String PROMPT_TEMPLATE = """
      Você é um especialista em ontologias OWL 2 DL.

      Sua tarefa é converter descrições em linguagem natural em axiomas ObjectPropertyRange.

      Construtor: ObjectPropertyRange
      Descrição: Declara o range de uma propriedade de objeto (a classe dos valores permitidos).
      Sintaxe Manchester: ObjectPropertyRange(<propriedade> <classe>)

      Exemplos:
      - "temPai leva a Pessoa" → ObjectPropertyRange(:temPai :Pessoa)
      - "temFilho tem como range Pessoa" → ObjectPropertyRange(:temFilho :Pessoa)
      - "escreveLivro leva a Livro" → ObjectPropertyRange(:escreveLivro :Livro)

      Contexto ontológico atual:
      {context}

      Descrição a converter:
      {description}

      Retorne APENAS o axioma em sintaxe Manchester, sem explicações adicionais.
      """;

  public ObjectPropertyRangeTool(ChatModel chatModel,
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
  public String getConstructorName() {
    return "ObjectPropertyRange";
  }

  @Override
  public String getDescription() {
    return "Declara o range de uma propriedade de objeto";
  }

  @Override
  public List<String> getExamples() {
    return List.of(
        "temPai leva a Pessoa",
        "temFilho tem como range Pessoa",
        "escreveLivro leva a Livro"
    );
  }
}
