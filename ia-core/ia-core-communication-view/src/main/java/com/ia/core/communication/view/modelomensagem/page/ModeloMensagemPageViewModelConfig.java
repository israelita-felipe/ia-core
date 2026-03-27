package com.ia.core.communication.view.modelomensagem.page;

import org.springframework.stereotype.Component;

import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.communication.view.modelomensagem.ModeloMensagemManager;
import com.ia.core.communication.view.modelomensagem.form.ModeloMensagemFormViewModelConfig;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.page.viewModel.PageViewModelConfig;
import com.ia.core.view.manager.DefaultBaseManager;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@Component
public class ModeloMensagemPageViewModelConfig extends PageViewModelConfig<ModeloMensagemDTO> {
  private final ModeloMensagemManager modeloMensagemManager;

  public ModeloMensagemPageViewModelConfig(DefaultBaseManager<ModeloMensagemDTO> service, ModeloMensagemManager modeloMensagemManager) {
    super(service);
    this.modeloMensagemManager = modeloMensagemManager;
  }

  @Override
  protected FormViewModelConfig<ModeloMensagemDTO> createFormViewModelConfig(boolean readOnly) {
    return new ModeloMensagemFormViewModelConfig(readOnly, modeloMensagemManager);
  }

  public ModeloMensagemManager getModeloMensagemManager() {
    return modeloMensagemManager;
  }
}
