package com.ia.core.security.view.log.operation;

import org.springframework.stereotype.Service;

import com.ia.core.security.service.model.log.operation.LogOperationDTO;
import com.ia.core.view.client.BaseClient;
import com.ia.core.view.service.DefaultBaseService;

/**
 * Serviço de log de operações
 *
 * @author Israel Araújo
 */
@Service
public class LogOperationService
  extends DefaultBaseService<LogOperationDTO> {

  /**
   * @param client cliente do serviço
   */
  public LogOperationService(BaseClient<LogOperationDTO> client) {
    super(client);
  }

}
