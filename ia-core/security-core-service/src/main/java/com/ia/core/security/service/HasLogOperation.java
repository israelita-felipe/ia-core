package com.ia.core.security.service;

import com.ia.core.security.service.log.operation.LogOperationService;

/**
 * @author Israel Araújo
 */
public interface HasLogOperation {

  /**
   * @return Serviço de log
   */
  LogOperationService getLogOperationService();

}
