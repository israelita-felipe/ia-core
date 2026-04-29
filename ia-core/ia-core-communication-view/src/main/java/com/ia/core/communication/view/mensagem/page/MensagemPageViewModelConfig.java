package com.ia.core.communication.view.mensagem.page;

import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.communication.service.model.modelomensagem.dto.ProcessadorVariaveis;
import com.ia.core.communication.view.mensagem.MensagemManager;
import com.ia.core.communication.view.mensagem.form.MensagemFormViewModelConfig;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.page.viewModel.PageViewModelConfig;
import com.ia.core.view.manager.DefaultBaseManager;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * Configuração do ViewModel para a página de Mensagens.
 *
 * @author Israel Araújo
 */
/**
 * Classe que representa as configurações para mensagem page view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a MensagemPageViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@UIScope
@Component
public class MensagemPageViewModelConfig
  extends PageViewModelConfig<MensagemDTO> {

    @Getter
  private final MensagemManager mensagemManager;
    @Getter
    private final ProcessadorVariaveis processadorVariaveis;

    public MensagemPageViewModelConfig(DefaultBaseManager<MensagemDTO> service,
                                     MensagemManager mensagemManager, ProcessadorVariaveis processadorVariaveis) {
    super(service);
    this.mensagemManager = mensagemManager;
    this.processadorVariaveis=processadorVariaveis;
  }

  @Override
  protected FormViewModelConfig<MensagemDTO> createFormViewModelConfig(boolean readOnly) {
    return new MensagemFormViewModelConfig(readOnly, mensagemManager,processadorVariaveis);
  }
}
