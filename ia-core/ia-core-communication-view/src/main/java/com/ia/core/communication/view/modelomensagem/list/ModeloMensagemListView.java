package com.ia.core.communication.view.modelomensagem.list;

import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;

public class ModeloMensagemListView extends ListView<ModeloMensagemDTO> {
  public ModeloMensagemListView(IListViewModel<ModeloMensagemDTO> viewModel) {
    super(viewModel);
  }

  @Override
  protected void createColumns() {
    super.createColumns();
    addColumn(ModeloMensagemDTO.CAMPOS.NOME);
    addColumn(ModeloMensagemDTO.CAMPOS.DESCRICAO);
    addColumn(ModeloMensagemDTO.CAMPOS.TIPO_CANAL);
    addColumn(ModeloMensagemDTO.CAMPOS.ATIVO);
  }
}
