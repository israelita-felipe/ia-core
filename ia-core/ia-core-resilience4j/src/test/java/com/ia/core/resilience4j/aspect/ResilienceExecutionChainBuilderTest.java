package com.ia.core.resilience4j.aspect;

import com.ia.core.resilience4j.dto.ResilienceContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes para {@link ResilienceExecutionChainBuilder}.
 *
 * <p>Valida que:</p>
 * <ul>
 *   <li>A cadeia é construída na ordem correta</li>
 *   <li>Os handlers são executados em sequência</li>
 *   <li>A cadeia é extensível sem modificação</li>
 *   <li>Os testes são independentes da implementação interna</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@DisplayName("ResilienceExecutionChainBuilder Tests")
class ResilienceExecutionChainBuilderTest {

    private ResilienceExecutionChainBuilder builder;

    @Mock
    private ResilienceMetricsCollector metricsCollector;

    @Mock
    private ResilienceAspect.ResilienceAspectContext contextPropagator;

    @Mock
    private RateLimiterStrategyHandler rateLimiterHandler;

    @Mock
    private BulkheadStrategyHandler bulkheadHandler;

    @Mock
    private CircuitBreakerStrategyHandler circuitBreakerHandler;

    @Mock
    private RetryStrategyHandler retryHandler;

    @Mock
    private TimeLimiterStrategyHandler timeLimiterHandler;

    @Mock
    private ResilienceContext context;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        builder = new ResilienceExecutionChainBuilder(
                metricsCollector,
                contextPropagator,
            Arrays.asList(rateLimiterHandler,
                bulkheadHandler,
                circuitBreakerHandler,
                retryHandler,
                timeLimiterHandler));

