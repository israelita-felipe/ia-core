package com.ia.core.communication.view.modelomensagem.form;

import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.communication.view.modelomensagem.ModeloMensagemManager;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import lombok.Getter;
/**
 * Classe que representa as configurações para modelo mensagem form view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a ModeloMensagemFormViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class ModeloMensagemFormViewModelConfig extends FormViewModelConfig<ModeloMensagemDTO> {
  @Getter
  private final ModeloMensagemManager modeloMensagemManager;

  public ModeloMensagemFormViewModelConfig(boolean readOnly, ModeloMensagemManager modeloMensagemManager) {
    super(readOnly);
    this.modeloMensagemManager = modeloMensagemManager;
  }
}
