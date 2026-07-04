package com.ia.core.llm.service.template;

import com.ia.core.llm.model.prompt.FinalidadePromptEnum;
import com.ia.core.llm.model.template.Template;
import com.ia.core.llm.model.template.TemplateParameterEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementação de PromptTemplateService.
 * <p>
 * Fornece métodos para criação e manipulação de templates de prompt.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PromptTemplateServiceImpl implements PromptTemplateService {

  private final TemplateRepository templateRepository;

  @Override
  public Prompt createSimplePrompt(String document, String text) {
    PromptTemplate pt = new PromptTemplate("{document}\n\n{text}");
    pt.add("document", document);
    pt.add("text", text);
    return new Prompt(pt.createMessage());
  }

  @Override
  public Prompt createSystemPrompt(String document, String text, String systemTemplate,
      FinalidadePromptEnum finalidade, Map<String, Object> params) {
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

  @Override
  public String processTemplate(String templateId, Map<String, Object> params) {
    String templateContent = getTemplateById(templateId);
    PromptTemplate promptTemplate = new PromptTemplate(templateContent);
    return promptTemplate.render(params);
  }

  @Override
  public String getTemplateById(String templateId) {
    Optional<Template> template = templateRepository.findByIdentificador(templateId);
    if (template.isEmpty()) {
      log.warn("Template não encontrado: {}", templateId);
      throw new IllegalArgumentException("Template não encontrado: " + templateId);
    }
    return template.get().getConteudo();
  }
}
