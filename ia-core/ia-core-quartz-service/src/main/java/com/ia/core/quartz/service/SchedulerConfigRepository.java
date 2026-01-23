package com.ia.core.quartz.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ia.core.quartz.model.scheduler.SchedulerConfig;
import com.ia.core.service.repository.BaseEntityRepository;

public interface SchedulerConfigRepository
  extends BaseEntityRepository<SchedulerConfig> {

  @Query("SELECT DISTINCT sc FROM SchedulerConfig sc JOIN FETCH sc.periodicidade p WHERE p.ativo = :active")
  List<SchedulerConfig> findAllActive(@Param("active") boolean active);
}
