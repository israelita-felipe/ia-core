package com.ia.core.security.service;

import com.ia.core.security.service.log.operation.LogOperationService;
/**
 * Serviço de negócio para gerenciamento de has log operation.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a HasLogOperation
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public interface HasLogOperation {

  /**
   * @return Serviço de log
   */
  LogOperationService getLogOperationService();

}
