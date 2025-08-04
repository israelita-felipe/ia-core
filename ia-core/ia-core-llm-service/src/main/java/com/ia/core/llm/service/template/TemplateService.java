package com.ia.core.llm.service.template;

import com.ia.core.llm.model.template.Template;
import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.service.DefaultBaseService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Israel Araújo
 */
@Slf4j
public class TemplateService
  extends DefaultBaseService<Template, TemplateDTO> {

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
