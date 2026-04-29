package com.ia.core.quartz;

import com.ia.core.quartz.service.AbstractJob;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * Fábrica de jobs do Spring Bean com injeção de dependências.
 * <p>
 * Estende {@link SpringBeanJobFactory} para fornecer injeção de dependências
 * Spring em jobs do Quartz, permitindo que jobs recebam beans Spring
 * via injeção automática.
 *
 * @author Israel Araújo
 * @since 1.0
 * @see SpringBeanJobFactory
 * @see ApplicationContextAware
 */
@Slf4j
@Component
public class CoreSpringBeanJobFactory
  extends SpringBeanJobFactory
  implements ApplicationContextAware {

  /**
   * Factory de beans do Spring para injeção de dependências.
   */
  private AutowireCapableBeanFactory beanFactory;

  /**
   * Inicializador de contexto para jobs.
   */
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
