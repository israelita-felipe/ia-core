package com.ia.core.security.view.log.operation;

import com.ia.core.security.service.model.log.operation.LogOperationDTO;
import com.ia.core.view.manager.DefaultBaseManager;
import org.springframework.stereotype.Service;

/**
 * Serviço de log de operações
 *
 * @author Israel Araújo
 */

@Service
public class LogOperationManager
  extends DefaultBaseManager<LogOperationDTO> {

  /**
   * @param config cliente do serviço
   */
  public LogOperationManager(LogOperationManagerConfig config) {
    super(config);
  }

}
