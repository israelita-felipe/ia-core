package com.ia.core.llm.view.template;

import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.view.service.DefaultBaseService;

/**
 * @author Israel Araújo
 */
public class TemplateService
  extends DefaultBaseService<TemplateDTO> {

  /**
   * @param config
   */
  public TemplateService(TemplateServiceConfig config) {
    super(config);
  }

}
