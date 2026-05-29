package com.ia.core.communication.view.contatomensagem.list;

import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
/**
 * Interface visual para lista de contatos de mensagens.
 * <p>
 * Responsável por gerenciar a exição de contatos de mensagens em formato de lista,
 * incluindo configuração de colunas para telefone e nome.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */

public class ContatoMensagemListView extends ListView<ContatoMensagemDTO> {
  public ContatoMensagemListView(IListViewModel<ContatoMensagemDTO> viewModel) {
    super(viewModel);
  }

  @Override
  protected void createColumns() {
    super.createColumns();
    addColumn(ContatoMensagemDTO.CAMPOS.TELEFONE);
    addColumn(ContatoMensagemDTO.CAMPOS.NOME);
  }
}
