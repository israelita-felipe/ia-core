package com.ia.core.flyway.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe FlywayExecution.
 */
@DisplayName("Testes de FlywayExecution")
class FlywayExecutionTest {

    @Test
    @DisplayName("Deve criar entidade com builder")
    void deveCriarEntidadeComBuilder() {
        ConcreteFlywayExecution execution = ConcreteFlywayExecution.builder()
            .id(1L)
            .migrationVersion("1.0.0")
            .description("Create users table")
            .type("SQL")
            .script("V1.0.0__Create_users_table.sql")
            .checksum(123456)
            .installedBy("admin")
            .installedOn(LocalDateTime.now())
            .executionTime(1000)
            .success(true)
            .build();

        assertThat(execution).isNotNull();
        assertThat(execution.getId()).isEqualTo(1L);
        assertThat(execution.getMigrationVersion()).isEqualTo("1.0.0");
    }

    @Test
    @DisplayName("Deve verificar campos da entidade")
    void deveVerificarCamposDaEntidade() {
        LocalDateTime now = LocalDateTime.now();
        ConcreteFlywayExecution execution = ConcreteFlywayExecution.builder()
            .id(1L)
            .migrationVersion("1.0.0")
            .description("Create users table")
            .type("SQL")
            .script("V1.0.0__Create_users_table.sql")
            .checksum(123456)
            .installedBy("admin")
            .installedOn(now)
            .executionTime(1000)
            .success(true)
            .build();

        assertThat(execution.getId()).isEqualTo(1L);
        assertThat(execution.getMigrationVersion()).isEqualTo("1.0.0");
        assertThat(execution.getDescription()).isEqualTo("Create users table");
        assertThat(execution.getType()).isEqualTo("SQL");
        assertThat(execution.getScript()).isEqualTo("V1.0.0__Create_users_table.sql");
        assertThat(execution.getChecksum()).isEqualTo(123456);
        assertThat(execution.getInstalledBy()).isEqualTo("admin");
        assertThat(execution.getInstalledOn()).isEqualTo(now);
        assertThat(execution.getExecutionTime()).isEqualTo(1000);
        assertThat(execution.getSuccess()).isTrue();
    }

    @Test
    @DisplayName("Deve verificar equals e hashCode")
    void deveVerificarEqualsEHashCode() {
        ConcreteFlywayExecution execution1 = ConcreteFlywayExecution.builder()
            .id(1L)
            .migrationVersion("1.0.0")
            .build();

        ConcreteFlywayExecution execution2 = ConcreteFlywayExecution.builder()
            .id(1L)
            .migrationVersion("1.0.0")
            .build();

        assertThat(execution1).usingRecursiveComparison().isEqualTo(execution2);
        assertThat(execution1).isSameAs(execution1);
    }

    @Test
    @DisplayName("Deve verificar toBuilder")
    void deveVerificarToBuilder() {
        ConcreteFlywayExecution execution = ConcreteFlywayExecution.builder()
            .id(1L)
            .migrationVersion("1.0.0")
            .description("Create users table")
            .build();

        ConcreteFlywayExecution modified = execution.toBuilder()
            .description("Modified description")
            .build();

        assertThat(modified.getId()).isEqualTo(execution.getId());
        assertThat(modified.getMigrationVersion()).isEqualTo(execution.getMigrationVersion());
        assertThat(modified.getDescription()).isEqualTo("Modified description");
    }

    @Test
    @DisplayName("Deve verificar toString")
    void deveVerificarToString() {
        ConcreteFlywayExecution execution = ConcreteFlywayExecution.builder()
            .id(1L)
            .migrationVersion("1.0.0")
            .description("Create users table")
            .build();

        String result = execution.toString();

        assertThat(result).isNotNull();
        assertThat(result).contains("FlywayExecution");
    }

    @lombok.experimental.SuperBuilder(toBuilder = true)
    static class ConcreteFlywayExecution extends FlywayExecution {
    }
}
