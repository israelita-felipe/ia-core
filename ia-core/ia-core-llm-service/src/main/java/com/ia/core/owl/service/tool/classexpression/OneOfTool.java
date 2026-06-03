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
 * Tool para gerar axiomas OneOf.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class OneOfTool extends AbstractOWLTool {

  private static final String CONSTRUCTOR_NAME = "OneOf";

  private static final String PROMPT_TEMPLATE = """
      Você é um especialista em ontologias OWL 2 DL.
      Sua tarefa é converter descrições em linguagem natural em axiomas OneOf.
      Construtor: OneOf
      Descrição: Declara que uma classe é uma enumeração de indivíduos específicos.
      Sintaxe Manchester: EquivalentClasses(<classe> OneOf(<individuo1> <individuo2> ...))
      Exemplos:
      - "DiaDaSemana é enumeração de Segunda, Terça, Quarta, Quinta, Sexta, Sábado e Domingo" → EquivalentClasses(:DiaDaSemana OneOf(:Segunda :Terca :Quarta :Quinta :Sexta :Sabado :Domingo))
      Contexto ontológico atual: {context}
      Descrição a converter: {description}
      Retorne APENAS o axioma em sintaxe Manchester.
      """;

  public OneOfTool(ChatModel chatModel,
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
  public String getDescription() { return "Declara enumeração de indivíduos"; }

  @Override
  public List<String> getExamples() {
    return List.of("DiaDaSemana é enumeração de Segunda, Terça, Quarta, Quinta, Sexta, Sábado e Domingo", "Mes é enumeração de Janeiro, Fevereiro, Março, Abril, Maio, Junho, Julho, Agosto, Setembro, Outubro, Novembro e Dezembro");
  }
}
