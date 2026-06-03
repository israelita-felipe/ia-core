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
 * Tool para gerar axiomas DisjointObjectProperties.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class DisjointObjectPropertiesTool extends AbstractOWLTool {

  private static final String CONSTRUCTOR_NAME = "DisjointObjectProperties";

  public DisjointObjectPropertiesTool(ChatModel chatModel,
                                      LLMCommunicator llmCommunicator,
                                      DefaultOwlService owlService,
                                      TemplateService templateService,
                                      FerramentaService ferramentaService) {
    super(chatModel, llmCommunicator, owlService, templateService, ferramentaService);
  }

  @Override
  public String getConstructorName() { return CONSTRUCTOR_NAME; }

  @Override
  public String getDescription() { return "Declara propriedades de objeto disjuntas"; }

  private static final String PROMPT_TEMPLATE = """
      Você é um especialista em ontologias OWL 2 DL.
      Sua tarefa é converter descrições em linguagem natural em axiomas DisjointObjectProperties.
      Construtor: DisjointObjectProperties
      Descrição: Declara que duas propriedades de objeto são disjuntas.
      Sintaxe Manchester: DisjointObjectProperties(<propriedade1> <propriedade2>)
      Exemplos:
      - "esposa e irmã são disjuntas" → DisjointObjectProperties(:esposa :irma)
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
    return List.of("esposa e irmã são disjuntas", "pai e mãe são disjuntas");
  }
}
