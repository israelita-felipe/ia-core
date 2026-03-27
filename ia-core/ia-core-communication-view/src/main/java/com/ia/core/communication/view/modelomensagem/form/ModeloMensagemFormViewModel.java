package com.ia.core.communication.view.modelomensagem.form;

import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.communication.view.modelomensagem.ModeloMensagemManager;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import lombok.Getter;

public class ModeloMensagemFormViewModel extends FormViewModel<ModeloMensagemDTO> {
  @Getter
  private final ModeloMensagemManager modeloMensagemManager;

  public ModeloMensagemFormViewModel(ModeloMensagemFormViewModelConfig config) {
    super(config);
    this.modeloMensagemManager = config.getModeloMensagemManager();
  }

  @Override
  public ModeloMensagemFormViewModelConfig getConfig() {
    return super.getConfig().cast();
  }
}
