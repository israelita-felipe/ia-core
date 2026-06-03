package com.ia.core.llm.view.ontologia.form;

import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.ia.core.llm.service.model.ontologia.OntologiaTranslator;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class OntologiaFormView extends FormView<OntologiaDTO> {

  public OntologiaFormView(IFormViewModel<OntologiaDTO> viewModel) {
    super(viewModel);
    TextField iriField = createTextField(OntologiaTranslator.IRI, "IRI da ontologia");
    TextField nomeField = createTextField(OntologiaTranslator.NOME, "Nome da ontologia");
    TextField versaoField = createTextField(OntologiaTranslator.VERSAO, "Versão");
    TextArea descricaoField = createTextArea(OntologiaTranslator.DESCRICAO, "Descrição");
    bind(OntologiaDTO.CAMPOS.IRI, iriField);
    bind(OntologiaDTO.CAMPOS.NOME, nomeField);
    bind(OntologiaDTO.CAMPOS.VERSAO, versaoField);
    bind(OntologiaDTO.CAMPOS.DESCRICAO, descricaoField);
  }
}
