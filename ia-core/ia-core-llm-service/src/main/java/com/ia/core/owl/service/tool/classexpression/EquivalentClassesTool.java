package com.ia.core.owl.service.tool.classexpression;

import com.ia.core.llm.service.ferramenta.FerramentaService;
import com.ia.core.llm.service.template.TemplateService;
import com.ia.core.owl.service.DefaultOwlService;
import com.ia.core.owl.service.LLMCommunicator;
import com.ia.core.owl.service.tool.base.AbstractOWLTool;
import com.ia.core.owl.service.tool.base.OWLTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Tool para gerar axiomas EquivalentClasses.
 * <p>
 * Exemplo: "Homem e MachoHumano são a mesma coisa" → EquivalentClasses(:Homem :MachoHumano)
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class EquivalentClassesTool extends AbstractOWLTool implements OWLTool {

  private static final String PROMPT_TEMPLATE = """
      Você é um especialista em ontologias OWL 2 DL.

      Sua tarefa é converter descrições em linguagem natural em axiomas EquivalentClasses.

      Construtor: EquivalentClasses
      Descrição: Declara que duas classes são equivalentes (têm a mesma extensão).
      Sintaxe Manchester: EquivalentClasses(<classe1> <classe2>)

      Exemplos:
      - "Homem e MachoHumano são a mesma coisa" → EquivalentClasses(:Homem :MachoHumano)
      - "Criança e Menor são equivalentes" → EquivalentClasses(:Crianca :Menor)
      - "Professor e Docente são sinônimos" → EquivalentClasses(:Professor :Docente)

      Contexto ontológico atual:
      {context}

      Descrição a converter:
      {description}

      Retorne APENAS o axioma em sintaxe Manchester, sem explicações adicionais.
      """;

  public EquivalentClassesTool(ChatModel chatModel,
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
    return "EquivalentClasses";
  }

  @Override
  public String getDescription() {
    return "Declara que duas classes são equivalentes";
  }

  @Override
  public List<String> getExamples() {
    return List.of(
        "Homem e MachoHumano são a mesma coisa",
        "Criança e Menor são equivalentes",
        "Professor e Docente são sinônimos"
    );
  }
}
