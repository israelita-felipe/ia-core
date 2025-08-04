package com.ia.core.security.view.role.list;

import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.security.service.model.role.RoleTranslator;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;

/**
 * View para lista de {@link RoleDTO}
 *
 * @author Israel Araújo
 */
public class RoleListView
  extends ListView<RoleDTO> {
  /** Serial UID */
  private static final long serialVersionUID = 9002146884772109491L;

  /**
   * @param viewModel View Model da lista
   */
  public RoleListView(IListViewModel<RoleDTO> viewModel) {
    super(viewModel);
  }

  @Override
  protected void createColumns() {
    super.createColumns();
    addColumn("name").setHeader($(RoleTranslator.NOME));
  }
}
