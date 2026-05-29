package com.ia.core.communication.view.modelomensagem.form;

import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.communication.view.modelomensagem.ModeloMensagemManager;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import lombok.Getter;
/**
 * Configurações para o ViewModel do formulário de modelo de mensagem.
 * <p>
 * Responsável por fornecer as dependências necessárias para o ViewModel,
 * incluindo o ModeloMensagemManager.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */

public class ModeloMensagemFormViewModelConfig extends FormViewModelConfig<ModeloMensagemDTO> {
  @Getter
  private final ModeloMensagemManager modeloMensagemManager;

  public ModeloMensagemFormViewModelConfig(boolean readOnly, ModeloMensagemManager modeloMensagemManager) {
    super(readOnly);
    this.modeloMensagemManager = modeloMensagemManager;
  }
}
