package com.ia.core.quartz.service.periodicidade.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.ia.core.model.HasVersion;
import com.ia.core.quartz.model.periodicidade.Periodicidade;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Israel Araújo
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
   * Request de pesquisa
   *
   * @return {@link SearchRequestDTO}
   */
  public static final SearchRequestDTO getSearchRequest() {
    return new PeriodicidadeSearchRequest();
  }

  /**
   * Filtros
   *
   * @return {@link Set} de filtros do DTO
   */
  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  @Default
  @NotNull(message = "{validation.periodicidade.intervaloBase.required}")
  private IntervaloTemporalDTO intervaloBase = new IntervaloTemporalDTO();

  @Default
  private RecorrenciaDTO regra = new RecorrenciaDTO();

  /**
   * Regra de exclusão de recorrência (EXRULE).
   * <p>
   * Equivalente ao parâmetro EXRULE da RFC 5545 (iCalendar).
   */
  @Default
  private ExclusaoRecorrenciaDTO exclusaoRecorrencia = new ExclusaoRecorrenciaDTO();

  @Default
  private String zoneId = ZoneId.systemDefault().getId();

  @Default
  private Set<LocalDate> exceptionDates = new HashSet<>();

  @Default
  private Set<LocalDate> includeDates = new HashSet<>();

  @Default
  private Boolean ativo = Boolean.TRUE;

  @Override
  public PeriodicidadeDTO cloneObject() {
    return toBuilder()
        .intervaloBase(intervaloBase != null ? intervaloBase.cloneObject()
                                             : null)
        .regra(regra != null ? regra.cloneObject() : null)
        .exclusaoRecorrencia(exclusaoRecorrencia != null
            ? exclusaoRecorrencia.cloneObject() : null).build();
  }

  @Override
  public PeriodicidadeDTO copyObject() {
    return toBuilder().id(null).version(HasVersion.DEFAULT_VERSION)
        .intervaloBase(intervaloBase != null ? intervaloBase.copyObject()
                                             : null)
        .regra(regra != null ? regra.copyObject() : null)
        .exclusaoRecorrencia(exclusaoRecorrencia != null
            ? exclusaoRecorrencia.copyObject() : null).build();
  }

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

    for (LocalDate exceptionDate : exceptionDates.stream().sorted(LocalDate::compareTo)
        .collect(Collectors.toList())) {
      for (LocalDate exceptionDateObj : p.exceptionDates.stream()
          .sorted(LocalDate::compareTo).collect(Collectors.toList())) {
        result = Boolean.TRUE.compareTo(Objects.equals(exceptionDate, exceptionDateObj));
        if (result != 0) {
          return result;
        }
      }
    }
    for (LocalDate includeDate : includeDates.stream().sorted(LocalDate::compareTo)
        .collect(Collectors.toList())) {
      for (LocalDate includeDateObj : p.includeDates.stream()
          .sorted(LocalDate::compareTo).collect(Collectors.toList())) {
        result = Boolean.TRUE.compareTo(Objects.equals(includeDate, includeDateObj));
        if (result != 0) {
          return result;
        }
      }
    }
    return super.compareTo(o);
  }

  public Duration duration() {
    return intervaloBase.duration();
  }

  public boolean hasRecurrence() {
    return regra != null;
  }

  public ZoneId getZoneIdValue() {
    return ZoneId.of(zoneId);
  }

  @SuppressWarnings("javadoc")
  public static class CAMPOS
    extends AbstractBaseEntityDTO.CAMPOS {
    public static final String ATIVO = "ativo";
    public static final String INTERVALO_BASE = "intervaloBase";
    public static final String REGRA = "regra";
    public static final String EXCLUSAO_RECORRENCIA = "exclusaoRecorrencia";
    public static final String ZONE_ID = "zoneId";
    public static final String EXCEPTION_DATES = "exceptionDates";
    public static final String INCLUDE_DATES = "includeDates";
  }

}
