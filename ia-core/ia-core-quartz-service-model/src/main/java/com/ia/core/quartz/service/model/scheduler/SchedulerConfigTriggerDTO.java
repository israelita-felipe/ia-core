package com.ia.core.quartz.service.model.scheduler;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.Objects;

import com.ia.core.quartz.model.scheduler.SchedulerConfig;
import com.ia.core.service.dto.AbstractDTO;

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
public class SchedulerConfigTriggerDTO
  extends AbstractDTO<SchedulerConfig> {
  /** Serial UID */
  private static final long serialVersionUID = -19560738760061623L;

  private String triggerName;
  private String schedulerName;
  private String triggerGroup;
  private String jobName;
  private String jobGroup;
  private String description;
  private LocalDateTime nextFireTime;
  private LocalDateTime prevFireTime;
  private Long priority;
  private String triggerState;
  private String triggerType;
  private LocalTime startTime;
  private LocalTime endTime;
  private String calendarName;
  private Long misFireInstr;
  private Map<String, Object> jobData;

  @Override
  public SchedulerConfigTriggerDTO cloneObject() {
    return toBuilder().build();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (schedulerName == null && triggerName == null
        && triggerGroup == null) {
      return this == obj;
    }
    if (!(getClass().isInstance(obj))) {
      return false;
    }
    SchedulerConfigTriggerDTO other = (SchedulerConfigTriggerDTO) obj;
    return Objects.equals(schedulerName, other.schedulerName)
        && Objects.equals(triggerName, other.triggerName)
        && Objects.equals(triggerGroup, other.triggerGroup);
  }

  @Override
  public int hashCode() {
    if (schedulerName != null && triggerName != null
        && triggerGroup != null) {
      return Objects.hash(schedulerName, triggerName, triggerGroup);
    }
    return super.hashCode();
  }

}
