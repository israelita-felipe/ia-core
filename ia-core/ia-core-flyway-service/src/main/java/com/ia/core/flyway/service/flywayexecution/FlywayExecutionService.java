package com.ia.core.flyway.service.flywayexecution;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ia.core.flyway.model.FlywayExecution;
import com.ia.core.flyway.service.model.flywayexecution.FlywayExecutionTranslator;
import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionDTO;
import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionSearchRequest;
import com.ia.core.flyway.service.model.flywayexecution.usecase.FlywayExecutionUseCase;
import com.ia.core.service.dto.request.SearchRequestDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * Serviço para gerenciar execuções de migrations do Flyway.
 * <p>
 * Este serviço fornece métodos para consultar o histórico de execuções
 * de migrações do banco de dados. Os dados são somente leitura pois são
 * gerenciados automaticamente pelo Flyway.
 * </p>
 *
 * @author Israel Araújo
 * @see FlywayExecutionUseCase
 */
@Slf4j
@Service
public class FlywayExecutionService implements FlywayExecutionUseCase {

	private final FlywayExecutionRepository repository;
	private final FlywayExecutionMapper mapper;

	/**
	 * Construtor com injeção de dependências.
	 *
	 * @param config a configuração do serviço
	 */
	public FlywayExecutionService(FlywayExecutionServiceConfig config) {
		this.repository = config.getFlywayRepository();
		this.mapper = config.getFlywayMapper();
	}

	@Override
	public List<FlywayExecutionDTO> listAll() {
		log.debug("Listando todas as execuções de migrations");
		List<FlywayExecution> executions = repository.findAllByOrderByIdAsc();
		return mapper.toDTOList(executions);
	}

	@Override
	public List<FlywayExecutionDTO> listSuccessful() {
		log.debug("Listando execuções bem-sucedidas");
		List<FlywayExecution> executions = repository.findBySuccessTrueOrderByIdAsc();
		return mapper.toDTOList(executions);
	}

	@Override
	public List<FlywayExecutionDTO> listFailed() {
		log.debug("Listando execuções falhadas");
		List<FlywayExecution> executions = repository.findBySuccessFalseOrderByIdAsc();
		return mapper.toDTOList(executions);
	}

	@Override
	public FlywayExecutionDTO findById(Long id) {
		log.debug("Buscando execução por id: {}", id);
		return repository.findById(id).map(mapper::toDTO).orElse(null);
	}

	/**
	 * Retorna o tipo de funcionalidade para controls de segurança.
	 *
	 * @return o nome da funcionalidade
	 */
	public String getFunctionalityTypeName() {
		return FlywayExecutionTranslator.FLYWAY_EXECUTION;
	}
}