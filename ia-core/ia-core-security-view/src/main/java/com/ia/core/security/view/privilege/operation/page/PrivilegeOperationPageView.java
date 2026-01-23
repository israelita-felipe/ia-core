package com.ia.core.security.view.privilege.operation.page;

import com.ia.core.security.service.model.privilege.PrivilegeOperationDTO;
import com.ia.core.security.view.privilege.operation.form.PrivilegeOperationFormView;
import com.ia.core.security.view.privilege.operation.list.PrivilegeOperationListView;
import com.ia.core.view.components.form.IFormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.IListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.page.CollectionPageView;
import com.ia.core.view.components.page.viewModel.ICollectionPageViewModel;

/**
 * @author Israel Araújo
 */
public class PrivilegeOperationPageView
  extends CollectionPageView<PrivilegeOperationDTO> {

  /**
   * @param viewModel
   */
  public PrivilegeOperationPageView(ICollectionPageViewModel<PrivilegeOperationDTO> viewModel) {
    super(viewModel);
  }

  @Override
  public IFormView<PrivilegeOperationDTO> createFormView(IFormViewModel<PrivilegeOperationDTO> formViewModel) {
    return new PrivilegeOperationFormView(formViewModel);
  }

  @Override
  public IListView<PrivilegeOperationDTO> createListView(IListViewModel<PrivilegeOperationDTO> listViewModel) {
    return new PrivilegeOperationListView(listViewModel);
  }
}
