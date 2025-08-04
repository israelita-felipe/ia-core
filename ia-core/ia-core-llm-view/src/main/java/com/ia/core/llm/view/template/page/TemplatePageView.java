package com.ia.core.llm.view.template.page;

import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.llm.view.template.form.TemplateFormView;
import com.ia.core.llm.view.template.list.TemplateListView;
import com.ia.core.security.view.log.operation.page.EntityPageView;
import com.ia.core.view.components.form.IFormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.IListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.page.viewModel.IPageViewModel;

/**
 * @author Israel Araújo
 */
public class TemplatePageView
  extends EntityPageView<TemplateDTO> {
  /** Serial UUID */
  private static final long serialVersionUID = -2796746737472741060L;
  /** Rota */
  public static final String ROUTE = "template";

  /**
   * @param viewModel
   */
  public TemplatePageView(IPageViewModel<TemplateDTO> viewModel) {
    super(viewModel);
  }

  @Override
  public IFormView<TemplateDTO> createFormView(IFormViewModel<TemplateDTO> formViewModel) {
    return new TemplateFormView(formViewModel);
  }

  @Override
  public IListView<TemplateDTO> createListView(IListViewModel<TemplateDTO> listViewModel) {
    return new TemplateListView(listViewModel);
  }

}
