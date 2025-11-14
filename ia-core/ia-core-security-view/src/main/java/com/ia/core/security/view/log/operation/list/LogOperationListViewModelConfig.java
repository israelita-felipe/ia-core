package com.ia.core.security.view.log.operation.list;

import com.ia.core.security.service.model.log.operation.LogOperationDTO;
import com.ia.core.security.view.log.operation.LogOperationManager;
import com.ia.core.view.components.list.viewModel.ListViewModelConfig;

import lombok.Getter;

/**
 *
 */
public class LogOperationListViewModelConfig
  extends ListViewModelConfig<LogOperationDTO> {
  @Getter
  private final LogOperationManager logOperationService;

  /**
   * @param readOnly
   * @param logOperationService
   */
  public LogOperationListViewModelConfig(boolean readOnly,
                                         LogOperationManager logOperationService) {
    super(readOnly);
    this.logOperationService = logOperationService;
  }

}
