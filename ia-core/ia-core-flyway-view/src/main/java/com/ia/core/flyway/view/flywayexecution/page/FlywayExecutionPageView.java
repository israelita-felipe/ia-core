package com.ia.core.flyway.view.flywayexecution.page;

import java.util.Collection;
import java.util.List;

import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionDTO;
import com.ia.core.flyway.view.flywayexecution.list.FlywayExecutionListView;
import com.ia.core.view.components.form.IFormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.IListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.page.PageView;
import com.ia.core.view.components.page.viewModel.IPageViewModel.PageAction;

/**
 * PageView para exibir o histórico de execuções do Flyway.
 * <p>
 * Esta página exibe uma lista de todas as migrações do Flyway, com suporte para
 * filtrar por execuções bem-sucedidas ou falhadas.
 * </p>
 *
 * @author Israel Araújo
 */
public class FlywayExecutionPageView
  extends PageView<FlywayExecutionDTO> {

  public static final String ROUTE = "flyway-execution";

  public FlywayExecutionPageView(FlywayExecutionPageViewModel viewModel) {
    super(viewModel);
  }

  @Override
  public IFormView<FlywayExecutionDTO> createFormView(IFormViewModel<FlywayExecutionDTO> formViewModel) {
    // Não há formulário pois é apenas leitura
    return null;
  }

  @Override
  public IListView<FlywayExecutionDTO> createListView(IListViewModel<FlywayExecutionDTO> listViewModel) {
    return new FlywayExecutionListView(listViewModel);
  }

  @Override
  public Collection<PageAction<FlywayExecutionDTO>> createDefaultPageActions() {
    List<PageAction<FlywayExecutionDTO>> actions = new java.util.ArrayList<>();
    // Ações de visualização apenas - não há edição pois é tabela de sistema
    return actions;
  }

  @Override
  public FlywayExecutionPageViewModel getViewModel() {
    return super.getViewModel().cast();
  }

}
