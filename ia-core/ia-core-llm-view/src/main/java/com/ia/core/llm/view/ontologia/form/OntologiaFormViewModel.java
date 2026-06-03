package com.ia.core.llm.view.ontologia.form;

import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.ia.core.view.components.form.viewModel.FormViewModel;

public class OntologiaFormViewModel extends FormViewModel<OntologiaDTO> {

  public OntologiaFormViewModel(OntologiaFormViewModelConfig config) {
    super(config);
  }

  @Override
  public OntologiaFormViewModelConfig getConfig() {
    return (OntologiaFormViewModelConfig) super.getConfig();
  }
}
