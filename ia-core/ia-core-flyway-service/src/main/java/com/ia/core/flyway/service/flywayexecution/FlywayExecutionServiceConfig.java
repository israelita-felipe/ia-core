package com.ia.core.flyway.service.flywayexecution;

import java.util.Collections;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.ia.core.flyway.model.FlywayExecution;
import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionDTO;
import com.ia.core.security.service.DefaultSecuredBaseService.DefaultSecuredBaseServiceConfig;
import com.ia.core.security.service.SecurityContextService;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.service.mapper.BaseEntityMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;

/**
 * Configuração de injeção de dependência para FlywayExecutionService.
 * <p>
 * Extende DefaultSecuredBaseServiceConfig para seguir os padrões do ia-core.
 * </p>
 *
 * @author Israel Araújo
 */
@Component
public class FlywayExecutionServiceConfig
  extends DefaultSecuredBaseServiceConfig<FlywayExecution, FlywayExecutionDTO> {

	/**
	 * @param repository          repositório da entidade
	 * @param mapper             mapper para conversão DTO
	 * @param searchRequestMapper mapeador de requisições de busca
	 * @param translator          tradutor para internacionalização
	 * @param authorizationManager gerenciador de autorização
	 * @param securityContextService contexto de segurança
	 * @param logOperationService serviço de operações de log
	 * @param eventPublisher     publicador de eventos
	 */
	public FlywayExecutionServiceConfig(
			BaseEntityRepository<FlywayExecution> repository,
			FlywayExecutionMapper mapper,
			SearchRequestMapper searchRequestMapper,
			Translator translator,
			CoreSecurityAuthorizationManager authorizationManager,
			SecurityContextService securityContextService,
			LogOperationService logOperationService,
			ApplicationEventPublisher eventPublisher) {
		super(repository, mapper, searchRequestMapper, translator,
				authorizationManager, securityContextService,
				logOperationService,
				Collections.emptyList(), // validators - não há para Flyway
				eventPublisher);
	}

	/**
	 * Retorna o repositório específico para FlywayExecution.
	 *
	 * @return o repositório
	 */
	public FlywayExecutionRepository getFlywayRepository() {
		return (FlywayExecutionRepository) getRepository();
	}

	/**
	 * Retorna o mapper específico para FlywayExecution.
	 *
	 * @return o mapper
	 */
	public FlywayExecutionMapper getFlywayMapper() {
		return (FlywayExecutionMapper) getMapper();
	}
}