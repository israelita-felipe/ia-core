package com.ia.core.service.dto;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DTO")
class DTOTestCore extends CoreBaseUnitTest {

    @Nested
    @DisplayName("classe de teste concreta")
    class ClasseDeTesteConcreta {

        @Test
        @DisplayName("CT001 - Deve implementar interface DTO")
        void deveImplementarInterfaceDTO() {
            // Arrange & Act
            TestDTO dto = new TestDTO();

            // Assert
            assertThat(dto).isInstanceOf(DTO.class);
        }

        @Test
        @DisplayName("CT002 - Deve clonar objeto")
        void deveClonarObjeto() {
            // Arrange
            TestDTO dto = new TestDTO();
            dto.setNome("João");

            // Act
            TestDTO clone = dto.cloneObject();

            // Assert
            assertThat(clone).isNotNull();
            assertThat(clone.getNome()).isEqualTo(dto.getNome());
            assertThat(clone).isNotSameAs(dto);
        }

        @Test
        @DisplayName("CT003 - Deve copiar objeto")
        void deveCopiarObjeto() {
            // Arrange
            TestDTO dto = new TestDTO();
            dto.setNome("João");

            // Act
            TestDTO copy = (TestDTO) dto.copyObject();

            // Assert
            assertThat(copy.getNome()).isEqualTo(dto.getNome());
        }
    }

    @Nested
    @DisplayName("seriabilidade")
    class Seriabilidade {

        @Test
        @DisplayName("CT004 - Deve ser serializável")
        void deveSerSerializavel() {
            // Arrange
            TestDTO dto = new TestDTO();

            // Act & Assert
            assertThat(dto).isInstanceOf(java.io.Serializable.class);
        }
    }

    static class TestDTO implements DTO {
        private String nome;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        @Override
        public TestDTO cloneObject() {
            TestDTO clone = new TestDTO();
            clone.setNome(this.nome);
            return clone;
        }
    }
}