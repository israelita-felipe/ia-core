package com.ia.core.llm.view.ontologia.form;

import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.ia.core.llm.view.ontologia.OntologiaManager;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class OntologiaFormViewModelConfig extends FormViewModelConfig<OntologiaDTO> {

  @Getter
  private final OntologiaManager ontologiaManager;

  public OntologiaFormViewModelConfig(boolean readOnly, OntologiaManager ontologiaManager) {
    super(readOnly);
    this.ontologiaManager = ontologiaManager;
  }
}
