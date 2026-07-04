package com.ia.core.quartz.service;

import com.ia.core.service.annotations.TransactionalWrite;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.TriggerListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Serviço para gerenciamento de listeners do scheduler Quartz.
 *
 * Responsável por criar e registrar listeners para monitorar
 * a execução de jobs e triggers no scheduler.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
public class SchedulerListenerService {

  /**
   * Cria e registra os listeners para jobs e triggers no scheduler Quartz.
   *
   * @param scheduler Instância do scheduler Quartz onde os listeners serão registrados
   */
  @TransactionalWrite
    public void criarListeners(Scheduler scheduler) {
    Objects.requireNonNull(scheduler, "scheduler não pode ser null");
    try {
      scheduler.getListenerManager().addJobListener(criarJobListener());
      scheduler.getListenerManager().addTriggerListener(criarTriggerListener());
      log.info("Listeners de job e trigger registrados com sucesso");
    } catch (Exception e) {
      log.error("Erro ao registrar listeners no scheduler: {}",
                e.getLocalizedMessage(), e);
    }
  }

  /**
   * Cria uma instância do listener de jobs.
   *
   * @return Instância de JobListener configurada
   */
  public JobListener criarJobListener() {
    return new JobsListener();
  }

  /**
   * Cria uma instância do listener de triggers.
   *
   * @return Instância de TriggerListener configurada
   */
  public TriggerListener criarTriggerListener() {
    return new TriggersListener();
  }
}
