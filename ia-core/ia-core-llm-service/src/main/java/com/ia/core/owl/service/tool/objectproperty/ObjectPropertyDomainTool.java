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
 * Tool para gerar axiomas ObjectPropertyDomain.
 * <p>
 * Exemplo: "A propriedade temPai se aplica a Pessoa" → ObjectPropertyDomain(:temPai :Pessoa)
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class ObjectPropertyDomainTool extends AbstractOWLTool {

  private static final String PROMPT_TEMPLATE = """
      Você é um especialista em ontologias OWL 2 DL.

      Sua tarefa é converter descrições em linguagem natural em axiomas ObjectPropertyDomain.

      Construtor: ObjectPropertyDomain
      Descrição: Declara o domínio de uma propriedade de objeto (a classe a que a propriedade se aplica).
      Sintaxe Manchester: ObjectPropertyDomain(<propriedade> <classe>)

      Exemplos:
      - "A propriedade temPai se aplica a Pessoa" → ObjectPropertyDomain(:temPai :Pessoa)
      - "temFilho é uma propriedade de Pessoa" → ObjectPropertyDomain(:temFilho :Pessoa)
      - "escreveLivro tem como domínio Autor" → ObjectPropertyDomain(:escreveLivro :Autor)

      Contexto ontológico atual:
      {context}

      Descrição a converter:
      {description}

      Retorne APENAS o axioma em sintaxe Manchester, sem explicações adicionais.
      """;

  public ObjectPropertyDomainTool(ChatModel chatModel,
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
    return "ObjectPropertyDomain";
  }

  @Override
  public String getDescription() {
    return "Declara o domínio de uma propriedade de objeto";
  }

  @Override
  public List<String> getExamples() {
    return List.of(
        "A propriedade temPai se aplica a Pessoa",
        "temFilho é uma propriedade de Pessoa",
        "escreveLivro tem como domínio Autor"
    );
  }
}
