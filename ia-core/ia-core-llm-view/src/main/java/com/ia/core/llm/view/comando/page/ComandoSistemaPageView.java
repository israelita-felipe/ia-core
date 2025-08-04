package com.ia.core.llm.view.comando.page;

import com.ia.core.llm.service.model.comando.ComandoSistemaDTO;
import com.ia.core.llm.view.comando.form.ComandoSistemaFormView;
import com.ia.core.llm.view.comando.list.ComandoSistemaListView;
import com.ia.core.security.view.log.operation.page.EntityPageView;
import com.ia.core.view.components.form.IFormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.IListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.page.viewModel.IPageViewModel;

/**
 * @author Israel Araújo
 */
public class ComandoSistemaPageView
  extends EntityPageView<ComandoSistemaDTO> {
  /** Serial UID */
  private static final long serialVersionUID = 8506976371963888902L;
  /** Rota */
  public static final String ROUTE = "comando/sistema";

  /**
   * @param viewModel
   */
  public ComandoSistemaPageView(IPageViewModel<ComandoSistemaDTO> viewModel) {
    super(viewModel);
  }

  @Override
  public IFormView<ComandoSistemaDTO> createFormView(IFormViewModel<ComandoSistemaDTO> formViewModel) {
    return new ComandoSistemaFormView(formViewModel);
  }

  @Override
  public IListView<ComandoSistemaDTO> createListView(IListViewModel<ComandoSistemaDTO> listViewModel) {
    return new ComandoSistemaListView(listViewModel);
  }
}
