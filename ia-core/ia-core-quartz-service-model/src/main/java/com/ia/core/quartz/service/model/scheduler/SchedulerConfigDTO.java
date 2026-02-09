package com.ia.core.quartz.service.model.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ia.core.model.HasVersion;
import com.ia.core.quartz.model.scheduler.SchedulerConfig;
import com.ia.core.quartz.service.model.scheduler.triggers.SchedulerConfigTriggerDTO;
import com.ia.core.quartz.service.periodicidade.dto.PeriodicidadeDTO;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Israel Araújo
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SchedulerConfigDTO
  extends AbstractBaseEntityDTO<SchedulerConfig> {
  /** Serial UID */
  private static final long serialVersionUID = -19560738760061623L;

  public static final SearchRequestDTO getSearchRequest() {
    return new SchedulerConfigSearchRequest();
  }

  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  /**
   * Nome da classe do Job
   */
  @NotNull(message = "{validation.scheduler.jobClassName.required}")
  @Pattern(regexp = "^[a-zA-Z0-9_.]+$", message = "{validation.scheduler.jobClassName.pattern}")
  private String jobClassName;

  @NotNull(message = "{validation.scheduler.periodicidade.required}")
  @Default
  private PeriodicidadeDTO periodicidade = PeriodicidadeDTO.builder()
      .build();

  @Default
  private List<SchedulerConfigTriggerDTO> triggers = new ArrayList<>();

  @Override
  public SchedulerConfigDTO cloneObject() {
    return toBuilder().periodicidade(periodicidade.cloneObject())
        .triggers(new ArrayList<>(triggers.stream()
            .map(SchedulerConfigTriggerDTO::cloneObject).toList()))
        .build();
  }

  @Override
  public SchedulerConfigDTO copyObject() {
    return toBuilder().id(null).version(HasVersion.DEFAULT_VERSION)
        .periodicidade(periodicidade.cloneObject())
        .triggers(new ArrayList<>()).build();
  }

  @JsonIgnore
  public Class<?> getJobClassNameAsClass()
    throws ClassNotFoundException {
    return Class.forName(jobClassName);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (id == null) {
      return this == obj;
    }
    if (!(getClass().isInstance(obj))) {
      return false;
    }
    SchedulerConfigDTO other = (SchedulerConfigDTO) obj;
    return Objects.equals(id, other.id);
  }

  @Override
  public int hashCode() {
    if (id != null) {
      return Objects.hash(id);
    }
    return super.hashCode();
  }

  @Override
  public String toString() {
    return String.format("%s (%s)", periodicidade);
  }

  @SuppressWarnings("javadoc")
  public static class CAMPOS
    extends AbstractBaseEntityDTO.CAMPOS {
    public static final String PERIODICIDADE = "periodicidade";
    public static final String JOB_CLASS_NAME = "jobClassName";
  }
}
