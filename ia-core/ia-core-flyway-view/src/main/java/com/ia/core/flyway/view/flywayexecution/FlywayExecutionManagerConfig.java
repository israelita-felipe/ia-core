package com.ia.core.flyway.view.flywayexecution;

import org.springframework.stereotype.Component;

import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionDTO;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.view.manager.DefaultSecuredViewBaseMangerConfig;

import lombok.Getter;

/**
 * Configuração de injeção de dependência para FlywayExecutionManager.
 *
 * @author Israel Araújo
 */
@Getter
@Component
public class FlywayExecutionManagerConfig
  extends DefaultSecuredViewBaseMangerConfig<FlywayExecutionDTO> {

  /**
   * @param client               {@link FlywayExecutionClient} de comunicação
   * @param authorizationManager {@link CoreSecurityAuthorizationManager}
   */
  public FlywayExecutionManagerConfig(FlywayExecutionClient client,
                                      CoreSecurityAuthorizationManager authorizationManager) {
    super(client, authorizationManager);
  }

  @Override
  public FlywayExecutionClient getClient() {
    return (FlywayExecutionClient) super.getClient();
  }
}
