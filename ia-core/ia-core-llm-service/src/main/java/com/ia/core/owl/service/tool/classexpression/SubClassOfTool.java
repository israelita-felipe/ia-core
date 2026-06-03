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
 * Tool para gerar axiomas SubClassOf.
 * <p>
 * Exemplo: "Todo cachorro é um mamífero" → SubClassOf(:Cachorro :Mamifero)
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class SubClassOfTool extends AbstractOWLTool  {

  private static final String PROMPT_TEMPLATE = """
      Você é um especialista em ontologias OWL 2 DL.

      Sua tarefa é converter descrições em linguagem natural em axiomas SubClassOf.

      Construtor: SubClassOf
      Descrição: Declara que uma classe é subclasse de outra classe.
      Sintaxe Manchester: SubClassOf(<subclasse> <superclasse>)

      Exemplos:
      - "Todo cachorro é um mamífero" → SubClassOf(:Cachorro :Mamifero)
      - "Todos os estudantes são pessoas" → SubClassOf(:Estudante :Pessoa)
      - "Carros elétricos são um tipo de veículo" → SubClassOf(:CarroEletrico :Veiculo)

      Contexto ontológico atual:
      {context}

      Descrição a converter:
      {description}

      Retorne APENAS o axioma em sintaxe Manchester, sem explicações adicionais.
      """;

  public SubClassOfTool(ChatModel chatModel,
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
    return "SubClassOf";
  }

  @Override
  public String getDescription() {
    return "Declara que uma classe é subclasse de outra classe";
  }

  @Override
  public List<String> getExamples() {
    return List.of(
        "Todo cachorro é um mamífero",
        "Todos os estudantes são pessoas",
        "Carros elétricos são um tipo de veículo"
    );
  }
}
