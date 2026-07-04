package com.ia.core.service.dto.sort;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para SortDirectionDTO.
 */
@DisplayName("SortDirectionDTO Tests")
class SortDirectionDTOTest {

  @Nested
  @DisplayName("valores")
  class Valores {

    @Test
    @DisplayName("deve ter valores ASC e DESC")
    void testEnumValues() {
      assertThat(SortDirectionDTO.values()).containsExactly(SortDirectionDTO.ASC, SortDirectionDTO.DESC);
    }

    @Test
    @DisplayName("deve ter valor ASC")
    void testAscValue() {
      assertThat(SortDirectionDTO.ASC).isNotNull();
    }

    @Test
    @DisplayName("deve ter valor DESC")
    void testDescValue() {
      assertThat(SortDirectionDTO.DESC).isNotNull();
    }
  }

  @Nested
  @DisplayName("valueOf")
  class ValueOf {

    @Test
    @DisplayName("deve retornar ASC para string 'ASC'")
    void testValueOfAsc() {
      assertThat(SortDirectionDTO.valueOf("ASC")).isEqualTo(SortDirectionDTO.ASC);
    }

    @Test
    @DisplayName("deve retornar DESC para string 'DESC'")
    void testValueOfDesc() {
      assertThat(SortDirectionDTO.valueOf("DESC")).isEqualTo(SortDirectionDTO.DESC);
    }
  }

  @Nested
  @DisplayName("ordinal")
  class Ordinal {

    @Test
    @DisplayName("deve ter ordinal 0 para ASC")
    void testOrdinalAsc() {
      assertThat(SortDirectionDTO.ASC.ordinal()).isEqualTo(0);
    }

    @Test
    @DisplayName("deve ter ordinal 1 para DESC")
    void testOrdinalDesc() {
      assertThat(SortDirectionDTO.DESC.ordinal()).isEqualTo(1);
    }
  }

  @Nested
  @DisplayName("name")
  class Name {

    @Test
    @DisplayName("deve ter nome 'ASC' para ASC")
    void testNameAsc() {
      assertThat(SortDirectionDTO.ASC.name()).isEqualTo("ASC");
    }

    @Test
    @DisplayName("deve ter nome 'DESC' para DESC")
    void testNameDesc() {
      assertThat(SortDirectionDTO.DESC.name()).isEqualTo("DESC");
    }
  }
}
