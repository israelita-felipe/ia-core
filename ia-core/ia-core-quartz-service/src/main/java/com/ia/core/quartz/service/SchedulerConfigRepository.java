package com.ia.core.quartz.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ia.core.quartz.model.scheduler.SchedulerConfig;
import com.ia.core.quartz.service.model.SchedulerConfigSummary;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Repository para entidade SchedulerConfig. Implementa EntityGraph para evitar
 * N+1 queries e Projection para otimização de listas.
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

  /**
   * Busca todos os SchedulerConfig ativos usando projection para otimização.
   * <p>
   * Este método retorna apenas os campos necessários para listas,
   * evitando o carregamento completo da entidade e seus relacionamentos.
   * </p>
   *
   * @return Lista de projeções resumidas
   */
  @Query("SELECT sc.id as id, sc.jobClassName as jobClassName, sc.ativo as ativo " +
         "FROM SchedulerConfig sc WHERE sc.ativo = true")
  List<SchedulerConfigSummary> findAllSummaries();

  /**
   * Busca SchedulerConfig paginados usando projection.
   *
   * @param pageable Configuração de paginação
   * @return Página de projeções resumidas
   */
  @Query(value = "SELECT sc.id as id, sc.jobClassName as jobClassName, sc.ativo as ativo " +
                  "FROM SchedulerConfig sc",
         countQuery = "SELECT count(sc) FROM SchedulerConfig sc")
  Page<SchedulerConfigSummary> findAllSummaries(Pageable pageable);
}
