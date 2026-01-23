package com.ia.core.service;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.ia.core.service.contract.HasTransactionManager;

/**
 * @author Israel Araújo
 */
public interface HasTransaction
  extends HasTransactionManager {

  Logger log = LoggerFactory.getLogger(HasTransaction.class);

  /**
   * @return {@link PlatformTransactionManager}
   */
  @Override
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
      if (status.isNewTransaction() && !status.isReadOnly()) {
        getTransactionManager().commit(status);
        log.info("Transação {} comitada", status.getTransactionName());
      }
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      if (status.isNewTransaction() && !status.isCompleted()) {
        getTransactionManager().rollback(status);
        log.info("Transação {} abortada", status.getTransactionName());
      }
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
        .setIsolationLevel(DefaultTransactionDefinition.ISOLATION_READ_UNCOMMITTED);
    return defaultTransactionDefinition;
  }

}
