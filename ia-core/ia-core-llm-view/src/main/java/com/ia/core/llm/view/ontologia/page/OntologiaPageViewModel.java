package com.ia.core.llm.view.ontologia.page;

import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.ia.core.llm.view.ontologia.form.OntologiaFormViewModel;
import com.ia.core.llm.view.ontologia.form.OntologiaFormViewModelConfig;
import com.ia.core.security.view.log.operation.page.EntityPageViewModel;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.form.viewModel.IFormViewModel;

public class OntologiaPageViewModel extends EntityPageViewModel<OntologiaDTO> {

  public OntologiaPageViewModel(OntologiaPageViewModelConfig config) {
    super(config);
  }

  @Override
  public OntologiaDTO cloneObject(OntologiaDTO object) {
    return object.cloneObject();
  }

  @Override
  public OntologiaDTO createNewObject() {
    return OntologiaDTO.builder().build();
  }

  @Override
  protected SearchRequestDTO createSearchRequest() {
    return SearchRequestDTO.builder().build();
  }

  @Override
  public Long getId(OntologiaDTO object) {
    return object.getId();
  }

  @Override
  public Class<OntologiaDTO> getType() {
    return OntologiaDTO.class;
  }

  @Override
  public IFormViewModel<OntologiaDTO> createFormViewModel(FormViewModelConfig<OntologiaDTO> config) {
    return new OntologiaFormViewModel(config.cast());
  }
}
