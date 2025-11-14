package com.ia.core.llm.view.template;

import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.view.manager.DefaultBaseManagerConfig;

/**
 *
 */
public class TemplateManagerConfig
  extends DefaultBaseManagerConfig<TemplateDTO> {

  /**
   * @param client cliente do serviço
   */
  public TemplateManagerConfig(TemplateClient client) {
    super(client);
  }

}
