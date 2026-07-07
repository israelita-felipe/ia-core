package com.ia.core.resilience4j.aspect;

import com.ia.core.resilience4j.dto.ResilienceContext;
import com.ia.core.resilience4j.metrics.ResilienceMetrics;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/**
 * Collects and records resilience metrics for method executions.
 *
 * <p>This class encapsulates the logic for measuring execution times and
 * recording success/failure metrics using the ResilienceMetrics service.</p>
 *
 * <p>Princípios SOLID aplicados:</p>
 * <ul>
 *   <li><b>Single Responsibility</b>: Apenas coleta métricas</li>
 *   <li><b>Open/Closed</b>: Fechado para modificação</li>
 *   <li><b>Dependency Inversion</b>: Depende de ResilienceMetrics (abstração)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ResilienceMetricsCollector {

    private final ResilienceMetrics resilienceMetrics;

    /**
     * Executes a supplier and collects metrics for the execution.
     *
     * @param context      the resilience context
     * @param joinPoint    the join point for method execution
     * @param supplier     the code to execute
     * @param <T>          the return type
     * @return the result of the supplier execution
     */
    public <T> T executeWithMetrics(ResilienceContext context, ProceedingJoinPoint joinPoint,
                                    java.util.concurrent.Callable<T> supplier){
        Objects.requireNonNull(context, "context must not be null");
        Objects.requireNonNull(supplier, "supplier must not be null");

        Method method = context.getMethod();
        ResilienceProfile profile = context.getProfile();
        String methodName = method.getName();

        Instant startTime = Instant.now();
        try {
            T result = supplier.call();
            Duration duration = Duration.between(startTime, Instant.now());
            resilienceMetrics.recordSuccess(profile.getName(), methodName, duration.toMillis());
            return result;
        } catch (Throwable ex) {
            Duration duration = Duration.between(startTime, Instant.now());
            String errorType = ex.getClass().getSimpleName();
            resilienceMetrics.recordError(profile.getName(), methodName, errorType, duration.toMillis());
            throw new RuntimeException(ex.getLocalizedMessage(), ex);
        }
    }
}
