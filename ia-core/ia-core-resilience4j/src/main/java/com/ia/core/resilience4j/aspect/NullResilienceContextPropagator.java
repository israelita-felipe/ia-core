package com.ia.core.resilience4j.aspect;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementação padrão de {@link ResilienceAspect.ResilienceAspectContext}.
 *
 * <p>Esta implementação básica não propaga nenhum contexto adicional,
 * apenas executa o supplier diretamente. Módulos que precisam
 * de propagação de contexto (SecurityContext, MDC) devem fornecer
 * suas próprias implementações.</p>
 *
 * <p>Para uso em Spring Security, por exemplo:</p>
 * <pre>{@code
 * @Component
 * public class SecurityContextPropagator implements ResilienceAspectContext {
 *     @Override
 *     public Object captureContext() {
 *         return SecurityContextHolder.getContext();
 *     }
 *
 *     @Override
 *     public <T> T executeWithContext(Object contextSnapshot, ContextSupplier<T> supplier) {
 *         try {
 *             SecurityContextHolder.setContext((SecurityContext) contextSnapshot);
 *             return supplier.get();
 *         } finally {
 *             SecurityContextHolder.clearContext();
 *         }
 *     }
 * }
 * }</pre>
 *
 * <p>Princípios SOLID aplicados:</p>
 * <ul>
 *   <li><b>Single Responsibility</b>: Apenas executa código sem propagação de contexto</li>
 *   <li><b>Open/Closed</b>: Projetos podem estender com suas próprias implementações</li>
 *   <li><b>Dependency Inversion</b>: Trabalha com a abstração ResilienceAspectContext</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
public class NullResilienceContextPropagator implements ResilienceAspect.ResilienceAspectContext {

    /**
     * Returns null as no context is captured.
     *
     * @return null
     */
    @Override
    public Object captureContext() {
        log.debug("Capturing context - no context propagation configured");
        return null;
    }

    /**
     * Executes the supplier without context propagation.
     *
     * <p>Simplesmente executa o supplier pois não há contexto a propagar.</p>
     *
     * @param contextSnapshot ignored (null)
     * @param supplier the code to execute
     * @param <T> the return type
     * @return the result from the supplier
     */
    @Override
    public <T> T executeWithContext(Object contextSnapshot, ContextSupplier<T> supplier) {
        log.debug("Executing without context propagation");
        return supplier.get();
    }
}
