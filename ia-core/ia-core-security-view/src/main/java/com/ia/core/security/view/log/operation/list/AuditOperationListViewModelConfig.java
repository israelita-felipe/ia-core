package com.ia.core.security.view.log.operation.list;

import com.ia.core.security.service.model.log.operation.LogOperationDTO;
import com.ia.core.security.view.log.operation.LogOperationService;
import com.ia.core.view.components.list.viewModel.ListViewModelConfig;

import lombok.Getter;

/**
 *
 */
public class AuditOperationListViewModelConfig
  extends ListViewModelConfig<LogOperationDTO> {
  @Getter
  private final LogOperationService logOperationService;

  /**
   * @param readOnly
   * @param logOperationService
   */
  public AuditOperationListViewModelConfig(boolean readOnly,
                                           LogOperationService logOperationService) {
    super(readOnly);
    this.logOperationService = logOperationService;
  }

}
