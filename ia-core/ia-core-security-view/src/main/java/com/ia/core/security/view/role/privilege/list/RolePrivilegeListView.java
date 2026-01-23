package com.ia.core.security.view.role.privilege.list;

import com.ia.core.security.service.model.role.RolePrivilegeDTO;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;

/**
 * {@link ListView} do usuário.
 *
 * @author Israel Araújo
 */
public class RolePrivilegeListView
  extends ListView<RolePrivilegeDTO> {
  /** Serial UID */
  private static final long serialVersionUID = -6807745731867108073L;

  /**
   * @param viewModel view model
   */
  public RolePrivilegeListView(IListViewModel<RolePrivilegeDTO> viewModel) {
    super(viewModel);
  }

  @Override
  protected void createColumns() {
    super.createColumns();
    addColumn("privilege");
    addColumn("operations");
  }
}
