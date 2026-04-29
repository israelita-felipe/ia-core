package com.ia.core.service.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para {@link JakartaValidator}.
 *
 * @author Israel Araújo
 */
/**
 * Classe que representa os serviços de negócio para jakarta validator test.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a JakartaValidatorTest
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@DisplayName("JakartaValidator")
class JakartaValidatorTest {

    @Nested
    @DisplayName("get")
    class TestesGet {

        @Test
        @DisplayName("Deve retornar instância singleton")
        void deveRetornarInstanciaSingleton() {
            // Quando
            JakartaValidator<Serializable> result = JakartaValidator.get(null);

            // Então
            assertThat(result).isNotNull();
        }

        @Test
        @DisplayName("Deve retornar mesma instância em múltiplas chamadas")
        void deveRetornarMesmaInstanciaEmMultiplasChamadas() {
            // Quando
            JakartaValidator<Serializable> result1 = JakartaValidator.get(null);
            JakartaValidator<Serializable> result2 = JakartaValidator.get(null);

            // Então
            assertThat(result1).isSameAs(result2);
        }
    }

    @Nested
    @DisplayName("getValidator")
    class TestesGetValidator {

        @Test
        @DisplayName("Deve retornar validador")
        void deveRetornarValidador() {
            // Dado
            JakartaValidator<TestDTO> validator = JakartaValidator.get(null);

            // Quando
            jakarta.validation.Validator result = validator.getValidator();

            // Então
            assertThat(result).isNotNull();
        }
    }

    // DTO de teste
    static class TestDTO implements Serializable {
        private static final long serialVersionUID = 1L;
    }
}
