package com.ia.core.quartz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ia.core.quartz.model.scheduler.SchedulerConfig;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Repository para entidade SchedulerConfig.
 * Implementa EntityGraph para evitar N+1 queries.
 * 
 * @author Israel Araújo
 */
public interface SchedulerConfigRepository
  extends BaseEntityRepository<SchedulerConfig> {

  /**
   * Busca configuração por ID com periodicidade carregada.
   * Evita N+1 query ao carregar relacionamento.
   */
  @EntityGraph("SchedulerConfig.withPeriodicidade")
  Optional<SchedulerConfig> findByIdWithPeriodicidade(Long id);

  /**
   * Lista todos os SchedulerConfigs com periodicidade carregada.
   */
  @EntityGraph("SchedulerConfig.withPeriodicidade")
  List<SchedulerConfig> findAllWithPeriodicidade();

  /**
   * Lista todos os SchedulerConfigs com periodicidade carregada e paginação.
   */
  @EntityGraph("SchedulerConfig.withPeriodicidade")
  Page<SchedulerConfig> findAllWithPeriodicidade(Pageable pageable);

  /**
   * Busca todos os ativos com periodicidade carregada.
   */
  @EntityGraph("SchedulerConfig.withPeriodicidade")
  @Query("SELECT DISTINCT sc FROM SchedulerConfig sc JOIN FETCH sc.periodicidade p WHERE p.ativo = :active")
  List<SchedulerConfig> findAllActiveWithPeriodicidade(@Param("active") boolean active);
}
