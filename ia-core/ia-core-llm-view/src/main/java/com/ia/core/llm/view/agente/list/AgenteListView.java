package com.ia.core.llm.view.agente.list;

import com.ia.core.llm.service.model.agente.AgenteDTO;
import com.ia.core.llm.service.model.agente.AgenteTranslator;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;

/**
 * View para exibição e manipulação de agente list.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a AgenteListView
 * dentro do sistema.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class AgenteListView
  extends ListView<AgenteDTO> {

  /**
   * @param viewModel
   */
  public AgenteListView(IListViewModel<AgenteDTO> viewModel) {
    super(viewModel);
  }

  @Override
  protected void createColumns() {
    super.createColumns();
    addColumn("identificador").setHeader($(AgenteTranslator.IDENTIFICADOR));
    addColumn("titulo").setHeader($(AgenteTranslator.TITULO));
    addColumn("descricao").setHeader($(AgenteTranslator.DESCRICAO));
    addColumn("modelo").setHeader($(AgenteTranslator.MODELO));
    addColumn("ativo").setHeader($(AgenteTranslator.ATIVO));
  }
}
