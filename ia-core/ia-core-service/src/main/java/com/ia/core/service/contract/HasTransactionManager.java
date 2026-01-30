package com.ia.core.service.contract;

import org.springframework.transaction.PlatformTransactionManager;

/**
 * Interface segregada para acesso ao gerenciador de transações.
 * 
 * Princípio: ISP (Interface Segregation Principle)
 * Responsabilidade Única: Fornecer acesso ao transaction manager.
 *
 * @author Israel Araújo
 */
public interface HasTransactionManager {

  /**
   * Obtém o gerenciador de transações do serviço.
   *
   * @return Platform transaction manager
   */
  PlatformTransactionManager getTransactionManager();
}
