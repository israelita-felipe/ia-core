package com.ia.core.llm.service.template;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;

import com.ia.core.llm.model.comando.FinalidadeComandoEnum;
import com.ia.core.llm.model.template.TemplateParameterEnum;

/**
 * Implementação de PromptTemplateService.
 * Fornece métodos para criação e manipulação de templates de prompt.
 *
 * @author Israel Araújo
 */
public class PromptTemplateServiceImpl implements PromptTemplateService {

  @Override
  public Prompt createSimplePrompt(String document, String text) {
    PromptTemplate pt = new PromptTemplate("{document}\n\n{text}");
    pt.add("document", document);
    pt.add("text", text);
    return new Prompt(pt.createMessage());
  }

  @Override
  public Prompt createSystemPrompt(String document, String text, String systemTemplate,
      FinalidadeComandoEnum finalidade, Map<String, Object> params) {
    SystemPromptTemplate promptTemplate = new SystemPromptTemplate(systemTemplate);

    promptTemplate.add(
        TemplateParameterEnum.TEMPLATE_PARAM_DOCUMENTO.getNome(),
        document);

    Message message = promptTemplate.createMessage(params);

    PromptTemplate pt = new PromptTemplate("{document}\n\n{text}");
    pt.add("document", document);
    pt.add("text", text);

    return new Prompt(List.of(message, pt.createMessage()));
  }

  @Override
  public Message createSystemMessage(String systemTemplate, Map<String, Object> params) {
    SystemPromptTemplate promptTemplate = new SystemPromptTemplate(systemTemplate);
    return promptTemplate.createMessage(params);
  }

  @Override
  public PromptTemplate createPromptTemplate(String templateString) {
    return new PromptTemplate(templateString);
  }
}
