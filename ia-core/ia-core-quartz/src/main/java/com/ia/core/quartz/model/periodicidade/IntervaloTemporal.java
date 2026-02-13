package com.ia.core.quartz.model.periodicidade;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class IntervaloTemporal
  implements Serializable {

  @Column(name = "start_time")
  private LocalTime startTime;
  @Column(name = "end_time")
  private LocalTime endTime;

  public IntervaloTemporal(LocalTime startTime, LocalTime endTime) {

    if (endTime.isBefore(startTime))
      throw new IllegalArgumentException();
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public Duration duration() {
    return Duration.between(startTime, endTime);
  }

  public boolean intersects(IntervaloTemporal other) {
    return !endTime.isBefore(other.startTime)
        && !other.endTime.isBefore(startTime);
  }
}
