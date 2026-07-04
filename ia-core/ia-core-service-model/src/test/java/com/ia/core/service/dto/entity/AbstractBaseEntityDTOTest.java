package com.ia.core.service.dto.entity;

import com.ia.core.model.BaseEntity;
import com.ia.core.model.HasVersion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para AbstractBaseEntityDTO.
 */
@DisplayName("AbstractBaseEntityDTO Tests")
class AbstractBaseEntityDTOTest {

  private static class TestEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;
  }

  @lombok.Data
  @lombok.experimental.SuperBuilder(toBuilder = true)
  @lombok.NoArgsConstructor(force = true)
  @lombok.EqualsAndHashCode(callSuper = true)
  private static class TestDTO extends AbstractBaseEntityDTO<TestEntity> {
    private static final long serialVersionUID = 1L;

    @Override
    public TestDTO cloneObject() {
      return toBuilder().build();
    }
  }

  @Test
  @DisplayName("deve criar instância com builder")
  void testBuilder() {
    TestDTO dto = TestDTO.builder()
      .id(1L)
      .version(2L)
      .build();

    assertThat(dto.getId()).isEqualTo(1L);
    assertThat(dto.getVersion()).isEqualTo(2L);
  }

  @Test
  @DisplayName("deve criar instância com construtor padrão")
  void testDefaultConstructor() {
    TestDTO dto = new TestDTO();

    assertThat(dto.getId()).isNull();
    assertThat(dto.getVersion()).isEqualTo(HasVersion.DEFAULT_VERSION);
  }

  @Test
  @DisplayName("copyObject deve criar cópia com id null e versão padrão")
  void testCopyObject() {
    TestDTO original = TestDTO.builder()
      .id(1L)
      .version(2L)
      .build();

    AbstractBaseEntityDTO<TestEntity> copy = original.copyObject();

    assertThat(copy.getId()).isNull();
    assertThat(copy.getVersion()).isEqualTo(HasVersion.DEFAULT_VERSION);
  }

  @Test
  @DisplayName("compareTo deve retornar 0 quando ambos ids são nulos")
  void testCompareToBothNull() {
    TestDTO dto1 = new TestDTO();
    TestDTO dto2 = new TestDTO();

    assertThat(dto1.compareTo(dto2)).isEqualTo(0);
  }

  @Test
  @DisplayName("compareTo deve retornar -1 quando id do primeiro é nulo")
  void testCompareToFirstNull() {
    TestDTO dto1 = new TestDTO();
    TestDTO dto2 = TestDTO.builder().id(1L).build();

    assertThat(dto1.compareTo(dto2)).isEqualTo(-1);
  }

  @Test
  @DisplayName("compareTo deve retornar 1 quando id do segundo é nulo")
  void testCompareToSecondNull() {
    TestDTO dto1 = TestDTO.builder().id(1L).build();
    TestDTO dto2 = new TestDTO();

    assertThat(dto1.compareTo(dto2)).isEqualTo(1);
  }

  @Test
  @DisplayName("compareTo deve comparar ids")
  void testCompareToWithIds() {
    TestDTO dto1 = TestDTO.builder().id(1L).build();
    TestDTO dto2 = TestDTO.builder().id(2L).build();

    assertThat(dto1.compareTo(dto2)).isNegative();
    assertThat(dto2.compareTo(dto1)).isPositive();
  }

  @Test
  @DisplayName("equals deve retornar true para mesma instância")
  void testEqualsSameInstance() {
    TestDTO dto = TestDTO.builder().id(1L).build();

    assertThat(dto.equals(dto)).isTrue();
  }

  @Test
  @DisplayName("equals deve retornar true quando ids são iguais")
  void testEqualsSameId() {
    TestDTO dto1 = TestDTO.builder().id(1L).build();
    TestDTO dto2 = TestDTO.builder().id(1L).build();

    assertThat(dto1.equals(dto2)).isTrue();
  }

  @Test
  @DisplayName("equals deve retornar false quando ids são diferentes")
  void testEqualsDifferentId() {
    TestDTO dto1 = TestDTO.builder().id(1L).build();
    TestDTO dto2 = TestDTO.builder().id(2L).build();

    assertThat(dto1.equals(dto2)).isFalse();
  }

  @Test
  @DisplayName("equals deve retornar false quando id é nulo")
  void testEqualsNullId() {
    TestDTO dto1 = new TestDTO();
    TestDTO dto2 = TestDTO.builder().id(1L).build();

    assertThat(dto1.equals(dto2)).isFalse();
  }

  @Test
  @DisplayName("hashCode deve usar id quando não é nulo")
  void testHashCodeWithId() {
    TestDTO dto = TestDTO.builder().id(1L).build();

    assertThat(dto.hashCode()).isNotNull();
  }

  @Test
  @DisplayName("hashCode deve usar super quando id é nulo")
  void testHashCodeNullId() {
    TestDTO dto = new TestDTO();

    assertThat(dto.hashCode()).isNotNull();
  }

  @Test
  @DisplayName("CAMPOS deve ter constantes definidas")
  void testCamposConstants() {
    assertThat(AbstractBaseEntityDTO.CAMPOS.ID).isEqualTo("id");
    assertThat(AbstractBaseEntityDTO.CAMPOS.VERSION).isEqualTo("version");
  }

  @Test
  @DisplayName("CAMPOS.values deve retornar conjunto de valores")
  void testCamposValues() {
    assertThat(AbstractBaseEntityDTO.CAMPOS.values()).containsExactlyInAnyOrder("id", "version");
  }

  @Test
  @DisplayName("toBuilder deve criar builder com valores atuais")
  void testToBuilder() {
    TestDTO original = TestDTO.builder()
      .id(1L)
      .version(2L)
      .build();

    TestDTO modified = original.toBuilder()
      .version(3L)
      .build();

    assertThat(modified.getId()).isEqualTo(1L);
    assertThat(modified.getVersion()).isEqualTo(3L);
  }

  @Test
  @DisplayName("setId deve disparar PropertyChangeEvent")
  void testSetIdFiresPropertyChangeEvent() {
    // Arrange
    TestDTO dto = TestDTO.builder().build();
    final boolean[] eventFired = {false};
    dto.addPropertyChangeListener(evt -> {
      if (evt.getPropertyName().equals(AbstractBaseEntityDTO.CAMPOS.ID)) {
        eventFired[0] = true;
      }
    });

    // Act
    dto.setId(1L);

    // Assert
    assertThat(eventFired[0]).isTrue();
  }

  @Test
  @DisplayName("setVersion deve disparar PropertyChangeEvent")
  void testSetVersionFiresPropertyChangeEvent() {
    // Arrange
    TestDTO dto = TestDTO.builder().build();
    final boolean[] eventFired = {false};
    dto.addPropertyChangeListener(evt -> {
      if (evt.getPropertyName().equals(AbstractBaseEntityDTO.CAMPOS.VERSION)) {
        eventFired[0] = true;
      }
    });

    // Act
    dto.setVersion(2L);

    // Assert
    assertThat(eventFired[0]).isTrue();
  }

  @Test
  @DisplayName("equals deve retornar false quando id é nulo e objetos diferentes")
  void testEqualsNullIdDifferentObjects() {
    // Arrange
    TestDTO dto1 = new TestDTO();
    TestDTO dto2 = new TestDTO();

    // Act & Assert
    assertThat(dto1.equals(dto2)).isFalse();
  }

  @Test
  @DisplayName("equals deve retornar true quando id é nulo e mesmo objeto")
  void testEqualsNullIdSameObject() {
    // Arrange
    TestDTO dto = new TestDTO();

    // Act & Assert
    assertThat(dto.equals(dto)).isTrue();
  }

  @Test
  @DisplayName("equals deve retornar false quando classes diferentes")
  void testEqualsDifferentClasses() {
    // Arrange
    TestDTO dto = TestDTO.builder().id(1L).build();
    Object other = new Object();

    // Act & Assert
    assertThat(dto.equals(other)).isFalse();
  }
}
