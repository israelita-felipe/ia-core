package com.ia.core.model.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DomainException hierarchy")
class DomainExceptionTest {

    @Nested
    @DisplayName("BusinessException")
    class BusinessExceptionTests {

        @Test
        @DisplayName("Deve criar com mensagem e código padrão")
        void deveCriarComMensagemECodigoPadrao() {
            BusinessException ex = new BusinessException("Operação não permitida");
            assertThat(ex.getMessage()).isEqualTo("Operação não permitida");
            assertThat(ex.getErrorCode()).isEqualTo("BUSINESS_RULE_VIOLATION");
        }

        @Test
        @DisplayName("Deve criar com código de erro personalizado")
        void deveCriarComCodigoPersonalizado() {
            BusinessException ex = new BusinessException("CUSTOM_CODE", "Erro customizado");
            assertThat(ex.getErrorCode()).isEqualTo("CUSTOM_CODE");
            assertThat(ex.getMessage()).isEqualTo("Erro customizado");
        }

        @Test
        @DisplayName("Deve criar com código, mensagem e causa")
        void deveCriarComCausa() {
            RuntimeException cause = new RuntimeException("root cause");
            BusinessException ex = new BusinessException("CODE", "message", cause);
            assertThat(ex.getCause()).isSameAs(cause);
            assertThat(ex.getErrorCode()).isEqualTo("CODE");
        }

        @Test
        @DisplayName("Deve ser uma RuntimeException")
        void deveSerRuntimeException() {
            BusinessException ex = new BusinessException("com/ia/test");
            assertThat(ex).isInstanceOf(RuntimeException.class);
            assertThat(ex).isInstanceOf(DomainException.class);
        }
    }

    @Nested
    @DisplayName("ValidationException")
    class ValidationExceptionTests {

        @Test
        @DisplayName("Deve criar com mensagem simples")
        void deveCriarComMensagemSimples() {
            ValidationException ex = new ValidationException("Erro de validação");
            assertThat(ex.getMessage()).isEqualTo("Erro de validação");
            assertThat(ex.getErrorCode()).isEqualTo("VALIDATION_ERROR");
            assertThat(ex.getField()).isNull();
            assertThat(ex.getRejectedValue()).isNull();
        }

        @Test
        @DisplayName("Deve criar com campo, valor rejeitado e mensagem")
        void deveCriarComCampoEValorRejeitado() {
            ValidationException ex = new ValidationException("email", "invalid@", "Email inválido");
            assertThat(ex.getField()).isEqualTo("email");
            assertThat(ex.getRejectedValue()).isEqualTo("invalid@");
            assertThat(ex.getMessage()).isEqualTo("Email inválido");
        }
    }

    @Nested
    @DisplayName("ResourceNotFoundException")
    class ResourceNotFoundExceptionTests {

        @Test
        @DisplayName("Deve criar com tipo e ID do recurso")
        void deveCriarComTipoEId() {
            ResourceNotFoundException ex = new ResourceNotFoundException("Usuario", "123");
            assertThat(ex.getMessage()).contains("Usuario").contains("123");
            assertThat(ex.getResourceType()).isEqualTo("Usuario");
            assertThat(ex.getResourceId()).isEqualTo("123");
            assertThat(ex.getErrorCode()).isEqualTo("RESOURCE_NOT_FOUND");
        }

        @Test
        @DisplayName("Deve criar com mensagem personalizada")
        void deveCriarComMensagemPersonalizada() {
            ResourceNotFoundException ex = new ResourceNotFoundException("Recurso não existe");
            assertThat(ex.getMessage()).isEqualTo("Recurso não existe");
            assertThat(ex.getResourceType()).isNull();
            assertThat(ex.getResourceId()).isNull();
        }
    }

    @Nested
    @DisplayName("DomainException.determineErrorCode")
    class DetermineErrorCode {

        @Test
        @DisplayName("Deve gerar código de erro automaticamente para BusinessException")
        void deveGerarCodigoAutomaticamente() {
            DomainException ex = new BusinessException("com/ia/test") {};
            assertThat(ex.getErrorCode()).isNotNull();
        }
    }
}
