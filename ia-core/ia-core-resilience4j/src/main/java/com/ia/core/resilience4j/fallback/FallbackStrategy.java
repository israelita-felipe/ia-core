package com.ia.core.resilience4j.fallback;

import java.lang.reflect.Method;

/**
 * Interface funcional para estratégias de fallback.
 *
 * <p>Define o contrato para manipulação de fallbacks quando uma operação
 * resiliente falha. A implementação decide como responder à falha,
 * podendo retornar valores padrão, mensagens de erro ou lançar novas exceções.</p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see FallbackResponse
 */
@FunctionalInterface
public interface FallbackStrategy {
    /**
     * Processa o fallback para a operação que falhou.
     *
     * @param method o método que falhou (não pode ser {@code null})
     * @param args os argumentos passados ao método (não pode ser {@code null})
     * @param exception a exceção que causou o fallback (não pode ser {@code null})
     * @return a resposta do fallback, nunca {@code null}
     */
    FallbackResponse handle(Method method, Object[] args, Throwable exception);
}
