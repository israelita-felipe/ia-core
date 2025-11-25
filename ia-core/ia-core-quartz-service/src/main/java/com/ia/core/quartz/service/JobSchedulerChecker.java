package com.ia.core.quartz.service;

import java.time.LocalTime;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

import lombok.extern.slf4j.Slf4j;

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
