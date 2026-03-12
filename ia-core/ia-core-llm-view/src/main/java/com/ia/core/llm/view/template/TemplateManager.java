package com.ia.core.llm.view.template;

import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.llm.service.model.template.TemplateUseCase;
import com.ia.core.view.manager.DefaultBaseManager;

/**
 * @author Israel Araújo
 */
public class TemplateManager
  extends DefaultBaseManager<TemplateDTO>
  implements TemplateUseCase {

  /**
   * @param config
   */
  public TemplateManager(TemplateManagerConfig config) {
    super(config);
  }

}
