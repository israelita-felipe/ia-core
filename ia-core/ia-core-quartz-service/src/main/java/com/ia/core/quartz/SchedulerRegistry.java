package com.ia.core.quartz;

import com.ia.core.quartz.service.SchedulerConfigService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
