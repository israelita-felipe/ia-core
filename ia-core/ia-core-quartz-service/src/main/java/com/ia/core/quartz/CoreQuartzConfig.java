package com.ia.core.quartz;

import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.JobExecutionContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.ia.core.quartz.model.QuartzModel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CoreQuartzConfig {

  public static final String PROP_SCHEDULER_INSTANCE_NAME = "org.quartz.scheduler.instanceName";
  public static final String PROP_SCHEDULER_INSTANCE_ID = "org.quartz.scheduler.instanceId";
  public static final String PROP_THREADPOOL_THREAD_COUNT = "org.quartz.threadPool.threadCount";
  public static final String PROP_JOBSTORE_IS_CLUSTERED = "org.quartz.jobStore.isClustered";
  public static final String PROP_JOBSTORE_CLUSTER_CHECKIN_INTERVAL = "org.quartz.jobStore.clusterCheckinInterval";
  public static final String PROP_JOBSTORE_DRIVER_DELEGATE_CLASS = "org.quartz.jobStore.driverDelegateClass";
  public static final String PROP_JOBSTORE_TABLE_PREFIX = "org.quartz.jobStore.tablePrefix";
  public static final String PROP_JOBSTORE_CLASS = "org.quartz.jobStore.class";

  private final CoreSpringBeanJobFactory springBeanJobFactory;

  @Bean
  SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {
    log.info("Configurando SchedulerFactoryBean");
    SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
    springBeanJobFactory
        .setContextInitializer(this::createJobContextInitializar);
    schedulerFactoryBean.setJobFactory(springBeanJobFactory);
    schedulerFactoryBean.setDataSource(dataSource);
    schedulerFactoryBean.setQuartzProperties(quartzProperties());
    schedulerFactoryBean.setOverwriteExistingJobs(true);
    schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
    return schedulerFactoryBean;
  }

  public void createJobContextInitializar(JobExecutionContext context) {

  }

  public Properties quartzProperties() {
    Properties properties = new Properties();
    properties.setProperty(PROP_SCHEDULER_INSTANCE_NAME,
                           "CoreQuartzScheduler");
    properties.setProperty(PROP_SCHEDULER_INSTANCE_ID, "AUTO");
    properties.setProperty(PROP_THREADPOOL_THREAD_COUNT, "5");
    properties.setProperty(PROP_JOBSTORE_IS_CLUSTERED, "true");
    properties.setProperty(PROP_JOBSTORE_CLUSTER_CHECKIN_INTERVAL, "2000");

    // properties.setProperty(PROP_JOBSTORE_CLASS,
    // "org.quartz.impl.jdbcjobstore.JobStoreTX");

    properties.setProperty(PROP_JOBSTORE_DRIVER_DELEGATE_CLASS,
                           "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
    properties.setProperty(PROP_JOBSTORE_TABLE_PREFIX,
                           QuartzModel.QUARTZ_SCHEMA + "."
                               + QuartzModel.QUARTZ_TABLE_PREFIX);

    return properties;
  }

}
