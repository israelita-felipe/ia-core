package com.ia.core.quartz.view.quartz;

import org.springframework.stereotype.Component;

import com.ia.core.quartz.service.model.scheduler.SchedulerConfigDTO;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.view.manager.DefaultSecuredViewBaseMangerConfig;
import com.ia.core.view.client.BaseClient;

/**
 *
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
