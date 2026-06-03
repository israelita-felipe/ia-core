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
 * Tool para gerar axiomas InverseObjectProperties.
 * <p>
 * Gera axiomas que definem propriedades inversas entre duas propriedades de objeto.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class InverseObjectPropertiesTool extends AbstractOWLTool {

  private static final String CONSTRUCTOR_NAME = "InverseObjectProperties";

  public InverseObjectPropertiesTool(ChatModel chatModel,
                                    LLMCommunicator llmCommunicator,
                                    DefaultOwlService owlService,
                                    TemplateService templateService,
                                    FerramentaService ferramentaService) {
    super(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Override
  public String getConstructorName() {
    return CONSTRUCTOR_NAME;
  }

  @Override
  public String getDescription() {
    return "Gera axiomas InverseObjectProperties para definir propriedades inversas";
  }

  private static final String PROMPT_TEMPLATE = """
      Você é um especialista em ontologias OWL 2 DL.

      Sua tarefa é converter descrições em linguagem natural em axiomas InverseObjectProperties.

      Construtor: InverseObjectProperties
      Descrição: Declara que duas propriedades de objeto são inversas uma da outra.
      Sintaxe Manchester: InverseObjectProperties(<propriedade1> <propriedade2>)

      Exemplos:
      - "temPai é inverso de temFilho" → InverseObjectProperties(:temPai :temFilho)
      - "éCônjugeDe é inverso de éCônjugeDe" → InverseObjectProperties(:éCônjugeDe :éCônjugeDe)
      - "éAntecessorDe é inverso de éSucessorDe" → InverseObjectProperties(:éAntecessorDe :éSucessorDe)

      Contexto ontológico atual:
      {context}

      Descrição a converter:
      {description}

      Retorne APENAS o axioma em sintaxe Manchester, sem explicações adicionais.
      """;

  @Override
  public String getPromptTemplate() {
    return PROMPT_TEMPLATE;
  }

  @Override
  public List<String> getExamples() {
    return List.of(
        "temPai é inverso de temFilho",
        "éCônjugeDe é inverso de éCônjugeDe",
        "éAntecessorDe é inverso de éSucessorDe"
    );
  }
}
