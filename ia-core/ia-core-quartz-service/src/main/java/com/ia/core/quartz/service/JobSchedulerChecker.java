package com.ia.core.quartz.service;

import java.time.LocalTime;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

import lombok.extern.slf4j.Slf4j;

/**
 * Job de verificação do scheduler.
 * <p>
 * Job de exemplo que verifica e loga a execução do scheduler.
 * Utiliza as anotações @DisallowConcurrentExecution e @PersistJobDataAfterExecution
 * para controle de concorrência e persistência de dados.
 *
 * @author Israel Araújo
 */
@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public final class JobSchedulerChecker
  extends AbstractJob {

  @Override
  protected void executeInternal(JobExecutionContext context)
    throws JobExecutionException {
    log.info("JobSchedulerChecker executed at {} {}", LocalTime.now(),
             context.getJobDetail().getDescription());
  }

}
