package com.ia.core.resilience4j.aspect;

import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.dto.ResilienceContext;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import com.ia.core.resilience4j.registry.ResilienceRegistry;
import com.ia.core.resilience4j.template.ResilienceTemplate;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * AOP Aspect that intercepts methods annotated with {@link Resilient}
 * and applies the resilience4j patterns.
 *
 * <p>Applied patterns:</p>
 * <ul>
 *   <li>Aspect (AOP) - transverse application of resilience</li>
 *   <li>Decorator - wraps the method with protection layers</li>
 *   <li>Template Method - defines the skeleton of resilient execution</li>
 *   <li>Strategy - selects configurations by profile</li>
 * </ul>
 *
 * <p>Execution flow:</p>
 * <ol>
 *   <li>Resolve the resilience profile</li>
 *   <li>Call resolve() on all handlers to configure context</li>
 *   <li>Execute the method with the configured handlers</li>
 *   <li>In case of failure, apply the configured fallback</li>
 * </ol>
 *
 * <p>Princípios SOLID aplicados:</p>
 * <ul>
 *   <li><b>Single Responsibility</b>: Orquestra handlers, não implementa lógica de resiliência</li>
 *   <li><b>Open/Closed</b>: Extensível via novos handlers</li>
 *   <li><b>Liskov Substitution</b>: Trabalha com interface ResilienceStrategyHandler</li>
 *   <li><b>Interface Segregation</b>: Usa apenas os métodos necessários</li>
 *   <li><b>Dependency Inversion</b>: Depende de abstrações (handlers)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Aspect
@Component
public class ResilienceAspect {

    private final ResilienceRegistry resilienceRegistry;
    private final ResilienceTemplate resilienceTemplate;
    private final ResilienceExecutionChainBuilder executionChainBuilder;

    public ResilienceAspect(ResilienceRegistry resilienceRegistry,
                            ResilienceTemplate resilienceTemplate,
                            ResilienceExecutionChainBuilder executionChainBuilder) {
        this.resilienceRegistry = Objects.requireNonNull(resilienceRegistry, "resilienceRegistry must not be null");
        this.resilienceTemplate = Objects.requireNonNull(resilienceTemplate, "resilienceTemplate must not be null");
        this.executionChainBuilder = Objects.requireNonNull(executionChainBuilder, "executionChainBuilder must not be null");
    }

    /**
     * Intercepts all methods annotated with @Resilient.
     */
    @Around("@annotation(com.ia.core.resilience4j.annotation.Resilient)")
    public Object applyResilience(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Resilient annotation = method.getAnnotation(Resilient.class);

        // 1. Resolve the profile
        ResilienceProfile profile = resolveProfile(annotation);

        // 2. Create the resilience context
        ResilienceContext context = ResilienceContext.builder()
                .profile(profile)
                .method(method)
                .joinPoint(joinPoint)
                .annotation(annotation)
                .resilienceRegistry(resilienceRegistry)
                .build();


        // 5. Execute within the resilient template

            return resilienceTemplate.execute(context, buildExecutionChain(context, joinPoint));

    }

    /**
     * Resolves the resilience profile (annotation > properties > DEFAULT).
     */
    private ResilienceProfile resolveProfile(Resilient annotation) {
        return annotation.value();
    }



    /**
     * Builds the execution chain with all resilience handlers.
     *
     * <p>Cria a cadeia de execução com os handlers na ordem correta:
     * RateLimiter → Bulkhead → CircuitBreaker → Retry → TimeLimiter → Metrics</p>
     *
     * <p>Delegada para {@link ResilienceExecutionChainBuilder} para manter SRP
     * e permitir extensibilidade sem violação do OCP.</p>
     *
     * @param context o contexto de resiliência
     * @param joinPoint o join point para execução do método
     * @return o supplier com a cadeia de execução
     */
    private Supplier<Object> buildExecutionChain(ResilienceContext context,
                                                  ProceedingJoinPoint joinPoint) {
        return executionChainBuilder.build(context, joinPoint);
    }


    /**
     * Encapsulates context propagation for thread environments (e.g., SecurityContext, MDC, etc).
     *
     * <p>This interface allows decoupling the resilience aspect from specific context implementations.
     * Implementations can capture and restore any ThreadLocal-based context (Spring Security,
     * SLF4J MDC, custom contexts, etc.) without coupling the aspect to those frameworks.</p>
     *
     * <p>Implementations should be Spring beans and will be automatically injected into
     * {@link ResilienceAspect}.</p>
     *
     * @author Israel Araújo
     * @since 1.0.0
     */
    public interface ResilienceAspectContext {

        /**
         * Captures the current context state from the calling thread.
         *
         * <p>This method is invoked in the original thread (where the method annotated
         * with @Resilient is being called) and should capture any ThreadLocal state
         * that needs to be propagated to the executor thread.</p>
         *
         * @return A snapshot of the context state. The returned object should be
         *         serializable (or at least transferable) to another thread.
         */
        Object captureContext();

        /**
         * Executes a supplier with the captured context restored.
         *
         * <p>This method is invoked in a worker thread (from the executor pool) and
         * should restore the captured context state before executing the supplier,
         * then clean up after execution.</p>
         *
         * @param contextSnapshot The context snapshot captured by {@link #captureContext()}.
         * @param supplier        The code to execute with the restored context.
         * @param <T>             The return type of the supplier.
         * @return The result returned by the supplier.
         */
        <T> T executeWithContext(Object contextSnapshot, ContextSupplier<T> supplier);

        /**
         * A supplier that can execute code and throw checked exceptions.
         *
         * @param <T> The return type.
         */
        interface ContextSupplier<T> {
            T get();
        }
    }
}
