package com.ia.core.security.view.role.page;

import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.security.view.log.operation.page.EntityPageView;
import com.ia.core.security.view.role.form.RoleFormView;
import com.ia.core.security.view.role.list.RoleListView;
import com.ia.core.view.components.form.IFormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.IListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.page.viewModel.IPageViewModel;

/**
 * View para {@link RoleDTO}
 *
 * @author Israel Araújo
 */
public class RolePageView
  extends EntityPageView<RoleDTO> {
  /** Serial UID */
  private static final long serialVersionUID = 8578640661122382000L;
  /** Rota padrão */
  public static final String ROUTE = "role";

  /**
   * @param viewModel View Model
   */
  public RolePageView(IPageViewModel<RoleDTO> viewModel) {
    super(viewModel);
  }

  @Override
  public IFormView<RoleDTO> createFormView(IFormViewModel<RoleDTO> formViewModel) {
    return new RoleFormView(formViewModel);
  }

  @Override
  public IListView<RoleDTO> createListView(IListViewModel<RoleDTO> listViewModel) {
    return new RoleListView(listViewModel);
  }

  @Override
  public RolePageViewModel getViewModel() {
    return (RolePageViewModel) super.getViewModel();
  }

}
