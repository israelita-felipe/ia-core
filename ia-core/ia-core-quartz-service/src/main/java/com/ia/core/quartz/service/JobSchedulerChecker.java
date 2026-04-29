package com.ia.core.quartz.service;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

import java.time.LocalTime;

/**
 * Job de verificação do scheduler.
 * <p>
 * Job de exemplo que verifica e loga a execução do scheduler.
 * Utiliza as anotações {@link DisallowConcurrentExecution} e {@link PersistJobDataAfterExecution}
 * para controle de concorrência e persistência de dados.
 *
 * @author Israel Araújo
 * @since 1.0
 * @see AbstractJob
 */
@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public final class JobSchedulerChecker extends AbstractJob {

  @Override
  protected void executeJob(JobExecutionContext context)
    throws JobExecutionException {
    log.info("JobSchedulerChecker executed at {} {}", LocalTime.now(),
             context.getJobDetail().getDescription());
  }

}
