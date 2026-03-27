package com.ia.core.communication.view.mensagem.page;

import org.springframework.stereotype.Component;

import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.communication.view.mensagem.MensagemManager;
import com.ia.core.communication.view.mensagem.form.MensagemFormViewModelConfig;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.page.viewModel.PageViewModelConfig;
import com.ia.core.view.manager.DefaultBaseManager;
import com.vaadin.flow.spring.annotation.UIScope;

/**
 * Configuração do ViewModel para a página de Mensagens.
 *
 * @author Israel Araújo
 */
@UIScope
@Component
public class MensagemPageViewModelConfig
  extends PageViewModelConfig<MensagemDTO> {

  private final MensagemManager mensagemManager;

  public MensagemPageViewModelConfig(DefaultBaseManager<MensagemDTO> service,
                                     MensagemManager mensagemManager) {
    super(service);
    this.mensagemManager = mensagemManager;
  }

  @Override
  protected FormViewModelConfig<MensagemDTO> createFormViewModelConfig(boolean readOnly) {
    return new MensagemFormViewModelConfig(readOnly, mensagemManager);
  }

  public MensagemManager getMensagemManager() {
    return mensagemManager;
  }
}
