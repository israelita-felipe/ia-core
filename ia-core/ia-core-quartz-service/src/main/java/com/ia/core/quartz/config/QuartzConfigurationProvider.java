package com.ia.core.quartz.config;

import com.ia.core.model.configuracao.TipoConfiguracao;
import com.ia.core.quartz.model.QuartzModel;
import com.ia.core.service.configuracao.ConfigurationProvider;
import com.ia.core.service.configuracao.dto.ConfiguracaoSistemaDTO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Provider de configuração para o módulo Quartz.
 * <p>
 * Fornece configurações específicas para o scheduler Quartz,
 * incluindo thread pool, job store e propriedades do scheduler.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
@EnableConfigurationProperties(QuartzProperties.class)
public class QuartzConfigurationProvider implements ConfigurationProvider {


    @Getter
    private final QuartzProperties quartzProperties;
    @Getter
    private final Properties properties;

    @Getter
    private List<ConfiguracaoSistemaDTO<?>> configurations;

    public QuartzConfigurationProvider(QuartzProperties properties) {
        this.quartzProperties = properties;
        this.properties = createProperties();
        this.configurations = buildConfigurations();
    }

    @Override
    public List<ConfiguracaoSistemaDTO<?>> getConfigurations() {
        return configurations;
    }

    @Override
    public String getModulo() {
        return QuartzModel.NAME;
    }

    @Override
    public void validar(ConfiguracaoSistemaDTO<?> config) {

        // Validações específicas para configurações do Quartz
        switch (config.getChave()) {
            case QuartzPropertiesConstants.THREAD_POOL_THREAD_COUNT:
                if (config.getValor() != null) {
                    try {
                        int threadCount = Integer.parseInt(config.getValor());
                        if (threadCount < 1 || threadCount > 50) {
                            throw new IllegalArgumentException("Thread count deve estar entre 1 e 50");
                        }
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Thread count inválido: " + config.getValor());
                    }
                }
                break;
            case QuartzPropertiesConstants.SCHEDULER_INSTANCE_NAME,
                 QuartzPropertiesConstants.SCHEDULER_INSTANCE_ID,
                 QuartzPropertiesConstants.SCHEDULER_MAKE_THREAD_DAEMON,
                 QuartzPropertiesConstants.THREAD_POOL_MAKE_THREADS_DAEMONS,
                 QuartzPropertiesConstants.JOB_STORE_IS_CLUSTERED,
                 QuartzPropertiesConstants.JOB_STORE_TABLE_PREFIX,
                 QuartzPropertiesConstants.JOB_STORE_DRIVER_DELEGATE_CLASS:
                if (config.getValor() != null) {
                    if (config.getChave().endsWith("isClustered")
                        || config.getChave().endsWith("makeThreadsDaemons")
                        || config.getChave().endsWith("makeSchedulerThreadDaemon")) {
                        Boolean.parseBoolean(config.getValor());
                    }
                }
                break;
            default:
                break;
        }
        log.debug("Validação concluída para configuração Quartz: {}", config.getChave());
    }

