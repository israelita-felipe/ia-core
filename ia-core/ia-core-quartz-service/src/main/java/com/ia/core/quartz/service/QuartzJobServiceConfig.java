package com.ia.core.quartz.service;

import org.quartz.Scheduler;
import org.springframework.stereotype.Component;

import com.ia.core.quartz.service.model.job.QuartzJobDTO;
import com.ia.core.quartz.service.model.job.QuartzJobInstanceDTO;
import com.ia.core.quartz.service.model.job.QuartzJobTriggerDTO;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.SecurityContextService;
import com.ia.core.service.translator.Translator;

import lombok.Getter;

/**
 * Configuração do serviço de gerenciamento de Jobs do Quartz.
 * 
 * @author Israel Araújo
 */
@Component
@Getter
public class QuartzJobServiceConfig {

  private final Scheduler quartzScheduler;
  private final Translator translator;
  private final CoreSecurityAuthorizationManager authorizationManager;
  private final SecurityContextService securityContextService;
  private final LogOperationService logOperationService;

  /**
   * Construtor com as dependências necessárias.
   * 
   * @param quartzScheduler       Scheduler do Quartz
   * @param translator            Translator para internacionalização
   * @param authorizationManager Gerenciador de autorização
   * @param securityContextService Serviço de contexto de segurança
   * @param logOperationService   Serviço de log de operações
   */
  public QuartzJobServiceConfig(Scheduler quartzScheduler,
                                Translator translator,
                                CoreSecurityAuthorizationManager authorizationManager,
                                SecurityContextService securityContextService,
                                LogOperationService logOperationService) {
    this.quartzScheduler = quartzScheduler;
    this.translator = translator;
    this.authorizationManager = authorizationManager;
    this.securityContextService = securityContextService;
    this.logOperationService = logOperationService;
  }
}
