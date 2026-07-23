package com.ia.core.flyway.view.flywayexecution;

import com.ia.core.flyway.model.FlywayExecution;
import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionDTO;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.view.manager.DefaultSecuredViewBaseMangerConfig;
import lombok.Getter;

/**
 * Configuração de injeção de dependência para FlywayExecutionManager.
 * <p>
 * Fornece as dependências necessárias para o manager, incluindo o cliente
 * Feign e o gerenciador de autorizações.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Getter
public class FlywayExecutionManagerConfig<TModel extends FlywayExecution, T extends FlywayExecutionDTO<TModel>>
    extends DefaultSecuredViewBaseMangerConfig<T> {

    /**
     * @param client               {@link FlywayExecutionClient} de comunicação
     * @param authorizationManager {@link CoreSecurityAuthorizationManager}
     */
    public FlywayExecutionManagerConfig(FlywayExecutionClient<T> client,
                                        CoreSecurityAuthorizationManager authorizationManager) {
        super(client, authorizationManager);
    }

    @Override
    public FlywayExecutionClient<T> getClient() {
        return (FlywayExecutionClient<T>) super.getClient();
    }
}
