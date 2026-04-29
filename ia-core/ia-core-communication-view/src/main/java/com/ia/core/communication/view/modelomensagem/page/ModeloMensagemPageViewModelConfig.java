package com.ia.core.communication.view.modelomensagem.page;

import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.communication.view.contatomensagem.ContatoMensagemManager;
import com.ia.core.communication.view.grupocontato.GrupoContatoManager;
import com.ia.core.communication.view.modelomensagem.ModeloMensagemManager;
import com.ia.core.communication.view.modelomensagem.form.ModeloMensagemFormViewModelConfig;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.page.viewModel.PageViewModelConfig;
import com.ia.core.view.manager.DefaultBaseManager;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * Configuração do ViewModel para a página de ModeloMensagem.
 *
 * @author Israel Araújo
 */
/**
 * Classe que representa as configurações para modelo mensagem page view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a ModeloMensagemPageViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@UIScope
@Component
public class ModeloMensagemPageViewModelConfig extends PageViewModelConfig<ModeloMensagemDTO> {
    @Getter
    private final ModeloMensagemManager modeloMensagemManager;
    @Getter
    private final GrupoContatoManager grupoContatoManager;
    @Getter
    private final ContatoMensagemManager contatoMensagemManager;

    public ModeloMensagemPageViewModelConfig(DefaultBaseManager<ModeloMensagemDTO> service,
                                           ModeloMensagemManager modeloMensagemManager,
                                           GrupoContatoManager grupoContatoManager,
                                           ContatoMensagemManager contatoMensagemManager) {
        super(service);
        this.modeloMensagemManager = modeloMensagemManager;
        this.grupoContatoManager = grupoContatoManager;
        this.contatoMensagemManager = contatoMensagemManager;
    }

    @Override
    protected FormViewModelConfig<ModeloMensagemDTO> createFormViewModelConfig(boolean readOnly) {
        return new ModeloMensagemFormViewModelConfig(readOnly, modeloMensagemManager);
    }
}
