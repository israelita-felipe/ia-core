package com.ia.core.quartz.service.periodicidade.dto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import com.ia.core.quartz.model.periodicidade.IntervaloTemporal;
import com.ia.core.service.dto.DTO;

import lombok.AllArgsConstructor;
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
public class IntervaloTemporalDTO
  implements DTO<IntervaloTemporal> {

  /** Serial UID */
  private static final long serialVersionUID = 1L;

  private LocalDateTime startTime;
  private LocalDateTime endTime;

  public int compareTo(IntervaloTemporalDTO other) {
    int result = Objects.compare(startTime, other.startTime,
                                 LocalDateTime::compareTo);
    if (result != 0) {
      return result;
    }
    return Objects.compare(endTime, other.endTime,
                           LocalDateTime::compareTo);
  }

  @Override
  public IntervaloTemporalDTO cloneObject() {
    return toBuilder().build();
  }

  public Duration duration() {
    return Duration.between(startTime, endTime);
  }

  public boolean intersects(IntervaloTemporalDTO other) {
    return !endTime.isBefore(other.startTime)
        && !other.endTime.isBefore(startTime);
  }

  /**
   * Campos para busca/filtro
   */
  public static class CAMPOS {
    public static final String START_TIME = "startTime";
    public static final String END_TIME = "endTime";
  }

}