        // Mock do método
        try {
            when(context.getMethod()).thenReturn(this.getClass().getMethod("setUp"));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Handlers são inicializados na ordem correta")
    void testHandlersInitializedInCorrectOrder() {
        // Arrange & Act
        List<String> order = builder.getHandlerOrder();

        // Assert
        assertEquals(5, order.size());
        assertEquals("RateLimiterStrategyHandler", order.get(0));
        assertEquals("BulkheadStrategyHandler", order.get(1));
        assertEquals("CircuitBreakerStrategyHandler", order.get(2));
        assertEquals("RetryStrategyHandler", order.get(3));
        assertEquals("TimeLimiterStrategyHandler", order.get(4));
    }

    @Test
    @DisplayName("Cadeia é construída e retorna uma Supplier válida")
    void testBuildReturnsValidSupplier() throws Throwable {
        // Arrange
        Object externalContext = new Object();
        when(contextPropagator.executeWithContext(any(), any()))
                .thenAnswer(invocation -> {
                    Supplier<?> supplier = invocation.getArgument(1);
                    return supplier.get();
                });
        when(rateLimiterHandler.execute(any(), any()))
                .thenAnswer(invocation -> {
                    Supplier<?> supplier = invocation.getArgument(1);
                    return supplier.get();
                });
        when(bulkheadHandler.execute(any(), any()))
                .thenAnswer(invocation -> {
                    Supplier<?> supplier = invocation.getArgument(1);
                    return supplier.get();
                });
        when(circuitBreakerHandler.execute(any(), any()))
                .thenAnswer(invocation -> {
                    Supplier<?> supplier = invocation.getArgument(1);
                    return supplier.get();
                });
        when(retryHandler.execute(any(), any()))
                .thenAnswer(invocation -> {
                    Supplier<?> supplier = invocation.getArgument(1);
                    return supplier.get();
                });
        when(timeLimiterHandler.execute(any(), any()))
                .thenAnswer(invocation -> {
                    Supplier<?> supplier = invocation.getArgument(1);
                    return supplier.get();
                });
        when(metricsCollector.executeWithMetrics(any(), any(), any()))
                .thenAnswer(invocation -> {
                    java.util.concurrent.Callable<?> callable = invocation.getArgument(2);
                    try {
                        return callable.call();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        when(joinPoint.proceed()).thenAnswer(invocation -> "SUCCESS");

        // Act
        Supplier<Object> chain = builder.build(context, joinPoint, externalContext);
        Object result = chain.get();

        // Assert
        assertNotNull(chain);
        assertEquals("SUCCESS", result);
    }

    @Test
    @DisplayName("Handlers podem ser reordenados dinamicamente")
    void testHandlersCanBeReordered() {
        // Arrange
        List<ResilienceStrategyHandler<?>> newOrder = new ArrayList<>();
        newOrder.add(retryHandler);
        newOrder.add(rateLimiterHandler);
        newOrder.add(bulkheadHandler);

        // Act
        builder.setHandlers(newOrder);
        List<String> order = builder.getHandlerOrder();

        // Assert
        assertEquals(3, order.size());
        assertEquals("RetryStrategyHandler", order.get(0));
        assertEquals("RateLimiterStrategyHandler", order.get(1));
        assertEquals("BulkheadStrategyHandler", order.get(2));
    }

    @Test
    @DisplayName("Extensibilidade: novo handler pode ser adicionado sem modificar ResilienceAspect")
    void testExtensibilityNewHandlerCanBeAdded() {
        // Arrange
        ResilienceStrategyHandler<?> newHandler = mock(ResilienceStrategyHandler.class);
        List<ResilienceStrategyHandler<?>> handlersWithNew = new ArrayList<>(builder.getHandlers());
        handlersWithNew.add(newHandler);

        // Act
        builder.setHandlers(handlersWithNew);
        List<String> order = builder.getHandlerOrder();

        // Assert
        assertEquals(6, order.size());
        // Verify que o novo handler foi adicionado (ordem é importante)
        assertTrue(order.contains("TimeLimiterStrategyHandler"));
    }

    @Test
    @DisplayName("Ordem de execução respeita RateLimiter antes de Bulkhead")
    void testExecutionOrderRespected() throws Throwable {
        // Arrange
        List<String> executionOrder = new ArrayList<>();

        when(rateLimiterHandler.execute(any(), any()))
                .thenAnswer(invocation -> {
                    executionOrder.add("RATE_LIMITER");
                    Supplier<?> supplier = invocation.getArgument(1);
                    return supplier.get();
                });

        when(bulkheadHandler.execute(any(), any()))
                .thenAnswer(invocation -> {
                    executionOrder.add("BULKHEAD");
                    Supplier<?> supplier = invocation.getArgument(1);
                    return supplier.get();
                });

        when(circuitBreakerHandler.execute(any(), any()))
                .thenAnswer(invocation -> {
                    Supplier<?> supplier = invocation.getArgument(1);
                    return supplier.get();
                });

        when(retryHandler.execute(any(), any()))
                .thenAnswer(invocation -> {
                    Supplier<?> supplier = invocation.getArgument(1);
                    return supplier.get();
                });

        when(timeLimiterHandler.execute(any(), any()))
                .thenAnswer(invocation -> {
                    Supplier<?> supplier = invocation.getArgument(1);
                    return supplier.get();
                });

        when(contextPropagator.executeWithContext(any(), any()))
                .thenAnswer(invocation -> {
                    Supplier<?> supplier = invocation.getArgument(1);
                    return supplier.get();
                });

        when(metricsCollector.executeWithMetrics(any(), any(), any()))
                .thenAnswer(invocation -> {
                    java.util.concurrent.Callable<?> callable = invocation.getArgument(2);
                    try {
                        return callable.call();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

        when(joinPoint.proceed()).thenAnswer(invocation -> "SUCCESS");

        // Act
        Supplier<Object> chain = builder.build(context, joinPoint, new Object());
        chain.get();

        // Assert
        assertEquals(2, executionOrder.size());
        assertEquals("RATE_LIMITER", executionOrder.get(0));
        assertEquals("BULKHEAD", executionOrder.get(1));
    }
}









