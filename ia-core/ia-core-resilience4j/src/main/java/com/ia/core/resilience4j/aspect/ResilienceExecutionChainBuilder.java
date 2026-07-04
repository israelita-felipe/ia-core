package com.ia.core.resilience4j.aspect;

import com.ia.core.resilience4j.dto.ResilienceContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Builder para construir a cadeia de execução de handlers de resiliência.
 *
 * <p>Responsável por:</p>
 * <ul>
 *   <li>Manter a lista de handlers na ordem correta</li>
 *   <li>Construir a cadeia de forma legível e testável</li>
 *   <li>Permitir fácil adição/remoção de handlers (extensibilidade)</li>
 *   <li>Separar a construção da execução</li>
 * </ul>
 *
 * <p>Exemplo de uso:</p>
 * <pre>
 * Supplier<Object> chain = executionChainBuilder.build(context, joinPoint, externalContext);
 * return resilienceTemplate.execute(context, chain);
 * </pre>
 *
 * <p>Padrões aplicados:</p>
 * <ul>
 *   <li><b>Builder</b> - Construção clara da cadeia</li>
 *   <li><b>Chain of Responsibility</b> - Encadeamento de handlers</li>
 *   <li><b>Strategy</b> - Cada handler é uma estratégia</li>
 *   <li><b>Decorator</b> - Cada handler decora o próximo</li>
 * </ul>
 *
 * <p>Princípios SOLID:</p>
 * <ul>
 *   <li><b>Single Responsibility</b> - Apenas constrói a cadeia</li>
 *   <li><b>Open/Closed</b> - Extensível sem modificação</li>
 *   <li><b>Dependency Inversion</b> - Depende de abstrações (handlers)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class ResilienceExecutionChainBuilder {

    private final ResilienceMetricsCollector resilienceMetricsCollector;
    private final ResilienceAspect.ResilienceAspectContext contextPropagator;

    // Injeção dos handlers - lista ordenada de estratégias
    @Getter
    private final List<ResilienceStrategyHandler<?>> handlers = new ArrayList<>();

    public ResilienceExecutionChainBuilder(
            ResilienceMetricsCollector resilienceMetricsCollector,
            ResilienceAspect.ResilienceAspectContext contextPropagator,
            List<ResilienceStrategyHandler<?>> handlers) {
        this.resilienceMetricsCollector = Objects.requireNonNull(resilienceMetricsCollector, "resilienceMetricsCollector must not be null");
        this.contextPropagator = Objects.requireNonNull(contextPropagator, "contextPropagator must not be null");
        this.handlers.addAll(Objects.requireNonNull(handlers, "handlers must not be null"));
    }

    /**
     * Constrói a cadeia de execução completa.
     *
     * <p>A cadeia é construída de dentro para fora (de trás para frente):</p>
     * <ol>
     *   <li>Começa com o método original (joinPoint.proceed())</li>
     *   <li>Envolve com propagação de contexto</li>
     *   <li>Envolve com cada handler na ordem reversa</li>
     *   <li>Envolve com coleta de métricas</li>
     * </ol>
     *
     * @param context contexto de resiliência
     * @param joinPoint join point do método original
     * @return Supplier pronto para execução
     */
    public Supplier<Object> build(ResilienceContext context,
                                   ProceedingJoinPoint joinPoint) {
        log.debug("Building resilience execution chain for {}", context.getMethod().getName());

        // 1. Base: o método original
        Supplier<Object> baseSupplier = buildBaseSupplier(joinPoint);

        // 2. Envolve com propagação de contexto
        Supplier<Object> withContextSupplier = buildContextPropagationSupplier(
                contextPropagator.captureContext(),
                baseSupplier);

        // 3. Envolve com cada handler na ordem
        Supplier<Object> chainSupplier = buildHandlersChain(context, withContextSupplier);

        // 4. Envolve com métricas
        return buildMetricsSupplier(context, joinPoint, chainSupplier);
    }

    /**
     * Constrói o supplier base que executa o método original.
     *
     * @param joinPoint o join point do método
     * @return supplier que executa o método original
     */
    private Supplier<Object> buildBaseSupplier(ProceedingJoinPoint joinPoint) {
        return () -> {
            try {
                return joinPoint.proceed();
            } catch (RuntimeException e) {
                throw e;
            } catch (Throwable e) {
                throw new RuntimeException(e.getLocalizedMessage(), e);
            }
        };
    }

    /**
     * Envolve o supplier com propagação de contexto.
     *
     * @param externalContext o snapshot do contexto
     * @param nextSupplier o próximo passo na cadeia
     * @return supplier com contexto propagado
     */
    private Supplier<Object> buildContextPropagationSupplier(
            Object externalContext,
            Supplier<Object> nextSupplier) {
        return () -> contextPropagator.executeWithContext(externalContext, nextSupplier::get);
    }

    /**
     * Constrói a cadeia de handlers.
     *
     * <p>Itera sobre os handlers mantendo a ordem correta:
     * cada handler envolve o próximo, criando a composição desejada.</p>
     *
     * @param context contexto de resiliência
     * @param innerSupplier o supplier mais interno
     * @return supplier com todos os handlers encadeados
     */
    private Supplier<Object> buildHandlersChain(ResilienceContext context,
                                                Supplier<Object> innerSupplier) {
        Supplier<Object> chain = innerSupplier;

        // Encadeia os handlers de trás para frente para manter a ordem correta
        for (int i = handlers.size() - 1; i >= 0; i--) {
            ResilienceStrategyHandler<?> handler = handlers.get(i);
            Supplier<Object> nextChain = chain;

            chain = () -> handler.execute(context, nextChain);

            log.debug("Handler {} added to chain", handler.getClass().getSimpleName());
        }

        return chain;
    }

    /**
     * Envolve a cadeia com coleta de métricas.
     *
     * @param context contexto de resiliência
     * @param joinPoint o join point do método
     * @param chainSupplier a cadeia de handlers
     * @return supplier com métricas
     */
    private Supplier<Object> buildMetricsSupplier(ResilienceContext context,
                                                   ProceedingJoinPoint joinPoint,
                                                   Supplier<Object> chainSupplier) {
        return () -> resilienceMetricsCollector.executeWithMetrics(
                context,
                joinPoint,
                chainSupplier::get);
    }

    /**
     * Retorna a ordem dos handlers.
     *
     * @return lista de nomes dos handlers na ordem de execução
     */
    public List<String> getHandlerOrder() {
        List<String> order = new ArrayList<>();
        for (ResilienceStrategyHandler<?> handler : handlers) {
            order.add(handler.getClass().getSimpleName());
        }
        return order;
    }

    /**
     * Redefine a lista de handlers (útil para testes).
     *
     * @param newHandlers nova lista de handlers
     */
    public void setHandlers(List<ResilienceStrategyHandler<?>> newHandlers) {
        this.handlers.clear();
        this.handlers.addAll(newHandlers);
        log.info("Handlers reordered: {}", getHandlerOrder());
    }
}




