package com.ia.core.communication.view.modelomensagem.page;

import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.communication.view.modelomensagem.form.ModeloMensagemFormViewModel;
import com.ia.core.communication.view.modelomensagem.form.ModeloMensagemFormViewModelConfig;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.page.viewModel.PageViewModel;

public class ModeloMensagemPageViewModel extends PageViewModel<ModeloMensagemDTO> {
  public ModeloMensagemPageViewModel(ModeloMensagemPageViewModelConfig config) {
    super(config);
  }

  @Override
  public Long getId(ModeloMensagemDTO object) {
    return object.getId();
  }

  @Override
  public ModeloMensagemDTO createNewObject() {
    return ModeloMensagemDTO.builder().build();
  }

  @Override
  protected SearchRequestDTO createSearchRequest() {
    return ModeloMensagemDTO.getSearchRequest();
  }

  @Override
  public Class<ModeloMensagemDTO> getType() {
    return ModeloMensagemDTO.class;
  }

  @Override
  public IFormViewModel<ModeloMensagemDTO> createFormViewModel(FormViewModelConfig<ModeloMensagemDTO> config) {
    return new ModeloMensagemFormViewModel((ModeloMensagemFormViewModelConfig) config.cast());
  }

  @Override
  public ModeloMensagemDTO cloneObject(ModeloMensagemDTO object) {
    return object != null ? object.cloneObject() : null;
  }

  @Override
  public ModeloMensagemDTO copyObject(ModeloMensagemDTO object) {
    return object != null ? object.copyObject() : null;
  }

  @Override
  public ModeloMensagemPageViewModelConfig getConfig() {
    return super.getConfig().cast();
  }
}
