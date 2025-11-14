package com.ia.core.security.view.log.operation;

import org.springframework.stereotype.Component;

import com.ia.core.security.service.model.log.operation.LogOperationDTO;
import com.ia.core.view.manager.DefaultBaseManagerConfig;

/**
 *
 */
@Component
public class LogOperationManagerConfig
  extends DefaultBaseManagerConfig<LogOperationDTO> {

  /**
   * @param client cliente do serviço
   */
  public LogOperationManagerConfig(LogOperationClient client) {
    super(client);
  }

}
