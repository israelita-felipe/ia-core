package com.ia.core.communication.view.grupocontato.page;

import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoDTO;
import com.ia.core.communication.view.grupocontato.form.GrupoContatoFormViewModel;
import com.ia.core.communication.view.grupocontato.form.GrupoContatoFormViewModelConfig;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.page.viewModel.PageViewModel;

public class GrupoContatoPageViewModel extends PageViewModel<GrupoContatoDTO> {

  public GrupoContatoPageViewModel(GrupoContatoPageViewModelConfig config) {
    super(config);
  }

  @Override
  public Long getId(GrupoContatoDTO object) {
    return object.getId();
  }

  @Override
  public GrupoContatoDTO createNewObject() {
    return GrupoContatoDTO.builder().build();
  }

  @Override
  protected SearchRequestDTO createSearchRequest() {
    return GrupoContatoDTO.getSearchRequest();
  }

  @Override
  public Class<GrupoContatoDTO> getType() {
    return GrupoContatoDTO.class;
  }

  @Override
  public IFormViewModel<GrupoContatoDTO> createFormViewModel(FormViewModelConfig<GrupoContatoDTO> config) {
    return new GrupoContatoFormViewModel((GrupoContatoFormViewModelConfig) config.cast());
  }

  @Override
  public GrupoContatoDTO cloneObject(GrupoContatoDTO object) {
    return object != null ? object.cloneObject() : null;
  }

  @Override
  public GrupoContatoDTO copyObject(GrupoContatoDTO object) {
    return object != null ? object.copyObject() : null;
  }

  @Override
  public GrupoContatoPageViewModelConfig getConfig() {
    return super.getConfig().cast();
  }
}
