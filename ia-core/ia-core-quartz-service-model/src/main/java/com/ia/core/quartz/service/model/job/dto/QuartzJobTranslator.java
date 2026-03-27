package com.ia.core.quartz.service.model.job.dto;

/**
 * Translator para mensagens internacionalizadas do QuartzJob.
 *
 * @author Israel Araújo
 */
@SuppressWarnings("javadoc")
public class QuartzJobTranslator {

  public static final class HELP {
    public static final String QUARTZ_JOB = "quartz.job.help";
    public static final String JOB_NAME = "quartz.job.name.help";
    public static final String JOB_GROUP = "quartz.job.group.help";
    public static final String JOB_CLASS_NAME = "quartz.job.class.name.help";
    public static final String JOB_STATE = "quartz.job.state.help";
    public static final String DESCRIPTION = "quartz.job.description.help";
    public static final String JOB_DATA = "quartz.job.data.help";
    public static final String LAST_EXECUTION_TIME = "quartz.job.last.execution.help";
    public static final String NEXT_EXECUTION_TIME = "quartz.job.next.execution.help";
    public static final String NUMBER_OF_EXECUTIONS = "quartz.job.executions.help";
    public static final String ACTIONS = "quartz.job.actions.help";
  }

  public static final String QUARTZ_JOB_CLASS = QuartzJobDTO.class
      .getCanonicalName();
  public static final String QUARTZ_JOB = "quartz.job";
  public static final String JOB_NAME = "quartz.job.name";
  public static final String JOB_GROUP = "quartz.job.group";
  public static final String JOB_CLASS_NAME = "quartz.job.class.name";
  public static final String JOB_STATE = "quartz.job.state";
  public static final String DESCRIPTION = "quartz.job.description";
  public static final String JOB_DATA = "quartz.job.data";
  public static final String IS_DURABLE = "quartz.job.is.durable";
  public static final String IS_VOLATILE = "quartz.job.is.volatile";
  public static final String REQUESTS_RECOVERY = "quartz.job.requests.recovery";
  public static final String LAST_EXECUTION_TIME = "quartz.job.last.execution.time";
  public static final String NEXT_EXECUTION_TIME = "quartz.job.next.execution.time";
  public static final String NUMBER_OF_EXECUTIONS = "quartz.job.number.of.executions";
  public static final String INSTANCE_ID = "quartz.job.instance.id";
  public static final String RESULT = "quartz.job.result";
  public static final String FIRE_TIME = "quartz.job.fire.time";
  public static final String SCHEDULED_FIRE_TIME = "quartz.job.scheduled.fire.time";
  public static final String COMPLETED_EXECUTION_TIME = "quartz.job.completed.execution.time";
  public static final String RECOVERED = "quartz.job.recovered";

  public static final class ACTIONS {
    public static final String PAUSE = "quartz.job.action.pause";
    public static final String RESUME = "quartz.job.action.resume";
    public static final String DELETE = "quartz.job.action.delete";
    public static final String TRIGGER = "quartz.job.action.trigger";
    public static final String VIEW_TRIGGERS = "quartz.job.action.view.triggers";
    // PageAction labels
    public static final String PAUSE_JOB = "quartz.job.action.pause.job";
    public static final String RESUME_JOB = "quartz.job.action.resume.job";
    public static final String TRIGGER_JOB = "quartz.job.action.trigger.job";
    public static final String DELETE_JOB = "quartz.job.action.delete.job";
    public static final String OPERACOES = "quartz.job.action.operacoes";
  }

  public static final class STATE {
    public static final String BLOCKED = "BLOCKED";
    public static final String COMPLETE = "COMPLETE";
    public static final String ERROR = "ERROR";
    public static final String NONE = "NONE";
    public static final String NORMAL = "NORMAL";
    public static final String PAUSED = "PAUSED";
    public static final String WAITING = "WAITING";
    public static final String ACQUIRED = "ACQUIRED";
    public static final String EXECUTING = "EXECUTING";
    public static final String BLOCKED_MARKED_FOR_ABORT = "BLOCKED_MARKED_FOR_ABORT";
  }

  // Trigger
  public static final String TRIGGER_NAME = "quartz.trigger.name";
  public static final String TRIGGER_GROUP = "quartz.trigger.group";
  public static final String TRIGGER_STATE = "quartz.trigger.state";
  public static final String TRIGGER_TYPE = "quartz.trigger.type";
  public static final String CALENDAR_NAME = "quartz.trigger.calendar.name";
  public static final String NEXT_FIRE_TIME = "quartz.trigger.next.fire.time";
  public static final String PREV_FIRE_TIME = "quartz.trigger.prev.fire.time";
  public static final String START_TIME = "quartz.trigger.start.time";
  public static final String END_TIME = "quartz.trigger.end.time";
  public static final String MISFIRE_INSTR = "quartz.trigger.misfire.instr";
  public static final String PRIORITY = "quartz.trigger.priority";
  public static final String REPEAT_COUNT = "quartz.trigger.repeat.count";
  public static final String REPEAT_INTERVAL = "quartz.trigger.repeat.interval";
  public static final String TIMES_TRIGGERED = "quartz.trigger.times.triggered";
  public static final String FINAL_FIRE_TIME = "quartz.trigger.final.fire.time";

  public static final class TRIGGER_STATE {
    public static final String BLOCKED = "BLOCKED";
    public static final String COMPLETE = "COMPLETE";
    public static final String ERROR = "ERROR";
    public static final String NONE = "NONE";
    public static final String NORMAL = "NORMAL";
    public static final String PAUSED = "PAUSED";
    public static final String WAITING = "WAITING";
    public static final String ACQUIRED = "ACQUIRED";
  }

  public static final class VALIDATION {
    public static final String JOB_NAME_REQUIRED = "validation.quartz.job.name.required";
    public static final String JOB_GROUP_REQUIRED = "validation.quartz.job.group.required";
  }
}
