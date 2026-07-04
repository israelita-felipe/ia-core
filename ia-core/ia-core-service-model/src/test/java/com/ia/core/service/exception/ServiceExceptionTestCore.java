package com.ia.core.service.exception;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ServiceException")
class ServiceExceptionTestCore extends CoreBaseUnitTest {

    @Nested
    @DisplayName("construtor com mensagem")
    class ConstrutorComMensagem {

        @Test
        @DisplayName("CT001 - Deve criar exceção com mensagem")
        void deveCriarComMensagem() {
            // Arrange
            String mensagem = "Erro de serviço";

            // Act
            ServiceException exception = new ServiceException(mensagem);

            // Assert
            assertThat(exception.getMessage()).isEqualTo(mensagem);
            assertThat(exception.hasErros()).isTrue();
        }
    }

    @Nested
    @DisplayName("adicionar erro string")
    class AdicionarErroString {

        @Test
        @DisplayName("CT002 - Deve adicionar erro como string")
        void deveAdicionarErroString() {
            // Arrange
            ServiceException exception = new ServiceException();
            String erroEspecifico = "Erro específico";

            // Act
            exception.add(erroEspecifico);

            // Assert
            assertThat(exception.hasErros()).isTrue();
            assertThat(exception.getErrors()).hasSize(1);
            assertThat(exception.getErrors().findFirst().orElse(null)).isEqualTo(erroEspecifico);
        }

        @Test
        @DisplayName("CT003 - Deve adicionar múltiplos erros string")
        void deveAdicionarMultiplosErrosString() {
            // Arrange
            ServiceException exception = new ServiceException();

            // Act
            exception.add("Erro 1");
            exception.add("Erro 2");

            // Assert
            assertThat(exception.hasErros()).isTrue();
            assertThat(exception.getErrors()).hasSize(2);
        }
    }

    @Nested
    @DisplayName("adicionar erro exception")
    class AdicionarErroException {

        @Test
        @DisplayName("CT004 - Deve adicionar erro como Exception")
        void deveAdicionarErroException() {
            // Arrange
            ServiceException exception = new ServiceException();
            Exception nested = new RuntimeException("Erro interno");

            // Act
            exception.add(nested);

            // Assert
            assertThat(exception.hasErros()).isTrue();
            assertThat(exception.getErrors()).hasSize(1);
            assertThat(exception.getErrors().findFirst().orElse(null)).isEqualTo(nested.getMessage());
        }

        @Test
        @DisplayName("CT005 - Deve adicionar múltiplos erros exception")
        void deveAdicionarMultiplosErrosException() {
            // Arrange
            ServiceException exception = new ServiceException();

            // Act
            exception.add(new RuntimeException("Erro 1"));
            exception.add(new RuntimeException("Erro 2"));

            // Assert
            assertThat(exception.hasErros()).isTrue();
            assertThat(exception.getErrors()).hasSize(2);
        }
    }

    @Nested
    @DisplayName("hasErros")
    class HasErros {

        @Test
        @DisplayName("CT006 - Deve retornar false sem erros")
        void deveRetornarFalseSemErros() {
            // Arrange
            ServiceException exception = new ServiceException();

            // Act & Assert
            assertThat(exception.hasErros()).isFalse();
        }

        @Test
        @DisplayName("CT007 - Deve retornar true com erros")
        void deveRetornarTrueComErros() {
            // Arrange
            ServiceException exception = new ServiceException();

            // Act
            exception.add("Erro específico");

            // Assert
            assertThat(exception.hasErros()).isTrue();
        }
    }

    @Nested
    @DisplayName("getErros")
    class GetErros {

        @Test
        @DisplayName("CT008 - Deve retornar lista vazia sem erros")
        void deveRetornarListaVaziaSemErros() {
            // Arrange
            ServiceException exception = new ServiceException();

            // Act & Assert
            assertThat(exception.getErrors()).isEmpty();
        }

        @Test
        @DisplayName("CT009 - Deve retornar lista com erros")
        void deveRetornarListaComErros() {
            // Arrange
            ServiceException exception = new ServiceException();

            // Act
            exception.add("Erro 1");
            exception.add("Erro 2");

            // Assert
            assertThat(exception.getErrors()).hasSize(2);
        }
    }
}
