package com.ia.core.quartz.service;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

/**
 * Implementação de {@link org.quartz.TriggerListener} para monitoramento de triggers.
 * <p>
 * Este listener registra e loga eventos importantes do ciclo de vida dos triggers:
 * <ul>
 * <li>Quando um trigger está prestes a executar um job ({@link #triggerFired})</li>
 * <li>Quando um trigger falha em executar ({@link #triggerMisfired})</li>
 * <li>Quando um trigger completa a execução ({@link #triggerComplete})</li>
 * </ul>
 * <p>
 * Útil para debugging e monitoramento de schedules.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see org.quartz.TriggerListener
 * @see org.quartz.Trigger
 */
@Slf4j
public class TriggersListener
  implements TriggerListener {

  /**
   * Obtém o nome deste listener.
   *
   * @return Nome do listener ("TriggersListener")
   * @since 1.0.0
   */
  @Override
  public String getName() {
    return "TriggersListener";
  }

  /**
   * Called when a {@link Trigger} has fired.
   * <p>
   * Este método é chamado quando o trigger dispara a execução do job.
   *
   * @param trigger O trigger que foi acionado
   * @param context Contexto de execução do job
   * @since 1.0.0
   */
  @Override
  public void triggerFired(Trigger trigger, JobExecutionContext context) {
    log.info("Trigger sendo acionado");
    final JobKey jobKey = trigger.getJobKey();

    log.info("Trigger acionado em {} :: Chave do job: {}",
             trigger.getStartTime(), jobKey);
  }

  /**
   * Called by the {@link org.quartz.Scheduler} to determine whether or not
   * the {@link org.quartz.Job} should be executed by the current {@link TriggerListener}.
   * <p>
   * Se este método retornar {@code true}, será solicitado ao JobListener
   * que chame o método {@code jobExecutionVetoed()}, vetando assim a execução.
   * Currently returns {@code false} to allow all job executions.
   *
   * @param trigger O trigger que está prestes a disparar
   * @param context Contexto de execução do job
   * @return {@code false} para permitir a execução, {@code true} para vetar
   * @since 1.0.0
   */
  @Override
  public boolean vetoJobExecution(Trigger trigger,
                                  JobExecutionContext context) {
    log.info("Verificando saúde do trigger");
    return false;
  }

  /**
   * Called when a {@link Trigger} has misfired.
   * <p>
   * Este método é chamado quando um trigger falha em disparar na hora correta.
   * Isso pode acontecer quando o scheduler estava parado ou sobrecarregado.
   *
   * @param trigger O trigger que falhou
   * @since 1.0.0
   */
  @Override
  public void triggerMisfired(Trigger trigger) {
    log.info("Trigger falhou (misfired)");
    final JobKey jobKey = trigger.getJobKey();

    log.info("Trigger falhou em {} :: Chave do job: {}",
             trigger.getStartTime(), jobKey);
  }

  /**
   * Called when a {@link Trigger} has fired, and the associated {@link org.quartz.Job}
   * has been executed.
   * <p>
   * Este método é chamado após a conclusão da execução do job,
   * independentemente de ter sido bem-sucedida ou não.
   *
   * @param trigger                O trigger que disparou a execução
   * @param context                Contexto de execução do job
   * @param triggerInstructionCode Instrução de execução completada
   * @since 1.0.0
   */
  @Override
  public void triggerComplete(Trigger trigger, JobExecutionContext context,
                              Trigger.CompletedExecutionInstruction triggerInstructionCode) {
    log.info("Trigger concluído");
    final JobKey jobKey = trigger.getJobKey();

    log.info("Trigger concluído em {} :: Chave do job: {}",
             trigger.getStartTime(), jobKey);
  }
}
