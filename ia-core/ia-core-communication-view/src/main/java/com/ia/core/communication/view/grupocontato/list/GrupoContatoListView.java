package com.ia.core.communication.view.grupocontato.list;

import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoDTO;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;

public class GrupoContatoListView extends ListView<GrupoContatoDTO> {

  public GrupoContatoListView(IListViewModel<GrupoContatoDTO> viewModel) {
    super(viewModel);
  }

  @Override
  protected void createColumns() {
    super.createColumns();
    addColumn(GrupoContatoDTO.CAMPOS.NOME);
    addColumn(GrupoContatoDTO.CAMPOS.DESCRICAO);
    addColumn(GrupoContatoDTO.CAMPOS.ATIVO);
  }
}
