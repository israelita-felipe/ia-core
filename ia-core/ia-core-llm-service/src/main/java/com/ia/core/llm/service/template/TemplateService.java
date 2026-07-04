package com.ia.core.llm.service.template;

import com.ia.core.llm.model.template.Template;
import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.llm.service.model.template.TemplateUseCase;
import com.ia.core.service.CrudBaseService;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Serviço para gerenciamento de templates.
 * <p>
 * Implementa operações CRUD para templates utilizados em prompts de modelos
 * de linguagem.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
public class TemplateService
  extends CrudBaseService<Template, TemplateDTO>
  implements TemplateUseCase {

  public TemplateService(TemplateServiceConfig config) {
    super(Objects.requireNonNull(config, "config não pode ser null"));
  }

  /**
   * Carrega um template pelo seu identificador (ID string).
   *
   * @param templateId identificador do template
   * @return DTO do template
   */
  public Optional<TemplateDTO> loadById(String templateId) {
    Objects.requireNonNull(templateId, "templateId não pode ser null");
    log.debug("Carregando template por ID: {}", templateId);
    return getRepository().findByIdentificador(templateId)
        .map(getMapper()::toDTO);
  }

  @Override
  public TemplateRepository getRepository() {
    return (TemplateRepository) super.getRepository();
  }

  /**
   * Processa um template substituindo os parâmetros pelos valores fornecidos.
   *
   * @param template DTO do template
   * @param params mapa de parâmetros para substituição
   * @return template processado com os parâmetros substituídos
   */
  public String processTemplate(TemplateDTO template, Map<String, Object> params) {
    Objects.requireNonNull(template, "template não pode ser null");
    if (template.getConteudo() == null) {
      return "";
    }

    String content = template.getConteudo();

    if (params == null || params.isEmpty()) {
      return content;
    }

    for (Map.Entry<String, Object> entry : params.entrySet()) {
      String placeholder = "{" + entry.getKey() + "}";
      String value = entry.getValue() != null ? entry.getValue().toString() : "";
      content = content.replace(placeholder, value);
    }

    log.debug("Template processado: {}", template.getIdentificador());
    return content;
  }
}
