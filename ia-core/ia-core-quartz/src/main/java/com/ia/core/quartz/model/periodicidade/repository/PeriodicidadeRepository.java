package com.ia.core.quartz.model.periodicidade.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ia.core.quartz.model.periodicidade.Periodicidade;

/**
 * Repositório para {@link Periodicidade}.
 *
 * @author Israel Araújo
 */
@Repository
public interface PeriodicidadeRepository
  extends JpaRepository<Periodicidade, Long> {

  /**
   * Busca periodicidades ativas.
   *
   * @return lista de periodicidades ativas
   */
  List<Periodicidade> findByAtivoTrue();

  /**
   * Busca periodicidades que estão ativas e têm data de início antes ou igual
   * à data especificada.
   *
   * @param data a data de referência
   * @return lista de periodicidades ativas que iniciaram antes da data
   */
  @Query("SELECT p FROM Periodicidade p WHERE p.ativo = true "
      + "AND p.intervaloBase.startDate <= :data")
  List<Periodicidade> findAtivasAteData(@Param("data") LocalDate data);

  /**
   * Busca periodicidades que têm regra de recurrência configurada.
   *
   * @return lista de periodicidades com regra de recurrência
   */
  @Query("SELECT p FROM Periodicidade p WHERE p.regra IS NOT NULL")
  List<Periodicidade> findAllWithRecurrence();

  /**
   * Busca periodicidades ativas que podem ter ocorrências em um período.
   * <p>
   * Considera eventos:
   * - Que estão ativos
   * - Que já começaram (startDate <= endDate do período)
   * - Que não terminaram ou têm recorrência
   *
   * @param endDate a data final do período para verificar
   * @return lista de periodicidades ativas que podem ocorrer no período
   */
  @Query("SELECT p FROM Periodicidade p WHERE p.ativo = true "
      + "AND (p.intervaloBase.endDate IS NULL OR p.intervaloBase.endDate >= :startDate) "
      + "AND p.intervaloBase.startDate <= :endDate")
  List<Periodicidade> findAtivasBetweenDates(
      @Param("startDate") java.time.LocalDate startDate,
      @Param("endDate") java.time.LocalDate endDate);
}
