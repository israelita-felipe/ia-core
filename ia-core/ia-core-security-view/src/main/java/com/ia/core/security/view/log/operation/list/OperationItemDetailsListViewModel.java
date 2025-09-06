package com.ia.core.security.view.log.operation.list;

import com.ia.core.security.service.model.log.operation.OperationItemDetails;
import com.ia.core.view.components.list.viewModel.ListViewModel;

/**
 * @author Israel Araújo
 */
public class OperationItemDetailsListViewModel
  extends ListViewModel<OperationItemDetails> {

  /**
   * @param config
   */
  public OperationItemDetailsListViewModel(OperationItemDetailsListViewModelConfig config) {
    super(config);
  }

  @Override
  public Class<OperationItemDetails> getType() {
    return OperationItemDetails.class;
  }

}
