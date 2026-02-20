package com.ia.core.quartz.service.model.recorrencia.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import com.ia.core.quartz.service.model.periodicidade.dto.IntervaloTemporalDTO;
import com.ia.core.quartz.service.model.periodicidade.dto.PeriodicidadeDTO;

/**
 *
 */
public interface OccurrenceCalculator {

  public static OccurrenceCalculator defaultCalculator() {
    return libRecurCalculator();
  }

  public static OccurrenceCalculator libRecurCalculator() {
    return LibRecurOccurrenceCalculator.get();
  }

  Optional<IntervaloTemporalDTO> nextOccurrence(PeriodicidadeDTO p,
                                                ZonedDateTime after);

  Optional<IntervaloTemporalDTO> nextOccurrence(PeriodicidadeDTO p,
                                                ZonedDateTime after,
                                                int maxResults);

  /**
   * Gera uma lista de ocorrências futuras.
   */
  List<IntervaloTemporalDTO> generateOccurrences(PeriodicidadeDTO periodicidade,
                                                 ZonedDateTime after,
                                                 int maxCount);

  /**
   * Gera uma lista de ocorrências a partir de uma data.
   */
  List<IntervaloTemporalDTO> generateOccurrences(PeriodicidadeDTO periodicidade,
                                                 LocalDate startDate,
                                                 int maxCount);

  /**
   * Gera uma lista de ocorrências dentro de um período.
   */
  List<IntervaloTemporalDTO> generateOccurrences(PeriodicidadeDTO periodicidade,
                                                 ZonedDateTime start,
                                                 ZonedDateTime end);

  /**
   * Verifica interseção entre dois eventos periódicos.
   */
  boolean intersects(PeriodicidadeDTO a, PeriodicidadeDTO b,
                     LocalDateTime windowStart, LocalDateTime windowEnd,
                     ZoneId zoneId);

}
