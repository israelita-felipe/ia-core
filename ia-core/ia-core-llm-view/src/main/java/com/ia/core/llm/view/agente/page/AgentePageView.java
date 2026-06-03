package com.ia.core.llm.view.agente.page;

import com.ia.core.llm.service.model.agente.AgenteDTO;
import com.ia.core.llm.view.agente.form.AgenteFormView;
import com.ia.core.llm.view.agente.list.AgenteListView;
import com.ia.core.security.view.log.operation.page.EntityPageView;
import com.ia.core.view.components.form.IFormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.IListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.page.viewModel.IPageViewModel;

/**
 * View para exibição e manipulação de agente page.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a AgentePageView
 * dentro do sistema.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class AgentePageView
  extends EntityPageView<AgenteDTO> {
  /** Serial UID */
  private static final long serialVersionUID = 8506976371963888902L;
  /** Rota */
  public static final String ROUTE = "agente";

  /**
   * @param viewModel
   */
  public AgentePageView(IPageViewModel<AgenteDTO> viewModel) {
    super(viewModel);
  }

  @Override
  public IFormView<AgenteDTO> createFormView(IFormViewModel<AgenteDTO> formViewModel) {
    return new AgenteFormView(formViewModel);
  }

  @Override
  public IListView<AgenteDTO> createListView(IListViewModel<AgenteDTO> listViewModel) {
    return new AgenteListView(listViewModel);
  }
}
