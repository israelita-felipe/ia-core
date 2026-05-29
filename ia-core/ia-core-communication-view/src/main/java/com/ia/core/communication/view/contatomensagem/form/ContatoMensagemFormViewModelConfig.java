package com.ia.core.communication.view.contatomensagem.form;

import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.communication.view.contatomensagem.ContatoMensagemManager;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import lombok.Getter;
/**
 * Configurações para o ViewModel do formulário de contato de mensagem.
 * <p>
 * Responsável por fornecer as dependências necessárias para o ViewModel,
 * incluindo o ContatoMensagemManager.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */

public class ContatoMensagemFormViewModelConfig extends FormViewModelConfig<ContatoMensagemDTO> {
  @Getter
  private final ContatoMensagemManager contatoMensagemManager;

  public ContatoMensagemFormViewModelConfig(boolean readOnly, ContatoMensagemManager contatoMensagemManager) {
    super(readOnly);
    this.contatoMensagemManager = contatoMensagemManager;
  }
}
