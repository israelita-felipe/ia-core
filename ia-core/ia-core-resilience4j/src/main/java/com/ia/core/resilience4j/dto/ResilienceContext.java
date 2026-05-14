package com.ia.core.resilience4j.dto;

import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import lombok.Builder;
import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * Contexto de execução para operações resilientes.
 *
 * <p>Contém todas as informações necessárias para aplicar
 * os padrões de resiliência, incluindo o método, anotação
 * e argumentos do método.</p>
 *
 * <p>Padrões aplicados:</p>
 * <ul>
 *   <li>Context Object - agrupa dados de contexto</li>
 *   <li>Builder - construção fluente</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder
public class ResilienceContext {

    /**
     * Resilience profile to be applied.
     */
    private ResilienceProfile profile;

    /**
     * Method being intercepted.
     */
    private Method method;

    /**
     * AOP join point of the call.
     */
    private ProceedingJoinPoint joinPoint;

    /**
     * @Resilient annotation applied to the method.
     */
    private Resilient annotation;

    /**
     * Method arguments.
     */
    private Object[] args;

    /**
     * Circuit Breaker name based on the method.
     */
    public String getCircuitBreakerName() {
        return profile.getName() + "-" + method.getName() + "-cb";
    }

    /**
     * Retry name based on the method.
     */
    public String getRetryName() {
        return profile.getName() + "-" + method.getName() + "-retry";
    }

    /**
     * Bulkhead name based on the method.
     */
    public String getBulkheadName() {
        return profile.getName() + "-" + method.getName() + "-bulkhead";
    }

    /**
     * Rate Limiter name based on the method.
     */
    public String getRateLimiterName() {
        return profile.getName() + "-" + method.getName() + "-ratelimiter";
    }

    /**
     * Time Limiter name based on the method.
     */
    public String getTimeLimiterName() {
        return profile.getName() + "-" + method.getName() + "-timelimiter";
    }
}
