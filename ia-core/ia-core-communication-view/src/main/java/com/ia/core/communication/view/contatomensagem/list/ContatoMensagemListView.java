package com.ia.core.communication.view.contatomensagem.list;

import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
/**
 * Classe que representa a interface visual para contato mensagem list.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a ContatoMensagemListView
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
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
