package com.ia.core.llm.view.comando.list;

import com.ia.core.llm.service.model.comando.ComandoSistemaDTO;
import com.ia.core.llm.service.model.comando.ComandoSistemaTranslator;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;

/**
 * @author Israel Araújo
 */
public class ComandoSistemaListView
  extends ListView<ComandoSistemaDTO> {

  /**
   * @param viewModel
   */
  public ComandoSistemaListView(IListViewModel<ComandoSistemaDTO> viewModel) {
    super(viewModel);
  }

  @Override
  protected void createColumns() {
    super.createColumns();
    addColumn("titulo").setHeader($(ComandoSistemaTranslator.TITULO));
    addColumn("finalidade")
        .setHeader($(ComandoSistemaTranslator.FINALIDADE));
  }
}
