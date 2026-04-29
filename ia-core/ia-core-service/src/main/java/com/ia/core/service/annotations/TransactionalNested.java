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
 * Executa lógica em subtransação (nested/savepoint).
 * <p>
 * <b>Características:</b>
 * </p>
 * <ul>
 * <li>readOnly = false: Permite modificações no banco</li>
 * <li>propagation = NESTED: Cria savepoint na transação existente</li>
 * <li>isolation = DEFAULT: Deixa banco de dados decidir</li>
 * <li>timeout = 30 segundos: Proteção contra operações muito longas</li>
 * </ul>
 * <p>
 * <b>Quando usar:</b> Para operações aninhadas que podem falhar sem afetar a
 * transação pai. Ideal para processamento de batch ou operações que precisam de
 * rollback parcial.
 * </p>
 * <p>
 * <b>Exemplo:</b>
 * </p>
 *
 * <pre>
 * {@literal @}Transactional
 * public void processarLote(List&lt;Item&gt; itens) {
 *   for (Item item : itens) {
 *     try {
 *       processarItem(item);  // Em subtransação
 *     } catch (Exception e) {
 *       log.error("Item {} falhou", item.getId());
 *       // Continua processando outros itens
 *     }
 *   }
 * }
 *
 * {@literal @}Transactional(propagation = Propagation.NESTED)
 * private void processarItem(Item item) {
 *   // Se falhar, só faz rollback desta operação
 *   itemService.validarEProcessar(item);
 * }
 * </pre>
 *
 * @see Transactional#readOnly()
 * @see Propagation#NESTED
 * @see Isolation#DEFAULT
 * @see Transactional#timeout()
 */

@Documented
@Retention(RUNTIME)
@Target(METHOD)
@Transactional(readOnly = false, propagation = Propagation.NESTED,
               isolation = Isolation.DEFAULT, timeout = 30)
/**
 * Classe que representa os serviços de negócio para transactional nested.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a TransactionalNested
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public @interface TransactionalNested {

}
