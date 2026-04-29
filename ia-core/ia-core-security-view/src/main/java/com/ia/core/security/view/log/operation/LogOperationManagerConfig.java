package com.ia.core.security.view.log.operation;

import com.ia.core.security.service.model.log.operation.LogOperationDTO;
import com.ia.core.view.manager.DefaultBaseManagerConfig;
import org.springframework.stereotype.Component;

/**
 * Classe que representa as configurações para log operation manager.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a LogOperationManagerConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
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
