package com.ia.core.security.view.role.privilege.page;

import com.ia.core.security.service.model.role.RolePrivilegeDTO;
import com.ia.core.security.view.role.privilege.form.RolePrivilegeFormView;
import com.ia.core.security.view.role.privilege.list.RolePrivilegeListView;
import com.ia.core.view.components.form.IFormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.IListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.page.CollectionPageView;
import com.ia.core.view.components.page.viewModel.ICollectionPageViewModel;
/**
 * View para exibição e manipulação de role privilege page.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a RolePrivilegePageView
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class RolePrivilegePageView
  extends CollectionPageView<RolePrivilegeDTO> {

  /**
   * @param viewModel
   */
  public RolePrivilegePageView(ICollectionPageViewModel<RolePrivilegeDTO> viewModel) {
    super(viewModel);
  }

  @Override
  public IFormView<RolePrivilegeDTO> createFormView(IFormViewModel<RolePrivilegeDTO> formViewModel) {
    return new RolePrivilegeFormView(formViewModel);
  }

  @Override
  public IListView<RolePrivilegeDTO> createListView(IListViewModel<RolePrivilegeDTO> listViewModel) {
    return new RolePrivilegeListView(listViewModel);
  }
}
