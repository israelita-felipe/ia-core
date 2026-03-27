package com.ia.core.communication.view.mensagem.list;

import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;

/**
 * Lista de Mensagens.
 *
 * @author Israel Araújo
 */
public class MensagemListView
  extends ListView<MensagemDTO> {

  public MensagemListView(IListViewModel<MensagemDTO> viewModel) {
    super(viewModel);
  }

  @Override
  protected void createColumns() {
    super.createColumns();
    addColumn(MensagemDTO.CAMPOS.TELEFONE_DESTINATARIO);
    addColumn(MensagemDTO.CAMPOS.NOME_DESTINATARIO);
    addColumn(MensagemDTO.CAMPOS.TIPO_CANAL);
    addColumn(MensagemDTO.CAMPOS.STATUS_MENSAGEM);
    addColumn(MensagemDTO.CAMPOS.DATA_ENVIO);
  }
}
