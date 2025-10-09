package com.ia.core.quartz.service;

import java.util.List;

import org.quartz.Scheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import com.ia.core.quartz.model.scheduler.SchedulerConfig;
import com.ia.core.quartz.service.model.scheduler.SchedulerConfigDTO;
import com.ia.core.security.service.DefaultSecuredBaseService.DefaultSecuredBaseServiceConfig;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.service.mapper.BaseMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;

import lombok.Getter;

/**
 *
 */
@Component
public class SchedulerConfigServiceConfig
  extends
  DefaultSecuredBaseServiceConfig<SchedulerConfig, SchedulerConfigDTO> {
  @Getter
  private final Scheduler quartzScheduler;
  /** Prefixo das triggers */
  public static final String TRIGGER_SUFIX = "Trigger";
  /** Grupo default do job */
  public static final String DEFAULT_JOB_GROUP = "FiCifModificationGroup";

  /**
   * @param repository
   * @param mapper
   * @param searchRequestMapper
   * @param translator
   * @param authorizationManager
   * @param logOperationService
   * @param passwordEncoder
   */
  public SchedulerConfigServiceConfig(PlatformTransactionManager transactionManager,
                                      SchedulerConfigRepository repository,
                                      BaseMapper<SchedulerConfig, SchedulerConfigDTO> mapper,
                                      SearchRequestMapper searchRequestMapper,
                                      Translator translator,
                                      CoreSecurityAuthorizationManager authorizationManager,
                                      LogOperationService logOperationService,
                                      List<IServiceValidator<SchedulerConfigDTO>> validators,
                                      Scheduler quartzScheduler) {
    super(transactionManager, repository, mapper, searchRequestMapper,
          translator, authorizationManager, logOperationService,
          validators);
    this.quartzScheduler = quartzScheduler;
  }

  @Override
  public SchedulerConfigRepository getRepository() {
    return (SchedulerConfigRepository) super.getRepository();
  }
}
