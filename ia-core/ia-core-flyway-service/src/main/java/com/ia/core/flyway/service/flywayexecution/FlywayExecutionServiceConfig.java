package com.ia.core.flyway.service.flywayexecution;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;

import com.ia.core.flyway.model.FlywayExecution;
import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionDTO;
import com.ia.core.security.service.DefaultSecuredBaseService.DefaultSecuredBaseServiceConfig;
import com.ia.core.security.service.SecurityContextService;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;

/**
 * Configuração de injeção de dependência para FlywayExecutionService.
 * <p>
 * Extende DefaultSecuredBaseServiceConfig para seguir os padrões do ia-core.
 * </p>
 *
 * @author Israel Araújo
 */
public class FlywayExecutionServiceConfig
  extends
  DefaultSecuredBaseServiceConfig<FlywayExecution, FlywayExecutionDTO> {

  /**
   * @param repository             repositório da entidade
   * @param mapper                 mapper para conversão DTO
   * @param searchRequestMapper    mapeador de requisições de busca
   * @param translator             tradutor para internacionalização
   * @param authorizationManager   gerenciador de autorização
   * @param securityContextService contexto de segurança
   * @param logOperationService    serviço de operações de log
   * @param eventPublisher         publicador de eventos
   */
  public FlywayExecutionServiceConfig(BaseEntityRepository<FlywayExecution> repository,
                                      FlywayExecutionMapper mapper,
                                      SearchRequestMapper searchRequestMapper,
                                      Translator translator,
                                      CoreSecurityAuthorizationManager authorizationManager,
                                      SecurityContextService securityContextService,
                                      LogOperationService logOperationService,
                                      List<IServiceValidator<FlywayExecutionDTO>> validators,
                                      ApplicationEventPublisher eventPublisher) {
    super(repository, mapper, searchRequestMapper, translator,
          authorizationManager, securityContextService, logOperationService,
          validators, eventPublisher);
  }

}
