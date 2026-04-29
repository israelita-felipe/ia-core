package com.ia.core.communication.view.contatomensagem.form;

import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.communication.view.contatomensagem.ContatoMensagemManager;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import lombok.Getter;
/**
 * Classe que representa as configurações para contato mensagem form view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a ContatoMensagemFormViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class ContatoMensagemFormViewModelConfig extends FormViewModelConfig<ContatoMensagemDTO> {
  @Getter
  private final ContatoMensagemManager contatoMensagemManager;

  public ContatoMensagemFormViewModelConfig(boolean readOnly, ContatoMensagemManager contatoMensagemManager) {
    super(readOnly);
    this.contatoMensagemManager = contatoMensagemManager;
  }
}
