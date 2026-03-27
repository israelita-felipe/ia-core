package com.ia.core.communication.view.contatomensagem.form;

import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.communication.view.contatomensagem.ContatoMensagemManager;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import lombok.Getter;

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
