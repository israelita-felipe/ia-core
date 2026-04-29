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
 * View para exibição e manipulação de privilege operation page.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PrivilegeOperationPageView
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
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
