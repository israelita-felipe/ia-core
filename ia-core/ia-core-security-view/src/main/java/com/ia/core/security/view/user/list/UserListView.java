package com.ia.core.security.view.user.list;

import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.model.user.UserTranslator;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;

/**
 * {@link ListView} do usuário.
 *
 * @author Israel Araújo
 */
public class UserListView
  extends ListView<UserDTO> {
  /** Serial UID */
  private static final long serialVersionUID = -6807745731867108073L;

  /**
   * @param viewModel view model
   */
  public UserListView(IListViewModel<UserDTO> viewModel) {
    super(viewModel);
  }

  @Override
  protected void createColumns() {
    super.createColumns();
    addColumn("userName").setHeader($(UserTranslator.NOME));
    addColumn("userCode").setHeader($(UserTranslator.CODIGO));
  }
}
