package com.ia.core.llm.view.prompt.list;

import com.ia.core.llm.service.model.prompt.PromptDTO;
import com.ia.core.llm.service.model.prompt.PromptTranslator;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
/**
 * View para exibição e manipulação de comando sistema list.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PromptListView
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class PromptListView
  extends ListView<PromptDTO> {

  /**
   * @param viewModel
   */
  public PromptListView(IListViewModel<PromptDTO> viewModel) {
    super(viewModel);
  }

  @Override
  protected void createColumns() {
    super.createColumns();
    addColumn("titulo").setHeader($(PromptTranslator.TITULO));
    addColumn("finalidade")
        .setHeader($(PromptTranslator.FINALIDADE));
  }
}
