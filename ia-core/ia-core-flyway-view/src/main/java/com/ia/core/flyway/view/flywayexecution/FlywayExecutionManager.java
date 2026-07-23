package com.ia.core.flyway.view.flywayexecution;

import com.ia.core.flyway.model.FlywayExecution;
import com.ia.core.flyway.service.model.flywayexecution.FlywayExecutionUseCase;
import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionDTO;
import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionTranslator;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.view.manager.CountSecuredViewBaseManager;
import com.ia.core.security.view.manager.FindSecuredViewBaseManager;
import com.ia.core.security.view.manager.ListSecuredViewBaseManager;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.manager.DefaultBaseManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

/**
 * Manager para operações de FlywayExecution.
 * <p>
 * Implementa o caso de uso para gerenciamento de execuções de migrations do
 * Flyway na camada de visualização. Atua como proxy para as operações do
 * serviço, delegando chamadas ao cliente Feign.
 *
 * @author Israel Araújo
 * @see FlywayExecutionUseCase
 * @since 1.0.0
 */
@Slf4j
public class FlywayExecutionManager<TModel extends FlywayExecution, T extends FlywayExecutionDTO<TModel>>
    extends DefaultBaseManager<T>
    implements CountSecuredViewBaseManager<T>,
    FindSecuredViewBaseManager<T>,
    ListSecuredViewBaseManager<T>, FlywayExecutionUseCase<TModel, T> {

    public FlywayExecutionManager(FlywayExecutionManagerConfig<TModel, T> config) {
        super(config);
    }

    @Override
    public FlywayExecutionManagerConfig<TModel, T> getConfig() {
        return (FlywayExecutionManagerConfig<TModel, T>) super.getConfig();
    }

    @Override
    public FlywayExecutionClient<T> getClient() {
        return getConfig().getClient();
    }

    @Override
    public String getFunctionalityTypeName() {
        return FlywayExecutionTranslator.FLYWAY_EXECUTION;
    }

    @Override
    public CoreSecurityAuthorizationManager getAuthorizationManager() {
        return getConfig().getAuthorizationManager();
    }

    @Override
    public void createContext() {

    }

    @Override
    public Page<T> listSuccessful(SearchRequestDTO request) {
        return getClient().findAllSuccessful(request);
    }

    @Override
    public Page<T> listFailed(SearchRequestDTO request) {
        return getClient().findAllFailed(request);
    }

}
