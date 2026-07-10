package com.ia.core.quartz.service.model.periodicidade.dto;

import com.ia.core.quartz.model.periodicidade.IntervaloTemporal;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

/**
 * DTO para intervalo temporal de eventos.
 * <p>
 * Representa um intervalo de tempo (DTSTART/DTEND) conforme especificação RFC 5545 (iCalendar).
 * Suporta modelagem de eventos com:
 * <ul>
 * <li>Data e hora separadas para início e fim</li>
 * <li>Eventos que passam da meia-noite (endDate diferente de startDate)</li>
 * <li>Eventos com apenas horário (sem data de fim explícita)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @see IntervaloTemporal
 * @see PeriodicidadeDTO
 * @since 1.0.0
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class IntervaloTemporalDTO
  implements DTO<IntervaloTemporal>, Comparable<IntervaloTemporalDTO> {

  /** Serial UID */
  private static final long serialVersionUID = 1L;

  /**
   * Data de início do intervalo.
   * <p>
   * Equivalente ao componente de data do DTSTART (RFC 5545).
   */
  @NotNull(message = PeriodicidadeTranslator.VALIDATION.START_DATE_REQUIRED)
  private LocalDate startDate;

  /**
   * Hora de início do intervalo.
   * <p>
   * Equivalente ao componente de hora do DTSTART (RFC 5545).
   */
  @NotNull(message = PeriodicidadeTranslator.VALIDATION.START_TIME_REQUIRED)
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
   * Retorna o request de pesquisa para este DTO.
   *
   * @return request de pesquisa
   */
  public static SearchRequestDTO getSearchRequest() {
    return new SearchRequestDTO();
  }

  /**
   * Retorna os filtros de propriedade disponíveis para pesquisa.
   *
   * @return conjunto de nomes de campos para filtro
   */
  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  /**
   * Verifica se há interseção temporal com outro intervalo.
   * <p>
   * Compara os períodos de tempo considerando data e hora.
   * Usa LocalTime.MIN/MAX como fallback quando horas não são especificadas.
   *
   * @param other o outro intervalo a ser comparado
   * @return true se houver interseção, false caso contrário
   */
  public boolean intersects(IntervaloTemporalDTO other) {
    if (this.startDate == null || other.startDate == null) {
      return false;
    }
    // Considera o tempo para interseção
    LocalDate thisStartDate = this.startDate;
    LocalTime thisStartTime = this.startTime != null ? this.startTime
                                                     : LocalTime.MIN;
    LocalDate thisEndDate = this.endDate != null ? this.endDate
                                                 : this.startDate;
    LocalTime thisEndTime = this.endTime != null ? this.endTime
                                                 : LocalTime.MAX;

    LocalDate otherStartDate = other.startDate;
    LocalTime otherStartTime = other.startTime != null ? other.startTime
                                                       : LocalTime.MIN;
    LocalDate otherEndDate = other.endDate != null ? other.endDate
                                                   : other.startDate;
    LocalTime otherEndTime = other.endTime != null ? other.endTime
                                                   : LocalTime.MAX;

    // Compara datas
    if (thisEndDate.isBefore(otherStartDate)
        || otherEndDate.isBefore(thisStartDate)) {
      return false;
    }
    // Mesmo dia, verifica horário
    if (thisEndDate.isEqual(otherStartDate)
        && otherEndDate.isEqual(thisStartDate)) {
      return thisStartTime.isBefore(otherEndTime)
          && otherStartTime.isBefore(thisEndTime);
    }
    return true;
  }

  /**
   * Cria uma cópia superficial (clone) deste objeto DTO.
   *
   * @return novo objeto com os mesmos valores
   */
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
   * @return valor negativo se este intervalo for menor, zero se igual, positivo
   *         se maior
   */
  @Override
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
   * Constantes para nomes dos campos deste DTO.
   */
  @SuppressWarnings("javadoc")
  public static class CAMPOS {

    /** Data de início */
    public static final String START_DATE = "startDate";

    /** Hora de início */
    public static final String START_TIME = "startTime";

    /** Data de fim */
    public static final String END_DATE = "endDate";

    /** Hora de fim */
    public static final String END_TIME = "endTime";

    /**
     * Retorna todos os nomes de campos deste DTO.
     *
     * @return conjunto de strings com os nomes dos campos
     */
    public static Set<String> values() {
        return Set.of(START_DATE, START_TIME, END_DATE, END_TIME);
    }
  }

  /**
   * Representação textual do intervalo temporal.
   *
   * @return string formatada com início e fim
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Início: ");
    if (startDate != null) {
      builder.append(startDate);
      builder.append("(");
      builder.append(startDate.getDayOfWeek());
      builder.append(")");
    }
    if (startTime != null) {
      builder.append(" ");
      builder.append(startTime);
    }
    builder.append(" - ");
    builder.append("Fim: ");
    if (endDate != null) {
      builder.append(endDate);
      builder.append("(");
      builder.append(endDate.getDayOfWeek());
      builder.append(")");
    }
    if (endTime != null) {
      builder.append(" ");
      builder.append(endTime);
    }
    return builder.toString();
  }
}
