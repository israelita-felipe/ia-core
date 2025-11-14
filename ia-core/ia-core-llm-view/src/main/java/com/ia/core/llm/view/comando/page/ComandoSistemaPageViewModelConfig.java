package com.ia.core.llm.view.comando.page;

import org.springframework.stereotype.Component;

import com.ia.core.llm.service.model.comando.ComandoSistemaDTO;
import com.ia.core.llm.view.template.TemplateManager;
import com.ia.core.security.view.log.operation.LogOperationManager;
import com.ia.core.security.view.log.operation.page.EntityPageViewModelConfig;
import com.ia.core.view.manager.DefaultBaseManager;
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
  private final TemplateManager templateService;

  /**
   * @param service
   * @param logOperationService
   */
  public ComandoSistemaPageViewModelConfig(DefaultBaseManager<ComandoSistemaDTO> service,
                                           LogOperationManager logOperationService,
                                           TemplateManager templateService) {
    super(service, logOperationService);
    this.templateService = templateService;
  }

}
