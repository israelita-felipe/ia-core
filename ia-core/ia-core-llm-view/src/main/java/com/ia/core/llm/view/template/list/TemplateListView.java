package com.ia.core.llm.view.template.list;

import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.llm.service.model.template.TemplateTranslator;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
/**
 * View para exibição e manipulação de template list.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a TemplateListView
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
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
