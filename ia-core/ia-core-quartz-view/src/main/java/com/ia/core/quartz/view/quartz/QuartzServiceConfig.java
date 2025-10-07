package com.ia.core.quartz.view.quartz;

import org.springframework.stereotype.Component;

import com.ia.core.quartz.service.model.scheduler.SchedulerConfigDTO;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.view.service.DefaultSecuredViewBaseServiceConfig;
import com.ia.core.view.client.BaseClient;

/**
 *
 */
@Component
public class QuartzServiceConfig
  extends DefaultSecuredViewBaseServiceConfig<SchedulerConfigDTO> {

  /**
   * @param client
   * @param authorizationManager
   */
  public QuartzServiceConfig(BaseClient<SchedulerConfigDTO> client,
                             CoreSecurityAuthorizationManager authorizationManager) {
    super(client, authorizationManager);
  }

}
