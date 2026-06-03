package com.ia.core.owl.service.tool.classexpression;

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
 * Tool para gerar axiomas UnionOf.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class UnionOfTool extends AbstractOWLTool {

  private static final String CONSTRUCTOR_NAME = "UnionOf";

  private static final String PROMPT_TEMPLATE = """
      Você é um especialista em ontologias OWL 2 DL.
      Sua tarefa é converter descrições em linguagem natural em axiomas UnionOf.
      Construtor: UnionOf
      Descrição: Declara que uma classe é a união de outras classes.
      Sintaxe Manchester: EquivalentClasses(<classe> UnionOf(<classe1> <classe2> ...))
      Exemplos:
      - "Animal é união de Mamífero e Réptil" → EquivalentClasses(:Animal UnionOf(:Mamifero :Reptil))
      Contexto ontológico atual: {context}
      Descrição a converter: {description}
      Retorne APENAS o axioma em sintaxe Manchester.
      """;

  public UnionOfTool(ChatModel chatModel,
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
  public String getDescription() { return "Declara união de classes"; }

  @Override
  public List<String> getExamples() {
    return List.of("Animal é união de Mamífero e Réptil", "Veículo é união de Carro e Moto");
  }
}
