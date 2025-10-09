package com.ia.core.service;

import java.util.function.Supplier;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @author Israel Araújo
 */
public interface HasTransaction {

  /**
   * @return {@link PlatformTransactionManager}
   */
  PlatformTransactionManager getTransactionManager();

  /**
   * Executa uma determinada ação em uma transação
   *
   * @param <E>    tipo do objeto a ser retornado
   * @param action {@link Supplier} do objeto
   * @return Objeto
   */
  default <E> E onTransaction(Supplier<E> action) {
    TransactionStatus status = getTransactionManager()
        .getTransaction(createTransactionDefinition());
    try {
      E result = action.get();
      getTransactionManager().commit(status);
      return result;
    } catch (Exception e) {
      getTransactionManager().rollback(status);
      throw e;
    }

  }

  /**
   * @return {@link TransactionDefinition}
   */
  default TransactionDefinition createTransactionDefinition() {
    DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
    defaultTransactionDefinition
        .setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
    defaultTransactionDefinition
        .setIsolationLevel(DefaultTransactionDefinition.ISOLATION_SERIALIZABLE);
    return defaultTransactionDefinition;
  }

}
