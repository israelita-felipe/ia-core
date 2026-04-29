package com.ia.core.security.view.privilege.operation.context.list;

import com.ia.core.security.service.model.privilege.PrivilegeOperationContextDTO;
import com.ia.core.security.service.model.user.UserTranslator;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
/**
 * View para exibição e manipulação de privilege operation context list.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PrivilegeOperationContextListView
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class PrivilegeOperationContextListView
  extends ListView<PrivilegeOperationContextDTO> {
  /** Serial UID */
  private static final long serialVersionUID = -6807745731867108073L;

  /**
   * @param viewModel view model
   */
  public PrivilegeOperationContextListView(IListViewModel<PrivilegeOperationContextDTO> viewModel) {
    super(viewModel);
  }

  @Override
  protected void createColumns() {
    super.createColumns();
    addColumn("contextKey").setHeader($(UserTranslator.NOME));
    addColumn("values");
  }
}
