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
 * Executa lógica de escrita em nova transação independente.
 * <p>
 * <b>Características:</b>
 * </p>
 * <ul>
 * <li>readOnly = false: Permite modificações no banco</li>
 * <li>propagation = REQUIRES_NEW: Cria nova transação sempre</li>
 * <li>isolation = DEFAULT: Deixa banco de dados decidir</li>
 * <li>timeout = 30 segundos: Proteção contra operações muito longas</li>
 * </ul>
 * <p>
 * <b>Quando usar:</b> Para operações que devem ser independentes da transação
 * pai, como auditoria, logs operacionais, ou updates que não podem falhar junto
 * com a operação principal.
 * </p>
 * <p>
 * <b>Exemplo:</b>
 * </p>
 *
 * <pre>
 * {@literal @}Transactional
 * public void deletarComAuditoria(Long id) {
 *   // Deleta entidade
 *   delete(id);
 *
 *   // Registra auditoria em transação independente
 *   // Se falhar, não faz rollback do delete
 *   registrarAuditoria(id);
 * }
 *
 * {@literal @}Transactional(propagation = Propagation.REQUIRES_NEW)
 * private void registrarAuditoria(Long id) {
 *   auditRepository.save(...);
 * }
 * </pre>
 *
 * @see Transactional#readOnly()
 * @see Propagation#REQUIRES_NEW
 * @see Isolation#DEFAULT
 * @see Transactional#timeout()
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW,
               isolation = Isolation.DEFAULT, timeout = 30)
/**
 * Classe que representa os serviços de negócio para transactional write independent.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a TransactionalWriteIndependent
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public @interface TransactionalWriteIndependent {

}
