package com.ia.core.security.view.user.privilege.list;

import com.ia.core.security.service.model.user.UserPrivilegeDTO;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;

/**
 * {@link ListView} do usuário.
 *
 * @author Israel Araújo
 */
public class UserPrivilegeListView
  extends ListView<UserPrivilegeDTO> {
  /** Serial UID */
  private static final long serialVersionUID = -6807745731867108073L;

  /**
   * @param viewModel view model
   */
  public UserPrivilegeListView(IListViewModel<UserPrivilegeDTO> viewModel) {
    super(viewModel);
  }

  @Override
  protected void createColumns() {
    super.createColumns();
    addColumn("privilege");
    addColumn("operations");
  }
}
