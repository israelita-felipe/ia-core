package com.ia.core.quartz.service;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ia.core.quartz.model.scheduler.SchedulerConfig;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Repository para entidade SchedulerConfig. Implementa EntityGraph para evitar
 * N+1 queries.
 *
 * @author Israel Araújo
 */
public interface SchedulerConfigRepository
  extends BaseEntityRepository<SchedulerConfig> {

  /**
   * Busca todos os ativos com periodicidade carregada.
   */
  @EntityGraph("SchedulerConfig.withPeriodicidade")
  @Query("SELECT DISTINCT sc FROM SchedulerConfig sc JOIN FETCH sc.periodicidade p WHERE p.ativo = :active")
  List<SchedulerConfig> findAllActiveWithPeriodicidade(@Param("active") boolean active);
}
