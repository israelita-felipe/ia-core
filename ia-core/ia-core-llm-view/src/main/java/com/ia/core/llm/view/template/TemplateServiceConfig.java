package com.ia.core.llm.view.template;

import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.view.service.DefaultBaseServiceConfig;

/**
 *
 */
public class TemplateServiceConfig
  extends DefaultBaseServiceConfig<TemplateDTO> {

  /**
   * @param client cliente do serviço
   */
  public TemplateServiceConfig(TemplateClient client) {
    super(client);
  }

}
