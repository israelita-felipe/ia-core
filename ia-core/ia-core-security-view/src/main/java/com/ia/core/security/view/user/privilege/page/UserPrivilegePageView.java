package com.ia.core.security.view.user.privilege.page;

import com.ia.core.security.service.model.user.UserPrivilegeDTO;
import com.ia.core.security.view.user.privilege.form.UserPrivilegeFormView;
import com.ia.core.security.view.user.privilege.list.UserPrivilegeListView;
import com.ia.core.view.components.form.IFormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.IListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.page.CollectionPageView;
import com.ia.core.view.components.page.viewModel.ICollectionPageViewModel;

/**
 * @author Israel Araújo
 */
public class UserPrivilegePageView
  extends CollectionPageView<UserPrivilegeDTO> {

  /**
   * @param viewModel
   */
  public UserPrivilegePageView(ICollectionPageViewModel<UserPrivilegeDTO> viewModel) {
    super(viewModel);
  }

  @Override
  public IFormView<UserPrivilegeDTO> createFormView(IFormViewModel<UserPrivilegeDTO> formViewModel) {
    return new UserPrivilegeFormView(formViewModel);
  }

  @Override
  public IListView<UserPrivilegeDTO> createListView(IListViewModel<UserPrivilegeDTO> listViewModel) {
    return new UserPrivilegeListView(listViewModel);
  }
}
