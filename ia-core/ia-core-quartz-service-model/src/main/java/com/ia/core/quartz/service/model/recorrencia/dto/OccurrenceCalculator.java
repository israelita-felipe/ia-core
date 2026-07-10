package com.ia.core.quartz.service.model.recorrencia.dto;

import com.ia.core.quartz.service.model.periodicidade.dto.IntervaloTemporalDTO;
import com.ia.core.quartz.service.model.periodicidade.dto.PeriodicidadeDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Calculador de ocorrências para regras de periodicidade.
 * <p>
 * Interface que define operações para cálculo e geração de ocorrências
 * a partir de regras de periodicidade (recorrência).
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface OccurrenceCalculator {

  /**
   * Retorna o calculador padrão.
   *
   * @return o calculador padrão (libRecur)
   */
  public static OccurrenceCalculator defaultCalculator() {
    return libRecurCalculator();
  }

  /**
   * Retorna o calculador baseado na biblioteca libRecur.
   *
   * @return o calculador libRecur
   */
  public static OccurrenceCalculator libRecurCalculator() {
    return LibRecurOccurrenceCalculator.get();
  }

  /**
   * Retorna a próxima ocorrência após uma data/hora.
   *
   * @param p a periodicidade
   * @param after a data/hora de referência
   * @return Optional contendo a próxima ocorrência, se existir
   */
  Optional<IntervaloTemporalDTO> nextOccurrence(PeriodicidadeDTO p,
                                                ZonedDateTime after);

  /**
   * Retorna a próxima ocorrência com limite de resultados.
   *
   * @param p a periodicidade
   * @param after a data/hora de referência
   * @param maxResults número máximo de resultados
   * @return Optional contendo a próxima ocorrência, se existir
   */
  Optional<IntervaloTemporalDTO> nextOccurrence(PeriodicidadeDTO p,
                                                ZonedDateTime after,
                                                int maxResults);

  /**
   * Gera uma lista de ocorrências futuras.
   *
   * @param periodicidade a periodicidade a ser processada
   * @param after a data/hora de início da busca
   * @param maxCount número máximo de ocorrências
   * @return lista de ocorrências
   */
  List<IntervaloTemporalDTO> generateOccurrences(PeriodicidadeDTO periodicidade,
                                                 ZonedDateTime after,
                                                 int maxCount);

  /**
   * Gera uma lista de ocorrências a partir de uma data.
   *
   * @param periodicidade a periodicidade a ser processada
   * @param startDate a data de início
   * @param maxCount número máximo de ocorrências
   * @return lista de ocorrências
   */
  List<IntervaloTemporalDTO> generateOccurrences(PeriodicidadeDTO periodicidade,
                                                 LocalDate startDate,
                                                 int maxCount);

  /**
   * Gera uma lista de ocorrências dentro de um período.
   *
   * @param periodicidade a periodicidade a ser processada
   * @param start data/hora de início
   * @param end data/hora de fim
   * @return lista de ocorrências dentro do período
   */
  List<IntervaloTemporalDTO> generateOccurrences(PeriodicidadeDTO periodicidade,
                                                 ZonedDateTime start,
                                                 ZonedDateTime end);

  /**
   * Verifica interseção entre dois eventos periódicos.
   *
   * @param a primeira periodicidade
   * @param b segunda periodicidade
   * @param windowStart início da janela de verificação
   * @param windowEnd fim da janela de verificação
   * @param zoneId zona horária
   * @return true se houver interseção, false caso contrário
   */
  boolean intersects(PeriodicidadeDTO a, PeriodicidadeDTO b,
                     LocalDateTime windowStart, LocalDateTime windowEnd,
                     ZoneId zoneId);

}
