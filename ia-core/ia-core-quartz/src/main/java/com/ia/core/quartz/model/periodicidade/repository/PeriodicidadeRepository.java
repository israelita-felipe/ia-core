package com.ia.core.quartz.model.periodicidade.repository;

import com.ia.core.quartz.model.periodicidade.Periodicidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositório para acesso a dados de {@link Periodicidade}.
 * <p>
 * Fornece métodos de consulta especializados para periodicidades, incluindo
 * busca por status ativo, por período de datas e por existência de regras de recorrência.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see Periodicidade
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
@Repository
public interface PeriodicidadeRepository
  extends JpaRepository<Periodicidade, Long> {

  /**
   * Busca todas as periodicidades que estão ativas.
   * <p>
   * Utilizado para encontrar todos os jobs agendados que estão
   * atualmente em execução.
   *
   * @return Lista de todas as periodicidades ativas, nunca {@code null}
   * @since 1.0.0
   */
  List<Periodicidade> findByAtivoTrue();

  /**
   * Busca periodicidades ativas que iniciaram até uma determinada data.
   * <p>
   * Utilizado para encontrar jobs que devem ser executados a partir de
   * uma data específica, considerando apenas aqueles que já começaram.
   *
   * @param data A data de referência para a busca. Não pode ser {@code null}
   * @return Lista de periodicidades ativas que iniciaram antes ou na data especificada
   * @since 1.0.0
   */
  @Query("SELECT p FROM Periodicidade p WHERE p.ativo = true "
      + "AND p.intervaloBase.startDate <= :data")
  List<Periodicidade> findAtivasAteData(@Param("data") LocalDate data);

  /**
   * Busca periodicidades que possuem uma regra de recorrência configurada.
   * <p>
   * Utilizado para identificar eventos que se repetem automaticamente
   * conforme uma regra RRULE da RFC 5545.
   *
   * @return Lista de periodicidades com regra de recorrência configurada
   * @since 1.0.0
   */
  @Query("SELECT p FROM Periodicidade p WHERE p.regra IS NOT NULL")
  List<Periodicidade> findAllWithRecurrence();

  /**
   * Busca periodicidades ativas que podem ter ocorrências em um período específico.
   * <p>
   * Considera eventos que estão ativos e que podem ocorrer dentro do período especificado.
   * A lógica considera:
   * <ul>
   * <li>Eventos ativos ({@code ativo = true})</li>
   * <li>Eventos que já começaram ({@code intervaloBase.startDate <= endDate})</li>
   * <li>Eventos que não terminaram ou têm recorrência ({@code intervaloBase.endDate >= startDate})</li>
   * </ul>
   *
   * @param startDate Data de início do período. Não pode ser {@code null}
   * @param endDate   Data de fim do período. Não pode ser {@code null}
   * @return Lista de periodicidades que podem ocorrer no período especificado
   * @since 1.0.0
   */
  @Query("SELECT p FROM Periodicidade p WHERE p.ativo = true "
      + "AND (p.intervaloBase.endDate IS NULL OR p.intervaloBase.endDate >= :startDate) "
      + "AND p.intervaloBase.startDate <= :endDate")
  List<Periodicidade> findAtivasBetweenDates(
      @Param("startDate") java.time.LocalDate startDate,
      @Param("endDate") java.time.LocalDate endDate);
}
