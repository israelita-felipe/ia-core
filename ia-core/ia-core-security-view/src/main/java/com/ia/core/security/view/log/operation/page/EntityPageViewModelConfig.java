package com.ia.core.security.view.log.operation.page;

import java.io.Serializable;

import com.ia.core.security.view.log.operation.LogOperationManager;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.view.components.page.viewModel.PageViewModelConfig;
import com.ia.core.view.manager.DefaultBaseManager;

import lombok.Getter;

/**
 *
 */
public class EntityPageViewModelConfig<T extends AbstractBaseEntityDTO<? extends Serializable> & Serializable>
  extends PageViewModelConfig<T> {

  @Getter
  private final LogOperationManager logOperationService;

  /**
   * @param service
   */
  public EntityPageViewModelConfig(DefaultBaseManager<T> service,
                                   LogOperationManager logOperationService) {
    super(service);
    this.logOperationService = logOperationService;
  }

}
