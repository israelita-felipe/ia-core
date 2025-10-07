package com.ia.core.quartz.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ia.core.quartz.model.scheduler.SchedulerConfig;
import com.ia.core.quartz.service.model.scheduler.SchedulerConfigDTO;
import com.ia.core.quartz.service.model.scheduler.SchedulerConfigTranslator;
import com.ia.core.quartz.service.model.scheduler.SchedulerConfigTriggerDTO;
import com.ia.core.quartz.service.periodicidade.dto.PeriodicidadeDTO;
import com.ia.core.quartz.service.periodicidade.dto.PeriodicidadeFormatter;
import com.ia.core.security.service.DefaultSecuredBaseService;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.exception.ServiceException;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Israel Araújo
 */
@Slf4j
@Service
public class SchedulerConfigService
  extends DefaultSecuredBaseService<SchedulerConfig, SchedulerConfigDTO> {

  /**
   * @param repository
   * @param mapper
   * @param searchRequestMapper
   * @param translator
   * @param authorizationManager
   * @param logOperationService
   */
  public SchedulerConfigService(SchedulerConfigServiceConfig config) {
    super(config);
  }

  @PostConstruct
  @Override
  public void baseServiceInit() {
    super.baseServiceInit();
    scheduleJobs();
  }

  /**
   * Busca todas as classes que estendem AbstractJob no pacote com.ia
   *
   * @return um mapa de classes de trabalho disponíveis que estendem
   *         AbstractJob, juntamente com suas traduções.
   */
  public Map<Class<? extends AbstractJob>, String> getAvaliableJobClasses() {
    Map<Class<? extends AbstractJob>, String> jobClasses = new HashMap<>();
    Reflections reflections = new Reflections("com.ia", Scanners.SubTypes);
    reflections.getSubTypesOf(AbstractJob.class).forEach(jobClass -> {
      jobClasses.put(jobClass,
                     getTranslator().getTranslation(jobClass.getName()));
    });
    return jobClasses;
  }

  @Override
  public SchedulerConfigDTO find(UUID id) {
    SchedulerConfigDTO schedulerConfigDTO = super.find(id);
    handleTriggers(schedulerConfigDTO);
    return schedulerConfigDTO;
  }

  @Override
  public Page<SchedulerConfigDTO> findAll(SearchRequestDTO requestDTO) {
    Page<SchedulerConfigDTO> all = super.findAll(requestDTO);
    all.forEach(this::handleTriggers);
    return all;
  }

  /**
   * @param schedulerConfigDTO
   */
  protected void handleTriggers(SchedulerConfigDTO schedulerConfigDTO) {
    try {
      Scheduler quartzScheduler = getConfig().getQuartzScheduler();
      for (Trigger trigger : quartzScheduler
          .getTriggersOfJob(JobKey.jobKey(getJobName(schedulerConfigDTO),
                                          getGroup(schedulerConfigDTO)))) {
        SchedulerConfigTriggerDTO triggerDTO = SchedulerConfigTriggerDTO
            .builder().build();
        triggerDTO.setCalendarName(trigger.getCalendarName());
        triggerDTO.setDescription(trigger.getDescription());
        Date endTime = trigger.getEndTime();
        if (endTime != null) {
          triggerDTO.setEndTime(LocalTime
              .ofInstant(endTime.toInstant(), getZoneId()));
        }
        triggerDTO.setJobData(trigger.getJobDataMap());
        triggerDTO.setJobGroup(trigger.getJobKey().getGroup());
        triggerDTO.setJobName(trigger.getJobKey().getName());
        Integer misfireInstruction = trigger.getMisfireInstruction();
        if (misfireInstruction != null) {
          triggerDTO.setMisFireInstr(Long.valueOf(misfireInstruction));
        }
        Date nextFireTime = trigger.getNextFireTime();
        if (nextFireTime != null) {
          triggerDTO.setNextFireTime(LocalDateTime
              .ofInstant(nextFireTime.toInstant(), getZoneId()));
        }
        Date previousFireTime = trigger.getPreviousFireTime();
        if (previousFireTime != null) {
          triggerDTO.setPrevFireTime(LocalDateTime
              .ofInstant(previousFireTime.toInstant(),
                         getZoneId()));
        }
        Integer priority = trigger.getPriority();
        if (priority != null) {
          triggerDTO.setPriority(Long.valueOf(priority));
        }
        triggerDTO.setSchedulerName(quartzScheduler.getSchedulerName());
        Date startTime = trigger.getStartTime();
        if (startTime != null) {
          triggerDTO.setStartTime(LocalTime
              .ofInstant(startTime.toInstant(), getZoneId()));
        }
        triggerDTO.setTriggerGroup(trigger.getKey().getGroup());
        triggerDTO.setTriggerName(trigger.getKey().getName());
        triggerDTO.setTriggerState(quartzScheduler
            .getTriggerState(trigger.getKey()).name());
        triggerDTO.setTriggerType(trigger.getClass().getName());
        schedulerConfigDTO.getTriggers().add(triggerDTO);
      }
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
  }

  /**
   * @return
   */
  protected ZoneId getZoneId() {
    return ZoneId.systemDefault();
  }

  /**
   * @param schedulerConfigDTO
   * @return
   */
  protected String getJobName(SchedulerConfigDTO schedulerConfigDTO) {
    return schedulerConfigDTO.getId().toString();
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public SchedulerConfigDTO save(SchedulerConfigDTO toSave)
    throws ServiceException {
    boolean isNew = toSave.getId() == null;
    SchedulerConfigDTO saved = super.save(toSave);
    ServiceException serviceException = new ServiceException();
    if (isNew && saved.getPeriodicidade().isAtivo()) {
      try {
        scheduleJob(saved);
      } catch (SchedulerException | ClassNotFoundException e) {
        serviceException.add(e);
      }
    } else {
      try {
        if (saved.getPeriodicidade().isAtivo()) {
          updateJob(saved);
        } else {
          cancelJob(saved);
        }
      } catch (SchedulerException e) {
        serviceException.add(e);
      }
    }
    if (serviceException.hasErros()) {
      throw serviceException;
    }
    return saved;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public void delete(UUID id)
    throws ServiceException {
    SchedulerConfigDTO schedulerConfigDTO = find(id);
    super.delete(id);
    if (schedulerConfigDTO != null
        && schedulerConfigDTO.getPeriodicidade().isAtivo()) {
      cancelJob(schedulerConfigDTO);
    }
  }

  @Override
  public SchedulerConfigServiceConfig getConfig() {
    return (SchedulerConfigServiceConfig) super.getConfig();
  }

  @Override
  public String getFunctionalityTypeName() {
    return SchedulerConfigTranslator.SCHEDULER_CONFIG;
  }

  public List<SchedulerConfigDTO> findAllActive(boolean active) {
    return getConfig().getRepository().findAllActive(active).stream()
        .map(getMapper()::toDTO).toList();
  }

  public void scheduleJobs() {
    try {
      List<SchedulerConfigDTO> configs = findAllActive(true);
      for (SchedulerConfigDTO config : configs) {
        scheduleJob(config);
      }
    } catch (SchedulerException | ClassNotFoundException e) {
      log.error("Error scheduling jobs", e);
    }
  }

  private void scheduleJob(SchedulerConfigDTO config)
    throws SchedulerException, ClassNotFoundException {

    JobDetail jobDetail = createJobDetails(config);

    Trigger trigger = createTrigger(config);

    if (getConfig().getQuartzScheduler().checkExists(trigger.getKey())) {
      log.warn("Trigger with for job {} already exists. Skipping scheduling.",
               config.getId());
    } else {
      getConfig().getQuartzScheduler().scheduleJob(jobDetail, trigger);
      log.info("Scheduled job with id: {}", config.getId());
    }
  }

  /**
   * @param config
   * @param cronExpression
   * @return
   */
  protected Trigger createTrigger(SchedulerConfigDTO config) {
    return TriggerBuilder.newTrigger()
        .withIdentity(getTriggerName(config), getGroup(config))
        .withSchedule(createScheduleBuilder(config)).build();
  }

  /**
   * @param config
   * @return
   */
  protected String getTriggerName(SchedulerConfigDTO config) {
    return config.getId() + getTriggerSufix();
  }

  /**
   * @return
   */
  protected String getTriggerSufix() {
    return SchedulerConfigServiceConfig.TRIGGER_SUFIX;
  }

  protected ScheduleBuilder<? extends Trigger> createScheduleBuilder(SchedulerConfigDTO config) {
    return CronScheduleBuilder
        .cronSchedule(generateCronExpression(config.getPeriodicidade()));

  }

  /**
   * @return
   */
  protected String getGroup(SchedulerConfigDTO config) {
    return getDefaultGroup();
  }

  /**
   * @return
   */
  protected String getDefaultGroup() {
    return SchedulerConfigServiceConfig.DEFAULT_JOB_GROUP;
  }

  /**
   * @param config
   * @return
   * @throws ClassNotFoundException
   */
  protected JobDetail createJobDetails(SchedulerConfigDTO config)
    throws ClassNotFoundException {
    return JobBuilder
        .newJob((Class<? extends Job>) config.getJobClassNameAsClass())
        .withIdentity(getJobName(config), getGroup(config)).build();
  }

  public void updateJobs() {
    try {
      List<SchedulerConfigDTO> configs = findAllActive(true);

      for (SchedulerConfigDTO config : configs) {
        String cronExpression = generateCronExpression(config
            .getPeriodicidade());
        TriggerKey triggerKey = getTriggerKey(config);

        if (getConfig().getQuartzScheduler().checkExists(triggerKey)) {
          CronTrigger oldTrigger = (CronTrigger) getConfig()
              .getQuartzScheduler().getTrigger(triggerKey);
          String oldCronExpression = oldTrigger.getCronExpression();

          if (!oldCronExpression.equals(cronExpression)) {
            log.info("Updating job with new cron expression: {}",
                     cronExpression);
            updateJob(config);
          }
        } else {
          scheduleJob(config);
        }
      }
    } catch (SchedulerException | ClassNotFoundException e) {
      log.error("Error updating jobs", e);
    }
  }

  /**
   * @param config
   * @return
   */
  protected TriggerKey getTriggerKey(SchedulerConfigDTO config) {
    return TriggerKey.triggerKey(getTriggerName(config), getGroup(config));
  }

  private void updateJob(SchedulerConfigDTO config)
    throws SchedulerException {
    TriggerKey triggerKey = getTriggerKey(config);
    Trigger newTrigger = TriggerBuilder.newTrigger()
        .withIdentity(triggerKey)
        .withSchedule(createScheduleBuilder(config)).build();
    getConfig().getQuartzScheduler().rescheduleJob(triggerKey, newTrigger);
    log.info("Rescheduled job with new periodicity: {}",
             config.getPeriodicidade());
  }

  public void cancelAllJobs() {
    try {
      getConfig().getQuartzScheduler().clear(); // This will clear all jobs,
                                                // stopping the
      // scheduler
      log.info("All scheduled jobs have been cancelled.");
    } catch (SchedulerException e) {
      log.error("Error cancelling all jobs", e);
    }
  }

  public void cancelJob(SchedulerConfigDTO config) {
    try {
      getConfig().getQuartzScheduler()
          .deleteJob(JobKey.jobKey(getJobName(config), getGroup(config)));
      log.info("{} scheduled job have been cancelled.", config.getId());
    } catch (SchedulerException e) {
      log.error("Error cancelling the job {}: {}", config.getId(), e);
    }
  }

  private String generateCronExpression(PeriodicidadeDTO periodicidade) {
    return PeriodicidadeFormatter.asCronExpression(periodicidade);
  }

}
