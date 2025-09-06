package com.ia.core.security.view.log.operation.page;

import java.io.Serializable;

import com.ia.core.security.view.log.operation.LogOperationService;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.view.components.page.viewModel.PageViewModelConfig;
import com.ia.core.view.service.DefaultBaseService;

import lombok.Getter;

/**
 *
 */
public class EntityPageViewModelConfig<T extends AbstractBaseEntityDTO<? extends Serializable> & Serializable>
  extends PageViewModelConfig<T> {

  @Getter
  private final LogOperationService logOperationService;

  /**
   * @param service
   */
  public EntityPageViewModelConfig(DefaultBaseService<T> service,
                                   LogOperationService logOperationService) {
    super(service);
    this.logOperationService = logOperationService;
  }

}
