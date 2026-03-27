package com.ia.core.flyway.service.model.flywayexecution.dto;

import java.time.LocalDateTime;

import com.ia.core.flyway.model.FlywayExecution;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO para representação de execuções de migrations do Flyway.
 * <p>
 * Este DTO é utilizado para transferir dados de execuções de migrações
 * do banco de dados para a camada de visualização ou API.
 * </p>
 * <p>
 * O campo {@code id} mapeia para a coluna {@code installed_rank}.
 * </p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class FlywayExecutionDTO extends AbstractBaseEntityDTO<FlywayExecution> {

	/** Serial UID */
	private static final long serialVersionUID = 1L;

	/**
	 * Versão da migração (mapeia para coluna version do Flyway).
	 */
	private String migrationVersion;

	/**
	 * Descrição da migração.
	 */
	private String description;

	/**
	 * Tipo de migração (SQL, JDBC, etc).
	 */
	private String type;

	/**
	 * Nome do script de migração.
	 */
	private String script;

	/**
	 * Checksum do script.
	 */
	private Integer checksum;

	/**
	 * Usuário que executou a migração.
	 */
	private String installedBy;

	/**
	 * Data e hora da execução.
	 */
	private LocalDateTime installedOn;

	/**
	 * Tempo de execução em milissegundos.
	 */
	private Integer executionTime;

	/**
	 * Indica se a migração foi executada com sucesso.
	 */
	private Boolean success;

	@Override
	public FlywayExecutionDTO cloneObject() {
		return toBuilder().build();
	}

}