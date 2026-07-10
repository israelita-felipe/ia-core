package com.ia.core.quartz.service.model.periodicidade.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ia.core.quartz.model.periodicidade.Periodicidade;
import com.ia.core.quartz.service.model.recorrencia.dto.ExclusaoRecorrenciaDTO;
import com.ia.core.quartz.service.model.recorrencia.dto.RecorrenciaDTO;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DTO para representação de periodicidade de agendamento.
 * <p>
 * Representa a periodicidade de um job no sistema Quartz, incluindo:
 * <ul>
 * <li>Intervalo temporal base (data/hora início e fim)</li>
 * <li>Regras de recorrência conforme RFC 5545 (RRULE)</li>
 * <li>Regras de exclusão de recorrência (EXRULE)</li>
 * <li>Datas de exceção (EXDATE)</li>
 * <li>Datas de inclusão (RDATE)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @see Periodicidade
 * @see PeriodicidadeTranslator
 * @since 1.0.0
 */
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PeriodicidadeDTO
  extends AbstractBaseEntityDTO<Periodicidade> {

  /** Serial UID */
  private static final long serialVersionUID = 8959576762018688242L;

  /**
   * Retorna o request de pesquisa para este DTO.
   *
   * @return request de pesquisa
   */
  public static SearchRequestDTO getSearchRequest() {
    return new PeriodicidadeSearchRequestDTO();
  }

  /**
   * Retorna os filtros de propriedade para pesquisa.
   *
   * @return conjunto de filtros
   */
  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  /**
   * Intervalo temporal base da periodicidade.
   */
  @Default
  @NotNull(message = PeriodicidadeTranslator.VALIDATION.INTERVALO_BASE_REQUIRED)
  private IntervaloTemporalDTO intervaloBase = new IntervaloTemporalDTO();

  /**
   * Regra de recorrência.
   */
  @Default
  private RecorrenciaDTO regra = new RecorrenciaDTO();

  /**
   * Regra de exclusão de recorrência (EXRULE).
   * <p>
   * Equivalente ao parâmetro EXRULE da RFC 5545 (iCalendar).
   */
  @Default
  private ExclusaoRecorrenciaDTO exclusaoRecorrencia = new ExclusaoRecorrenciaDTO();

  /**
   * Fuso horário da periodicidade.
   */
  @Default
  private String zoneId = ZoneId.systemDefault().getId();

  /**
   * Datas de exceção da periodicidade.
   */
  @Default
  private Set<LocalDate> exceptionDates = new HashSet<>();

  /**
   * Datas de inclusão da periodicidade.
   */
  @Default
  private Set<LocalDate> includeDates = new HashSet<>();

  /**
   * Indica se a periodicidade está ativa.
   */
  @Default
  private Boolean ativo = Boolean.TRUE;

  /**
   * Cria uma cópia superficial (clone) deste objeto DTO.
   *
   * @return novo objeto com os mesmos valores
   */
  @Override
  public PeriodicidadeDTO cloneObject() {
    return toBuilder()
        .intervaloBase(intervaloBase != null ? intervaloBase.cloneObject()
                                            : null)
        .regra(regra != null ? regra.cloneObject() : null)
        .exclusaoRecorrencia(exclusaoRecorrencia != null ? exclusaoRecorrencia
            .cloneObject() : null)
        .build();
  }

  /**
   * Cria uma cópia deste objeto DTO com id e version nulos.
   *
   * @return cópia do objeto
   */
  @Override
  public PeriodicidadeDTO copyObject() {
    return (PeriodicidadeDTO) super.copyObject();
  }

  /**
   * Compara este objeto com outro para ordenação.
   *
   * @param o o objeto a ser comparado
   * @return valor negativo, zero ou positivo se este objeto for menos, igual ou maior
   */
  @Override
  public int compareTo(AbstractBaseEntityDTO<Periodicidade> o) {
    PeriodicidadeDTO p = (PeriodicidadeDTO) o;
    int result = Objects.compare(intervaloBase, p.intervaloBase,
                                 IntervaloTemporalDTO::compareTo);
    if (result != 0) {
      return result;
    }
    result = Objects.compare(regra, p.regra, RecorrenciaDTO::compareTo);
    if (result != 0) {
      return result;
    }
    result = Objects.compare(exclusaoRecorrencia, p.exclusaoRecorrencia,
                             ExclusaoRecorrenciaDTO::compareTo);
    if (result != 0) {
      return result;
    }
    result = zoneId.compareTo(p.zoneId);
    if (result != 0) {
      return result;
    }

    for (LocalDate exceptionDate : exceptionDates.stream()
        .sorted(LocalDate::compareTo).collect(Collectors.toList())) {
      for (LocalDate exceptionDateObj : p.exceptionDates.stream()
          .sorted(LocalDate::compareTo).collect(Collectors.toList())) {
        result = Boolean.TRUE
            .compareTo(Objects.equals(exceptionDate, exceptionDateObj));
        if (result != 0) {
          return result;
        }
      }
    }
    for (LocalDate includeDate : includeDates.stream()
        .sorted(LocalDate::compareTo).collect(Collectors.toList())) {
      for (LocalDate includeDateObj : p.includeDates.stream()
          .sorted(LocalDate::compareTo).collect(Collectors.toList())) {
        result = Boolean.TRUE
            .compareTo(Objects.equals(includeDate, includeDateObj));
        if (result != 0) {
          return result;
        }
      }
    }
    return super.compareTo(o);
  }

  /**
   * Calcula a duração da periodicidade.
   *
   * @return duração calculada
   */
  public Duration duration() {
    if (intervaloBase == null) {
      return Duration.ZERO;
    }

    java.time.LocalDateTime start;
    java.time.LocalDateTime end;

    if (intervaloBase.getStartDate() != null
        && intervaloBase.getStartTime() != null) {
      start = java.time.LocalDateTime.of(intervaloBase.getStartDate(),
                                         intervaloBase.getStartTime());
    } else {
      return Duration.ZERO;
    }

    if (intervaloBase.getEndDate() != null
        && intervaloBase.getEndTime() != null) {
      end = java.time.LocalDateTime.of(intervaloBase.getEndDate(),
                                       intervaloBase.getEndTime());
    } else if (intervaloBase.getEndTime() != null) {
      end = java.time.LocalDateTime.of(intervaloBase.getStartDate(),
                                       intervaloBase.getEndTime());
    } else {
      end = start.plusHours(1);
    }

    return Duration.between(start, end);
  }

  /**
   * Verifica se há regra de recorrência definida.
   *
   * @return true se há recorrência, false caso contrário
   */
  public boolean hasRecurrence() {
    if (regra == null) {
      return false;
    }
    return regra.getFrequency() != null;
  }

  /**
   * Retorna o ZoneId a partir do campo zoneId.
   *
   * @return ZoneId convertido
   */
  @JsonIgnore
  public ZoneId getZoneIdValue() {
    return ZoneId.of(zoneId);
  }

  /**
   * Constantes para nomes dos campos deste DTO.
   */
  @SuppressWarnings("javadoc")
  public static class CAMPOS
    extends AbstractBaseEntityDTO.CAMPOS {

    /** Intervalo temporal base */
    public static final String INTERVALO_BASE = "intervaloBase";

    /** Regra de recorrência */
    public static final String REGRA = "regra";

    /** Regra de exclusão */
    public static final String EXCLUSAO_RECORRENCIA = "exclusaoRecorrencia";

    /** Fuso horário */
    public static final String ZONE_ID = "zoneId";

    /** Datas de exceção */
    public static final String EXCEPTION_DATES = "exceptionDates";

    /** Datas de inclusão */
    public static final String INCLUDE_DATES = "includeDates";

    /** Status ativo */
    public static final String ATIVO = "ativo";

    // Nested path constants for search filters
    /** Path para startTime do intervaloBase */
    public static final String INTERVALO_BASE_START_TIME = "intervaloBase.startTime";

    /** Path para endTime do intervaloBase */
    public static final String INTERVALO_BASE_END_TIME = "intervaloBase.endTime";

    /** Path para frequency da regra */
    public static final String REGRA_FREQUENCY = "regra.frequency";

    /** Path para intervalValue da regra */
    public static final String REGRA_INTERVAL_VALUE = "regra.intervalValue";

    /**
     * Retorna todos os nomes de campos deste DTO incluindo os da superclasse.
     *
     * @return conjunto de strings com os nomes dos campos
     */
    public static Set<String> values() {
      var baseValues = AbstractBaseEntityDTO.CAMPOS.values();
      var currentValues = Set.of(ATIVO, INTERVALO_BASE, REGRA,
          EXCLUSAO_RECORRENCIA, ZONE_ID, EXCEPTION_DATES, INCLUDE_DATES);
      var allValues = new HashSet<String>();
      allValues.addAll(baseValues);
      allValues.addAll(currentValues);
      return Collections.unmodifiableSet(allValues);
    }
  }
}
