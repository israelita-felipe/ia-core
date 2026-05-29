package com.ia.core.llm.view.prompt.page;

import com.ia.core.llm.service.model.prompt.PromptDTO;
import com.ia.core.llm.view.prompt.form.PromptFormView;
import com.ia.core.llm.view.prompt.list.PromptListView;
import com.ia.core.security.view.log.operation.page.EntityPageView;
import com.ia.core.view.components.form.IFormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.IListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.page.viewModel.IPageViewModel;
/**
 * View para exibição e manipulação de comando sistema page.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PromptPageView
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class PromptPageView
  extends EntityPageView<PromptDTO> {
  /** Serial UID */
  private static final long serialVersionUID = 8506976371963888902L;
  /** Rota */
  public static final String ROUTE = "prompt";

  /**
   * @param viewModel
   */
  public PromptPageView(IPageViewModel<PromptDTO> viewModel) {
    super(viewModel);
  }

  @Override
  public IFormView<PromptDTO> createFormView(IFormViewModel<PromptDTO> formViewModel) {
    return new PromptFormView(formViewModel);
  }

  @Override
  public IListView<PromptDTO> createListView(IListViewModel<PromptDTO> listViewModel) {
    return new PromptListView(listViewModel);
  }
}
