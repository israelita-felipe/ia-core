package com.ia.core.flyway.view.flywayexecution;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ia.core.flyway.service.model.flywayexecution.FlywayExecutionTranslator;
import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionDTO;
import com.ia.core.flyway.service.model.flywayexecution.usecase.FlywayExecutionUseCase;
import com.ia.core.security.view.manager.DefaultSecuredViewBaseManager;

import lombok.extern.slf4j.Slf4j;

/**
 * Manager para operações de FlywayExecution.
 * <p>
 * Implementa o caso de uso para gerenciamento deexecuções de migrations do Flyway
 * na camada de visualização. Atua como proxy para as operações do serviço,
 * delegando chamadas ao cliente Feign.
 * </p>
 *
 * @author Israel Araújo
 * @see FlywayExecutionUseCase
 */
@Slf4j
@Service
public class FlywayExecutionManager
	extends DefaultSecuredViewBaseManager<FlywayExecutionDTO>
	implements FlywayExecutionUseCase {

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
	public List<FlywayExecutionDTO> listAll() {
		log.debug("Listando todas as execuções de migrations via Manager");
		return getClient().listAll();
	}

	@Override
	public List<FlywayExecutionDTO> listSuccessful() {
		log.debug("Listando execuções bem-sucedidas via Manager");
		return getClient().listSuccessful();
	}

	@Override
	public List<FlywayExecutionDTO> listFailed() {
		log.debug("Listando execuções falhadas via Manager");
		return getClient().listFailed();
	}

	@Override
	public FlywayExecutionDTO findById(Long id) {
		log.debug("Buscando execução por id: {} via Manager", id);
		return getClient().find(id);
	}

	// ========== OPERACOES NAO SUPORTADAS ==========
	// Como a tabela flyway_schema_history é de leitura apenas (gerenciada pelo Flyway),
	// não oferecemos operacoes de save, delete, etc.

	public FlywayExecutionDTO save(FlywayExecutionDTO toSave) {
		throw new UnsupportedOperationException(
			"Operação não suportada: a tabela flyway_schema_history é gerenciada automaticamente pelo Flyway");
	}

	public void delete(Long id) {
		throw new UnsupportedOperationException(
			"Operação não suportada: a tabela flyway_schema_history é gerenciada automaticamente pelo Flyway");
	}

	public FlywayExecutionDTO update(FlywayExecutionDTO toUpdate) {
		throw new UnsupportedOperationException(
			"Operação não suportada: a tabela flyway_schema_history é gerenciada automaticamente pelo Flyway");
	}
}