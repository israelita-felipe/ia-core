package com.ia.core.model.exception;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Testes para ResourceNotFoundException baseados nos casos de teste documentados.
 */
class ResourceNotFoundExceptionTestCore extends CoreBaseUnitTest {

    @Test
    void deveCriarExcecaoComTipoEIdDoRecurso() {
        // Arrange
        String tipoRecurso = "Usuario";
        String idRecurso = "123";

        // Act
        ResourceNotFoundException exception = new ResourceNotFoundException(tipoRecurso, idRecurso);

        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getErrorCode()).isEqualTo("RESOURCE_NOT_FOUND");
        assertThat(exception.getMessage()).contains("Recurso não encontrado");
        assertThat(exception.getMessage()).contains(tipoRecurso);
        assertThat(exception.getMessage()).contains(idRecurso);
        assertThat(exception.getResourceType()).isEqualTo(tipoRecurso);
        assertThat(exception.getResourceId()).isEqualTo(idRecurso);
    }

    @Test
    void deveCriarExcecaoComMensagemPersonalizada() {
        // Arrange
        String mensagem = "Recurso específico não encontrado";

        // Act
        ResourceNotFoundException exception = new ResourceNotFoundException(mensagem);

        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getErrorCode()).isEqualTo("RESOURCE_NOT_FOUND");
        assertThat(exception.getMessage()).isEqualTo(mensagem);
        assertThat(exception.getResourceType()).isNull();
        assertThat(exception.getResourceId()).isNull();
    }

    @Test
    void deveRetornarTipoEIdDoRecurso() {
        // Arrange
        String tipoRecurso = "Pedido";
        String idRecurso = "456";
        ResourceNotFoundException exception = new ResourceNotFoundException(tipoRecurso, idRecurso);

        // Act
        String tipoRetornado = exception.getResourceType();
        String idRetornado = exception.getResourceId();

        // Assert
        assertThat(tipoRetornado).isEqualTo(tipoRecurso);
        assertThat(idRetornado).isEqualTo(idRecurso);
    }

    @Test
    void deveLancarExcecaoComTipoEId() {
        // Arrange
        String tipoRecurso = "Pedido";
        String idRecurso = "456";

        // Act & Assert
        assertThatThrownBy(() -> {
            throw new ResourceNotFoundException(tipoRecurso, idRecurso);
        }).isInstanceOf(ResourceNotFoundException.class)
          .hasMessageContaining("Recurso não encontrado")
          .hasMessageContaining(tipoRecurso)
          .hasMessageContaining(idRecurso)
          .extracting("errorCode")
          .isEqualTo("RESOURCE_NOT_FOUND");
    }
}
