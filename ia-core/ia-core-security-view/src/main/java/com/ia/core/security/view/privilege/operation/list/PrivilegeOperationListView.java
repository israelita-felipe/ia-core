package com.ia.core.security.view.privilege.operation.list;

import com.ia.core.security.service.model.privilege.PrivilegeOperationDTO;
import com.ia.core.security.service.model.user.UserTranslator;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;

/**
 * {@link ListView} do usuário.
 *
 * @author Israel Araújo
 */
public class PrivilegeOperationListView
  extends ListView<PrivilegeOperationDTO> {
  /** Serial UID */
  private static final long serialVersionUID = -6807745731867108073L;

  /**
   * @param viewModel view model
   */
  public PrivilegeOperationListView(IListViewModel<PrivilegeOperationDTO> viewModel) {
    super(viewModel);
  }

  @Override
  protected void createColumns() {
    super.createColumns();
    addColumn("operation").setHeader($(UserTranslator.NOME));
  }
}
