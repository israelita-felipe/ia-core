package com.ia.core.communication.view.mensagem.list;

import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;

/**
 * Interface visual para lista de mensagens.
 * <p>
 * Responsável por gerenciar a exibição de mensagens em formato de lista,
 * incluindo configuração de colunas para telefone destinatário, nome destinatário,
 * tipo de canal, status da mensagem e data de envio.
 *
 * @author Israel Araújo
 * @since 1.0.0
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
