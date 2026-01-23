package com.ia.core.security.view.privilege.page;

import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.view.log.operation.page.EntityPageView;
import com.ia.core.security.view.privilege.form.PrivilegeFormView;
import com.ia.core.security.view.privilege.list.PrivilegeListView;
import com.ia.core.view.components.form.IFormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.IListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.page.viewModel.IPageViewModel;

/**
 * View de Página de {@link PrivilegeDTO}
 *
 * @author Israel Araújo
 */
public class PrivilegePageView
  extends EntityPageView<PrivilegeDTO> {
  /** Serial UID */
  private static final long serialVersionUID = 6664006407082709257L;
  /**
   * Rota
   */
  public static final String ROUTE = "privilege";

  /**
   * @param viewModel View Model da página
   */
  public PrivilegePageView(IPageViewModel<PrivilegeDTO> viewModel) {
    super(viewModel);
  }

  @Override
  public IFormView<PrivilegeDTO> createFormView(IFormViewModel<PrivilegeDTO> formViewModel) {
    return new PrivilegeFormView(formViewModel);
  }

  @Override
  public IListView<PrivilegeDTO> createListView(IListViewModel<PrivilegeDTO> listViewModel) {
    return new PrivilegeListView(listViewModel);
  }

}
