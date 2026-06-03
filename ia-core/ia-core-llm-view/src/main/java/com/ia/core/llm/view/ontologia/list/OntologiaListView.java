package com.ia.core.llm.view.ontologia.list;

import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.ia.core.llm.service.model.ontologia.OntologiaTranslator;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;

public class OntologiaListView extends ListView<OntologiaDTO> {

  public OntologiaListView(IListViewModel<OntologiaDTO> viewModel) {
    super(viewModel);
  }

  @Override
  protected void createColumns() {
    super.createColumns();
    addColumn(OntologiaDTO.CAMPOS.IRI).setHeader($(OntologiaTranslator.IRI));
    addColumn(OntologiaDTO.CAMPOS.NOME).setHeader($(OntologiaTranslator.NOME));
    addColumn(OntologiaDTO.CAMPOS.VERSAO).setHeader($(OntologiaTranslator.VERSAO));
    addColumn(OntologiaDTO.CAMPOS.CONSISTENTE).setHeader($(OntologiaTranslator.CONSISTENTE));
  }
}
