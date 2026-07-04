package com.ia.core.flyway.service.model.flywayexecution.dto;

import com.ia.core.service.dto.CoreBaseDTOUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe FlywayExecutionDTO.
 */
@DisplayName("Testes de FlywayExecutionDTO")
class FlywayExecutionDTOTestCore extends CoreBaseDTOUnitTest<FlywayExecutionDTO> {

  @Override
  protected Class<?> getDtoInterface() {
    return com.ia.core.service.dto.DTO.class;
  }

  @Test
  @DisplayName("Deve criar DTO com builder")
  void deveCriarDTOComBuilder() {
    FlywayExecutionDTO dto = FlywayExecutionDTO.builder()
        .id(1L)
        .version(1L)
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

    assertThat(dto).isNotNull();
    assertThat(dto.getId()).isEqualTo(1L);
    assertThat(dto.getMigrationVersion()).isEqualTo("1.0.0");
  }

  @Test
  @DisplayName("Deve clonar DTO usando cloneObject")
  void deveClonarDTOUsandoCloneObject() {
    FlywayExecutionDTO dto = FlywayExecutionDTO.builder()
        .id(1L)
        .migrationVersion("1.0.0")
        .description("Create users table")
        .build();

    FlywayExecutionDTO clone = dto.cloneObject();

    assertThat(clone).isNotNull();
    assertThat(clone.getId()).isEqualTo(dto.getId());
    assertThat(clone.getMigrationVersion()).isEqualTo(dto.getMigrationVersion());
    assertThat(clone.getDescription()).isEqualTo(dto.getDescription());
  }

  @Test
  @DisplayName("Deve clonar DTO usando toBuilder")
  void deveClonarDTOUsandoToBuilder() {
    FlywayExecutionDTO dto = FlywayExecutionDTO.builder()
        .id(1L)
        .migrationVersion("1.0.0")
        .description("Create users table")
        .build();

    FlywayExecutionDTO modified = dto.toBuilder()
        .description("Modified description")
        .build();

    assertThat(modified.getId()).isEqualTo(dto.getId());
    assertThat(modified.getMigrationVersion()).isEqualTo(dto.getMigrationVersion());
    assertThat(modified.getDescription()).isEqualTo("Modified description");
  }
}
