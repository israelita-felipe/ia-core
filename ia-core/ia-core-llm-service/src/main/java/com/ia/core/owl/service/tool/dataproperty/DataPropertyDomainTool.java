package com.ia.core.owl.service.tool.dataproperty;

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
 * Tool para gerar axiomas DataPropertyDomain.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class DataPropertyDomainTool extends AbstractOWLTool {

  private static final String CONSTRUCTOR_NAME = "DataPropertyDomain";

  private static final String PROMPT_TEMPLATE = """
      Você é um especialista em ontologias OWL 2 DL.
      Sua tarefa é converter descrições em linguagem natural em axiomas DataPropertyDomain.
      Construtor: DataPropertyDomain
      Descrição: Declara o domínio de uma propriedade de dados.
      Sintaxe Manchester: DataPropertyDomain(<propriedade> <classe>)
      Exemplos:
      - "idade se aplica a Pessoa" → DataPropertyDomain(:idade :Pessoa)
      Contexto ontológico atual: {context}
      Descrição a converter: {description}
      Retorne APENAS o axioma em sintaxe Manchester.
      """;

  public DataPropertyDomainTool(ChatModel chatModel,
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
  public String getDescription() { return "Declara o domínio de uma propriedade de dados"; }

  @Override
  public List<String> getExamples() {
    return List.of("idade se aplica a Pessoa", "nome se aplica a Pessoa", "salário se aplica a Funcionário");
  }
}
