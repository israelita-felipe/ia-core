package com.ia.core.service.dto.sort;

import com.ia.core.service.dto.DTO;
import com.ia.test.dto.CoreDTOUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para SortRequestDTO.
 */
@DisplayName("SortRequestDTO Tests")
class SortRequestDTOTest extends CoreDTOUnitTest<SortRequestDTO> {

    @Override
    protected Class<?> getDtoInterface() {
        return DTO.class;
    }

    @Nested
  @DisplayName("construtor builder")
  class Builder {

    @Test
    @DisplayName("deve criar instância com builder")
    void testBuilder() {
      SortRequestDTO sortRequestDTO = SortRequestDTO.builder()
        .key("name")
        .direction(SortDirectionDTO.ASC)
        .build();

      assertThat(sortRequestDTO.getKey()).isEqualTo("name");
      assertThat(sortRequestDTO.getDirection()).isEqualTo(SortDirectionDTO.ASC);
    }

    @Test
    @DisplayName("deve criar instância com builder sem direção")
    void testBuilderWithoutDirection() {
      SortRequestDTO sortRequestDTO = SortRequestDTO.builder()
        .key("name")
        .build();

      assertThat(sortRequestDTO.getKey()).isEqualTo("name");
      assertThat(sortRequestDTO.getDirection()).isEqualTo(SortDirectionDTO.ASC);
    }

    @Test
    @DisplayName("deve criar instância com builder com direção DESC")
    void testBuilderWithDescDirection() {
      SortRequestDTO sortRequestDTO = SortRequestDTO.builder()
        .key("name")
        .direction(SortDirectionDTO.DESC)
        .build();

      assertThat(sortRequestDTO.getKey()).isEqualTo("name");
      assertThat(sortRequestDTO.getDirection()).isEqualTo(SortDirectionDTO.DESC);
    }
  }

  @Nested
  @DisplayName("construtor padrão")
  class DefaultConstructor {

    @Test
    @DisplayName("deve criar instância com construtor padrão")
    void testDefaultConstructor() {
      SortRequestDTO sortRequestDTO = new SortRequestDTO();

      assertThat(sortRequestDTO.getDirection()).isEqualTo(SortDirectionDTO.ASC);
    }
  }

  @Nested
  @DisplayName("construtor completo")
  class AllArgsConstructor {

    @Test
    @DisplayName("deve criar instância com construtor completo")
    void testAllArgsConstructor() {
      SortRequestDTO sortRequestDTO = new SortRequestDTO("name", SortDirectionDTO.DESC);

      assertThat(sortRequestDTO.getKey()).isEqualTo("name");
      assertThat(sortRequestDTO.getDirection()).isEqualTo(SortDirectionDTO.DESC);
    }

    @Test
    @DisplayName("deve criar instância com construtor completo e direção nula")
    void testAllArgsConstructorWithNullDirection() {
      SortRequestDTO sortRequestDTO = new SortRequestDTO("name", null);

      assertThat(sortRequestDTO.getKey()).isEqualTo("name");
      assertThat(sortRequestDTO.getDirection()).isNull();
    }
  }

  @Nested
  @DisplayName("cloneObject")
  class CloneObject {

    @Test
    @DisplayName("deve clonar objeto")
    void testCloneObject() {
      SortRequestDTO original = SortRequestDTO.builder()
        .key("name")
        .direction(SortDirectionDTO.ASC)
        .build();

      SortRequestDTO cloned = original.cloneObject();

      assertThat(cloned.getKey()).isEqualTo(original.getKey());
      assertThat(cloned.getDirection()).isEqualTo(original.getDirection());
    }

    @Test
    @DisplayName("deve criar instância diferente ao clonar")
    void testCloneObjectCreatesDifferentInstance() {
      SortRequestDTO original = SortRequestDTO.builder()
        .key("name")
        .direction(SortDirectionDTO.ASC)
        .build();

      SortRequestDTO cloned = original.cloneObject();

      assertThat(cloned).isNotSameAs(original);
    }
  }

  @Nested
  @DisplayName("CAMPOS")
  class Campos {

    @Test
    @DisplayName("CAMPOS deve ter constantes definidas")
    void testCamposConstants() {
      assertThat(SortRequestDTO.CAMPOS.PROPRIEDADE).isEqualTo("key");
      assertThat(SortRequestDTO.CAMPOS.DIRECAO).isEqualTo("direction");
    }
  }

  @Nested
  @DisplayName("toBuilder")
  class ToBuilder {

    @Test
    @DisplayName("toBuilder deve criar builder com valores atuais")
    void testToBuilder() {
      // Arrange
      SortRequestDTO original = SortRequestDTO.builder()
        .key("nome")
        .direction(SortDirectionDTO.ASC)
        .build();

      // Act
      SortRequestDTO modified = original.toBuilder()
        .direction(SortDirectionDTO.DESC)
        .build();

      // Assert
      assertThat(modified.getKey()).isEqualTo("nome");
      assertThat(modified.getDirection()).isEqualTo(SortDirectionDTO.DESC);
    }

    @Test
    @DisplayName("toBuilder deve modificar key")
    void testToBuilderModifyKey() {
      // Arrange
      SortRequestDTO original = SortRequestDTO.builder()
        .key("nome")
        .direction(SortDirectionDTO.ASC)
        .build();

      // Act
      SortRequestDTO modified = original.toBuilder()
        .key("sobrenome")
        .build();

      // Assert
      assertThat(modified.getKey()).isEqualTo("sobrenome");
      assertThat(modified.getDirection()).isEqualTo(SortDirectionDTO.ASC);
    }
  }
}
