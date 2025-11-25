package com.ia.core.quartz.service;

import java.util.function.Consumer;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public abstract class AbstractJob
  extends QuartzJobBean {

  @Getter
  private Consumer<JobExecutionContext> contextInitializer = context -> {
  };

  @Override
  protected void executeInternal(JobExecutionContext context)
    throws JobExecutionException {
    initContext(context);
  }

  public void initContext(Consumer<JobExecutionContext> contextInitializer) {
    this.contextInitializer = contextInitializer;
  }

  public void initContext(JobExecutionContext context) {
    this.contextInitializer.accept(context);
  }
}
