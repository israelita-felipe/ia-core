package com.ia.core.quartz.service.periodicidade.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
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
  implements DTO<IntervaloTemporal>, Comparable<IntervaloTemporalDTO> {

  /** Serial UID */
  private static final long serialVersionUID = 1L;

  /**
   * Data de início. Equivalente ao componente de data do DTSTART (RFC 5545).
   */
  @NotNull(message = "{validation.periodicidade.intervaloBase.startDate.required}")
  private LocalDate startDate;

  /**
   * Hora de início. Equivalente ao componente de hora do DTSTART (RFC 5545).
   */
  @NotNull(message = "{validation.periodicidade.intervaloBase.startTime.required}")
  private LocalTime startTime;

  /**
   * Data de fim. Equivalente ao componente de data do DTEND (RFC 5545).
   */
  private LocalDate endDate;

  /**
   * Hora de fim. Equivalente ao componente de hora do DTEND (RFC 5545).
   */
  private LocalTime endTime;

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
   * Verifica se há interseção com outro intervalo.
   */
  public boolean intersects(IntervaloTemporalDTO other) {
    if (this.startDate == null || other.startDate == null) {
      return false;
    }
    // Considera o tempo para interseção
    LocalDate thisStartDate = this.startDate;
    LocalTime thisStartTime = this.startTime != null ? this.startTime : LocalTime.MIN;
    LocalDate thisEndDate = this.endDate != null ? this.endDate : this.startDate;
    LocalTime thisEndTime = this.endTime != null ? this.endTime : LocalTime.MAX;
    
    LocalDate otherStartDate = other.startDate;
    LocalTime otherStartTime = other.startTime != null ? other.startTime : LocalTime.MIN;
    LocalDate otherEndDate = other.endDate != null ? other.endDate : other.startDate;
    LocalTime otherEndTime = other.endTime != null ? other.endTime : LocalTime.MAX;

    // Compara datas
    if (thisEndDate.isBefore(otherStartDate) || otherEndDate.isBefore(thisStartDate)) {
      return false;
    }
    // Mesmo dia, verifica horário
    if (thisEndDate.isEqual(otherStartDate) && otherEndDate.isEqual(thisStartDate)) {
      return thisStartTime.isBefore(otherEndTime) && otherStartTime.isBefore(thisEndTime);
    }
    return true;
  }

  @Override
  public IntervaloTemporalDTO cloneObject() {
    return toBuilder().build();
  }

  /**
   * Compara este intervalo com outro para ordenação.
   * <p>
   * A comparação é feita na ordem: startDate, startTime, endDate, endTime.
   *
   * @param other o intervalo a ser comparado
   * @return valor negativo se este intervalo for menor, zero se igual, positivo se maior
   */
  public int compareTo(IntervaloTemporalDTO other) {
    if (other == null) {
      return 1;
    }
    
    // Compara startDate
    int result = Objects.compare(startDate, other.startDate,
        java.time.LocalDate::compareTo);
    if (result != 0) {
      return result;
    }
    
    // Compara startTime
    result = Objects.compare(startTime, other.startTime,
        java.time.LocalTime::compareTo);
    if (result != 0) {
      return result;
    }
    
    // Compara endDate
    result = Objects.compare(endDate, other.endDate,
        java.time.LocalDate::compareTo);
    if (result != 0) {
      return result;
    }
    
    // Compara endTime
    return Objects.compare(endTime, other.endTime,
        java.time.LocalTime::compareTo);
  }

  /**
   * Campos para busca/filtro
   */
  public static class CAMPOS {
    public static final String START_DATE = "startDate";
    public static final String START_TIME = "startTime";
    public static final String END_DATE = "endDate";
    public static final String END_TIME = "endTime";
  }

}
