package com.ia.core.security.view.log.operation;

import org.springframework.stereotype.Component;

import com.ia.core.security.service.model.log.operation.LogOperationDTO;
import com.ia.core.view.service.DefaultBaseServiceConfig;

/**
 *
 */
@Component
public class LogOperationServiceConfig
  extends DefaultBaseServiceConfig<LogOperationDTO> {

  /**
   * @param client cliente do serviço
   */
  public LogOperationServiceConfig(LogOperationClient client) {
    super(client);
  }

}
