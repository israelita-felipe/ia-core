package com.ia.core.llm.view.prompt.page;

import com.ia.core.llm.service.model.prompt.PromptDTO;
import com.ia.core.llm.view.prompt.form.PromptFormViewModel;
import com.ia.core.security.view.log.operation.page.EntityPageViewModel;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
/**
 * Model de dados para a view de comando sistema page.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PromptPageViewModel
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class PromptPageViewModel
  extends EntityPageViewModel<PromptDTO> {

  /**
   * @param service
   */
  public PromptPageViewModel(PromptPageViewModelConfig config) {
    super(config);

  }

  @Override
  public PromptDTO cloneObject(PromptDTO object) {
    return object.cloneObject();
  }

  @Override
  public IFormViewModel<PromptDTO> createFormViewModel(FormViewModelConfig<PromptDTO> config) {
    return new PromptFormViewModel(config.cast());
  }

  @Override
  public PromptPageViewModelConfig getConfig() {
    return (PromptPageViewModelConfig) super.getConfig();
  }

  @Override
  public PromptDTO createNewObject() {
    return PromptDTO.builder().build();
  }

  @Override
  protected SearchRequestDTO createSearchRequest() {
    return PromptDTO.getSearchRequest();
  }

  @Override
  public Long getId(PromptDTO object) {
    return object.getId();
  }

  @Override
  public Class<PromptDTO> getType() {
    return PromptDTO.class;
  }
}
