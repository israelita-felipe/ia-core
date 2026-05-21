package com.ia.core.resilience4j.aspect;

import com.ia.core.resilience4j.dto.ResilienceContext;

import java.util.function.Supplier;

/**
 * Interface base para handlers de estratégias de resiliência.
 *
 * <p>Define o padrão de dois métodos para separar a resolução do contexto
 * da execução da lógica resiliente.</p>
 *
 * <p>Princípios SOLID aplicados:</p>
 * <ul>
 *   <li><b>Single Responsibility</b>: Cada handler tem apenas uma razão para mudar</li>
 *   <li><b>Open/Closed</b>: Extensível sem modificar código existente</li>
 *   <li><b>Liskov Substitution</b>: Todos os handlers seguem a mesma interface</li>
 *   <li><b>Interface Segregation</b>: Interface mínima necessária</li>
 *   <li><b>Dependency Inversion</b>: Dependem de abstrações (ResilienceContext)</li>
 * </ul>
 *
 * @param <T> o tipo do componente resilience4j (Retry, CircuitBreaker, etc.)
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface ResilienceStrategyHandler<T> {

    /**
     * Resolve/configura o contexto criando o componente resilience4j.
     *
     * <p>Este método é responsável por:</p>
     * <ul>
     *   <li>Ler as configurações do perfil e anotação</li>
     *   <li>Criar o componente resilience4j apropriado</li>
     *   <li>Armazenar o componente no contexto para uso posterior</li>
     * </ul>
     *
     * @param context o contexto de resiliência contendo perfil e anotação
     * @return o componente resilience4j configurado
     */
    T resolve(ResilienceContext context);

    /**
     * Executa a lógica com o contexto já resolvido.
     *
     * <p>Este método é responsável por:</p>
     * <ul>
     *   <li>Recuperar o componente do contexto</li>
     *   <li>Executar a lógica com o componente aplicado</li>
     *   <li>Tratar exceções específicas do componente</li>
     * </ul>
     *
     * <p>O parâmetro next representa a próxima etapa na cadeia de handlers,
     * permitindo a composição de múltiplos padrões de resiliência.</p>
     *
     * @param context o contexto de resiliência já resolvido
     * @param next o próximo passo na cadeia de execução
     * @return o resultado da execução
     */
    Object execute(ResilienceContext context, Supplier<Object> next);
}
