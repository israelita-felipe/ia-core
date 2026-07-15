package com.ia.core.quartz.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Propriedades de configuração centralizada para o Quartz Scheduler.
 *
 * <p>Permite configuração via YAML (application.yml) e programática.
 * Cada propriedade do Quartz pode ser configurada individualmente.</p>
 *
 * <p>Estrutura de configuração:</p>
 * <pre>
 * ia-core:
 *   quartz:
 *     scheduler:
 *       instance-name: "CoreQuartzScheduler"
 *       instance-id: "AUTO"
 *       thread-name: "CoreQuartzSchedulerThread"
 *       make-scheduler-thread-daemon: false
 *       threads-inherit-context-class-loader: false
 *       idle-wait-time: 30000ms
 *       db-failure-retry-interval: 15000ms
 *       skip-update-check: false
 *       batch-trigger-acquisition-max-count: 1
 *     thread-pool:
 *       class: "org.quartz.simpl.SimpleThreadPool"
 *       thread-count: 5
 *       thread-priority: 5
 *       make-threads-daemons: false
 *     job-store:
 *       class: "org.quartz.impl.jdbcjobstore.JobStoreTX"
 *       driver-delegate-class: "org.quartz.impl.jdbcjobstore.StdJDBCDelegate"
 *       data-source: "quartzDS"
 *       table-prefix: "QUARTZ.QRTZ_"
 *       use-properties: false
 *       misfire-threshold: 60000ms
 *       is-clustered: false
 *       cluster-checkin-interval: 2000ms
 *       max-misfires-to-handle-at-a-time: 20
 *       dont-set-auto-commit-false: false
 *       acquire-triggers-within-lock: false
 * </pre>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "ia-core.quartz")
public class QuartzProperties {

    /**
     * Configurações do scheduler principal.
     */
    private SchedulerConfig scheduler = new SchedulerConfig();

    /**
     * Configurações do thread pool.
     */
    private ThreadPoolConfig threadPool = new ThreadPoolConfig();

    /**
     * Configurações do job store.
     */
    private JobStoreConfig jobStore = new JobStoreConfig();

    /**
     * Configurações de datasource para JobStore JDBC.
     * Mapa de nomes de datasource para suas configurações.
     */
    private Map<String, DataSourceConfig> dataSource = new HashMap<>();

    /**
     * Configurações adicionais como listeners e plugins.
     */
    private PluginsConfig plugins = new PluginsConfig();

    @Getter
    @Setter
    public static class SchedulerConfig {
        /**
         * Nome da instância do scheduler.
         */
        private String instanceName = "CoreQuartzScheduler";

        /**
         * ID da instância do scheduler. Use "AUTO" para geração automática.
         */
        private String instanceId = "AUTO";

        /**
         * Classe do gerador de ID de instância.
         * Default: org.quartz.simpl.SimpleInstanceIdGenerator
         */
        private String instanceIdGeneratorClass;

        /**
         * Nome da thread do scheduler.
         */
        private String threadName;

        /**
         * Se a thread principal do scheduler deve ser daemon.
         */
        private boolean makeSchedulerThreadDaemon = false;

        /**
         * Se as threads herdam o ClassLoader do inicializador.
         */
        private boolean threadsInheritContextClassLoaderOfInitializer = false;

        /**
         * Tempo de espera (em milissegundos) quando o scheduler está idle.
         */
        private Duration idleWaitTime = Duration.ofMillis(30000);

        /**
         * Intervalo de retry (em milissegundos) para falhas de conexão com o JobStore.
         */
        private Duration dbFailureRetryInterval = Duration.ofMillis(15000);

        /**
         * Classe do ClassLoadHelper.
         * Default: org.quartz.simpl.CascadingClassLoadHelper
         */
        private String classLoadHelperClass;

        /**
         * Classe do JobFactory.
         */
        private String jobFactoryClass;

        /**
         * URL da transação do usuário JTA.
         */
        private String userTransactionUrl;

        /**
         * Se deve envolver execução de jobs em transação do usuário.
         */
        private boolean wrapJobExecutionInUserTransaction = false;

        /**
         * Se deve pular a verificação de atualização do Quartz.
         */
        private boolean skipUpdateCheck = true;

        /**
         * Máximo de triggers que podem ser adquiridos de uma vez.
         */
        private int batchTriggerAcquisitionMaxCount = 1;

        /**
         * Janela de tempo (em milissegundos) para disparo antecipado em batch.
         */
        private long batchTriggerAcquisitionFireAheadTimeWindow = 0;

        /**
         * Se o scheduler deve iniciar automaticamente.
         */
        private boolean autoStartup = true;

