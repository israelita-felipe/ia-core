package com.ia.core.quartz.service;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import com.ia.core.quartz.service.model.job.QuartzJobDTO;
import com.ia.core.quartz.service.model.job.QuartzJobInstanceDTO;
import com.ia.core.quartz.service.model.job.QuartzJobTranslator;
import com.ia.core.quartz.service.model.job.QuartzJobTriggerDTO;
import com.ia.core.quartz.service.model.job.QuartzJobUseCase;
import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.service.authorization.HasAuthorizationManager;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.functionality.FunctionalityManager;
import com.ia.core.security.service.model.functionality.HasFunctionality;

import lombok.extern.slf4j.Slf4j;

/**
 * Serviço para manipulação direta de Jobs do Quartz.
 * <p>
 * Este serviço permite gerenciar jobs do Quartz diretamente, sem depender de
 * configurações do SchedulerConfig.
 *
 * @author Israel Araújo
 */
@Slf4j
@Service
public class QuartzJobService
  implements QuartzJobUseCase, HasFunctionality, HasAuthorizationManager {

  private final QuartzJobServiceConfig config;

  /**
   * Construtor com a configuração do serviço.
   *
   * @param config Configuração do serviço
   */
  public QuartzJobService(QuartzJobServiceConfig config) {
    this.config = config;
  }

  /**
   * Retorna a configuração do serviço.
   *
   * @return Configuração do serviço
   */
  protected QuartzJobServiceConfig getConfig() {
    return config;
  }

  /**
   * Retorna o scheduler do Quartz.
   *
   * @return Scheduler
   */
  protected Scheduler getScheduler() {
    return getConfig().getQuartzScheduler();
  }

  /**
   * Lista todos os jobs do scheduler.
   *
   * @return Lista de DTOs de jobs
   */
  @Override
  public List<QuartzJobDTO> findAllJobs() {
    List<QuartzJobDTO> jobs = new ArrayList<>();
    try {
      Scheduler scheduler = getScheduler();
      List<String> jobGroups = scheduler.getJobGroupNames();

      for (String group : jobGroups) {
        Set<JobKey> jobKeys = scheduler
            .getJobKeys(GroupMatcher.jobGroupEquals(group));

        for (JobKey jobKey : jobKeys) {
          try {
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail != null) {
              QuartzJobDTO dto = buildJobDTO(jobDetail, scheduler);
              jobs.add(dto);
            }
          } catch (SchedulerException e) {
            log.error("Erro ao buscar detalhes do job {}: {}", jobKey,
                      e.getMessage());
          }
        }
      }
      log.info("Encontrados {} jobs no scheduler", jobs.size());
    } catch (SchedulerException e) {
      log.error("Erro ao listar jobs: {}", e.getMessage(), e);
    }
    return jobs;
  }

  /**
   * Busca um job específico pelo nome e grupo.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return DTO do job ou null se não encontrado
   */
  @Override
  public QuartzJobDTO findJob(String jobName, String jobGroup) {
    try {
      Scheduler scheduler = getScheduler();
      JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
      JobDetail jobDetail = scheduler.getJobDetail(jobKey);

      if (jobDetail != null) {
        return buildJobDTO(jobDetail, scheduler);
      }
    } catch (SchedulerException e) {
      log.error("Erro ao buscar job {}/{}: {}", jobName, jobGroup,
                e.getMessage(), e);
    }
    return null;
  }

  /**
   * Pausa um job no scheduler.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return true se o job foi pausado com sucesso
   */
  @Override
  public boolean pauseJob(String jobName, String jobGroup) {
    try {
      Scheduler scheduler = getScheduler();
      JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

      if (scheduler.checkExists(jobKey)) {
        scheduler.pauseJob(jobKey);
        log.info("Job pausado: {}/{}", jobName, jobGroup);
        return true;
      } else {
        log.warn("Job não encontrado para pausar: {}/{}", jobName,
                 jobGroup);
      }
    } catch (SchedulerException e) {
      log.error("Erro ao pausar job {}/{}: {}", jobName, jobGroup,
                e.getMessage(), e);
    }
    return false;
  }

  /**
   * Resume (retorna) um job pausado.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return true se o job foi resumido com sucesso
   */
  @Override
  public boolean resumeJob(String jobName, String jobGroup) {
    try {
      Scheduler scheduler = getScheduler();
      JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

      if (scheduler.checkExists(jobKey)) {
        scheduler.resumeJob(jobKey);
        log.info("Job resumido: {}/{}", jobName, jobGroup);
        return true;
      } else {
        log.warn("Job não encontrado para resumir: {}/{}", jobName,
                 jobGroup);
      }
    } catch (SchedulerException e) {
      log.error("Erro ao resumir job {}/{}: {}", jobName, jobGroup,
                e.getMessage(), e);
    }
    return false;
  }

  /**
   * Remove um job do scheduler.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return true se o job foi removido com sucesso
   */
  @Override
  public boolean deleteJob(String jobName, String jobGroup) {
    try {
      Scheduler scheduler = getScheduler();
      JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

      if (scheduler.checkExists(jobKey)) {
        boolean deleted = scheduler.deleteJob(jobKey);
        if (deleted) {
          log.info("Job removido: {}/{}", jobName, jobGroup);
        }
        return deleted;
      } else {
        log.warn("Job não encontrado para remover: {}/{}", jobName,
                 jobGroup);
      }
    } catch (SchedulerException e) {
      log.error("Erro ao remover job {}/{}: {}", jobName, jobGroup,
                e.getMessage(), e);
    }
    return false;
  }

  /**
   * Executa um job imediatamente (dispara).
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return true se o job foi disparado com sucesso
   */
  @Override
  public boolean triggerJob(String jobName, String jobGroup) {
    try {
      Scheduler scheduler = getScheduler();
      JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

      if (scheduler.checkExists(jobKey)) {
        scheduler.triggerJob(jobKey);
        log.info("Job disparado: {}/{}", jobName, jobGroup);
        return true;
      } else {
        log.warn("Job não encontrado para disparar: {}/{}", jobName,
                 jobGroup);
      }
    } catch (SchedulerException e) {
      log.error("Erro ao disparar job {}/{}: {}", jobName, jobGroup,
                e.getMessage(), e);
    }
    return false;
  }

  /**
   * Lista os triggers associados a um job.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return Lista de DTOs de triggers
   */
  @Override
  public List<QuartzJobTriggerDTO> findTriggersOfJob(String jobName,
                                                     String jobGroup) {
    List<QuartzJobTriggerDTO> triggers = new ArrayList<>();
    try {
      Scheduler scheduler = getScheduler();
      JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
      List<? extends Trigger> triggerList = scheduler
          .getTriggersOfJob(jobKey);

      ZoneId zoneId = ZoneId.systemDefault();

      for (Trigger trigger : triggerList) {
        QuartzJobTriggerDTO dto = buildTriggerDTO(trigger, scheduler,
                                                  zoneId);
        triggers.add(dto);
      }
    } catch (SchedulerException e) {
      log.error("Erro ao buscar triggers do job {}/{}: {}", jobName,
                jobGroup, e.getMessage(), e);
    }
    return triggers;
  }

  /**
   * Pausa um trigger específico.
   *
   * @param triggerName  Nome do trigger
   * @param triggerGroup Grupo do trigger
   * @return true se o trigger foi pausado com sucesso
   */
  @Override
  public boolean pauseTrigger(String triggerName, String triggerGroup) {
    try {
      Scheduler scheduler = getScheduler();
      TriggerKey triggerKey = TriggerKey.triggerKey(triggerName,
                                                    triggerGroup);

      if (scheduler.checkExists(triggerKey)) {
        scheduler.pauseTrigger(triggerKey);
        log.info("Trigger pausado: {}/{}", triggerName, triggerGroup);
        return true;
      }
    } catch (SchedulerException e) {
      log.error("Erro ao pausar trigger {}/{}: {}", triggerName,
                triggerGroup, e.getMessage(), e);
    }
    return false;
  }

  /**
   * Resume um trigger pausado.
   *
   * @param triggerName  Nome do trigger
   * @param triggerGroup Grupo do trigger
   * @return true se o trigger foi resumido com sucesso
   */
  @Override
  public boolean resumeTrigger(String triggerName, String triggerGroup) {
    try {
      Scheduler scheduler = getScheduler();
      TriggerKey triggerKey = TriggerKey.triggerKey(triggerName,
                                                    triggerGroup);

      if (scheduler.checkExists(triggerKey)) {
        scheduler.resumeTrigger(triggerKey);
        log.info("Trigger resumido: {}/{}", triggerName, triggerGroup);
        return true;
      }
    } catch (SchedulerException e) {
      log.error("Erro ao resumir trigger {}/{}: {}", triggerName,
                triggerGroup, e.getMessage(), e);
    }
    return false;
  }

  /**
   * Lista todos os jobs atualmente em execução.
   *
   * @return Lista de DTOs de instâncias em execução
   */
  @Override
  public List<QuartzJobInstanceDTO> findCurrentlyExecutingJobs() {
    List<QuartzJobInstanceDTO> instances = new ArrayList<>();
    try {
      Scheduler scheduler = getScheduler();
      List<JobExecutionContext> executingJobs = scheduler
          .getCurrentlyExecutingJobs();

      ZoneId zoneId = ZoneId.systemDefault();

      for (JobExecutionContext context : executingJobs) {
        QuartzJobInstanceDTO dto = buildInstanceDTO(context, zoneId);
        instances.add(dto);
      }
    } catch (SchedulerException e) {
      log.error("Erro ao buscar jobs em execução: {}", e.getMessage(), e);
    }
    return instances;
  }

  /**
   * Retorna o nome do tipo de funcionalidade para autorização.
   *
   * @return Nome do tipo de funcionalidade
   */
  @Override
  public String getFunctionalityTypeName() {
    return QuartzJobTranslator.QUARTZ_JOB;
  }

  /**
   * Constrói um DTO de job a partir do JobDetail do Quartz.
   *
   * @param jobDetail Detalhes do job
   * @param scheduler Scheduler para buscar informações de estado
   * @return DTO preenchido
   */
  private QuartzJobDTO buildJobDTO(JobDetail jobDetail,
                                   Scheduler scheduler) {
    QuartzJobTriggerDTO last = findTriggersOfJob(jobDetail.getKey()
        .getName(), jobDetail.getKey().getGroup()).getLast();
    QuartzJobDTO dto = QuartzJobDTO.builder()
        .jobState(last != null ? last.getTriggerState() : null)
        .jobName(jobDetail.getKey().getName())
        .jobGroup(jobDetail.getKey().getGroup())
        .description(jobDetail.getDescription())
        .jobClassName(jobDetail.getJobClass().getName())
        .durable(jobDetail.isDurable())
        .requestsRecovery(jobDetail.requestsRecovery())
        .jobData(jobDetail.getJobDataMap()).build();

    try {
      // Buscar triggers para obter informações de tempo
      List<? extends Trigger> triggers = scheduler
          .getTriggersOfJob(jobDetail.getKey());
      if (!triggers.isEmpty()) {
        Trigger trigger = triggers.get(0); // Pegar o primeiro trigger
        dto.setNextExecutionTime(trigger.getNextFireTime() != null ? trigger
            .getNextFireTime().toInstant().atZone(ZoneId.systemDefault())
            .toLocalDateTime() : null);
      }
    } catch (SchedulerException e) {
      log.debug("Erro ao buscar triggers para job {}: {}",
                jobDetail.getKey(), e.getMessage());
    }

    return dto;
  }

  /**
   * Constrói um DTO de trigger a partir do Trigger do Quartz.
   *
   * @param trigger   Trigger do Quartz
   * @param scheduler Scheduler para buscar informações de estado
   * @param zoneId    ZoneId para conversão de datas
   * @return DTO preenchido
   */
  private QuartzJobTriggerDTO buildTriggerDTO(Trigger trigger,
                                              Scheduler scheduler,
                                              ZoneId zoneId) {
    QuartzJobTriggerDTO dto = QuartzJobTriggerDTO.builder()
        .triggerName(trigger.getKey().getName())
        .triggerGroup(trigger.getKey().getGroup())
        .jobName(trigger.getJobKey().getName())
        .jobGroup(trigger.getJobKey().getGroup())
        .description(trigger.getDescription())
        .triggerType(trigger.getClass().getName())
        .calendarName(trigger.getCalendarName())
        .priority(trigger.getPriority())
        .misFireInstr((long) trigger.getMisfireInstruction())
        .jobData(trigger.getJobDataMap()).build();

    // Converter timestamps
    if (trigger.getNextFireTime() != null) {
      dto.setNextFireTime(trigger.getNextFireTime().toInstant()
          .atZone(zoneId).toLocalDateTime());
    }
    if (trigger.getPreviousFireTime() != null) {
      dto.setPrevFireTime(trigger.getPreviousFireTime().toInstant()
          .atZone(zoneId).toLocalDateTime());
    }
    if (trigger.getStartTime() != null) {
      dto.setStartTime(trigger.getStartTime().toInstant().atZone(zoneId)
          .toLocalDateTime());
    }
    if (trigger.getEndTime() != null) {
      dto.setEndTime(trigger.getEndTime().toInstant().atZone(zoneId)
          .toLocalDateTime());
    }
    if (trigger.getFinalFireTime() != null) {
      dto.setFinalFireTime(trigger.getFinalFireTime().toInstant()
          .atZone(zoneId).toLocalDateTime());
    }

    // Obter estado do trigger
    try {
      dto.setTriggerState(scheduler.getTriggerState(trigger.getKey())
          .name());
    } catch (SchedulerException e) {
      log.debug("Erro ao buscar estado do trigger {}: {}", trigger.getKey(),
                e.getMessage());
    }

    return dto;
  }

  /**
   * Constrói um DTO de instância de execução a partir do contexto de execução.
   *
   * @param context Contexto de execução do job
   * @param zoneId  ZoneId para conversão de datas
   * @return DTO preenchido
   */
  private QuartzJobInstanceDTO buildInstanceDTO(JobExecutionContext context,
                                                ZoneId zoneId) {
    return QuartzJobInstanceDTO.builder()
        .instanceId(context.getFireInstanceId())
        .jobName(context.getJobDetail().getKey().getName())
        .jobGroup(context.getJobDetail().getKey().getGroup())
        .triggerName(context.getTrigger().getKey().getName())
        .triggerGroup(context.getTrigger().getKey().getGroup())
        .fireTime(context.getFireTime().toInstant().atZone(zoneId)
            .toLocalDateTime())
        .scheduledFireTime(context.getScheduledFireTime() != null ? context
            .getScheduledFireTime().toInstant().atZone(zoneId)
            .toLocalDateTime() : null)
        .jobDataMap(context.getJobDetail().getJobDataMap())
        .recovered(context.isRecovering()).build();
  }

  @Override
  public CoreSecurityAuthorizationManager getAuthorizationManager() {
    return getConfig().getAuthorizationManager();
  }

  @Override
  public Set<Functionality> registryFunctionalities(FunctionalityManager manager) {
    return Set.of(manager.addFunctionality(this));
  }
}
