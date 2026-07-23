package com.ia.core.quartz.config;

/**
 * Constantes para as chaves de propriedades do Quartz Scheduler.
 * <p>
 * Estas constantes são usadas internamente por {@link QuartzProperties}
 * e podem ser usadas por classes que estendem {@link CoreQuartzConfig}
 * para adicionar propriedades adicionais.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public final class QuartzPropertiesConstants {

    /**
     * Property key: org.quartz.scheduler.instanceName
     */
    public static final String SCHEDULER_INSTANCE_NAME = "org.quartz.scheduler.instanceName";

    // =========================================================================
    // Scheduler Properties (org.quartz.scheduler.*)
    // =========================================================================
    /**
     * Property key: org.quartz.scheduler.instanceId
     */
    public static final String SCHEDULER_INSTANCE_ID = "org.quartz.scheduler.instanceId";
    /**
     * Property key: org.quartz.scheduler.instanceIdGenerator.class
     */
    public static final String SCHEDULER_INSTANCE_ID_GENERATOR_CLASS = "org.quartz.scheduler.instanceIdGenerator.class";
    /**
     * Property key: org.quartz.scheduler.threadName
     */
    public static final String SCHEDULER_THREAD_NAME = "org.quartz.scheduler.threadName";
    /**
     * Property key: org.quartz.scheduler.makeSchedulerThreadDaemon
     */
    public static final String SCHEDULER_MAKE_THREAD_DAEMON = "org.quartz.scheduler.makeSchedulerThreadDaemon";
    /**
     * Property key: org.quartz.scheduler.threadsInheritContextClassLoaderOfInitializer
     */
    public static final String SCHEDULER_THREADS_INHERIT_CONTEXT = "org.quartz.scheduler.threadsInheritContextClassLoaderOfInitializer";
    /**
     * Property key: org.quartz.scheduler.idleWaitTime
     */
    public static final String SCHEDULER_IDLE_WAIT_TIME = "org.quartz.scheduler.idleWaitTime";
    /**
     * Property key: org.quartz.scheduler.dbFailureRetryInterval
     */
    public static final String SCHEDULER_DB_FAILURE_RETRY_INTERVAL = "org.quartz.scheduler.dbFailureRetryInterval";
    /**
     * Property key: org.quartz.scheduler.classLoadHelper.class
     */
    public static final String SCHEDULER_CLASS_LOAD_HELPER_CLASS = "org.quartz.scheduler.classLoadHelper.class";
    /**
     * Property key: org.quartz.scheduler.jobFactory.class
     */
    public static final String SCHEDULER_JOB_FACTORY_CLASS = "org.quartz.scheduler.jobFactory.class";
    /**
     * Property key: org.quartz.scheduler.userTransactionURL
     */
    public static final String SCHEDULER_USER_TRANSACTION_URL = "org.quartz.scheduler.userTransactionURL";
    /**
     * Property key: org.quartz.scheduler.wrapJobExecutionInUserTransaction
     */
    public static final String SCHEDULER_WRAP_JOB_IN_TRANSACTION = "org.quartz.scheduler.wrapJobExecutionInUserTransaction";
    /**
     * Property key: org.quartz.scheduler.skipUpdateCheck
     */
    public static final String SCHEDULER_SKIP_UPDATE_CHECK = "org.quartz.scheduler.skipUpdateCheck";
    /**
     * Property key: org.quartz.scheduler.batchTriggerAcquisitionMaxCount
     */
    public static final String SCHEDULER_BATCH_TRIGGER_MAX_COUNT = "org.quartz.scheduler.batchTriggerAcquisitionMaxCount";
    /**
     * Property key: org.quartz.scheduler.batchTriggerAcquisitionFireAheadTimeWindow
     */
    public static final String SCHEDULER_BATCH_TRIGGER_FIRE_AHEAD_TIME_WINDOW = "org.quartz.scheduler.batchTriggerAcquisitionFireAheadTimeWindow";
    /**
     * Property key: org.quartz.threadPool.class
     */
    public static final String THREAD_POOL_CLASS = "org.quartz.threadPool.class";

    // =========================================================================
    // ThreadPool Properties (org.quartz.threadPool.*)
    // =========================================================================
    /**
     * Property key: org.quartz.threadPool.threadCount
     */
    public static final String THREAD_POOL_THREAD_COUNT = "org.quartz.threadPool.threadCount";
    /**
     * Property key: org.quartz.threadPool.threadPriority
     */
    public static final String THREAD_POOL_THREAD_PRIORITY = "org.quartz.threadPool.threadPriority";
    /**
     * Property key: org.quartz.threadPool.makeThreadsDaemons
     */
    public static final String THREAD_POOL_MAKE_THREADS_DAEMONS = "org.quartz.threadPool.makeThreadsDaemons";
    /**
     * Property key: org.quartz.jobStore.class
     */
    public static final String JOB_STORE_CLASS = "org.quartz.jobStore.class";

    // =========================================================================
    // JobStore Properties (org.quartz.jobStore.*)
    // =========================================================================
    /**
     * Property key: org.quartz.jobStore.driverDelegateClass
     */
    public static final String JOB_STORE_DRIVER_DELEGATE_CLASS = "org.quartz.jobStore.driverDelegateClass";
    /**
     * Property key: org.quartz.jobStore.dataSource
     */
    public static final String JOB_STORE_DATA_SOURCE = "org.quartz.jobStore.dataSource";
    /**
     * Property key: org.quartz.jobStore.tablePrefix
     */
    public static final String JOB_STORE_TABLE_PREFIX = "org.quartz.jobStore.tablePrefix";
    /**
     * Property key: org.quartz.jobStore.useProperties
     */
    public static final String JOB_STORE_USE_PROPERTIES = "org.quartz.jobStore.useProperties";
    /**
     * Property key: org.quartz.jobStore.misfireThreshold
     */
    public static final String JOB_STORE_MISFIRE_THRESHOLD = "org.quartz.jobStore.misfireThreshold";
    /**
     * Property key: org.quartz.jobStore.isClustered
     */
    public static final String JOB_STORE_IS_CLUSTERED = "org.quartz.jobStore.isClustered";
    /**
     * Property key: org.quartz.jobStore.clusterCheckinInterval
     */
    public static final String JOB_STORE_CLUSTER_CHECKIN_INTERVAL = "org.quartz.jobStore.clusterCheckinInterval";
    /**
     * Property key: org.quartz.jobStore.maxMisfiresToHandleAtATime
     */
    public static final String JOB_STORE_MAX_MISFIRES = "org.quartz.jobStore.maxMisfiresToHandleAtATime";
    /**
     * Property key: org.quartz.jobStore.dontSetAutoCommitFalse
     */
    public static final String JOB_STORE_DONT_SET_AUTO_COMMIT_FALSE = "org.quartz.jobStore.dontSetAutoCommitFalse";
    /**
     * Property key: org.quartz.jobStore.selectWithLockSQL
     */
    public static final String JOB_STORE_SELECT_WITH_LOCK_SQL = "org.quartz.jobStore.selectWithLockSQL";
    /**
     * Property key: org.quartz.jobStore.txIsolationLevelSerializable
     */
    public static final String JOB_STORE_TX_ISOLATION_SERIALIZABLE = "org.quartz.jobStore.txIsolationLevelSerializable";
    /**
     * Property key: org.quartz.jobStore.acquireTriggersWithinLock
     */
    public static final String JOB_STORE_ACQUIRE_TRIGGERS_WITHIN_LOCK = "org.quartz.jobStore.acquireTriggersWithinLock";
    /**
     * Property key: org.quartz.jobStore.lockHandler.class
     */
    public static final String JOB_STORE_LOCK_HANDLER_CLASS = "org.quartz.jobStore.lockHandler.class";
    /**
     * Property key: org.quartz.jobStore.driverDelegateInitString
     */
    public static final String JOB_STORE_DRIVER_DELEGATE_INIT_STRING = "org.quartz.jobStore.driverDelegateInitString";
    /**
     * Property key: org.quartz.jobStore.useEnhancedStatements
     */
    public static final String JOB_STORE_USE_ENHANCED_STATEMENTS = "org.quartz.jobStore.useEnhancedStatements";
    private QuartzPropertiesConstants() {
        // Utility class
    }
}
