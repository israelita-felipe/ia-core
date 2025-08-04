package com.ia.core.security.view.log.operation.form;

import com.ia.core.security.service.model.log.operation.LogOperationDetails;
import com.ia.core.security.view.log.operation.list.OperationItemDetailsListView;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.utils.DataProviderFactory;

/**
 * @author Israel Araújo
 */
public class LogOperationDetailsFormView
  extends FormView<LogOperationDetails> {

  /**
   * @param viewModel
   */
  public LogOperationDetailsFormView(IFormViewModel<LogOperationDetails> viewModel) {
    super(viewModel);
  }

  @Override
  public void createLayout() {
    super.createLayout();
    createLogOperationItemDetailsListField();
  }

  /**
   * @return {@link LogOperationDetailsFormView}
   */
  protected OperationItemDetailsListView createLogOperationItemDetailsListField() {
    OperationItemDetailsListView field = new OperationItemDetailsListView(getViewModel()
        .getLogOperationItemDetailsListViewModel());
    field.setDataProvider(DataProviderFactory
        .createDataProviderFromSupplier(getViewModel()::getLogOperationItensDetails));
    add(field, 6);
    return field;
  }

  @Override
  public LogOperationDetailsFormViewModel getViewModel() {
    return super.getViewModel().cast();
  }
}
