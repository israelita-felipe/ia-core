package com.ia.core.communication.view.mensagem.form;

import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.communication.service.model.modelomensagem.dto.ProcessadorVariaveis;
import com.ia.core.communication.view.mensagem.MensagemManager;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import lombok.Getter;

/**
 * Configuração do ViewModel para o formulário de Mensagens.
 *
 * @author Israel Araújo
 */
public class MensagemFormViewModelConfig
  extends FormViewModelConfig<MensagemDTO> {

  @Getter
  private final MensagemManager mensagemManager;
  @Getter
    private final ProcessadorVariaveis processadorVariaveis;

    public MensagemFormViewModelConfig(boolean readOnly,
                                     MensagemManager mensagemManager, ProcessadorVariaveis processadorVariaveis) {
    super(readOnly);
    this.mensagemManager = mensagemManager;
    this.processadorVariaveis = processadorVariaveis;
  }
}
