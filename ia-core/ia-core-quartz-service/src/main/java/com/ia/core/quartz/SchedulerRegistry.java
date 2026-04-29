package com.ia.core.quartz;

import com.ia.core.quartz.service.SchedulerConfigService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Registrador de schedulers do Quartz.
 * <p>
 * Responsável por registrar listeners e agendar jobs
 * durante a inicialização da aplicação.
 *
 * @author Israel Araújo
 * @since 1.0
 * @see SchedulerConfigService
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerRegistry {

  /**
   * Serviço de configuração do scheduler.
   */
  private final SchedulerConfigService service;

  /**
   * Registra listeners e agenda jobs na inicialização.
   */
  @PostConstruct
  public void registry() {
    log.info("Registrando schedulers do Quartz");
    service.criarListeners();
    service.agendarJobs();
    log.info("Schedulers do Quartz registrados com sucesso");
  }
}
