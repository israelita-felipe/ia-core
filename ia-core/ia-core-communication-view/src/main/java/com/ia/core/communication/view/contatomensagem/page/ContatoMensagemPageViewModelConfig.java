package com.ia.core.communication.view.contatomensagem.page;

import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.communication.view.contatomensagem.ContatoMensagemManager;
import com.ia.core.communication.view.contatomensagem.form.ContatoMensagemFormViewModelConfig;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.page.viewModel.PageViewModelConfig;
import com.ia.core.view.manager.DefaultBaseManager;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

/**
 * Configuração do ViewModel para a página de ContatoMensagem.
 *
 * @author Israel Araújo
 */
/**
 * Classe que representa as configurações para contato mensagem page view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a ContatoMensagemPageViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@UIScope
@Component
public class ContatoMensagemPageViewModelConfig
  extends PageViewModelConfig<ContatoMensagemDTO> {

  private final ContatoMensagemManager contatoMensagemManager;

  public ContatoMensagemPageViewModelConfig(DefaultBaseManager<ContatoMensagemDTO> service,
                                            ContatoMensagemManager contatoMensagemManager) {
    super(service);
    this.contatoMensagemManager = contatoMensagemManager;
  }

  @Override
  protected FormViewModelConfig<ContatoMensagemDTO> createFormViewModelConfig(boolean readOnly) {
    return new ContatoMensagemFormViewModelConfig(readOnly, contatoMensagemManager);
  }

  public ContatoMensagemManager getContatoMensagemManager() {
    return contatoMensagemManager;
  }
}
