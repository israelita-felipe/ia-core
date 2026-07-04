package com.ia.core.model.exception;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Testes para BusinessException baseados nos casos de teste documentados.
 */
class BusinessExceptionTestCore extends CoreBaseUnitTest {

    @Test
    void deveCriarExcecaoComMensagem() {
        // Arrange
        String mensagem = "Violação de regra de negócio";

        // Act
        BusinessException exception = new BusinessException(mensagem);

        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getErrorCode()).isEqualTo("BUSINESS_RULE_VIOLATION");
        assertThat(exception.getMessage()).isEqualTo(mensagem);
    }

    @Test
    void deveCriarExcecaoComCodigoEMensagem() {
        // Arrange
        String codigo = "PEDIDO_CANCELADO";
        String mensagem = "Não é possível cancelar pedido já enviado";

        // Act
        BusinessException exception = new BusinessException(codigo, mensagem);

        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getErrorCode()).isEqualTo(codigo);
        assertThat(exception.getMessage()).isEqualTo(mensagem);
    }

    @Test
    void deveCriarExcecaoComCodigoMensagemECausa() {
        // Arrange
        String codigo = "PEDIDO_CANCELADO";
        String mensagem = "Não é possível cancelar pedido já enviado";
        Throwable cause = new RuntimeException("Causa original");

        // Act
        BusinessException exception = new BusinessException(codigo, mensagem, cause);

        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getErrorCode()).isEqualTo(codigo);
        assertThat(exception.getMessage()).isEqualTo(mensagem);
        assertThat(exception.getCause()).isEqualTo(cause);
    }

    @Test
    void deveLancarExcecaoComMensagem() {
        // Arrange
        String mensagem = "Violação de regra de negócio";

        // Act & Assert
        assertThatThrownBy(() -> {
            throw new BusinessException(mensagem);
        }).isInstanceOf(BusinessException.class)
          .hasMessage(mensagem)
          .extracting("errorCode")
          .isEqualTo("BUSINESS_RULE_VIOLATION");
    }
}
