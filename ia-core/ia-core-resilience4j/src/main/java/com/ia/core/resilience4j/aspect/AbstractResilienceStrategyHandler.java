package com.ia.core.resilience4j.aspect;

import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.dto.ResilienceContext;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Classe abstrata base para handlers de estratégias de resiliência.
 *
 * <p>Proporciona implementação padrão para os métodos comuns de todos os handlers,
 * reduzindo a duplicação de código e garantindo consistência.</p>
 *
 * <p>Princípios SOLID aplicados:</p>
 * <ul>
 *   <li><b>Single Responsibility</b>: Fornece funcionalidades comuns a todos os handlers</li>
 *   <li><b>Open/Closed</b>: Extensível via herança sem modificação</li>
 *   <li><b>Liskov Substitution</b>: Classes derivadas podem substituir a base</li>
 *   <li><b>Dependency Inversion</b>: Trabalha com abstrações</li>
 * </ul>
 *
 * @param <T> o tipo do componente resilience4j
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public abstract class AbstractResilienceStrategyHandler<T> implements ResilienceStrategyHandler<T> {

    /**
     * Extrai o perfil e annotation do contexto com validação de null.
     *
     * @param context o contexto de resiliência
     * @return tupla contendo profile e annotation
     */
    protected ProfileAnnotation extractProfileAndAnnotation(ResilienceContext context) {
        Objects.requireNonNull(context, "context must not be null");
        ResilienceProfile profile = context.getProfile();
        Resilient annotation = context.getAnnotation();
        Objects.requireNonNull(profile, "profile must not be null in context");
        Objects.requireNonNull(annotation, "annotation must not be null in context");
        return new ProfileAnnotation(profile, annotation);
    }

    /**
     * Tupla simples para encapsular profile e annotation.
     */
    protected record ProfileAnnotation(ResilienceProfile profile, Resilient annotation) {}

    /**
     * Obtém um valor inteiro com fallback para o valor do profile.
     *
     * <p>Retorna o valor da annotation se maior ou igual a zero,
     * caso contrário retorna o valor do profile.</p>
     *
     * @param annotationValue valor da annotation (use -1 como "não definido")
     * @param profileValue valor padrão do profile
     * @return valor a ser utilizado
     */
    protected int getIntValue(int annotationValue, int profileValue) {
        return annotationValue >= 0 ? annotationValue : profileValue;
    }

    /**
     * Obtém um valor long com fallback para o valor do profile.
     *
     * <p>Retorna o valor da annotation se maior ou igual a zero,
     * caso contrário retorna o valor do profile.</p>
     *
     * @param annotationValue valor da annotation (use -1 como "não definido")
     * @param profileValue valor padrão do profile
     * @return valor a ser utilizado
     */
    protected long getLongValue(long annotationValue, long profileValue) {
        return annotationValue >= 0 ? annotationValue : profileValue;
    }

    /**
     * Obtém um valor double com fallback para o valor do profile.
     *
     * <p>Retorna o valor da annotation se maior ou igual a zero,
     * caso contrário retorna o valor do profile.</p>
     *
     * @param annotationValue valor da annotation (use -1 como "não definido")
     * @param profileValue valor padrão do profile
     * @return valor a ser utilizado
     */
    protected double getDoubleValue(double annotationValue, double profileValue) {
        return annotationValue >= 0 ? annotationValue : profileValue;
    }

    /**
     * Gets the root cause of an exception by traversing the cause chain.
     *
     * <p>Utilizado para identificar a exceção raiz real, evitando a acumulação
     * de mensagens de exceções aninhadas em handlers de resiliência.</p>
     *
     * @param throwable the exception to analyze
     * @return the root cause exception, or the original if no root cause exists
     */
    protected Throwable getRootCause(Throwable throwable) {
        if (throwable == null) {
            return null;
        }
        Throwable cause = throwable.getCause();
        if (cause == null) {
            return throwable;
        }
        // Traverse to find the true root cause (where cause.getCause() == null or cause == throwable)
        while (cause != null && cause != throwable) {
            if (cause.getCause() == null || cause.getCause() == cause) {
                return cause;
            }
            cause = cause.getCause();
        }
        return throwable;
    }

    /**
     * Checks if the exception or any of its causes is of the specified type.
     *
     * <p>Este método é usado para determinar se uma exceção é realmente
     * causada pelo mecanismo específico de resiliência (TimeoutException para TimeLimiter,
     * BulkheadFullException para Bulkhead, etc.) antes de adicionar o prefixo.</p>
     *
     * @param throwable the exception to check
     * @param exceptionType the exception type to look for
     * @return true if the exception or any cause is of the specified type
     */
    protected boolean isCausedBy(Throwable throwable, Class<? extends Throwable> exceptionType) {
        if (throwable == null || exceptionType == null) {
            return false;
        }
        if (exceptionType.isInstance(throwable)) {
            return true;
        }
        Throwable cause = throwable.getCause();
        while (cause != null && cause != throwable) {
            if (exceptionType.isInstance(cause)) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }
}
