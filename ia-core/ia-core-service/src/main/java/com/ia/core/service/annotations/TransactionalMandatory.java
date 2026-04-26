package com.ia.core.service.annotations;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Executa lógica que lança exceção se não houver transação existente.
 * <p>
 * <b>Características:</b>
 * </p>
 * <ul>
 * <li>readOnly = false: Permite modificações</li>
 * <li>propagation = MANDATORY: Requer transação existente</li>
 * <li>isolation = DEFAULT: Deixa banco de dados decidir</li>
 * </ul>
 * <p>
 * <b>Quando usar:</b> Para métodos que SEMPRE devem estar em uma transação. Se
 * chamado sem transação, lança IllegalTransactionStateException.
 * </p>
 * <p>
 * <b>Exemplo:</b>
 * </p>
 *
 * <pre>
 * {@literal @}Transactional
 * public void operacaoPrincipal() {
 *   // Esta operação DEVE estar em transação
 *   validarIntegridade();
 * }
 *
 * {@literal @}Transactional(propagation = Propagation.MANDATORY)
 * private void validarIntegridade() {
 *   // Lança exceção se chamada sem transação
 * }
 * </pre>
 *
 * @see Propagation#MANDATORY
 */

@Documented
@Retention(RUNTIME)
@Target(METHOD)
@Transactional(propagation = Propagation.MANDATORY,
               isolation = Isolation.DEFAULT)
public @interface TransactionalMandatory {

}
