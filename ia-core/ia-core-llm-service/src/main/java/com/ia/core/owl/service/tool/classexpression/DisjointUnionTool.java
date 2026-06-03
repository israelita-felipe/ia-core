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
 * Tool para gerar axiomas DisjointUnion.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class DisjointUnionTool extends AbstractOWLTool {

  private static final String CONSTRUCTOR_NAME = "DisjointUnion";

  private static final String PROMPT_TEMPLATE = """
      Você é um especialista em ontologias OWL 2 DL.
      Sua tarefa é converter descrições em linguagem natural em axiomas DisjointUnion.
      Construtor: DisjointUnion
      Descrição: Declara que uma classe é a união disjunta de outras classes.
      Sintaxe Manchester: DisjointUnion(<classe> <classe1> <classe2> ...)
      Exemplos:
      - "Animal é união disjunta de Gato e Cachorro" → DisjointUnion(:Animal :Gato :Cachorro)
      Contexto ontológico atual: {context}
      Descrição a converter: {description}
      Retorne APENAS o axioma em sintaxe Manchester.
      """;

  public DisjointUnionTool(ChatModel chatModel,
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
  public String getDescription() { return "Declara união disjunta de classes"; }

  @Override
  public List<String> getExamples() {
    return List.of("Animal é união disjunta de Gato e Cachorro", "Veículo é união disjunta de Carro e Moto");
  }
}
