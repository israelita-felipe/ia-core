package com.ia.core.llm.view.template.page;

import java.util.UUID;

import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.llm.view.template.form.TemplateFormViewModel;
import com.ia.core.security.view.log.operation.LogOperationService;
import com.ia.core.security.view.log.operation.page.EntityPageViewModel;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.service.DefaultBaseService;

/**
 * @author Israel Araújo
 */
public class TemplatePageViewModel
  extends EntityPageViewModel<TemplateDTO> {

  /**
   * @param service
   */
  public TemplatePageViewModel(DefaultBaseService<TemplateDTO> service,
                               LogOperationService logOperationService) {
    super(service, logOperationService);
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
  public UUID getId(TemplateDTO object) {
    return object.getId();
  }

  @Override
  public Class<TemplateDTO> getType() {
    return TemplateDTO.class;
  }

  @Override
  public IFormViewModel<TemplateDTO> createFormViewModel(boolean readOnly) {
    return new TemplateFormViewModel(readOnly);
  }
}
