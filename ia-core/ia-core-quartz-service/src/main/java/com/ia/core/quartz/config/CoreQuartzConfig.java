package com.ia.core.quartz.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

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
public class CoreQuartzConfig {

    /**
     * Factory para criação de instâncias de jobs com injeção de dependências
     * Spring.
     */
    private final CoreSpringBeanJobFactory springBeanJobFactory;

    /**
     * Propriedades de configuração do Quartz.
     */
    private final QuartzConfigurationProvider quartzProperties;

    /**
     * Construtor com injeção de dependências.
     *
     * @param springBeanJobFactory        factory para criação de jobs Spring
     * @param quartzConfigurationProvider provedor de configurações
     */
    public CoreQuartzConfig(CoreSpringBeanJobFactory springBeanJobFactory,
                            QuartzConfigurationProvider quartzConfigurationProvider) {
        this.springBeanJobFactory = springBeanJobFactory;
        this.quartzProperties = quartzConfigurationProvider;
    }

    public QuartzConfigurationProvider getQuartzProperties() {
        return quartzProperties;
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
        schedulerFactoryBean.setQuartzProperties(quartzProperties.getProperties());

        // Configurações que podem ser sobrescritas por subclasses
        schedulerFactoryBean.setOverwriteExistingJobs(quartzProperties.getQuartzProperties().getScheduler().isOverwriteExistingJobs());
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(quartzProperties.getQuartzProperties().getScheduler().isWaitForJobsToCompleteOnShutdown());
        schedulerFactoryBean.setAutoStartup(quartzProperties.getQuartzProperties().getScheduler().isAutoStartup());

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

}
