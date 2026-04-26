package com.ia.core.quartz.service;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;

/**
 * Implementação de {@link org.quartz.JobListener} para monitoramento de jobs.
 * <p>
 * Este listener registra e loga eventos importantes do ciclo de vida dos jobs:
 * <ul>
 * <li>Quando um job está prestes a ser executado ({@link #jobToBeExecuted})</li>
 * <li>Quando a execução de um job foi vetada ({@link #jobExecutionVetoed})</li>
 * <li>Quando um job foi executado com sucesso ou com erro ({@link #jobWasExecuted})</li>
 * </ul>
 * <p>
 * Útil para debugging e monitoramento de jobs agendados.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see org.quartz.JobListener
 * @see org.quartz.JobExecutionContext
 */
@Slf4j
public class JobsListener
  implements JobListener {

  /**
   * Obtém o nome deste listener.
   *
   * @return Nome do listener ("JobsListener")
   * @since 1.0.0
   */
  @Override
  public String getName() {
    return "JobsListener";
  }

  /**
   * Called before a {@link org.quartz.Job} is executed.
   * <p>
   * Este método é chamado imediatamente antes da execução do job.
   * É útil para logging e preparação de recursos.
   *
   * @param context Contexto de execução do job contendo informações sobre o job e trigger
   * @since 1.0.0
   */
  @Override
  public void jobToBeExecuted(JobExecutionContext context) {
    log.info("Iniciando execução do Job");
    JobKey jobKey = context.getJobDetail().getKey();
    log.info("Chave do job: {}", jobKey);
  }

  /**
   * Called when a {@link org.quartz.Job} execution was vetoed by a {@link org.quartz.trigger.TriggerListener}.
   * <p>
   * Este método é chamado quando um listener de trigger decide vetar a execução
   * do job. Isso pode acontecer quando, por exemplo, uma condição de pausa é detectada.
   *
   * @param context Contexto de execução do job
   * @since 1.0.0
   */
  @Override
  public void jobExecutionVetoed(JobExecutionContext context) {
    log.info("Execução do job vetada/abortada");
    JobKey jobKey = context.getJobDetail().getKey();
    log.info("Chave do job vetado: {}", jobKey);
  }

  /**
   * Called after a {@link org.quartz.Job} has been executed.
   * <p>
   * Este método é chamado após a conclusão da execução do job, independentemente
   * de ter sido bem-sucedida ou não. Se ocorreu uma exceção, a mesma será
   * informada no parâmetro {@code jobException}.
   *
   * @param context        Contexto de execução do job
   * @param jobException   Exceção lançada durante a execução, ou {@code null} se bem-sucedida
   * @since 1.0.0
   */
  @Override
  public void jobWasExecuted(JobExecutionContext context,
                             JobExecutionException jobException) {
    log.info("Job executado com conclusão");
    JobKey jobKey = context.getJobDetail().getKey();
    log.info("Chave do job executado: {}", jobKey);

    if (jobException != null) {
      log.error("Erro durante a execução do job {}: {}", jobKey,
                jobException.getMessage(), jobException);
    }
  }
}
