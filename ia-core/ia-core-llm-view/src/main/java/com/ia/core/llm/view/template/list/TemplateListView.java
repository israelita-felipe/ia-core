package com.ia.core.llm.view.template.list;

import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.llm.service.model.template.TemplateTranslator;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;

/**
 * @author Israel Araújo
 */
public class TemplateListView
  extends ListView<TemplateDTO> {

  /**
   * @param viewModel
   */
  public TemplateListView(IListViewModel<TemplateDTO> viewModel) {
    super(viewModel);
  }

  @Override
  protected void createColumns() {
    super.createColumns();
    addColumn("titulo").setHeader($(TemplateTranslator.TITULO));
    addColumn("exigeContexto")
        .setHeader($(TemplateTranslator.EXIGE_CONTEXTO));
  }
}
