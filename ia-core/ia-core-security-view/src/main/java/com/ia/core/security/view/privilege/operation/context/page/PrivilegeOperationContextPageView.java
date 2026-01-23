package com.ia.core.security.view.privilege.operation.context.page;

import com.ia.core.security.service.model.privilege.PrivilegeOperationContextDTO;
import com.ia.core.security.view.privilege.operation.context.form.PrivilegeOperationContextFormView;
import com.ia.core.security.view.privilege.operation.context.list.PrivilegeOperationContextListView;
import com.ia.core.view.components.form.IFormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.IListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.page.CollectionPageView;
import com.ia.core.view.components.page.viewModel.ICollectionPageViewModel;

/**
 * @author Israel Araújo
 */
public class PrivilegeOperationContextPageView
  extends CollectionPageView<PrivilegeOperationContextDTO> {

  /**
   * @param viewModel
   */
  public PrivilegeOperationContextPageView(ICollectionPageViewModel<PrivilegeOperationContextDTO> viewModel) {
    super(viewModel);
  }

  @Override
  public IFormView<PrivilegeOperationContextDTO> createFormView(IFormViewModel<PrivilegeOperationContextDTO> formViewModel) {
    return new PrivilegeOperationContextFormView(formViewModel);
  }

  @Override
  public IListView<PrivilegeOperationContextDTO> createListView(IListViewModel<PrivilegeOperationContextDTO> listViewModel) {
    return new PrivilegeOperationContextListView(listViewModel);
  }
}
