package com.ia.core.quartz.view.quartz;

import com.ia.core.quartz.service.model.scheduler.dto.SchedulerConfigDTO;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.view.manager.DefaultSecuredViewBaseMangerConfig;
import com.ia.core.view.client.BaseClient;
import org.springframework.stereotype.Component;

/**
 * Classe que representa as configurações para quartz manager.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a QuartzManagerConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

@Component
public class QuartzManagerConfig
  extends DefaultSecuredViewBaseMangerConfig<SchedulerConfigDTO> {

  /**
   * @param client
   * @param authorizationManager
   */
  public QuartzManagerConfig(BaseClient<SchedulerConfigDTO> client,
                             CoreSecurityAuthorizationManager authorizationManager) {
    super(client, authorizationManager);
  }

}
