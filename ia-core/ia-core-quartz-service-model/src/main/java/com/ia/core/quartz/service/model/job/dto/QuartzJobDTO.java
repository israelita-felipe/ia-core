package com.ia.core.quartz.service.model.job.dto;

import com.ia.core.service.dto.AbstractDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * DTO para representação de um Job do Quartz.
 * <p>
 * Representa as configurações de um job agendado no sistema Quartz,
 * incluindo nome, grupo, descrição, classe e triggers associados.
 *
 * @author Israel Araújo
 * @see QuartzJobTranslator
 * @since 1.0.0
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

  /**
   * Nome do job.
   */
  private String jobName;

  /**
   * Grupo do job.
   */
  private String jobGroup;

  /**
   * Descrição do job.
   */
  private String description;

  /**
   * Nome da classe do job.
   */
  private String jobClassName;

  /**
   * Indica se o job é durável.
   */
  private boolean durable;

  /**
   * Indica se requer recuperação após falha.
   */
  private boolean requestsRecovery;

  /**
   * Dados do job como mapa.
   */
  private Map<String, Object> jobData;

  /**
   * Estado do job.
   */
  private String jobState;

  /**
   * Número de execuções.
   */
  private int numberOfExecutions;

  /**
   * Última data de execução.
   */
  private LocalDateTime lastExecutionTime;

  /**
   * Próxima data de execução.
   */
  private LocalDateTime nextExecutionTime;

  /**
   * Data do último disparo (prevFireTime).
   */
  private LocalDateTime prevFireTime;

  /**
   * Lista de triggers associados ao job.
   */
  private List<QuartzJobTriggerDTO> triggers = new ArrayList<>();

  /**
   * Retorna o request de pesquisa para este DTO.
   *
   * @return request de pesquisa
   */
  public static SearchRequestDTO getSearchRequest() {
    return new QuartzJobSearchRequestDTO();
  }

  /**
   * Retorna os filtros de propriedade para pesquisa.
   *
   * @return conjunto de filtros
   */
  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  /**
   * Cria uma cópia superficial (clone) deste objeto DTO.
   *
   * @return novo objeto com os mesmos valores
   */
  @Override
  public QuartzJobDTO cloneObject() {
    return toBuilder().build();
  }

  /**
   * Cria uma cópia deste objeto DTO com id e version nulos.
   *
   * @return cópia do objeto
   */
  @Override
  public QuartzJobDTO copyObject() {
    return (QuartzJobDTO) super.copyObject();
  }

  /**
   * Retorna uma representação em string deste objeto.
   *
   * @return string contendo o nome do job
   */
  @Override
  public String toString() {
    return String.format("%s", jobName);
  }

  /**
   * Verifica a igualdade entre este objeto e outro.
   *
   * @param obj o objeto a ser comparado
   * @return true se os objetos forem iguais, false caso contrário
   */
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

  /**
   * Calcula o código hash para este objeto.
   *
   * @return código hash calculado
   */
  @Override
  public int hashCode() {
    if (jobName != null && jobGroup != null) {
      return Objects.hash(jobName, jobGroup);
    }
    return super.hashCode();
  }

  /**
   * Constantes para nomes dos campos deste DTO.
   */
  @SuppressWarnings("javadoc")
  public static class CAMPOS
    extends AbstractDTO.CAMPOS {

    /** Nome do job */
    public static final String JOB_NAME = "jobName";

    /** Grupo do job */
    public static final String JOB_GROUP = "jobGroup";

    /** Descrição do job */
    public static final String DESCRIPTION = "description";

    /** Nome da classe do job */
    public static final String JOB_CLASS_NAME = "jobClassName";

    /** Flag de durabilidade */
    public static final String IS_DURABLE = "durable";

    /** Flag de recuperação */
    public static final String REQUESTS_RECOVERY = "requestsRecovery";

    /** Dados do job */
    public static final String JOB_DATA = "jobData";

    /** Estado do job */
    public static final String JOB_STATE = "jobState";

    /** Número de execuções */
    public static final String NUMBER_OF_EXECUTIONS = "numberOfExecutions";

    /** Última data de execução */
    public static final String LAST_EXECUTION_TIME = "lastExecutionTime";

    /** Próxima data de execução */
    public static final String NEXT_EXECUTION_TIME = "nextExecutionTime";

    /** Data do último disparo */
    public static final String PREV_FIRE_TIME = "prevFireTime";

    /** Triggers associados */
    public static final String TRIGGERS = "triggers";

    /**
     * Retorna todos os nomes de campos deste DTO.
     *
     * @return conjunto de strings com os nomes dos campos
     */
    public static Set<String> values() {
      return Set.of(PROPERTY_CHANGE_SUPPORT, JOB_NAME, JOB_GROUP, DESCRIPTION,
          JOB_CLASS_NAME, IS_DURABLE, REQUESTS_RECOVERY, JOB_DATA, JOB_STATE,
          NUMBER_OF_EXECUTIONS, LAST_EXECUTION_TIME, NEXT_EXECUTION_TIME,
          PREV_FIRE_TIME, TRIGGERS);
    }
  }
}
