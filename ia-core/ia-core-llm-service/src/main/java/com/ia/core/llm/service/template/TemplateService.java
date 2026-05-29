package com.ia.core.llm.service.template;

import com.ia.core.llm.model.template.Template;
import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.llm.service.model.template.TemplateUseCase;
import com.ia.core.service.CrudBaseService;
import lombok.extern.slf4j.Slf4j;

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

  /**
   * @param repository
   * @param mapper
   * @param searchRequestMapper
   * @param translator
   */
  public TemplateService(TemplateServiceConfig config) {
    super(config);
  }
}
