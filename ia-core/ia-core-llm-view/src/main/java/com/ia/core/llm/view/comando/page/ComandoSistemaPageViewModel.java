package com.ia.core.llm.view.comando.page;

import java.util.UUID;

import com.ia.core.llm.service.model.comando.ComandoSistemaDTO;
import com.ia.core.llm.view.comando.form.ComandoSistemaFormViewModel;
import com.ia.core.security.view.log.operation.page.EntityPageViewModel;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.form.viewModel.IFormViewModel;

/**
 * @author Israel Araújo
 */
public class ComandoSistemaPageViewModel
  extends EntityPageViewModel<ComandoSistemaDTO> {

  /**
   * @param service
   */
  public ComandoSistemaPageViewModel(ComandoSistemaPageViewModelConfig config) {
    super(config);

  }

  @Override
  public ComandoSistemaDTO cloneObject(ComandoSistemaDTO object) {
    return object.cloneObject();
  }

  @Override
  public IFormViewModel<ComandoSistemaDTO> createFormViewModel(FormViewModelConfig<ComandoSistemaDTO> config) {
    return new ComandoSistemaFormViewModel(config.cast());
  }

  @Override
  public ComandoSistemaPageViewModelConfig getConfig() {
    return (ComandoSistemaPageViewModelConfig) super.getConfig();
  }

  @Override
  public ComandoSistemaDTO createNewObject() {
    return ComandoSistemaDTO.builder().build();
  }

  @Override
  protected SearchRequestDTO createSearchRequest() {
    return ComandoSistemaDTO.getSearchRequest();
  }

  @Override
  public UUID getId(ComandoSistemaDTO object) {
    return object.getId();
  }

  @Override
  public Class<ComandoSistemaDTO> getType() {
    return ComandoSistemaDTO.class;
  }
}
