package com.ia.core.communication.view.contatomensagem.form;

import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.communication.view.contatomensagem.ContatoMensagemManager;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import lombok.Getter;
/**
 * Classe que representa o modelo de dados para a view de contato mensagem form.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a ContatoMensagemFormViewModel
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class ContatoMensagemFormViewModel extends FormViewModel<ContatoMensagemDTO> {
  @Getter
  private final ContatoMensagemManager contatoMensagemManager;

  public ContatoMensagemFormViewModel(ContatoMensagemFormViewModelConfig config) {
    super(config);
    this.contatoMensagemManager = config.getContatoMensagemManager();
  }

  @Override
  public ContatoMensagemFormViewModelConfig getConfig() {
    return super.getConfig().cast();
  }
}
