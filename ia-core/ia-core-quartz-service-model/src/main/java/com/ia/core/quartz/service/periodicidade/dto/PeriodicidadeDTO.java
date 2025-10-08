package com.ia.core.quartz.service.periodicidade.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.ia.core.model.HasVersion;
import com.ia.core.quartz.model.periodicidade.OcorrenciaSemanal;
import com.ia.core.quartz.model.periodicidade.Periodicidade;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

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
  private static final long serialVersionUID = 6477113455992137531L;

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
  private boolean ativo = true;
  @Default
  private boolean diaTodo = false;
  @Default
  private boolean periodico = false;
  @Default
  private boolean intervaloTempo = false;
  private LocalDate dataInicio;
  private LocalTime horaInicio;
  private LocalDate dataFim;
  private LocalTime horaFim;
  private LocalTime tempoIntervalo;
  @Default
  private Set<DayOfWeek> dias = new HashSet<>();
  @Default
  private Set<Month> meses = new HashSet<>();

  @Default
  private Set<OcorrenciaSemanal> ocorrenciaSemanal = new HashSet<>();

  @Default
  private Set<Integer> ocorrenciaDiaria = new HashSet<>();

  @Override
  public PeriodicidadeDTO cloneObject() {
    return toBuilder().dias(new HashSet<>(dias)).meses(new HashSet<>(meses))
        .ocorrenciaDiaria(new HashSet<>(ocorrenciaDiaria))
        .ocorrenciaSemanal(new HashSet<>(ocorrenciaSemanal)).build();
  }

  @Override
  public PeriodicidadeDTO copyObject() {
    return toBuilder().id(null).version(HasVersion.DEFAULT_VERSION)
        .dias(new HashSet<>(dias)).meses(new HashSet<>(meses))
        .ocorrenciaDiaria(new HashSet<>(ocorrenciaDiaria))
        .ocorrenciaSemanal(new HashSet<>(ocorrenciaSemanal)).build();
  }

  @Override
  public int compareTo(AbstractBaseEntityDTO<Periodicidade> o) {
    PeriodicidadeDTO p = (PeriodicidadeDTO) o;
    int result = Boolean.valueOf(ativo).compareTo(p.ativo);
    if (result != 0) {
      return result;
    }
    result = Boolean.valueOf(diaTodo).compareTo(p.diaTodo);
    if (result != 0) {
      return result;
    }
    result = Boolean.valueOf(periodico).compareTo(p.periodico);
    if (result != 0) {
      return result;
    }
    result = Boolean.valueOf(intervaloTempo).compareTo(p.intervaloTempo);
    if (result != 0) {
      return result;
    }
    result = Objects.compare(dataInicio, p.dataInicio,
                             LocalDate::compareTo);
    if (result != 0) {
      return result;
    }
    result = Objects.compare(dataFim, p.dataFim, LocalDate::compareTo);
    if (result != 0) {
      return result;
    }
    result = Objects.compare(horaInicio, p.horaInicio,
                             LocalTime::compareTo);
    if (result != 0) {
      return result;
    }
    result = Objects.compare(horaFim, p.horaFim, LocalTime::compareTo);
    if (result != 0) {
      return result;
    }

    result = Objects.compare(tempoIntervalo, p.tempoIntervalo,
                             LocalTime::compareTo);
    if (result != 0) {
      return result;
    }

    for (DayOfWeek dia : dias.stream().sorted(DayOfWeek::compareTo)
        .collect(Collectors.toList())) {
      for (DayOfWeek diaObject : p.dias.stream()
          .sorted(DayOfWeek::compareTo).collect(Collectors.toList())) {
        result = Boolean.TRUE.compareTo(Objects.equals(dia, diaObject));
        if (result != 0) {
          return result;
        }
      }
    }
    for (Month dia : meses.stream().sorted(Month::compareTo)
        .collect(Collectors.toList())) {
      for (Month diaObject : p.meses.stream().sorted(Month::compareTo)
          .collect(Collectors.toList())) {
        result = Boolean.TRUE.compareTo(Objects.equals(dia, diaObject));
        if (result != 0) {
          return result;
        }
      }
    }
    for (OcorrenciaSemanal dia : ocorrenciaSemanal.stream()
        .sorted(OcorrenciaSemanal::compareTo)
        .collect(Collectors.toList())) {
      for (OcorrenciaSemanal diaObject : p.ocorrenciaSemanal.stream()
          .sorted(OcorrenciaSemanal::compareTo)
          .collect(Collectors.toList())) {
        result = Boolean.TRUE.compareTo(Objects.equals(dia, diaObject));
        if (result != 0) {
          return result;
        }
      }
    }
    for (Integer dia : ocorrenciaDiaria.stream().sorted(Integer::compareTo)
        .collect(Collectors.toList())) {
      for (Integer diaObject : p.ocorrenciaDiaria.stream()
          .sorted(Integer::compareTo).collect(Collectors.toList())) {
        result = Boolean.TRUE.compareTo(Objects.equals(dia, diaObject));
        if (result != 0) {
          return result;
        }
      }
    }
    return super.compareTo(o);
  }

  @SuppressWarnings("javadoc")
  public static class CAMPOS
    extends AbstractBaseEntityDTO.CAMPOS {
    public static final String DATA_INICIO = "dataInicio";
    public static final String DATA_FIM = "dataFim";
    public static final String HORA_INICIO = "horaInicio";
    public static final String HORA_FIM = "horaFim";
    public static final String ATIVO = "ativo";
    public static final String DIA_TODO = "diaTodo";
    public static final String PERIODICO = "periodico";
    public static final String INTERVALO_TEMPO = "intervaloTempo";
    public static final String TEMPO_INTERVALO = "tempoIntervalo";
    public static final String DIAS = "dias";
    public static final String MESES = "meses";
    public static final String OCORRENCIA_SEMANAL = "ocorrenciaSemanal";
    public static final String OCORRENCIA_DIARIA = "ocorrenciaDiaria";
  }

}
