package com.ia.core.flyway.service.model.flywayexecution;

import java.util.List;

import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionDTO;

/**
 * Interface de Use Case para FlywayExecution.
 * <p>
 * Define as operações específicas do domínio de execuções de migrations do Flyway.
 * Como a tabela flyway_schema_history é gerenciada automaticamente pelo Flyway,
 * esta interface fornece apenas operações de leitura.
 * </p>
 *
 * @author Israel Araújo
 */
public interface FlywayExecutionUseCase {

	/**
	 * Lista todas as execuções de migrations ordenadas por rank de instalação.
	 *
	 * @return lista de execuções
	 */
	List<FlywayExecutionDTO> listAll();

	/**
	 * Lista apenas as execuções bem-sucedidas.
	 *
	 * @return lista de execuções bem-sucedidas
	 */
	List<FlywayExecutionDTO> listSuccessful();

	/**
	 * Lista apenas as execuções falhadas.
	 *
	 * @return lista de execuções falhadas
	 */
	List<FlywayExecutionDTO> listFailed();

	/**
	 * Obtém uma execução específica pelo ID.
	 *
	 * @param id o ID da execução
	 * @return a execução encontrada ou null
	 */
	FlywayExecutionDTO findById(Long id);
}