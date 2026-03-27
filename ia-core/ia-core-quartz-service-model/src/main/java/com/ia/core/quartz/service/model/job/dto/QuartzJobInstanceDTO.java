package com.ia.core.quartz.service.model.job;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import com.ia.core.service.dto.AbstractDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * DTO para representação de uma execução de Job do Quartz.
 * 
 * @author Israel Araújo
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class QuartzJobInstanceDTO extends AbstractDTO<QuartzJobInstanceDTO>
  implements Serializable {
  /** Serial UID */
  private static final long serialVersionUID = -19560738760061626L;

  private String instanceId;
  private String jobName;
  private String jobGroup;
  private String triggerName;
  private String triggerGroup;
  
  // Timestamps
  private LocalDateTime fireTime;
  private LocalDateTime scheduledFireTime;
  private LocalDateTime completedExecutionTime;
  
  // Resultado
  private String result;
  private boolean jobExecuted;
  private String exceptionMessage;
  
  // Dados
  private Map<String, Object> jobDataMap;

  @Builder.Default
  private boolean recovered = false;

  public static final SearchRequestDTO getSearchRequest() {
    return new QuartzJobInstanceSearchRequest();
  }

  @Override
  public QuartzJobInstanceDTO cloneObject() {
    return toBuilder().build();
  }

  @Override
  public QuartzJobInstanceDTO copyObject() {
    return toBuilder().jobDataMap(null).build();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (instanceId == null && fireTime == null) {
      return this == obj;
    }
    if (!(getClass().isInstance(obj))) {
      return false;
    }
    QuartzJobInstanceDTO other = (QuartzJobInstanceDTO) obj;
    return Objects.equals(instanceId, other.instanceId)
        && Objects.equals(fireTime, other.fireTime);
  }

  @Override
  public int hashCode() {
    if (instanceId != null && fireTime != null) {
      return Objects.hash(instanceId, fireTime);
    }
    return super.hashCode();
  }

  public static class CAMPOS {
    public static final String INSTANCE_ID = "instanceId";
    public static final String JOB_NAME = "jobName";
    public static final String JOB_GROUP = "jobGroup";
    public static final String TRIGGER_NAME = "triggerName";
    public static final String TRIGGER_GROUP = "triggerGroup";
    public static final String FIRE_TIME = "fireTime";
    public static final String SCHEDULED_FIRE_TIME = "scheduledFireTime";
    public static final String COMPLETED_EXECUTION_TIME = "completedExecutionTime";
    public static final String RESULT = "result";
    public static final String JOB_EXECUTED = "jobExecuted";
    public static final String EXCEPTION_MESSAGE = "exceptionMessage";
    public static final String JOB_DATA_MAP = "jobDataMap";
    public static final String RECOVERED = "recovered";
  }
}
