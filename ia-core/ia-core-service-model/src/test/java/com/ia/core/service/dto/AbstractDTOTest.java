package com.ia.core.service.dto;

import com.ia.core.service.dto.properties.HasPropertyChangeSupport;
import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("AbstractDTO")
class AbstractDTOTest extends CoreBaseUnitTest {

    @Nested
    @DisplayName("PropertyChangeSupport")
    class PropertyChangeSupport {

        @Test
        @DisplayName("CT001 - Deve suportar PropertyChangeSupport")
        void deveSuportarPropertyChangeSupport() {
            // Arrange
            TestDTO dto = TestDTO.builder().build();
            PropertyChangeListener listener = new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    // Listener vazio
                }
            };

            // Act
            dto.addPropertyChangeListener(listener);

            // Assert
            assertThat(dto).isInstanceOf(HasPropertyChangeSupport.class);
        }

        @Test
        @DisplayName("CT002 - Deve notificar mudança de propriedade")
        void deveNotificarMudancaDePropriedade() {
            // Arrange
            TestDTO dto = TestDTO.builder().build();
            final boolean[] notified = {false};
            PropertyChangeListener listener = evt -> notified[0] = true;
            dto.addPropertyChangeListener(listener);

            // Act
            dto.setNome("Novo Nome");

            // Assert
            assertThat(notified[0]).isTrue();
        }
    }

    @Nested
    @DisplayName("clonagem")
    class Clonagem {

        @Test
        @DisplayName("CT003 - Deve clonar objeto via builder")
        void deveClonarObjetoViaBuilder() {
            // Arrange
            TestDTO dto = TestDTO.builder().nome("João").build();

            // Act
            TestDTO clone = dto.toBuilder().build();

            // Assert
            assertThat(clone).isNotNull();
            assertThat(clone.getNome()).isEqualTo(dto.getNome());
            assertThat(clone).isNotSameAs(dto);
        }
    }

    @Nested
    @DisplayName("seriabilidade")
    class Seriabilidade {

        @Test
        @DisplayName("CT004 - Deve ser serializável")
        void deveSerSerializavel() {
            // Arrange
            TestDTO dto = TestDTO.builder().build();

            // Act & Assert
            assertThat(dto).isInstanceOf(java.io.Serializable.class);
        }
    }

    @Nested
    @DisplayName("implementação DTO")
    class ImplementacaoDTO {

        @Test
        @DisplayName("CT005 - Deve implementar interface DTO")
        void deveImplementarInterfaceDTO() {
            // Arrange
            TestDTO dto = TestDTO.builder().build();

            // Act & Assert
            assertThat(dto).isInstanceOf(DTO.class);
        }
    }

    @lombok.experimental.SuperBuilder(toBuilder = true)
    @lombok.NoArgsConstructor
    static class TestDTO extends AbstractDTO<java.io.Serializable> {
        private String nome;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            String oldValue = this.nome;
            this.nome = nome;
            firePropertyChange("nome", oldValue, nome);
        }

        @Override
        public TestDTO cloneObject() {
            return toBuilder().build();
        }
    }
}
