package com.ia.core.quartz.view.quartz;

import com.ia.core.quartz.service.model.scheduler.dto.SchedulerConfigDTO;
import com.ia.core.quartz.service.model.scheduler.dto.SchedulerConfigTranslator;
import com.ia.core.security.view.manager.DefaultSecuredViewBaseManager;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Manager unificado para operações do Quartz (SchedulerConfig e Jobs).
 *
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
    return (QuartzClient) super.getClient();
  }

  public Map<String, String> getAvaliableJobClasses() {
    return getClient().getAvaliableJobClasses();
  }

}
