package com.ia.core.quartz.service.model.scheduler.triggers;

/**
 * @author Israel Araújo
 */
@SuppressWarnings("javadoc")
public class SchedulerConfigTriggerTranslator {
  public static final class HELP {
    // Campos básicos do trigger
    public static final String TRIGGER_NAME = "scheduler.config.trigger.help.triggerName";
    public static final String SCHEDULER_NAME = "scheduler.config.trigger.help.schedulerName";
    public static final String TRIGGER_GROUP = "scheduler.config.trigger.help.triggerGroup";
    public static final String JOB_NAME = "scheduler.config.trigger.help.jobName";
    public static final String JOB_GROUP = "scheduler.config.trigger.help.jobGroup";
    public static final String DESCRIPTION = "scheduler.config.trigger.help.description";

    // Campos de tempo de execução
    public static final String NEXT_FIRE_TIME = "scheduler.config.trigger.help.nextFireTime";
    public static final String PREV_FIRE_TIME = "scheduler.config.trigger.help.prevFireTime";
    public static final String START_TIME = "scheduler.config.trigger.help.startTime";
    public static final String END_TIME = "scheduler.config.trigger.help.endTime";

    // Campos de configuração e estado
    public static final String PRIORITY = "scheduler.config.trigger.help.priority";
    public static final String TRIGGER_STATE = "scheduler.config.trigger.help.triggerState";
    public static final String TRIGGER_TYPE = "scheduler.config.trigger.help.triggerType";
    public static final String CALENDAR_NAME = "scheduler.config.trigger.help.calendarName";
    public static final String MISFIRE_INSTR = "scheduler.config.trigger.help.misFireInstr";
    public static final String JOB_DATA = "scheduler.config.trigger.help.jobData";
  }

  public static final String SCHEDULER_CONFIG_CLASS = SchedulerConfigTriggerDTO.class
      .getCanonicalName();
  // Campos básicos do trigger
  public static final String TRIGGER_NAME = "scheduler.config.trigger.triggerName";
  public static final String SCHEDULER_NAME = "scheduler.config.trigger.schedulerName";
  public static final String TRIGGER_GROUP = "scheduler.config.trigger.triggerGroup";
  public static final String JOB_NAME = "scheduler.config.trigger.jobName";
  public static final String JOB_GROUP = "scheduler.config.trigger.jobGroup";
  public static final String DESCRIPTION = "scheduler.config.trigger.description";

  // Campos de tempo de execução
  public static final String NEXT_FIRE_TIME = "scheduler.config.trigger.nextFireTime";
  public static final String PREV_FIRE_TIME = "scheduler.config.trigger.prevFireTime";
  public static final String START_TIME = "scheduler.config.trigger.startTime";
  public static final String END_TIME = "scheduler.config.trigger.endTime";

  // Campos de configuração e estado
  public static final String PRIORITY = "scheduler.config.trigger.priority";
  public static final String TRIGGER_STATE = "scheduler.config.trigger.triggerState";
  public static final String TRIGGER_TYPE = "scheduler.config.trigger.triggerType";
  public static final String CALENDAR_NAME = "scheduler.config.trigger.calendarName";
  public static final String MISFIRE_INSTR = "scheduler.config.trigger.misFireInstr";
  public static final String JOB_DATA = "scheduler.config.trigger.jobData";
}
