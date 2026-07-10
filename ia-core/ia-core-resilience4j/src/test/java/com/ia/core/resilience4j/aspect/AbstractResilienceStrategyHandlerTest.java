package com.ia.core.resilience4j.aspect;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para os métodos utilitários de AbstractResilienceStrategyHandler.
 *
 * <p>Valida que:</p>
 * <ul>
 *   <li>getRootCause() identifica corretamente a exceção raiz</li>
 *   <li>isCausedBy() detecta exceções específicas na cadeia de causas</li>
 *   <li>As exceções são reenviadas sem acumulação de mensagens</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AbstractResilienceStrategyHandler Utility Tests")
class AbstractResilienceStrategyHandlerTest {

    private final TestHandler handler = new TestHandler();

    /**
     * Handler de teste para acessar métodos protegidos.
     */
    private static class TestHandler extends AbstractResilienceStrategyHandler<Object> {
        @Override
        public Object resolve(com.ia.core.resilience4j.dto.ResilienceContext context) {
            return null;
        }

        @Override
        public Object execute(com.ia.core.resilience4j.dto.ResilienceContext context, 
                              java.util.function.Supplier<Object> next) {
            return null;
        }
    }

    @Test
    @DisplayName("getRootCause() retorna a exceção raiz de uma cadeia")
    void testGetRootCause() {
        // Arrange
        Throwable rootCause = new RuntimeException("Root cause");
        Throwable middleCause = new RuntimeException("Middle", rootCause);
        Throwable topCause = new RuntimeException("Top", middleCause);

        // Act
        Throwable result = handler.getRootCause(topCause);

        // Assert
        assertThat(result).isEqualTo(rootCause);
        assertThat(result.getMessage()).isEqualTo("Root cause");
    }

    @Test
    @DisplayName("getRootCause() retorna a própria exceção se não tiver causa")
    void testGetRootCauseWhenNoCause() {
        // Arrange
        RuntimeException exception = new RuntimeException("Single exception");

        // Act
        Throwable result = handler.getRootCause(exception);

        // Assert
        assertThat(result).isEqualTo(exception);
    }

    @Test
    @DisplayName("getRootCause() retorna null para exceção nula")
    void testGetRootCauseWhenNull() {
        // Act
        Throwable result = handler.getRootCause(null);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("isCausedBy() detecta exceção na causa direta")
    void testIsCausedByDirectCause() {
        // Arrange
        TimeoutException timeout = new TimeoutException("Timeout");
        RuntimeException wrapper = new RuntimeException("Wrapper", timeout);

        // Act
        boolean result = handler.isCausedBy(wrapper, TimeoutException.class);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("isCausedBy() detecta exceção em causa indireta")
    void testIsCausedByIndirectCause() {
        // Arrange
        TimeoutException timeout = new TimeoutException("Timeout");
        RuntimeException middle = new RuntimeException("Middle", timeout);
        RuntimeException top = new RuntimeException("Top", middle);

        // Act
        boolean result = handler.isCausedBy(top, TimeoutException.class);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("isCausedBy() retorna false se exceção não estiver na cadeia")
    void testIsCausedByNotPresent() {
        // Arrange
        IllegalArgumentException illegalArg = new IllegalArgumentException("Illegal arg");
        RuntimeException wrapper = new RuntimeException("Wrapper", illegalArg);

        // Act
        boolean result = handler.isCausedBy(wrapper, TimeoutException.class);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("isCausedBy() retorna false para exceção nula")
    void testIsCausedByNullException() {
        // Act
        boolean result = handler.isCausedBy(null, TimeoutException.class);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("isCausedBy() retorna false para tipo de exceção nulo")
    void testIsCausedByNullType() {
        // Arrange
        RuntimeException exception = new RuntimeException("Test");

        // Act
        boolean result = handler.isCausedBy(exception, null);

        // Assert
        assertThat(result).isFalse();
    }
}