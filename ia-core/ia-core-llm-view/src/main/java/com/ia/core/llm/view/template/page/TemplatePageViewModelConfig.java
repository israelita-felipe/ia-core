package com.ia.core.llm.view.template.page;

import org.springframework.stereotype.Component;

import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.security.view.log.operation.LogOperationService;
import com.ia.core.security.view.log.operation.page.EntityPageViewModelConfig;
import com.ia.core.view.service.DefaultBaseService;
import com.vaadin.flow.spring.annotation.UIScope;

/**
 *
 */
@UIScope
@Component
public class TemplatePageViewModelConfig
  extends EntityPageViewModelConfig<TemplateDTO> {

  /**
   * @param service
   * @param logOperationService
   */
  public TemplatePageViewModelConfig(DefaultBaseService<TemplateDTO> service,
                                     LogOperationService logOperationService) {
    super(service, logOperationService);
  }

}
