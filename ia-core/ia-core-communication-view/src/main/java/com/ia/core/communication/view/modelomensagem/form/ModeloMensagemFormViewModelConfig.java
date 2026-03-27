package com.ia.core.communication.view.modelomensagem.form;

import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.communication.view.modelomensagem.ModeloMensagemManager;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import lombok.Getter;

public class ModeloMensagemFormViewModelConfig extends FormViewModelConfig<ModeloMensagemDTO> {
  @Getter
  private final ModeloMensagemManager modeloMensagemManager;

  public ModeloMensagemFormViewModelConfig(boolean readOnly, ModeloMensagemManager modeloMensagemManager) {
    super(readOnly);
    this.modeloMensagemManager = modeloMensagemManager;
  }
}
