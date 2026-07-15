package com.ia.core.quartz.config;

import com.ia.core.quartz.service.SchedulerJobManagementService;
import com.ia.core.quartz.service.SchedulerListenerService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.stereotype.Component;

/**
 * Registrador de schedulers do Quartz.
 * <p>
 * Responsável por registrar listeners e agendar jobs
 * durante a inicialização da aplicação.
 *
 * @author Israel Araújo
 * @see SchedulerJobManagementService
 * @see SchedulerListenerService
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerRegistry {

    /**
     * Serviço de gerenciamento de jobs.
     */
    private final SchedulerJobManagementService jobManagementService;

    /**
     * Serviço de listeners do scheduler.
     */
    private final SchedulerListenerService listenerService;

    /**
     * Instância do scheduler Quartz.
     */
    private final Scheduler scheduler;

    /**
     * Registra listeners e agenda jobs na inicialização.
     */
    @PostConstruct
    public void registry() {
        log.info("Registrando schedulers do Quartz");
        listenerService.criarListeners(scheduler);
        jobManagementService.iniciarJobs();
        log.info("Schedulers do Quartz registrados com sucesso");
    }
}
