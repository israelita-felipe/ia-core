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
 * Executa lógica de escrita em transação padrão (REQUIRED).
 * <p>
 * <b>Características:</b>
 * </p>
 * <ul>
 * <li>readOnly = false: Permite modificações no banco</li>
 * <li>propagation = REQUIRED: Usa transação existente ou cria nova</li>
 * <li>isolation = DEFAULT: Deixa banco de dados decidir</li>
 * <li>timeout = 30 segundos: Proteção contra operações muito longas</li>
 * </ul>
 * <p>
 * <b>Quando usar:</b> Para operações de save, update, delete que devem
 * participar de uma transação existente ou criar uma nova.
 * </p>
 *
 * @see Transactional#readOnly()
 * @see Propagation#REQUIRED
 * @see Isolation#DEFAULT
 * @see Transactional#timeout()
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
@Transactional(readOnly = false, propagation = Propagation.REQUIRED,
               isolation = Isolation.DEFAULT, timeout = 30)
public @interface TransactionalWrite {

}