        /**
         * Se jobs configurados devem sobrescrever jobs existentes.
         */
        private boolean overwriteExistingJobs = true;

        /**
         * Se deve aguardar jobs em execução completarem no shutdown.
         */
        private boolean waitForJobsToCompleteOnShutdown = true;

        /**
         * Atraso antes do início do scheduler.
         */
        private Duration startupDelay = Duration.ZERO;
    }

    @Getter
    @Setter
    public static class ThreadPoolConfig {
        /**
         * Classe do thread pool.
         * Default: org.quartz.simpl.SimpleThreadPool
         */
        private String threadPoolClass = "org.quartz.simpl.SimpleThreadPool";

        /**
         * Número de threads no pool.
         */
        private int threadCount = 5;

        /**
         * Prioridade das threads (1-10).
         */
        private int threadPriority = 5;

        /**
         * Se as threads devem ser daemons.
         */
        private boolean makeThreadsDaemons = false;

        /**
         * Classe do thread pool customizado (se diferente do SimpleThreadPool).
         */
        private String customThreadPoolClass;
    }

    @Getter
    @Setter
    public static class JobStoreConfig {
        /**
         * Classe do JobStore.
         * Default para JDBC: org.quartz.impl.jdbcjobstore.JobStoreTX
         * Default para memória: org.quartz.simpl.RAMJobStore
         */
        private String jobStoreClass;

        /**
         * Classe do delegate do driver JDBC.
         * Default: org.quartz.impl.jdbcjobstore.StdJDBCDelegate
         */
        private String driverDelegateClass = "org.quartz.impl.jdbcjobstore.StdJDBCDelegate";

        /**
         * Nome do datasource configurado.
         * Default: dataSource padrão do Spring
         */
        private String dataSourceName;

        /**
         * Prefixo das tabelas do Quartz.
         * Default: QRTZ_
         */
        private String tablePrefix;

        /**
         * Se deve usar properties (não serializes objetos) no JobStore.
         */
        private boolean useProperties = false;

        /**
         * Threshold de misfire em milissegundos.
         */
        private Duration misfireThreshold = Duration.ofMillis(60000);

        /**
         * Se o JobStore está em modo cluster.
         */
        private boolean isClustered = false;

        /**
         * Intervalo de check-in do cluster em milissegundos.
         */
        private Duration clusterCheckinInterval = Duration.ofMillis(2000);

        /**
         * Máximo de misfires para tratar de cada vez.
         */
        private int maxMisfiresToHandleAtATime = 20;

        /**
         * Se não deve setar auto-commit como false.
         */
        private boolean dontSetAutoCommitFalse = false;

        /**
         * SQL para seleção com lock.
         */
        private String selectWithLockSql;

        /**
         * Se deve usar isolamento de transação serializável.
         */
        private boolean txIsolationLevelSerializable = false;

        /**
         * Se deve adquirir triggers dentro do lock.
         */
        private boolean acquireTriggersWithinLock = false;

        /**
         * Classe do lock handler customizado.
         */
        private String lockHandlerClass;

        /**
         * String de inicialização do driver delegate.
         */
        private String driverDelegateInitString;

        /**
         * Se deve usar statements enhanced (para bancos específicos).
         */
        private boolean useEnhancedStatements = false;
    }

    @Getter
    @Setter
    public static class DataSourceConfig {
        /**
         * Driver JDBC.
         */
        private String driver;

        /**
         * URL de conexão.
         */
        private String url;

        /**
         * Usuário do banco.
         */
        private String user;

        /**
         * Senha do banco.
         */
        private String password;

        /**
         * Máximo de conexões no pool.
         */
        private int maxConnections = 5;

        /**
         * Query de validação de conexão.
         */
        private String validationQuery;

        /**
         * Provider do datasource (se diferente do padrão).
         */
        private String provider;

        /**
         * URL JNDI (se usando JNDI).
         */
        private String jndiUrl;
    }

    @Getter
    @Setter
    public static class PluginsConfig {
        /**
         * Configurações de listeners.
         */
        private Map<String, ListenerConfig> listeners = new HashMap<>();

        /**
         * Configurações de plugins.
         */
        private Map<String, PluginConfig> plugins = new HashMap<>();
    }

    @Getter
    @Setter
    public static class ListenerConfig {
        /**
         * Classe do listener.
         */
        private String className;
    }

    @Getter
    @Setter
    public static class PluginConfig {
        /**
         * Classe do plugin.
         */
        private String className;

        /**
         * Propriedades específicas do plugin.
         */
        private Map<String, String> properties = new HashMap<>();
    }
}