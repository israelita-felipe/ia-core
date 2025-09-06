package com.ia.core.security.view.log.operation.list;

import com.ia.core.security.service.model.log.operation.OperationItemDetails;
import com.ia.core.view.components.list.viewModel.ListViewModelConfig;

/**
 *
 */
public class OperationItemDetailsListViewModelConfig
  extends ListViewModelConfig<OperationItemDetails> {

  /**
   * @param readOnly
   */
  public OperationItemDetailsListViewModelConfig(boolean readOnly) {
    super(readOnly);
  }

}