    @Override
    public void aplicar(ConfiguracaoSistemaDTO<?> config) {
        validar(config);

        String chave = config.getChave();
        String valor = config.getValor();

        int index = configurations.indexOf(config);
        if (index != -1) {
            configurations.set(index, config);
        } else {
            configurations.add(config);
        }

        QuartzProperties.SchedulerConfig scheduler = quartzProperties.getScheduler();
        QuartzProperties.ThreadPoolConfig threadPool = quartzProperties.getThreadPool();
        QuartzProperties.JobStoreConfig jobStore = quartzProperties.getJobStore();

        switch (chave) {
            case QuartzPropertiesConstants.SCHEDULER_INSTANCE_NAME:
                scheduler.setInstanceName(valor);
                break;
            case QuartzPropertiesConstants.SCHEDULER_INSTANCE_ID:
                scheduler.setInstanceId(valor);
                break;
            case QuartzPropertiesConstants.SCHEDULER_INSTANCE_ID_GENERATOR_CLASS:
                scheduler.setInstanceIdGeneratorClass(valor);
                break;
            case QuartzPropertiesConstants.SCHEDULER_THREAD_NAME:
                scheduler.setThreadName(valor);
                break;
            case QuartzPropertiesConstants.SCHEDULER_MAKE_THREAD_DAEMON:
                scheduler.setMakeSchedulerThreadDaemon(Boolean.parseBoolean(valor));
                break;
            case QuartzPropertiesConstants.SCHEDULER_THREADS_INHERIT_CONTEXT:
                scheduler.setThreadsInheritContextClassLoaderOfInitializer(Boolean.parseBoolean(valor));
                break;
            case QuartzPropertiesConstants.SCHEDULER_IDLE_WAIT_TIME:
                scheduler.setIdleWaitTime(Duration.ofMillis(Long.parseLong(valor)));
                break;
            case QuartzPropertiesConstants.SCHEDULER_DB_FAILURE_RETRY_INTERVAL:
                scheduler.setDbFailureRetryInterval(Duration.ofMillis(Long.parseLong(valor)));
                break;
            case QuartzPropertiesConstants.SCHEDULER_CLASS_LOAD_HELPER_CLASS:
                scheduler.setClassLoadHelperClass(valor);
                break;
            case QuartzPropertiesConstants.SCHEDULER_JOB_FACTORY_CLASS:
                scheduler.setJobFactoryClass(valor);
                break;
            case QuartzPropertiesConstants.SCHEDULER_USER_TRANSACTION_URL:
                scheduler.setUserTransactionUrl(valor);
                break;
            case QuartzPropertiesConstants.SCHEDULER_WRAP_JOB_IN_TRANSACTION:
                scheduler.setWrapJobExecutionInUserTransaction(Boolean.parseBoolean(valor));
                break;
            case QuartzPropertiesConstants.SCHEDULER_SKIP_UPDATE_CHECK:
                scheduler.setSkipUpdateCheck(Boolean.parseBoolean(valor));
                break;
            case QuartzPropertiesConstants.SCHEDULER_BATCH_TRIGGER_MAX_COUNT:
                scheduler.setBatchTriggerAcquisitionMaxCount(Integer.parseInt(valor));
                break;
            case QuartzPropertiesConstants.SCHEDULER_BATCH_TRIGGER_FIRE_AHEAD_TIME_WINDOW:
                scheduler.setBatchTriggerAcquisitionFireAheadTimeWindow(Long.parseLong(valor));
                break;
            case QuartzPropertiesConstants.THREAD_POOL_CLASS:
                threadPool.setThreadPoolClass(valor);
                break;
            case QuartzPropertiesConstants.THREAD_POOL_THREAD_COUNT:
                threadPool.setThreadCount(Integer.parseInt(valor));
                break;
            case QuartzPropertiesConstants.THREAD_POOL_THREAD_PRIORITY:
                threadPool.setThreadPriority(Integer.parseInt(valor));
                break;
            case QuartzPropertiesConstants.THREAD_POOL_MAKE_THREADS_DAEMONS:
                threadPool.setMakeThreadsDaemons(Boolean.parseBoolean(valor));
                break;
            case QuartzPropertiesConstants.JOB_STORE_CLASS:
                jobStore.setJobStoreClass(valor);
                break;
            case QuartzPropertiesConstants.JOB_STORE_DRIVER_DELEGATE_CLASS:
                jobStore.setDriverDelegateClass(valor);
                break;
            case QuartzPropertiesConstants.JOB_STORE_DATA_SOURCE:
                jobStore.setDataSourceName(valor);
                break;
            case QuartzPropertiesConstants.JOB_STORE_TABLE_PREFIX:
                jobStore.setTablePrefix(valor);
                break;
            case QuartzPropertiesConstants.JOB_STORE_USE_PROPERTIES:
                jobStore.setUseProperties(Boolean.parseBoolean(valor));
                break;
            case QuartzPropertiesConstants.JOB_STORE_MISFIRE_THRESHOLD:
                jobStore.setMisfireThreshold(Duration.ofMillis(Long.parseLong(valor)));
                break;
            case QuartzPropertiesConstants.JOB_STORE_IS_CLUSTERED:
                jobStore.setClustered(Boolean.parseBoolean(valor));
                break;
            case QuartzPropertiesConstants.JOB_STORE_CLUSTER_CHECKIN_INTERVAL:
                jobStore.setClusterCheckinInterval(Duration.ofMillis(Long.parseLong(valor)));
                break;
            case QuartzPropertiesConstants.JOB_STORE_MAX_MISFIRES:
                jobStore.setMaxMisfiresToHandleAtATime(Integer.parseInt(valor));
                break;
            case QuartzPropertiesConstants.JOB_STORE_DONT_SET_AUTO_COMMIT_FALSE:
                jobStore.setDontSetAutoCommitFalse(Boolean.parseBoolean(valor));
                break;
            case QuartzPropertiesConstants.JOB_STORE_SELECT_WITH_LOCK_SQL:
                jobStore.setSelectWithLockSql(valor);
                break;
            case QuartzPropertiesConstants.JOB_STORE_TX_ISOLATION_SERIALIZABLE:
                jobStore.setTxIsolationLevelSerializable(Boolean.parseBoolean(valor));
                break;
            case QuartzPropertiesConstants.JOB_STORE_ACQUIRE_TRIGGERS_WITHIN_LOCK:
                jobStore.setAcquireTriggersWithinLock(Boolean.parseBoolean(valor));
                break;
            case QuartzPropertiesConstants.JOB_STORE_LOCK_HANDLER_CLASS:
                jobStore.setLockHandlerClass(valor);
                break;
            case QuartzPropertiesConstants.JOB_STORE_DRIVER_DELEGATE_INIT_STRING:
                jobStore.setDriverDelegateInitString(valor);
                break;
            case QuartzPropertiesConstants.JOB_STORE_USE_ENHANCED_STATEMENTS:
                jobStore.setUseEnhancedStatements(Boolean.parseBoolean(valor));
                break;
            default:
                log.debug("Chave de configuração Quartz não mapeada para aplicação: {}", chave);
                return;
        }

        this.properties.clear();
        this.properties.putAll(createProperties());

        log.info("Configuração Quartz aplicada: chave={}, valor={}", chave, valor);
    }

