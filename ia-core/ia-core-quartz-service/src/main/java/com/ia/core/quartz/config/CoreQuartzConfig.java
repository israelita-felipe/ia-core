package com.ia.core.quartz.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
 * @see SchedulerFactoryBean
 * @see CoreSpringBeanJobFactory
 * @since 1.0.0
 */
@Slf4j
@EnableConfigurationProperties(QuartzProperties.class)
public class CoreQuartzConfig {

    /**
     * Factory para criação de instâncias de jobs com injeção de dependências
     * Spring.
     */
    private final CoreSpringBeanJobFactory springBeanJobFactory;

    /**
     * Propriedades de configuração do Quartz.
     */
    private final QuartzProperties quartzProperties;

    /**
     * Construtor com injeção de dependências.
     *
     * @param springBeanJobFactory factory para criação de jobs Spring
     * @param quartzProperties     propriedades de configuração do Quartz
     */
    public CoreQuartzConfig(CoreSpringBeanJobFactory springBeanJobFactory,
                            QuartzProperties quartzProperties) {
        this.springBeanJobFactory = springBeanJobFactory;
        this.quartzProperties = quartzProperties;
    }

    /**
     * Cria o {@link SchedulerFactoryBean} que inicializa o Quartz Scheduler.
     * <p>
     * Este método configura o factory com:
     * <ul>
     * <li>DataSource para persistência</li>
     * <li>Propriedades do Quartz mapeadas de {@link QuartzProperties}</li>
     * <li>JobFactory com suporte a Spring DI</li>
     * <li>Sobreposição de jobs existentes</li>
     * <li>Configuração de auto startup e shutdown</li>
     * </ul>
     *
     * @param dataSource Fonte de dados para o Quartz usar na persistência
     * @return SchedulerFactoryBean configurado
     * @since 1.0.0
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {
        log.info("Configurando SchedulerFactoryBean com QuartzProperties");
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        springBeanJobFactory.setContextInitializer(this::createJobContextInitializar);
        schedulerFactoryBean.setJobFactory(springBeanJobFactory);
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setQuartzProperties(quartzProperties());

        // Configurações que podem ser sobrescritas por subclasses
        schedulerFactoryBean.setOverwriteExistingJobs(quartzProperties.getScheduler().isOverwriteExistingJobs());
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(quartzProperties.getScheduler().isWaitForJobsToCompleteOnShutdown());
        schedulerFactoryBean.setAutoStartup(quartzProperties.getScheduler().isAutoStartup());

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
    protected void createJobContextInitializar(JobExecutionContext context) {
        // Hook para subclasses customizarem o contexto de execução

    }

    /**
     * Configura as propriedades do Quartz.
     * <p>
     * Este método constrói as propriedades a partir de {@link QuartzProperties}.
     * Classes que estendem {@link CoreQuartzConfig} podem sobrescrever este método
     * para personalizar ou adicionar propriedades adicionais.
     *
     * <p>Propriedades mapeadas incluem:</p>
     * <ul>
     * <li>{@code org.quartz.scheduler.instanceName} - nome do scheduler</li>
     * <li>{@code org.quartz.scheduler.instanceId} - ID da instância</li>
     * <li>{@code org.quartz.scheduler.skipUpdateCheck} - pular verificação de atualização</li>
     * <li>{@code org.quartz.scheduler.batchTriggerAcquisitionMaxCount} - máximo de triggers por batch</li>
     * <li>{@code org.quartz.threadPool.threadCount} - número de threads</li>
     * <li>{@code org.quartz.threadPool.threadPriority} - prioridade das threads</li>
     * <li>{@code org.quartz.threadPool.makeThreadsDaemons} - threads como daemon</li>
     * <li>{@code org.quartz.jobStore.class} - classe do JobStore</li>
     * <li>{@code org.quartz.jobStore.driverDelegateClass} - delegate JDBC</li>
     * <li>{@code org.quartz.jobStore.tablePrefix} - prefixo das tabelas</li>
     * <li>{@code org.quartz.jobStore.isClustered} - modo cluster</li>
     * <li>{@code org.quartz.jobStore.clusterCheckinInterval} - intervalo de cluster</li>
     * <li>{@code org.quartz.jobStore.misfireThreshold} - threshold de misfire</li>
     * </ul>
     *
     * @return Properties configurado com todas as propriedades do Quartz
     * @since 1.0.0
     */
    protected Properties quartzProperties() {
        Properties properties = new Properties();

        // Configurações do Scheduler
        QuartzProperties.SchedulerConfig scheduler = quartzProperties.getScheduler();
        if (scheduler.getInstanceName() != null) {
            properties.setProperty(QuartzPropertiesConstants.SCHEDULER_INSTANCE_NAME, scheduler.getInstanceName());
        }
        if (scheduler.getInstanceId() != null) {
            properties.setProperty(QuartzPropertiesConstants.SCHEDULER_INSTANCE_ID, scheduler.getInstanceId());
        }
        if (scheduler.getInstanceIdGeneratorClass() != null) {
            properties.setProperty(QuartzPropertiesConstants.SCHEDULER_INSTANCE_ID_GENERATOR_CLASS, scheduler.getInstanceIdGeneratorClass());
        }
        if (scheduler.getThreadName() != null) {
            properties.setProperty(QuartzPropertiesConstants.SCHEDULER_THREAD_NAME, scheduler.getThreadName());
        }
        properties.setProperty(QuartzPropertiesConstants.SCHEDULER_MAKE_THREAD_DAEMON,
            String.valueOf(scheduler.isMakeSchedulerThreadDaemon()));
        properties.setProperty(QuartzPropertiesConstants.SCHEDULER_THREADS_INHERIT_CONTEXT,
            String.valueOf(scheduler.isThreadsInheritContextClassLoaderOfInitializer()));

        if (scheduler.getIdleWaitTime() != null) {
            properties.setProperty(QuartzPropertiesConstants.SCHEDULER_IDLE_WAIT_TIME,
                String.valueOf(scheduler.getIdleWaitTime().toMillis()));
        }
        if (scheduler.getDbFailureRetryInterval() != null) {
            properties.setProperty(QuartzPropertiesConstants.SCHEDULER_DB_FAILURE_RETRY_INTERVAL,
                String.valueOf(scheduler.getDbFailureRetryInterval().toMillis()));
        }
        if (scheduler.getClassLoadHelperClass() != null) {
            properties.setProperty(QuartzPropertiesConstants.SCHEDULER_CLASS_LOAD_HELPER_CLASS, scheduler.getClassLoadHelperClass());
        }
        if (scheduler.getJobFactoryClass() != null) {
            properties.setProperty(QuartzPropertiesConstants.SCHEDULER_JOB_FACTORY_CLASS, scheduler.getJobFactoryClass());
        }
        if (scheduler.getUserTransactionUrl() != null) {
            properties.setProperty(QuartzPropertiesConstants.SCHEDULER_USER_TRANSACTION_URL, scheduler.getUserTransactionUrl());
        }
        properties.setProperty(QuartzPropertiesConstants.SCHEDULER_WRAP_JOB_IN_TRANSACTION,
            String.valueOf(scheduler.isWrapJobExecutionInUserTransaction()));
        properties.setProperty(QuartzPropertiesConstants.SCHEDULER_SKIP_UPDATE_CHECK,
            String.valueOf(scheduler.isSkipUpdateCheck()));
        properties.setProperty(QuartzPropertiesConstants.SCHEDULER_BATCH_TRIGGER_MAX_COUNT,
            String.valueOf(scheduler.getBatchTriggerAcquisitionMaxCount()));
        properties.setProperty(QuartzPropertiesConstants.SCHEDULER_BATCH_TRIGGER_FIRE_AHEAD_TIME_WINDOW,
            String.valueOf(scheduler.getBatchTriggerAcquisitionFireAheadTimeWindow()));

        // Configurações do ThreadPool
        QuartzProperties.ThreadPoolConfig threadPool = quartzProperties.getThreadPool();
        properties.setProperty(QuartzPropertiesConstants.THREAD_POOL_CLASS, threadPool.getThreadPoolClass());
        properties.setProperty(QuartzPropertiesConstants.THREAD_POOL_THREAD_COUNT, String.valueOf(threadPool.getThreadCount()));
        properties.setProperty(QuartzPropertiesConstants.THREAD_POOL_THREAD_PRIORITY, String.valueOf(threadPool.getThreadPriority()));
        properties.setProperty(QuartzPropertiesConstants.THREAD_POOL_MAKE_THREADS_DAEMONS,
            String.valueOf(threadPool.isMakeThreadsDaemons()));

        // Configurações do JobStore
        QuartzProperties.JobStoreConfig jobStore = quartzProperties.getJobStore();
        if (jobStore.getJobStoreClass() != null) {
            properties.setProperty(QuartzPropertiesConstants.JOB_STORE_CLASS, jobStore.getJobStoreClass());
        }
        if (jobStore.getDriverDelegateClass() != null) {
            properties.setProperty(QuartzPropertiesConstants.JOB_STORE_DRIVER_DELEGATE_CLASS, jobStore.getDriverDelegateClass());
        }
        if (jobStore.getDataSourceName() != null) {
            properties.setProperty(QuartzPropertiesConstants.JOB_STORE_DATA_SOURCE, jobStore.getDataSourceName());
        }
        if (jobStore.getTablePrefix() != null) {
            properties.setProperty(QuartzPropertiesConstants.JOB_STORE_TABLE_PREFIX, jobStore.getTablePrefix());
        }
        properties.setProperty(QuartzPropertiesConstants.JOB_STORE_USE_PROPERTIES, String.valueOf(jobStore.isUseProperties()));
        if (jobStore.getMisfireThreshold() != null) {
            properties.setProperty(QuartzPropertiesConstants.JOB_STORE_MISFIRE_THRESHOLD,
                String.valueOf(jobStore.getMisfireThreshold().toMillis()));
        }
        properties.setProperty(QuartzPropertiesConstants.JOB_STORE_IS_CLUSTERED, String.valueOf(jobStore.isClustered()));
        if (jobStore.getClusterCheckinInterval() != null) {
            properties.setProperty(QuartzPropertiesConstants.JOB_STORE_CLUSTER_CHECKIN_INTERVAL,
                String.valueOf(jobStore.getClusterCheckinInterval().toMillis()));
        }
        properties.setProperty(QuartzPropertiesConstants.JOB_STORE_MAX_MISFIRES,
            String.valueOf(jobStore.getMaxMisfiresToHandleAtATime()));
        properties.setProperty(QuartzPropertiesConstants.JOB_STORE_DONT_SET_AUTO_COMMIT_FALSE,
            String.valueOf(jobStore.isDontSetAutoCommitFalse()));
        if (jobStore.getSelectWithLockSql() != null) {
            properties.setProperty(QuartzPropertiesConstants.JOB_STORE_SELECT_WITH_LOCK_SQL, jobStore.getSelectWithLockSql());
        }
        properties.setProperty(QuartzPropertiesConstants.JOB_STORE_TX_ISOLATION_SERIALIZABLE,
            String.valueOf(jobStore.isTxIsolationLevelSerializable()));
        properties.setProperty(QuartzPropertiesConstants.JOB_STORE_ACQUIRE_TRIGGERS_WITHIN_LOCK,
            String.valueOf(jobStore.isAcquireTriggersWithinLock()));
        if (jobStore.getLockHandlerClass() != null) {
            properties.setProperty(QuartzPropertiesConstants.JOB_STORE_LOCK_HANDLER_CLASS, jobStore.getLockHandlerClass());
        }
        if (jobStore.getDriverDelegateInitString() != null) {
            properties.setProperty(QuartzPropertiesConstants.JOB_STORE_DRIVER_DELEGATE_INIT_STRING, jobStore.getDriverDelegateInitString());
        }
        properties.setProperty(QuartzPropertiesConstants.JOB_STORE_USE_ENHANCED_STATEMENTS,
            String.valueOf(jobStore.isUseEnhancedStatements()));

        return properties;
    }

}
