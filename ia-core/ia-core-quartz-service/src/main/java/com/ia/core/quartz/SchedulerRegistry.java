package com.ia.core.quartz;

import org.springframework.stereotype.Component;

import com.ia.core.quartz.service.SchedulerConfigService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

/**
 *
 */
@Component
@RequiredArgsConstructor
public class SchedulerRegistry {
  private final SchedulerConfigService service;

  @PostConstruct
  public void registry() {
    service.criarListeners();
    service.agendarJobs();
  }
}
