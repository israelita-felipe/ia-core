package com.ia.core.flyway.view.flywayexecution.page;

import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionDTO;
import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionSearchRequestDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.page.viewModel.PageViewModel;
import lombok.extern.slf4j.Slf4j;

/**
 * ViewModel para a página de execuções do Flyway.
 * <p>
 * Como esta é uma tabela de histórico de migrations (apenas leitura), este
 * ViewModel fornece métodos específicos para listar todas as execuções, apenas
 * as bem-sucedidas ou apenas as falhadas.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
public abstract class FlywayExecutionPageViewModel<T extends FlywayExecutionDTO<?>>
    extends PageViewModel<T> {

    public FlywayExecutionPageViewModel(FlywayExecutionPageViewModelConfig<T> config) {
        super(config);
    }

    @Override
    protected SearchRequestDTO createSearchRequest() {
        return new FlywayExecutionSearchRequestDTO();
    }

    @Override
    public IFormViewModel<T> createFormViewModel(FormViewModelConfig<T> config) {
        // Tabela de histórico de migrations é apenas leitura - não há formulário
        throw new UnsupportedOperationException("Operação não suportada: a tabela flyway_schema_history é gerenciada automaticamente pelo Flyway");
    }

    @Override
    public Long getId(T object) {
        return object != null ? object.getId() : null;
    }


    @Override
    public FlywayExecutionPageViewModelConfig<T> getConfig() {
        return super.getConfig().cast();
    }


}
