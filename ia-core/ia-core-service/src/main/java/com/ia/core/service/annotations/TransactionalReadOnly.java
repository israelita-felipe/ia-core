package com.ia.core.service.annotations;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Executa lógica de leitura em transação read-only.
 * <p>
 * <b>Características:</b>
 * </p>
 * <ul>
 * <li>readOnly = true: Otimização do banco para operações de leitura</li>
 * <li>propagation = REQUIRED: Usa transação existente ou cria nova</li>
 * <li>isolation = DEFAULT: Deixa banco de dados decidir</li>
 * <li>timeout = indefinido: Sem limitação de tempo</li>
 * </ul>
 * <p>
 * <b>Quando usar:</b> Para operações de busca, filtros, listas. O banco de
 * dados pode otimizar queries de leitura em modo read-only.
 * </p>
 *
 * @see Transactional#readOnly()
 * @see Propagation#REQUIRED
 * @see Isolation#DEFAULT
 */
@Documented
@Retention(RUNTIME)
@Target(ElementType.METHOD)
@Transactional(readOnly = true, propagation = Propagation.REQUIRED,
               isolation = Isolation.DEFAULT)
@Inherited
public @interface TransactionalReadOnly {

}
