package com.ia.core.quartz;

import java.util.function.Consumer;

import org.quartz.JobExecutionContext;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

import com.ia.core.quartz.service.AbstractJob;

import lombok.Setter;

@Component
public class CoreSpringBeanJobFactory
  extends SpringBeanJobFactory
  implements ApplicationContextAware {

  private AutowireCapableBeanFactory beanFactory;
  @Setter
  private Consumer<JobExecutionContext> contextInitializer;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    this.beanFactory = applicationContext.getAutowireCapableBeanFactory();
  }

  @Override
  protected Object createJobInstance(TriggerFiredBundle bundle)
    throws Exception {
    AbstractJob job = (AbstractJob) super.createJobInstance(bundle);
    beanFactory.autowireBean(job);
    job.initContext(contextInitializer);
    return job;
  }

}
