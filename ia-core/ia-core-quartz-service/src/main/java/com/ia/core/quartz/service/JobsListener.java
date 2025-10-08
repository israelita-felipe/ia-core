package com.ia.core.quartz.service;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;

import lombok.extern.slf4j.Slf4j;

/**
 * Escutadores de jobs
 *
 * @author Israel Araújo
 */
@Slf4j
public class JobsListener
  implements JobListener {

  @Override
  public String getName() {
    return "JobsListener";
  }

  @Override
  public void jobToBeExecuted(JobExecutionContext context) {
    log.info("Iniciando execução do Job");
    JobKey jobKey = context.getJobDetail().getKey();
    log.info("Chave do job: {}", jobKey);
  }

  @Override
  public void jobExecutionVetoed(JobExecutionContext context) {
    log.info("Execução do job vetada/abortada");
    JobKey jobKey = context.getJobDetail().getKey();
    log.info("Chave do job vetado: {}", jobKey);
  }

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
