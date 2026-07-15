package com.ia.core.quartz.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para QuartzProperties.
 * <p>
 * Verifica que as propriedades padrão são configuradas corretamente
 * e que as estruturas aninhadas funcionam conforme esperado.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@DisplayName("Testes de QuartzProperties")
class QuartzPropertiesTest {

    @Test
    @DisplayName("Deve criar QuartzProperties com valores padrão")
    void deveCriarQuartzPropertiesComValoresPadrao() {
        // Arrange & Act
        QuartzProperties properties = new QuartzProperties();

        // Assert - Scheduler config defaults
        assertThat(properties.getScheduler().getInstanceName()).isEqualTo("CoreQuartzScheduler");
        assertThat(properties.getScheduler().getInstanceId()).isEqualTo("AUTO");
        assertThat(properties.getScheduler().isMakeSchedulerThreadDaemon()).isFalse();
        assertThat(properties.getScheduler().isSkipUpdateCheck()).isTrue();
        assertThat(properties.getScheduler().isAutoStartup()).isTrue();
        assertThat(properties.getScheduler().isOverwriteExistingJobs()).isTrue();
        assertThat(properties.getScheduler().isWaitForJobsToCompleteOnShutdown()).isTrue();

        // Assert - ThreadPool config defaults
        assertThat(properties.getThreadPool().getThreadPoolClass()).isEqualTo("org.quartz.simpl.SimpleThreadPool");
        assertThat(properties.getThreadPool().getThreadCount()).isEqualTo(5);
        assertThat(properties.getThreadPool().getThreadPriority()).isEqualTo(5);
        assertThat(properties.getThreadPool().isMakeThreadsDaemons()).isFalse();

        // Assert - JobStore config defaults
        assertThat(properties.getJobStore().getDriverDelegateClass()).isEqualTo("org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        assertThat(properties.getJobStore().isClustered()).isFalse();
        assertThat(properties.getJobStore().getMaxMisfiresToHandleAtATime()).isEqualTo(20);

        // Assert - Plugins config defaults
        assertThat(properties.getPlugins()).isNotNull();
        assertThat(properties.getPlugins().getListeners()).isEmpty();
        assertThat(properties.getPlugins().getPlugins()).isEmpty();
    }

    @Test
    @DisplayName("Deve permitir customizar SchedulerConfig")
    void devePermitirCustomizarSchedulerConfig() {
        // Arrange
        QuartzProperties properties = new QuartzProperties();

        // Act
        properties.getScheduler().setInstanceName("MyCustomScheduler");
        properties.getScheduler().setInstanceId("1");
        // ThreadCount é de ThreadPoolConfig
        properties.getScheduler().setIdleWaitTime(Duration.ofSeconds(60));

        // Assert
        assertThat(properties.getScheduler().getInstanceName()).isEqualTo("MyCustomScheduler");
        assertThat(properties.getScheduler().getInstanceId()).isEqualTo("1");
        assertThat(properties.getScheduler().getInstanceName()).isEqualTo("MyCustomScheduler");
    }

    @Test
    @DisplayName("Deve permitir customizar ThreadPoolConfig")
    void devePermitirCustomizarThreadPoolConfig() {
        // Arrange
        QuartzProperties properties = new QuartzProperties();

        // Act
        properties.getThreadPool().setThreadCount(10);
        properties.getThreadPool().setThreadPriority(8);
        properties.getThreadPool().setMakeThreadsDaemons(true);

        // Assert
        assertThat(properties.getThreadPool().getThreadCount()).isEqualTo(10);
        assertThat(properties.getThreadPool().getThreadPriority()).isEqualTo(8);
        assertThat(properties.getThreadPool().isMakeThreadsDaemons()).isTrue();
    }

    @Test
    @DisplayName("Deve permitir customizar JobStoreConfig")
    void devePermitirCustomizarJobStoreConfig() {
        // Arrange
        QuartzProperties properties = new QuartzProperties();

        // Act
        properties.getJobStore().setTablePrefix("CUSTOM.QRTZ_");
        properties.getJobStore().setClustered(true);
        properties.getJobStore().setClusterCheckinInterval(Duration.ofSeconds(10));

        // Assert
        assertThat(properties.getJobStore().getTablePrefix()).isEqualTo("CUSTOM.QRTZ_");
        assertThat(properties.getJobStore().isClustered()).isTrue();
        assertThat(properties.getJobStore().getClusterCheckinInterval()).isEqualTo(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Deve permitir configurar DataSource")
    void devePermitirConfigurarDataSource() {
        // Arrange
        QuartzProperties properties = new QuartzProperties();

        // Act
        QuartzProperties.DataSourceConfig dsConfig = new QuartzProperties.DataSourceConfig();
        dsConfig.setDriver("org.postgresql.Driver");
        dsConfig.setUrl("jdbc:postgresql://localhost:5432/quartz");
        dsConfig.setUser("quartz");
        dsConfig.setPassword("password");
        dsConfig.setMaxConnections(10);

        properties.getDataSource().put("quartzDS", dsConfig);

        // Assert
        assertThat(properties.getDataSource()).hasSize(1);
        assertThat(properties.getDataSource().get("quartzDS").getDriver()).isEqualTo("org.postgresql.Driver");
        assertThat(properties.getDataSource().get("quartzDS").getUrl()).isEqualTo("jdbc:postgresql://localhost:5432/quartz");
    }

    @Test
    @DisplayName("Deve permitir configurar plugins e listeners")
    void devePermitirConfigurarPluginsEListeners() {
        // Arrange
        QuartzProperties properties = new QuartzProperties();

        // Act - Configurar listener
        QuartzProperties.ListenerConfig jobListener = new QuartzProperties.ListenerConfig();
        jobListener.setClassName("com.ia.core.quartz.service.JobsListener");
        properties.getPlugins().getListeners().put("jobListener", jobListener);

        // Act - Configurar plugin
        QuartzProperties.PluginConfig logPlugin = new QuartzProperties.PluginConfig();
        logPlugin.setClassName("org.quartz.plugins.history.LoggingJobHistoryPlugin");
        logPlugin.getProperties().put("jobSuccessMessage", "Job {1} completed successfully");
        properties.getPlugins().getPlugins().put("logPlugin", logPlugin);

        // Assert
        assertThat(properties.getPlugins().getListeners()).hasSize(1);
        assertThat(properties.getPlugins().getListeners().get("jobListener").getClassName())
            .isEqualTo("com.ia.core.quartz.service.JobsListener");

        assertThat(properties.getPlugins().getPlugins()).hasSize(1);
        assertThat(properties.getPlugins().getPlugins().get("logPlugin").getClassName())
            .isEqualTo("org.quartz.plugins.history.LoggingJobHistoryPlugin");
        assertThat(properties.getPlugins().getPlugins().get("logPlugin").getProperties())
            .containsEntry("jobSuccessMessage", "Job {1} completed successfully");
    }
}