package com.ia.core.communication.view.mensagem.form;

import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.communication.view.mensagem.MensagemManager;
import com.ia.core.view.components.form.viewModel.FormViewModel;

/**
 * ViewModel para o formulário de Mensagens.
 *
 * @author Israel Araújo
 */
public class MensagemFormViewModel
  extends FormViewModel<MensagemDTO> {

  private final MensagemManager mensagemManager;

  public MensagemFormViewModel(MensagemFormViewModelConfig config) {
    super(config);
    this.mensagemManager = config.getMensagemManager();
  }

  @Override
  public void setModel(MensagemDTO model) {
    super.setModel(model);
  }

  @Override
  public MensagemFormViewModelConfig getConfig() {
    return super.getConfig().cast();
  }

  public MensagemManager getMensagemManager() {
    return mensagemManager;
  }
}
