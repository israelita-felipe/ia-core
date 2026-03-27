package com.ia.core.quartz.service.model.job.dto;

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
 * DTO para representação de um Job do Quartz.
 *
 * @author Israel Araújo
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class QuartzJobDTO
  extends AbstractDTO<QuartzJobDTO>
  implements Serializable {
  /** Serial UID */
  private static final long serialVersionUID = -19560738760061624L;

  private String jobName;
  private String jobGroup;
  private String description;
  private String jobClassName;
  private boolean durable;
  private boolean requestsRecovery;
  private Map<String, Object> jobData;

  // Informações de estado
  private String jobState;
  private int numberOfExecutions;
  private LocalDateTime lastExecutionTime;
  private LocalDateTime nextExecutionTime;
  private LocalDateTime prevFireTime;

  public static final SearchRequestDTO getSearchRequest() {
    return new QuartzJobSearchRequest();
  }

  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  @Override
  public QuartzJobDTO cloneObject() {
    return toBuilder().build();
  }

  @Override
  public QuartzJobDTO copyObject() {
    return toBuilder().jobData(null).build();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (jobName == null && jobGroup == null) {
      return this == obj;
    }
    if (!(getClass().isInstance(obj))) {
      return false;
    }
    QuartzJobDTO other = (QuartzJobDTO) obj;
    return Objects.equals(jobName, other.jobName)
        && Objects.equals(jobGroup, other.jobGroup);
  }

  @Override
  public int hashCode() {
    if (jobName != null && jobGroup != null) {
      return Objects.hash(jobName, jobGroup);
    }
    return super.hashCode();
  }

  public static class CAMPOS {
    public static final String JOB_NAME = "jobName";
    public static final String JOB_GROUP = "jobGroup";
    public static final String DESCRIPTION = "description";
    public static final String JOB_CLASS_NAME = "jobClassName";
    public static final String IS_DURABLE = "durable";
    public static final String REQUESTS_RECOVERY = "requestsRecovery";
    public static final String JOB_DATA = "jobData";
    public static final String JOB_STATE = "jobState";
    public static final String NUMBER_OF_EXECUTIONS = "numberOfExecutions";
    public static final String LAST_EXECUTION_TIME = "lastExecutionTime";
    public static final String NEXT_EXECUTION_TIME = "nextExecutionTime";
    public static final String PREV_FIRE_TIME = "prevFireTime";
  }
}