    private List<ConfiguracaoSistemaDTO<?>> buildConfigurations() {
        List<ConfiguracaoSistemaDTO<?>> configs = new ArrayList<>();

        QuartzProperties.SchedulerConfig scheduler = quartzProperties.getScheduler();
        QuartzProperties.ThreadPoolConfig threadPool = quartzProperties.getThreadPool();
        QuartzProperties.JobStoreConfig jobStore = quartzProperties.getJobStore();

        // Scheduler
        add(configs, QuartzPropertiesConstants.SCHEDULER_INSTANCE_NAME, scheduler.getInstanceName(), TipoConfiguracao.STRING, "Scheduler", "Nome da instância do scheduler");
        add(configs, QuartzPropertiesConstants.SCHEDULER_INSTANCE_ID, scheduler.getInstanceId(), TipoConfiguracao.STRING, "Scheduler", "ID da instância do scheduler");
        add(configs, QuartzPropertiesConstants.SCHEDULER_INSTANCE_ID_GENERATOR_CLASS, scheduler.getInstanceIdGeneratorClass(), TipoConfiguracao.STRING, "Scheduler", "Classe do gerador de ID de instância");
        add(configs, QuartzPropertiesConstants.SCHEDULER_THREAD_NAME, scheduler.getThreadName(), TipoConfiguracao.STRING, "Scheduler", "Nome da thread do scheduler");
        add(configs, QuartzPropertiesConstants.SCHEDULER_MAKE_THREAD_DAEMON, scheduler.isMakeSchedulerThreadDaemon(), TipoConfiguracao.BOOLEAN, "Scheduler", "Se a thread principal do scheduler deve ser daemon");
        add(configs, QuartzPropertiesConstants.SCHEDULER_THREADS_INHERIT_CONTEXT, scheduler.isThreadsInheritContextClassLoaderOfInitializer(), TipoConfiguracao.BOOLEAN, "Scheduler", "Se as threads herdam o ClassLoader do inicializador");
        add(configs, QuartzPropertiesConstants.SCHEDULER_IDLE_WAIT_TIME, scheduler.getIdleWaitTime(), TipoConfiguracao.STRING, "Scheduler", "Tempo de espera idle em ms");
        add(configs, QuartzPropertiesConstants.SCHEDULER_DB_FAILURE_RETRY_INTERVAL, scheduler.getDbFailureRetryInterval(), TipoConfiguracao.STRING, "Scheduler", "Intervalo de retry para falhas de conexão em ms");
        add(configs, QuartzPropertiesConstants.SCHEDULER_CLASS_LOAD_HELPER_CLASS, scheduler.getClassLoadHelperClass(), TipoConfiguracao.STRING, "Scheduler", "Classe do ClassLoadHelper");
        add(configs, QuartzPropertiesConstants.SCHEDULER_JOB_FACTORY_CLASS, scheduler.getJobFactoryClass(), TipoConfiguracao.STRING, "Scheduler", "Classe do JobFactory");
        add(configs, QuartzPropertiesConstants.SCHEDULER_USER_TRANSACTION_URL, scheduler.getUserTransactionUrl(), TipoConfiguracao.STRING, "Scheduler", "URL da transação do usuário JTA");
        add(configs, QuartzPropertiesConstants.SCHEDULER_WRAP_JOB_IN_TRANSACTION, scheduler.isWrapJobExecutionInUserTransaction(), TipoConfiguracao.BOOLEAN, "Scheduler", "Se deve envolver execução de jobs em transação do usuário");
        add(configs, QuartzPropertiesConstants.SCHEDULER_SKIP_UPDATE_CHECK, scheduler.isSkipUpdateCheck(), TipoConfiguracao.BOOLEAN, "Scheduler", "Se deve pular a verificação de atualização do Quartz");
        add(configs, QuartzPropertiesConstants.SCHEDULER_BATCH_TRIGGER_MAX_COUNT, scheduler.getBatchTriggerAcquisitionMaxCount(), TipoConfiguracao.INTEGER, "Scheduler", "Máximo de triggers em batch");
        add(configs, QuartzPropertiesConstants.SCHEDULER_BATCH_TRIGGER_FIRE_AHEAD_TIME_WINDOW, scheduler.getBatchTriggerAcquisitionFireAheadTimeWindow(), TipoConfiguracao.INTEGER, "Scheduler", "Janela de disparo antecipado em batch em ms");

        // Thread Pool
        add(configs, QuartzPropertiesConstants.THREAD_POOL_CLASS, threadPool.getThreadPoolClass(), TipoConfiguracao.STRING, "Thread Pool", "Classe do thread pool");
        add(configs, QuartzPropertiesConstants.THREAD_POOL_THREAD_COUNT, threadPool.getThreadCount(), TipoConfiguracao.INTEGER, "Thread Pool", "Número de threads no pool");
        add(configs, QuartzPropertiesConstants.THREAD_POOL_THREAD_PRIORITY, threadPool.getThreadPriority(), TipoConfiguracao.INTEGER, "Thread Pool", "Prioridade das threads");
        add(configs, QuartzPropertiesConstants.THREAD_POOL_MAKE_THREADS_DAEMONS, threadPool.isMakeThreadsDaemons(), TipoConfiguracao.BOOLEAN, "Thread Pool", "Se as threads devem ser daemons");

        // Job Store
        add(configs, QuartzPropertiesConstants.JOB_STORE_CLASS, jobStore.getJobStoreClass(), TipoConfiguracao.STRING, "Job Store", "Classe do JobStore");
        add(configs, QuartzPropertiesConstants.JOB_STORE_DRIVER_DELEGATE_CLASS, jobStore.getDriverDelegateClass(), TipoConfiguracao.STRING, "Job Store", "Classe do delegate do driver JDBC");
        add(configs, QuartzPropertiesConstants.JOB_STORE_DATA_SOURCE, jobStore.getDataSourceName(), TipoConfiguracao.STRING, "Job Store", "Nome do datasource configurado");
        add(configs, QuartzPropertiesConstants.JOB_STORE_TABLE_PREFIX, jobStore.getTablePrefix(), TipoConfiguracao.STRING, "Job Store", "Prefixo das tabelas do Quartz");
        add(configs, QuartzPropertiesConstants.JOB_STORE_USE_PROPERTIES, jobStore.isUseProperties(), TipoConfiguracao.BOOLEAN, "Job Store", "Se deve usar properties no JobStore");
        add(configs, QuartzPropertiesConstants.JOB_STORE_MISFIRE_THRESHOLD, jobStore.getMisfireThreshold(), TipoConfiguracao.STRING, "Job Store", "Threshold de misfire em ms");
        add(configs, QuartzPropertiesConstants.JOB_STORE_IS_CLUSTERED, jobStore.isClustered(), TipoConfiguracao.BOOLEAN, "Job Store", "Se o JobStore está em modo cluster");
        add(configs, QuartzPropertiesConstants.JOB_STORE_CLUSTER_CHECKIN_INTERVAL, jobStore.getClusterCheckinInterval(), TipoConfiguracao.STRING, "Job Store", "Intervalo de check-in do cluster em ms");
        add(configs, QuartzPropertiesConstants.JOB_STORE_MAX_MISFIRES, jobStore.getMaxMisfiresToHandleAtATime(), TipoConfiguracao.INTEGER, "Job Store", "Máximo de misfires por processamento");
        add(configs, QuartzPropertiesConstants.JOB_STORE_DONT_SET_AUTO_COMMIT_FALSE, jobStore.isDontSetAutoCommitFalse(), TipoConfiguracao.BOOLEAN, "Job Store", "Se não deve setar auto-commit como false");
        add(configs, QuartzPropertiesConstants.JOB_STORE_SELECT_WITH_LOCK_SQL, jobStore.getSelectWithLockSql(), TipoConfiguracao.STRING, "Job Store", "SQL para seleção com lock");
        add(configs, QuartzPropertiesConstants.JOB_STORE_TX_ISOLATION_SERIALIZABLE, jobStore.isTxIsolationLevelSerializable(), TipoConfiguracao.BOOLEAN, "Job Store", "Se deve usar isolamento serializável");
        add(configs, QuartzPropertiesConstants.JOB_STORE_ACQUIRE_TRIGGERS_WITHIN_LOCK, jobStore.isAcquireTriggersWithinLock(), TipoConfiguracao.BOOLEAN, "Job Store", "Se deve adquirir triggers dentro do lock");
        add(configs, QuartzPropertiesConstants.JOB_STORE_LOCK_HANDLER_CLASS, jobStore.getLockHandlerClass(), TipoConfiguracao.STRING, "Job Store", "Classe do lock handler customizado");
        add(configs, QuartzPropertiesConstants.JOB_STORE_DRIVER_DELEGATE_INIT_STRING, jobStore.getDriverDelegateInitString(), TipoConfiguracao.STRING, "Job Store", "String de inicialização do driver delegate");
        add(configs, QuartzPropertiesConstants.JOB_STORE_USE_ENHANCED_STATEMENTS, jobStore.isUseEnhancedStatements(), TipoConfiguracao.BOOLEAN, "Job Store", "Se deve usar statements enhanced");

        return configs;
    }

    private void add(List<ConfiguracaoSistemaDTO<?>> configs, String chave, Object valor, TipoConfiguracao tipo, String categoria, String descricao) {
        configs.add(ConfiguracaoSistemaDTO.builder()
            .modulo(getModulo())
            .chave(chave)
            .valor(convertValue(valor))
            .tipo(tipo)
            .categoria(categoria)
            .descricao(descricao)
            .build());
    }

    private String convertValue(Object valor) {
        if (valor == null) {
            return "";
        }
        if (valor instanceof Duration duration) {
            return String.valueOf(duration.toMillis());
        }
        return String.valueOf(valor);
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
    protected Properties createProperties() {
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
