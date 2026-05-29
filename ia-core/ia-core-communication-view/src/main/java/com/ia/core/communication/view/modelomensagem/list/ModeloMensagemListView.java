package com.ia.core.communication.view.modelomensagem.list;

import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
/**
 * Interface visual para lista de modelos de mensagens.
 * <p>
 * Responsável por gerenciar a exibição de modelos de mensagens em formato de lista,
 * incluindo configuração de colunas para nome, descrição, tipo de canal e status ativo.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */

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
