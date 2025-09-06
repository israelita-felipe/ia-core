package com.ia.core.llm.view.comando.page;

import org.springframework.stereotype.Component;

import com.ia.core.llm.service.model.comando.ComandoSistemaDTO;
import com.ia.core.llm.view.template.TemplateService;
import com.ia.core.security.view.log.operation.LogOperationService;
import com.ia.core.security.view.log.operation.page.EntityPageViewModelConfig;
import com.ia.core.view.service.DefaultBaseService;
import com.vaadin.flow.spring.annotation.UIScope;

import lombok.Getter;

/**
 *
 */
@UIScope
@Component
public class ComandoSistemaPageViewModelConfig
  extends EntityPageViewModelConfig<ComandoSistemaDTO> {

  /** Serviço de template */
  @Getter
  private final TemplateService templateService;

  /**
   * @param service
   * @param logOperationService
   */
  public ComandoSistemaPageViewModelConfig(DefaultBaseService<ComandoSistemaDTO> service,
                                           LogOperationService logOperationService,
                                           TemplateService templateService) {
    super(service, logOperationService);
    this.templateService = templateService;
  }

}
