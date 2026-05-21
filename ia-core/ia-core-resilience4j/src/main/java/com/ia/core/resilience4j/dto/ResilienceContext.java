package com.ia.core.resilience4j.dto;

import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import com.ia.core.resilience4j.registry.ResilienceRegistry;
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
     * Resilience registry for creating resilience4j components.
     */
    private ResilienceRegistry resilienceRegistry;


}
