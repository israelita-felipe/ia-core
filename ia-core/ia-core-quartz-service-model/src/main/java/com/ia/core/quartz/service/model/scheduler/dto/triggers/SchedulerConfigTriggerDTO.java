package com.ia.core.quartz.service.model.scheduler.triggers;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.ia.core.quartz.model.scheduler.SchedulerConfigTrigger;
import com.ia.core.service.dto.AbstractDTO;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

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
  extends AbstractDTO<SchedulerConfigTrigger> {
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
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private String calendarName;
  private Long misFireInstr;
  private Map<String, Object> jobData;

  public static final SearchRequestDTO getSearchRequest() {
    return new SchedulerConfigTriggerSearchRequest();
  }

  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  @Override
  public SchedulerConfigTriggerDTO cloneObject() {
    return toBuilder().build();
  }

  @Override
  public SchedulerConfigTriggerDTO copyObject() {
    return (SchedulerConfigTriggerDTO) super.copyObject();
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

  public static class CAMPOS
    extends AbstractBaseEntityDTO.CAMPOS {

    // Campos básicos do trigger
    public static final String TRIGGER_NAME = "triggerName";
    public static final String SCHEDULER_NAME = "schedulerName";
    public static final String TRIGGER_GROUP = "triggerGroup";
    public static final String JOB_NAME = "jobName";
    public static final String JOB_GROUP = "jobGroup";
    public static final String DESCRIPTION = "description";

    // Campos de tempo de execução
    public static final String NEXT_FIRE_TIME = "nextFireTime";
    public static final String PREV_FIRE_TIME = "prevFireTime";
    public static final String START_TIME = "startTime";
    public static final String END_TIME = "endTime";

    // Campos de configuração e estado
    public static final String PRIORITY = "priority";
    public static final String TRIGGER_STATE = "triggerState";
    public static final String TRIGGER_TYPE = "triggerType";
    public static final String CALENDAR_NAME = "calendarName";
    public static final String MISFIRE_INSTR = "misFireInstr";
    public static final String JOB_DATA = "jobData";

  }
}
