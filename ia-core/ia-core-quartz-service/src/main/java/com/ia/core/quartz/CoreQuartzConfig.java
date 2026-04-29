package com.ia.core.quartz;

import com.ia.core.quartz.model.QuartzModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configuração principal do Quartz Scheduler para a aplicação.
 * <p>
 * Esta classe define as configurações necessárias para inicializar o scheduler
 * Quartz, incluindo:
 * <ul>
 * <li>Configurações do scheduler (nome, ID)</li>
 * <li>Configurações do pool de threads</li>
 * <li>Configurações do job store (clusterizado ou não)</li>
 * <li>Factory para criação de jobs</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see SchedulerFactoryBean
 * @see CoreSpringBeanJobFactory
 */
@Slf4j
@RequiredArgsConstructor
public class CoreQuartzConfig {

  /**
   * Propriedade para nome do scheduler.
   */
  public static final String PROP_SCHEDULER_INSTANCE_NAME = "org.quartz.scheduler.instanceName";
  /**
   * Propriedade para ID do scheduler.
   */
  public static final String PROP_SCHEDULER_INSTANCE_ID = "org.quartz.scheduler.instanceId";
  /**
   * Propriedade para número de threads do pool.
   */
  public static final String PROP_THREADPOOL_THREAD_COUNT = "org.quartz.threadPool.threadCount";
  /**
   * Propriedade para habilitação de cluster.
   */
  public static final String PROP_JOBSTORE_IS_CLUSTERED = "org.quartz.jobStore.isClustered";
  /**
   * Propriedade para intervalo de checkin em cluster.
   */
  public static final String PROP_JOBSTORE_CLUSTER_CHECKIN_INTERVAL = "org.quartz.jobStore.clusterCheckinInterval";
  /**
   * Propriedade para classe do delegate do driver JDBC.
   */
  public static final String PROP_JOBSTORE_DRIVER_DELEGATE_CLASS = "org.quartz.jobStore.driverDelegateClass";
  /**
   * Propriedade para prefixo das tabelas.
   */
  public static final String PROP_JOBSTORE_TABLE_PREFIX = "org.quartz.jobStore.tablePrefix";
  /**
   * Propriedade para classe do job store.
   */
  public static final String PROP_JOBSTORE_CLASS = "org.quartz.jobStore.class";

  /**
   * Factory para criação de instâncias de jobs com injeção de dependências
   * Spring.
   */
  private final CoreSpringBeanJobFactory springBeanJobFactory;

  /**
   * Cria o {@link SchedulerFactoryBean} que inicializa o Quartz Scheduler.
   * <p>
   * Este método configura o factory com:
   * <ul>
   * <li>DataSource para persistência</li>
   * <li>Propriedades do Quartz</li>
   * <li>JobFactory com suporte a Spring DI</li>
   * <li>Sobreposição de jobs existentes</li>
   * </ul>
   *
   * @param dataSource Fonte de dados para o Quartz usar na persistência
   * @return SchedulerFactoryBean configurado
   * @since 1.0.0
   */
  @Bean
  public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {
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

  /**
   * Inicializador de contexto de execução de job.
   * <p>
   * Este método pode ser sobrescrito para adicionar customização ao contexto de
   * execução dos jobs.
   *
   * @param context Contexto de execução do job
   * @since 1.0.0
   */
  public void createJobContextInitializar(JobExecutionContext context) {

  }

  /**
   * Configura as propriedades do Quartz.
   * <p>
   * As propriedades configuradas incluem:
   * <ul>
   * <li>Nome do scheduler: CoreQuartzScheduler</li>
   * <li>ID do scheduler: AUTO</li>
   * <li>Threads do pool: 5</li>
   * <li>JobStore clusterizado: true</li>
   * <li>Intervalo de checkin: 2000ms</li>
   * <li>Driver delegate: StdJDBCDelegate</li>
   * </ul>
   *
   * @return Properties configurado com as propriedades do Quartz
   * @since 1.0.0
   */
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
                           "org.quartz.impl.jdbcjobstore.HSQLDBDelegate");
    properties
        .setProperty(PROP_JOBSTORE_TABLE_PREFIX,
                     QuartzModel.SCHEMA + "." + QuartzModel.TABLE_PREFIX);

    return properties;
  }

}
