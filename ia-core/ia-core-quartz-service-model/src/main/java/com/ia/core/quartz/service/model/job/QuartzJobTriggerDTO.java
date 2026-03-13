package com.ia.core.quartz.service.model.job;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.ia.core.service.dto.AbstractDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * DTO para representação de um Trigger do Quartz associado a um Job.
 * 
 * @author Israel Araújo
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class QuartzJobTriggerDTO extends AbstractDTO<QuartzJobTriggerDTO>
  implements Serializable {
  /** Serial UID */
  private static final long serialVersionUID = -19560738760061625L;

  private String triggerName;
  private String triggerGroup;
  private String jobName;
  private String jobGroup;
  private String description;
  private String triggerState;
  private String triggerType;
  private String calendarName;
  
  // Timestamps
  private LocalDateTime nextFireTime;
  private LocalDateTime prevFireTime;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private LocalDateTime finalFireTime;
  
  // Configurações
  private Long misFireInstr;
  private Integer priority;
  private Integer repeatCount;
  private Long repeatInterval;
  private Integer timesTriggered;
  
  // Dados do job
  private Map<String, Object> jobData;

  public static final SearchRequestDTO getSearchRequest() {
    return new QuartzJobTriggerSearchRequest();
  }

  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  @Override
  public QuartzJobTriggerDTO cloneObject() {
    return toBuilder().build();
  }

  @Override
  public QuartzJobTriggerDTO copyObject() {
    return toBuilder().jobData(null).build();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (triggerName == null && triggerGroup == null) {
      return this == obj;
    }
    if (!(getClass().isInstance(obj))) {
      return false;
    }
    QuartzJobTriggerDTO other = (QuartzJobTriggerDTO) obj;
    return Objects.equals(triggerName, other.triggerName)
        && Objects.equals(triggerGroup, other.triggerGroup);
  }

  @Override
  public int hashCode() {
    if (triggerName != null && triggerGroup != null) {
      return Objects.hash(triggerName, triggerGroup);
    }
    return super.hashCode();
  }

  public static class CAMPOS {
    public static final String TRIGGER_NAME = "triggerName";
    public static final String TRIGGER_GROUP = "triggerGroup";
    public static final String JOB_NAME = "jobName";
    public static final String JOB_GROUP = "jobGroup";
    public static final String DESCRIPTION = "description";
    public static final String TRIGGER_STATE = "triggerState";
    public static final String TRIGGER_TYPE = "triggerType";
    public static final String CALENDAR_NAME = "calendarName";
    public static final String NEXT_FIRE_TIME = "nextFireTime";
    public static final String PREV_FIRE_TIME = "prevFireTime";
    public static final String START_TIME = "startTime";
    public static final String END_TIME = "endTime";
    public static final String FINAL_FIRE_TIME = "finalFireTime";
    public static final String MISFIRE_INSTR = "misFireInstr";
    public static final String PRIORITY = "priority";
    public static final String REPEAT_COUNT = "repeatCount";
    public static final String REPEAT_INTERVAL = "repeatInterval";
    public static final String TIMES_TRIGGERED = "timesTriggered";
    public static final String JOB_DATA = "jobData";
  }
}
