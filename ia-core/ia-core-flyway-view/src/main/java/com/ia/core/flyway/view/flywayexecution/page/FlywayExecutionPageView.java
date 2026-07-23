package com.ia.core.flyway.view.flywayexecution.page;

import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionDTO;
import com.ia.core.flyway.view.flywayexecution.list.FlywayExecutionListView;
import com.ia.core.view.components.form.IFormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.IListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.page.PageView;

/**
 * PageView para exibir o histórico de execuções do Flyway.
 * <p>
 * Esta página exibe uma lista de todas as migrações do Flyway, com suporte para
 * filtrar por execuções bem-sucedidas ou falhadas.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class FlywayExecutionPageView<T extends FlywayExecutionDTO<?>>
    extends PageView<T> {

    public static final String ROUTE = "flyway-execution";

    public FlywayExecutionPageView(FlywayExecutionPageViewModel<T> viewModel) {
        super(viewModel);
        viewModel.setReadOnly(true);
        refreshButtons();
    }

    @Override
    public IFormView<T> createFormView(IFormViewModel<T> formViewModel) {
        // Não há formulário pois é apenas leitura
        return null;
    }

    @Override
    public IListView<T> createListView(IListViewModel<T> listViewModel) {
        return new FlywayExecutionListView<>(listViewModel);
    }

    @Override
    @SuppressWarnings("unchecked")
    public FlywayExecutionPageViewModel<T> getViewModel() {
        return super.getViewModel().cast();
    }

}
