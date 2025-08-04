package com.ia.core.llm.view.comando.page;

import java.util.UUID;

import com.ia.core.llm.service.model.comando.ComandoSistemaDTO;
import com.ia.core.llm.view.comando.form.ComandoSistemaFormViewModel;
import com.ia.core.llm.view.template.TemplateService;
import com.ia.core.security.view.log.operation.LogOperationService;
import com.ia.core.security.view.log.operation.page.EntityPageViewModel;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.service.DefaultBaseService;

/**
 * @author Israel Araújo
 */
public class ComandoSistemaPageViewModel
  extends EntityPageViewModel<ComandoSistemaDTO> {
  /** Serviço de template */
  private TemplateService templateService;

  /**
   * @param service
   */
  public ComandoSistemaPageViewModel(DefaultBaseService<ComandoSistemaDTO> service,
                                     TemplateService templateService,
                                     LogOperationService logOperationService) {
    super(service, logOperationService);
    this.templateService = templateService;
  }

  @Override
  public ComandoSistemaDTO cloneObject(ComandoSistemaDTO object) {
    return object.cloneObject();
  }

  @Override
  public IFormViewModel<ComandoSistemaDTO> createFormViewModel(boolean readOnly) {
    return new ComandoSistemaFormViewModel(readOnly, templateService);
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
