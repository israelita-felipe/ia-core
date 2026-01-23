package com.ia.core.security.view.privilege.list;

import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.privilege.PrivilegeTranslator;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;

/**
 * View de Lista de {@link PrivilegeDTO}
 *
 * @author Israel Araújo
 */
public class PrivilegeListView
  extends ListView<PrivilegeDTO> {
  /** Serial UID */
  private static final long serialVersionUID = -7797334507809777883L;

  /**
   * @param viewModel View Model
   */
  public PrivilegeListView(IListViewModel<PrivilegeDTO> viewModel) {
    super(viewModel);
  }

  @Override
  protected void createColumns() {
    super.createColumns();
    addColumn("name").setHeader($(PrivilegeTranslator.NOME));
    addColumn("type").setHeader($(PrivilegeTranslator.TYPE));
  }
}
