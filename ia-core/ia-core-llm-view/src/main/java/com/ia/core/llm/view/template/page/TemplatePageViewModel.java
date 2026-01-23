package com.ia.core.llm.view.template.page;

import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.llm.view.template.form.TemplateFormViewModel;
import com.ia.core.security.view.log.operation.page.EntityPageViewModel;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.form.viewModel.IFormViewModel;

/**
 * @author Israel Araújo
 */
public class TemplatePageViewModel
  extends EntityPageViewModel<TemplateDTO> {

  /**
   * @param service
   */
  public TemplatePageViewModel(TemplatePageViewModelConfig config) {
    super(config);
  }

  @Override
  public TemplateDTO cloneObject(TemplateDTO object) {
    return object.cloneObject();
  }

  @Override
  public TemplateDTO createNewObject() {
    return TemplateDTO.builder().build();
  }

  @Override
  protected SearchRequestDTO createSearchRequest() {
    return TemplateDTO.getSearchRequest();
  }

  @Override
  public Long getId(TemplateDTO object) {
    return object.getId();
  }

  @Override
  public Class<TemplateDTO> getType() {
    return TemplateDTO.class;
  }

  @Override
  public IFormViewModel<TemplateDTO> createFormViewModel(FormViewModelConfig<TemplateDTO> config) {
    return new TemplateFormViewModel(config);
  }
}
