package com.ia.core.service.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Executa lógica com suporte opcional a transação (read-only).
 * <p>
 * <b>Características:</b>
 * </p>
 * <ul>
 * <li>readOnly = true: Sem modificações no banco</li>
 * <li>propagation = SUPPORTS: Usa transação se existir, senão executa sem</li>
 * <li>isolation = DEFAULT: Deixa banco de dados decidir</li>
 * <li>timeout = indefinido: Sem limitação de tempo</li>
 * </ul>
 * <p>
 * <b>Quando usar:</b> Para operações de logging, métricas, cálculos que podem
 * rodar com ou sem transação. Não participa de transação obrigatoriamente.
 * </p>
 * <p>
 * <b>Exemplo:</b>
 * </p>
 *
 * <pre>
 * {@literal @}Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
 * public void registrarMetrica(String chave, int valor) {
 *   metricaService.incrementar(chave, valor);
 * }
 * </pre>
 *
 * @see Transactional#readOnly()
 * @see Propagation#SUPPORTS
 * @see Isolation#DEFAULT
 */

@Documented
@Retention(RUNTIME)
@Target(METHOD)
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS,
               isolation = Isolation.DEFAULT)
public @interface TransactionalReadOnlyOptional {

}
