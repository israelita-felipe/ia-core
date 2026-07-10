package com.ia.core.flyway.service.model.flywayexecution.dto;

import com.ia.core.flyway.model.FlywayExecution;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
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

    /**
     * Cria uma cópia superficial (clone) deste DTO.
     */
    @Override
    public FlywayExecutionDTO cloneObject() {
        return toBuilder().build();
    }

    /**
     * Retorna uma representação em string deste objeto.
     */
    @Override
    public String toString() {
        return String.format("FlywayExecutionDTO{version=%s, success=%s}", migrationVersion, success);
    }

    /**
     * Inner class com constantes para nomes dos campos.
     */
    @SuppressWarnings("javadoc")
    public static class CAMPOS extends AbstractBaseEntityDTO.CAMPOS {
        public static final String MIGRATION_VERSION = "migrationVersion";
        public static final String DESCRIPTION = "description";
        public static final String TYPE = "type";
        public static final String SCRIPT = "script";
        public static final String CHECKSUM = "checksum";
        public static final String INSTALLED_BY = "installedBy";
        public static final String INSTALLED_ON = "installedOn";
        public static final String EXECUTION_TIME = "executionTime";
        public static final String SUCCESS = "success";

        /**
         * Retorna todos os valores das constantes de campos.
         */
        public static Set<String> values() {
            var baseValues = AbstractBaseEntityDTO.CAMPOS.values();
            var currentValues = Set.of(MIGRATION_VERSION, DESCRIPTION, TYPE, SCRIPT,
                CHECKSUM, INSTALLED_BY, INSTALLED_ON, EXECUTION_TIME, SUCCESS);
            var allValues = new HashSet<String>();
            allValues.addAll(baseValues);
            allValues.addAll(currentValues);
            return Collections.unmodifiableSet(allValues);
        }
    }
}