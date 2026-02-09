package com.ia.core.quartz.service.model.scheduler;

/**
 * @author Israel Araújo
 */
@SuppressWarnings("javadoc")
public class SchedulerConfigTranslator {
  public static final class HELP {
    public static final String SCHEDULER_CONFIG = "scheduler.config.help";
    public static final String PERIODICIDADE = "periodicidade.help";
    public static final String JOB_CLASS_NAME = "job.class.name.help";
  }

  public static final String SCHEDULER_CONFIG_CLASS = SchedulerConfigDTO.class
      .getCanonicalName();
  public static final String SCHEDULER_CONFIG = "scheduler.config";
  public static final String PERIODICIDADE = "periodicidade";
  public static final String JOB_CLASS_NAME = "job.class.name";

  public static final class VALIDATION {
    public static final String JOB_CLASS_NAME_REQUIRED = "validation.scheduler.jobClassName.required";
    public static final String JOB_CLASS_NAME_PATTERN = "validation.scheduler.jobClassName.pattern";
    public static final String PERIODICIDADE_REQUIRED = "validation.scheduler.periodicidade.required";
  }
}
