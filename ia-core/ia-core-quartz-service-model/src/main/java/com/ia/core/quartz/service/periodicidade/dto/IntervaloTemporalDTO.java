package com.ia.core.quartz.service.periodicidade.dto;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.Objects;

import com.ia.core.quartz.model.periodicidade.IntervaloTemporal;
import com.ia.core.service.dto.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * DTO para intervalo temporal de eventos.
 * <p>
 * Suporta modelagem de eventos com:
 * <ul>
 * <li>Data e hora separadas</li>
 * <li>Eventos que passam da meia-noite</li>
 * <li>Eventos com apenas horário (sem data)</li>
 * </ul>
 *
 * @author Israel Araújo
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class IntervaloTemporalDTO
  implements DTO<IntervaloTemporal> {

  /** Serial UID */
  private static final long serialVersionUID = 1L;

  /**
   * Data de início.
   * Equivalente ao componente de data do DTSTART (RFC 5545).
   */
  @NotNull(message = "{validation.periodicidade.intervaloBase.startDate.required}")
  private LocalDate startDate;

  /**
   * Hora de início.
   * Equivalente ao componente de hora do DTSTART (RFC 5545).
   */
  @NotNull(message = "{validation.periodicidade.intervaloBase.startTime.required}")
  private LocalTime startTime;

  /**
   * Data de fim.
   * Equivalente ao componente de data do DTEND (RFC 5545).
   */
  private LocalDate endDate;

  /**
   * Hora de fim.
   * Equivalente ao componente de hora do DTEND (RFC 5545).
   */
  private LocalTime endTime;

  // ========================
  // Campos de compatibilidade para MapStruct
  // ========================

  /**
   * Campo de compatibilidade para MapStruct.
   * Não persistido, usado apenas para mapeamento.
   */
  @jakarta.persistence.Transient
  private LocalDateTime startDateTime;

  /**
   * Campo de compatibilidade para MapStruct.
   * Não persistido, usado apenas para mapeamento.
   */
  @jakarta.persistence.Transient
  private LocalDateTime endDateTime;

  /**
   * Construtor com data e hora.
   */
  public IntervaloTemporalDTO(LocalDate startDate, LocalTime startTime,
                             LocalDate endDate, LocalTime endTime) {
    this.startDate = startDate;
    this.startTime = startTime;
    this.endDate = endDate;
    this.endTime = endTime;
  }

  /**
   * Construtor para evento no mesmo dia.
   */
  public IntervaloTemporalDTO(LocalDate date, LocalTime startTime,
                             LocalTime endTime) {
    this.startDate = date;
    this.startTime = startTime;
    this.endDate = date;
    this.endTime = endTime;
  }

  /**
   * Construtor com LocalDateTime (para compatibilidade).
   */
  public IntervaloTemporalDTO(LocalDateTime startDateTime,
                              LocalDateTime endDateTime) {
    if (startDateTime != null) {
      this.startDate = startDateTime.toLocalDate();
      this.startTime = startDateTime.toLocalTime();
    }
    if (endDateTime != null) {
      this.endDate = endDateTime.toLocalDate();
      this.endTime = endDateTime.toLocalTime();
    }
  }

  // ========================
  // Métodos de compatibilidade para MapStruct
  // ========================

  /**
   * Setter para compatibilidade com MapStruct.
   */
  public void setStartDateTime(LocalDateTime startDateTime) {
    if (startDateTime != null) {
      this.startDate = startDateTime.toLocalDate();
      this.startTime = startDateTime.toLocalTime();
    }
  }

  /**
   * Setter para compatibilidade com MapStruct.
   */
  public void setEndDateTime(LocalDateTime endDateTime) {
    if (endDateTime != null) {
      this.endDate = endDateTime.toLocalDate();
      this.endTime = endDateTime.toLocalTime();
    }
  }

  /**
   * Verifica se há interseção com outro intervalo.
   */
  public boolean intersects(IntervaloTemporalDTO other) {
    if (this.startDate == null || other.startDate == null) {
      return false;
    }
    // Considera o tempo para interseção
    LocalDateTime thisStart = getStartDateTime();
    LocalDateTime thisEnd = getEndDateTime();
    LocalDateTime otherStart = other.getStartDateTime();
    LocalDateTime otherEnd = other.getEndDateTime();

    return thisStart != null && otherEnd != null
        && thisStart.isBefore(otherEnd)
        && otherStart.isBefore(thisEnd);
  }

  /**
   * Retorna a data/hora de início como LocalDateTime.
   */
  public LocalDateTime getStartDateTime() {
    if (startDate == null || startTime == null) {
      return null;
    }
    return LocalDateTime.of(startDate, startTime);
  }

  /**
   * Retorna a data/hora de fim como LocalDateTime.
   */
  public LocalDateTime getEndDateTime() {
    if (endDate == null) {
      // Assume mesmo dia se endDate não informado
      if (endTime != null && startDate != null) {
        return LocalDateTime.of(startDate, endTime);
      }
      return null;
    }
    // Usa startTime se endTime não informado
    LocalTime time = endTime != null ? endTime : (startTime != null ? startTime : LocalTime.MIDNIGHT);
    return LocalDateTime.of(endDate, time);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startDate, startTime, endDate, endTime);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof IntervaloTemporalDTO)) {
      return false;
    }
    IntervaloTemporalDTO other = (IntervaloTemporalDTO) obj;
    return compareTo(other) == 0;
  }

  public int compareTo(IntervaloTemporalDTO other) {
    int result = Objects.compare(startDate, other.startDate,
                                java.time.LocalDate::compareTo);
    if (result != 0) {
      return result;
    }
    result = Objects.compare(startTime, other.startTime,
                           java.time.LocalTime::compareTo);
    if (result != 0) {
      return result;
    }
    result = Objects.compare(endDate, other.endDate,
                           java.time.LocalDate::compareTo);
    if (result != 0) {
      return result;
    }
    return Objects.compare(endTime, other.endTime,
                          java.time.LocalTime::compareTo);
  }

  @Override
  public IntervaloTemporalDTO cloneObject() {
    return toBuilder().build();
  }

  /**
   * Calcula a duração do intervalo.
   */
  public Duration duration() {
    LocalDateTime start = getStartDateTime();
    LocalDateTime end = getEndDateTime();
    if (start == null || end == null) {
      return Duration.ZERO;
    }
    return Duration.between(start, end);
  }

  /**
   * Campos para busca/filtro
   */
  public static class CAMPOS {
    public static final String START_DATE = "startDate";
    public static final String START_TIME = "startTime";
    public static final String END_DATE = "endDate";
    public static final String END_TIME = "endTime";
    // Para compatibilidade com código existente
    public static final String START_DATE_TIME = "startDateTime";
    public static final String END_DATE_TIME = "endDateTime";
  }

}
