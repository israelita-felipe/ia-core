package com.ia.core.llm.view.ontologia.page;

import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.ia.core.llm.view.ontologia.form.OntologiaFormView;
import com.ia.core.llm.view.ontologia.list.OntologiaListView;
import com.ia.core.security.view.log.operation.page.EntityPageView;
import com.ia.core.view.components.form.IFormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.IListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.page.viewModel.IPageViewModel;

/**
 * View para exibição e manipulação de ontologias.
 * <p>
 * Segue o padrão ADR-008 MVVM com separação View-ViewModel.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class OntologiaPageView extends EntityPageView<OntologiaDTO> {

  private static final long serialVersionUID = 1L;
  public static final String ROUTE = "ontologia";

  public OntologiaPageView(IPageViewModel<OntologiaDTO> viewModel) {
    super(viewModel);
  }

  @Override
  public IFormView<OntologiaDTO> createFormView(IFormViewModel<OntologiaDTO> formViewModel) {
    return new OntologiaFormView(formViewModel);
  }

  @Override
  public IListView<OntologiaDTO> createListView(IListViewModel<OntologiaDTO> listViewModel) {
    return new OntologiaListView(listViewModel);
  }
}
