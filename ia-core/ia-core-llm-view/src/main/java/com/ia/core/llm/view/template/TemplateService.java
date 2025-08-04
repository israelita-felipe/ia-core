package com.ia.core.llm.view.template;

import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.view.client.BaseClient;
import com.ia.core.view.service.DefaultBaseService;

/**
 * @author Israel Araújo
 */
public class TemplateService
  extends DefaultBaseService<TemplateDTO> {

  /**
   * @param client
   */
  public TemplateService(BaseClient<TemplateDTO> client) {
    super(client);
  }

}
