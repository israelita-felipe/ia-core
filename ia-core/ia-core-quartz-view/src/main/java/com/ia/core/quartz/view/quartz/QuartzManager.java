package com.ia.core.quartz.view.quartz;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ia.core.quartz.service.model.scheduler.SchedulerConfigDTO;
import com.ia.core.quartz.service.model.scheduler.SchedulerConfigTranslator;
import com.ia.core.security.view.manager.DefaultSecuredViewBaseManager;

/**
 * @author Israel Araújo
 */
@Service
public class QuartzManager
  extends DefaultSecuredViewBaseManager<SchedulerConfigDTO> {

  /**
   * @param client
   * @param authorizationManager
   */
  public QuartzManager(QuartzManagerConfig config) {
    super(config);
  }

  @Override
  public String getFunctionalityTypeName() {
    return SchedulerConfigTranslator.SCHEDULER_CONFIG;
  }

  @SuppressWarnings("unchecked")
  @Override
  public QuartzClient getClient() {
    return super.getClient();
  }

  public Map<String, String> getAvaliableJobClasses() {
    return getClient().getAvaliableJobClasses();
  }
}
