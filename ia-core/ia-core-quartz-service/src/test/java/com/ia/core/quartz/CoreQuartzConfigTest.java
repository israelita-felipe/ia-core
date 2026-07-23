package com.ia.core.quartz;

import com.ia.core.quartz.config.CoreQuartzConfig;
import com.ia.core.quartz.config.CoreSpringBeanJobFactory;
import com.ia.core.quartz.config.QuartzConfigurationProvider;
import com.ia.core.quartz.config.QuartzProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Testes para CoreQuartzConfig.
 * <p>
 * Verifica que as propriedades do Quartz são mapeadas corretamente
 * a partir de QuartzProperties.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@DisplayName("Testes de CoreQuartzConfig")
class CoreQuartzConfigTest {

    @Test
    @DisplayName("Deve mapear scheduler properties corretamente")
    void deveMapearSchedulerPropertiesCorretamente() {
        // Arrange
        QuartzProperties props = new QuartzProperties();
        props.getScheduler().setInstanceName("TestScheduler");
        props.getScheduler().setInstanceId("TEST-1");
        props.getScheduler().setSkipUpdateCheck(true);
        props.getScheduler().setBatchTriggerAcquisitionMaxCount(5);

        CoreSpringBeanJobFactory jobFactory = mock(CoreSpringBeanJobFactory.class);
        QuartzConfigurationProvider configurationProvider = new QuartzConfigurationProvider(props);
        TestableQuartzConfig config = new TestableQuartzConfig(jobFactory, configurationProvider);

        // Act
        Properties quartzProps = config.getQuartzProperties().getProperties();

        // Assert
        assertThat(quartzProps.getProperty("org.quartz.scheduler.instanceName")).isEqualTo("TestScheduler");
        assertThat(quartzProps.getProperty("org.quartz.scheduler.instanceId")).isEqualTo("TEST-1");
        assertThat(quartzProps.getProperty("org.quartz.scheduler.skipUpdateCheck")).isEqualTo("true");
        assertThat(quartzProps.getProperty("org.quartz.scheduler.batchTriggerAcquisitionMaxCount")).isEqualTo("5");
    }

    @Test
    @DisplayName("Deve mapear thread pool properties corretamente")
    void deveMapearThreadPoolPropertiesCorretamente() {
        // Arrange
        QuartzProperties props = new QuartzProperties();
        props.getThreadPool().setThreadCount(10);
        props.getThreadPool().setThreadPriority(8);
        props.getThreadPool().setMakeThreadsDaemons(true);

        CoreSpringBeanJobFactory jobFactory = mock(CoreSpringBeanJobFactory.class);
        QuartzConfigurationProvider configurationProvider = new QuartzConfigurationProvider(props);
        TestableQuartzConfig config = new TestableQuartzConfig(jobFactory, configurationProvider);

        // Act
        Properties quartzProps = config.getQuartzProperties().getProperties();

        // Assert
        assertThat(quartzProps.getProperty("org.quartz.threadPool.threadCount")).isEqualTo("10");
        assertThat(quartzProps.getProperty("org.quartz.threadPool.threadPriority")).isEqualTo("8");
        assertThat(quartzProps.getProperty("org.quartz.threadPool.makeThreadsDaemons")).isEqualTo("true");
    }

    @Test
    @DisplayName("Deve mapear job store properties corretamente")
    void deveMapearJobStorePropertiesCorretamente() {
        // Arrange
        QuartzProperties props = new QuartzProperties();
        props.getJobStore().setTablePrefix("TEST.QRTZ_");
        props.getJobStore().setClustered(true);
        props.getJobStore().setClusterCheckinInterval(java.time.Duration.ofMillis(5000));
        props.getJobStore().setUseProperties(true);
        props.getJobStore().setAcquireTriggersWithinLock(true);

        CoreSpringBeanJobFactory jobFactory = mock(CoreSpringBeanJobFactory.class);
        QuartzConfigurationProvider configurationProvider = new QuartzConfigurationProvider(props);
        TestableQuartzConfig config = new TestableQuartzConfig(jobFactory, configurationProvider);

        // Act
        Properties quartzProps = config.getQuartzProperties().getProperties();

        // Assert
        assertThat(quartzProps.getProperty("org.quartz.jobStore.tablePrefix")).isEqualTo("TEST.QRTZ_");
        assertThat(quartzProps.getProperty("org.quartz.jobStore.isClustered")).isEqualTo("true");
        assertThat(quartzProps.getProperty("org.quartz.jobStore.clusterCheckinInterval")).isEqualTo("5000");
        assertThat(quartzProps.getProperty("org.quartz.jobStore.useProperties")).isEqualTo("true");
        assertThat(quartzProps.getProperty("org.quartz.jobStore.acquireTriggersWithinLock")).isEqualTo("true");
    }

    @Test
    @DisplayName("Deve usar valores padrão quando propriedades não são configuradas")
    void deveUsarValoresPadraoQuandoPropriedadesNaoSaoConfiguradas() {
        // Arrange
        QuartzProperties props = new QuartzProperties(); // Usa valores padrão

        CoreSpringBeanJobFactory jobFactory = mock(CoreSpringBeanJobFactory.class);
        QuartzConfigurationProvider configurationProvider = new QuartzConfigurationProvider(props);
        TestableQuartzConfig config = new TestableQuartzConfig(jobFactory, configurationProvider);

        // Act
        Properties quartzProps = config.getQuartzProperties().getProperties();

        // Assert - Scheduler
        assertThat(quartzProps.getProperty("org.quartz.scheduler.instanceName")).isEqualTo("CoreQuartzScheduler");
        assertThat(quartzProps.getProperty("org.quartz.scheduler.instanceId")).isEqualTo("AUTO");
        assertThat(quartzProps.getProperty("org.quartz.scheduler.idleWaitTime")).isEqualTo("30000");
        assertThat(quartzProps.getProperty("org.quartz.scheduler.dbFailureRetryInterval")).isEqualTo("15000");

        // Assert - ThreadPool
        assertThat(quartzProps.getProperty("org.quartz.threadPool.threadCount")).isEqualTo("5");
        assertThat(quartzProps.getProperty("org.quartz.threadPool.threadPriority")).isEqualTo("5");

        // Assert - JobStore
        assertThat(quartzProps.getProperty("org.quartz.jobStore.driverDelegateClass"))
            .isEqualTo("org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        assertThat(quartzProps.getProperty("org.quartz.jobStore.isClustered")).isEqualTo("false");
        assertThat(quartzProps.getProperty("org.quartz.jobStore.maxMisfiresToHandleAtATime")).isEqualTo("20");
    }

    /**
     * Classe de teste que expõe o QuartzConfigurationProvider de CoreQuartzConfig.
     */
    @Configuration
    @EnableConfigurationProperties(QuartzProperties.class)
    static class TestableQuartzConfig extends CoreQuartzConfig {

        public TestableQuartzConfig(CoreSpringBeanJobFactory springBeanJobFactory,
                                    QuartzConfigurationProvider quartzConfigurationProvider) {
            super(springBeanJobFactory, quartzConfigurationProvider);
        }
    }
}
