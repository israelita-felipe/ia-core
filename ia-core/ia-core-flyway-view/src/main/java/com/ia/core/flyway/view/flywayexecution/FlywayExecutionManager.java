package com.ia.core.flyway.view.flywayexecution;

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
import org.springframework.stereotype.Service;

/**
 * Manager para operações de FlywayExecution.
 * <p>
 * Implementa o caso de uso para gerenciamento deexecuções de migrations do
 * Flyway na camada de visualização. Atua como proxy para as operações do
 * serviço, delegando chamadas ao cliente Feign.
 * </p>
 *
 * @author Israel Araújo
 * @see FlywayExecutionUseCase
 */
@Slf4j
@Service
public class FlywayExecutionManager
  extends DefaultBaseManager<FlywayExecutionDTO>
  implements CountSecuredViewBaseManager<FlywayExecutionDTO>,
  FindSecuredViewBaseManager<FlywayExecutionDTO>,
  ListSecuredViewBaseManager<FlywayExecutionDTO>, FlywayExecutionUseCase {

  public FlywayExecutionManager(FlywayExecutionManagerConfig config) {
    super(config);
  }

  @Override
  public FlywayExecutionManagerConfig getConfig() {
    return (FlywayExecutionManagerConfig) super.getConfig();
  }

  @Override
  public FlywayExecutionClient getClient() {
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
  public Page<FlywayExecutionDTO> listSuccessful(SearchRequestDTO request) {
    return getClient().findAllSuccessful(request);
  }

  @Override
  public Page<FlywayExecutionDTO> listFailed(SearchRequestDTO request) {
    return getClient().findAllFailed(request);
  }

}
