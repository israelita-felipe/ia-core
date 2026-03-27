package com.ia.core.service.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Executa lógica que NÃO requer transação.
 * <p>
 * <b>Características:</b>
 * </p>
 * <ul>
 * <li>readOnly = false: Sem controle de transação</li>
 * <li>propagation = NOT_SUPPORTED: Suspende transação existente</li>
 * <li>isolation = DEFAULT: Não aplicável (sem transação)</li>
 * </ul>
 * <p>
 * <b>Quando usar:</b> Para operações que não devem estar em transação, como
 * chamadas a APIs externas, envios de e-mail, ou operações de I/O.
 * </p>
 * <p>
 * <b>Exemplo:</b>
 * </p>
 *
 * <pre>
 * {@literal @}Transactional
 * public void criarComNotificacao(Entidade entidade) {
 *   Entidade salvo = save(entidade);
 *   notificar(salvo);  // Sem transação
 * }
 *
 * {@literal @}Transactional(propagation = Propagation.NOT_SUPPORTED)
 * private void notificar(Entidade entidade) {
 *   emailService.enviar(entidade.getEmail());
 * }
 * </pre>
 *
 * @see Propagation#NOT_SUPPORTED
 */

@Documented
@Retention(RUNTIME)
@Target(METHOD)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public @interface NonTransactional {

}
