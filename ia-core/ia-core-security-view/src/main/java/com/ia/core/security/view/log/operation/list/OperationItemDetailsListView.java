package com.ia.core.security.view.log.operation.list;

import com.ia.core.security.service.model.log.operation.OperationItemDetails;
import com.ia.core.security.service.model.log.operation.LogOperationTranslator;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;

/**
 * @author Israel Araújo
 */
public class OperationItemDetailsListView
  extends ListView<OperationItemDetails> {

  private static final long serialVersionUID = -4254384005285464654L;

  /**
   * @param viewModel
   */
  public OperationItemDetailsListView(IListViewModel<OperationItemDetails> viewModel) {
    super(viewModel);
  }

  @Override
  protected void createColumns() {
    super.createColumns();
    addColumn("property").setHeader($(LogOperationTranslator.PROPERTY));
    addColumn("oldValue").setHeader($(LogOperationTranslator.OLD_VALUE));
    addColumn("newValue").setHeader($(LogOperationTranslator.NEW_VALUE));
  }

}
