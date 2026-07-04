package com.ia.core.service.dto.entity;

import com.ia.core.model.HasVersion;
import com.ia.core.service.dto.DTO;
import com.ia.test.CoreBaseUnitTest;
import org.instancio.Select;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BaseEntityDTO")
class CoreBaseEntityDTOTest extends CoreBaseUnitTest {

    @Nested
    @DisplayName("interface")
    class Interface {

        @Test
        @DisplayName("CT001 - Deve estender DTO")
        void deveEstenderDTO() {
            // Arrange & Act
            TestEntityDTO dto = createFixture(TestEntityDTO.class);

            // Assert
            assertThat(dto).isInstanceOf(DTO.class);
        }

        @Test
        @DisplayName("CT002 - Deve estender HasVersion")
        void deveEstenderHasVersion() {
            // Arrange & Act
            TestEntityDTO dto = createFixture(TestEntityDTO.class);

            // Assert
            assertThat(dto).isInstanceOf(HasVersion.class);
        }
    }

    @Nested
    @DisplayName("identificação")
    class Identificacao {

        @Test
        @DisplayName("CT003 - Deve ter ID")
        void deveTerId() {
            // Arrange
            Long id = 1L;

            // Act
            TestEntityDTO dto = createFixture(TestEntityDTO.class,
                Select.field(TestEntityDTO::getId), id);

            // Assert
            assertThat(dto.getId()).isEqualTo(id);
        }

        @Test
        @DisplayName("CT004 - Deve ter versão")
        void deveTerVersao() {
            // Arrange
            Long versao = 1L;

            // Act
            TestEntityDTO dto = createFixture(TestEntityDTO.class,
                Select.field(TestEntityDTO::getVersion), versao);

            // Assert
            assertThat(dto.getVersion()).isEqualTo(versao);
        }
    }

    @Nested
    @DisplayName("seriabilidade")
    class Seriabilidade {

        @Test
        @DisplayName("CT005 - Deve ser serializável")
        void deveSerSerializavel() {
            // Arrange
            TestEntityDTO dto = createFixture(TestEntityDTO.class);

            // Act & Assert
            assertThat(dto).isInstanceOf(java.io.Serializable.class);
        }
    }

    static class TestEntityDTO implements BaseEntityDTO {
        private Long id;
        private Long version;

        @Override
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        @Override
        public Long getVersion() {
            return version;
        }

        @Override
        public void setVersion(Object version) {
            this.version = (Long) version;
        }

        @Override
        public TestEntityDTO cloneObject() {
            TestEntityDTO clone = new TestEntityDTO();
            clone.setId(this.id);
            clone.setVersion(this.version);
            return clone;
        }

    }
}
