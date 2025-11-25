package com.ia.core.quartz.service;

import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

import lombok.extern.slf4j.Slf4j;

/**
 * Escutador de triggers
 *
 * @author Israel Araújo
 */
@Slf4j
public class TriggersListener
  implements TriggerListener {

  @Override
  public String getName() {
    return "TriggersListener";
  }

  @Override
  public void triggerFired(Trigger trigger, JobExecutionContext context) {
    log.info("Trigger sendo acionado");
    final JobKey jobKey = trigger.getJobKey();

    log.info("Trigger acionado em {} :: Chave do job: {}",
             trigger.getStartTime(), jobKey);
  }

  /**
   * @Content : Se este método retornar true, será solicitado ao JobListener o
   *          método jobExecutionVetoed()
   */
  @Override
  public boolean vetoJobExecution(Trigger trigger,
                                  JobExecutionContext context) {
    log.info("Verificando saúde do trigger");
    return false;
  }

  @Override
  public void triggerMisfired(Trigger trigger) {
    log.info("Trigger falhou (misfired)");
    final JobKey jobKey = trigger.getJobKey();

    log.info("Trigger falhou em {} :: Chave do job: {}",
             trigger.getStartTime(), jobKey);
  }

  @Override
  public void triggerComplete(Trigger trigger, JobExecutionContext context,
                              Trigger.CompletedExecutionInstruction triggerInstructionCode) {
    log.info("Trigger concluído");
    final JobKey jobKey = trigger.getJobKey();

    log.info("Trigger concluído em {} :: Chave do job: {}",
             trigger.getStartTime(), jobKey);
  }
}
