package com.ia.core.resilience4j.aspect;

import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.dto.ResilienceContext;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.core.IntervalFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

/**
 * Handles retry strategy configuration and execution.
 *
 * <p>This class encapsulates the logic for creating and configuring Retry instances
 * based on resilience profiles and annotations.</p>
 *
 * <p>Princípios SOLID aplicados:</p>
 * <ul>
 *   <li><b>Single Responsibility</b>: Apenas lida com Retry</li>
 *   <li><b>Open/Closed</b>: Extensível via herança da classe abstrata</li>
 *   <li><b>Liskov Substitution</b>: Estende AbstractResilienceStrategyHandler</li>
 *   <li><b>Interface Segregation</b>: Interface mínima necessária</li>
 *   <li><b>Dependency Inversion</b>: Depende de ResilienceContext (abstração)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class RetryStrategyHandler extends AbstractResilienceStrategyHandler<Retry> {

    /**
      * Creates or resolves a Retry instance for the given context.
      *
      * <p>Configures the Retry with:</p>
      * <ul>
      *   <li>Max attempts from annotation or profile</li>
      *   <li>Wait duration with exponential backoff</li>
      *   <li>Retryable exceptions (IOException, TimeoutException)</li>
      * </ul>
      *
      * @param context the resilience context containing profile and annotation info
      * @return configured Retry instance
      */
    @Override
    public Retry resolve(ResilienceContext context) {
        ProfileAnnotation data = extractProfileAndAnnotation(context);
        ResilienceProfile profile = data.profile();
        Resilient annotation = data.annotation();

        String name = profile.getName() + "-" + context.getMethod().getName() + "-retry";

        RetryConfig config = RetryConfig.custom()
                .maxAttempts(getIntValue(annotation.maxRetryAttempts(),
                        profile.getMaxRetryAttempts()))
                .waitDuration(Duration.ofMillis(
                        getLongValue(annotation.retryInitialWait(),
                                profile.getRetryInitialWaitMs())))
                .intervalFunction(IntervalFunction.ofExponentialBackoff(
                        getLongValue(annotation.retryInitialWait(),
                                profile.getRetryInitialWaitMs()),
                        getDoubleValue(annotation.retryBackoffMultiplier(),
                                profile.getRetryBackoffMultiplier())))
                .retryExceptions(IOException.class, TimeoutException.class)
                .build();

        log.debug("Resolved Retry '{}' with maxAttempts={}", name, config.getMaxAttempts());
        return context.getResilienceRegistry().retry(name, config);
    }

    /**
      * Executes the next step with the resolved Retry.
      *
      * <p>Retrieves the Retry from the context and applies it to the next supplier.
      * The Retry is expected to be stored in the context during the resolve phase.</p>
      *
      * <p>Tratamento de exceções:</p>
      * <ul>
      *   <li>Retries are handled by Resilience4j's internal retry logic</li>
      *   <li>After max retries exceeded, exceptions are unwrapped from CompletionException
      *       to prevent message accumulation</li>
      * </ul>
      *
      * @param context o contexto de resiliência já resolvido
      * @param next o próximo passo na cadeia de execução
      * @return o resultado da execução
      */
    @Override
    public Object execute(ResilienceContext context, Supplier<Object> next) {
        Retry retry = resolve(context);
        try {
            return retry.executeSupplier(() ->
                CompletableFuture.supplyAsync(next::get)
            ).join();
        } catch (Exception e) {
            // Unwrap CompletionException to get the actual cause and prevent
            // nested message accumulation in error dialogs
            Throwable cause = unwrapCompletionException(e);
            // Rethrow without wrapping to prevent message accumulation.
            // The exception already contains the retry context in its cause chain.
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            throw new RuntimeException(cause);
        }
    }

    /**
      * Unwraps CompletionException to get the actual cause.
      *
      * <p>CompletableFuture.join() wraps checked exceptions in CompletionException.
      * We need to extract the actual cause to prevent message duplication
      * in error dialogs and logs.</p>
      *
      * @param throwable the exception to unwrap
      * @return the unwrapped cause, or the original if not a CompletionException
      */
    private Throwable unwrapCompletionException(Throwable throwable) {
        if (throwable instanceof java.util.concurrent.CompletionException) {
            Throwable cause = throwable.getCause();
            if (cause != null) {
                return cause;
            }
        }
        return throwable;
    }
}