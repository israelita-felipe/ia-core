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
 * Tool para gerar axiomas ComplementOf.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class ComplementOfTool extends AbstractOWLTool {

  private static final String CONSTRUCTOR_NAME = "ComplementOf";

  private static final String PROMPT_TEMPLATE = """
      Você é um especialista em ontologias OWL 2 DL.
      Sua tarefa é converter descrições em linguagem natural em axiomas ComplementOf.
      Construtor: ComplementOf
      Descrição: Declara que uma classe é o complemento de outra.
      Sintaxe Manchester: EquivalentClasses(<classe> ComplementOf(<outra_classe>))
      Exemplos:
      - "NãoMamífero é complemento de Mamífero" → EquivalentClasses(:NaoMamifero ComplementOf(:Mamifero))
      Contexto ontológico atual: {context}
      Descrição a converter: {description}
      Retorne APENAS o axioma em sintaxe Manchester.
      """;

  public ComplementOfTool(ChatModel chatModel,
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
  public String getDescription() { return "Declara complemento de classe"; }

  @Override
  public List<String> getExamples() {
    return List.of("NãoMamífero é complemento de Mamífero", "NãoHumano é complemento de Humano");
  }
}
