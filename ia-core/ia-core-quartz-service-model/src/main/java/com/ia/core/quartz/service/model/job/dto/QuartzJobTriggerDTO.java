package com.ia.core.quartz.service.model.job.dto;

import com.ia.core.service.dto.AbstractDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * DTO para representação de um Trigger do Quartz associado a um Job.
 * <p>
 * Representa as configurações de um trigger do Quartz, incluindo
 * nome, grupo, estado, timestamps e configurações de recorrência.
 *
 * @author Israel Araújo
 * @see QuartzJobTranslator
 * @since 1.0.0
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class QuartzJobTriggerDTO
  extends AbstractDTO<QuartzJobTriggerDTO>
  implements Serializable {

  /** Serial UID */
  private static final long serialVersionUID = -19560738760061625L;

  /**
   * Nome do trigger.
   */
  private String triggerName;

  /**
   * Grupo do trigger.
   */
  private String triggerGroup;

  /**
   * Nome do job associado.
   */
  private String jobName;

  /**
   * Grupo do job associado.
   */
  private String jobGroup;

  /**
   * Descrição do trigger.
   */
  private String description;

  /**
   * Estado do trigger.
   */
  private String triggerState;

  /**
   * Tipo do trigger.
   */
  private String triggerType;

  /**
   * Nome do calendário associado.
   */
  private String calendarName;

  /**
   * Próxima data de disparo.
   */
  private LocalDateTime nextFireTime;

  /**
   * Data do último disparo.
   */
  private LocalDateTime prevFireTime;

  /**
   * Data de início do trigger.
   */
  private LocalDateTime startTime;

  /**
   * Data de término do trigger.
   */
  private LocalDateTime endTime;

  /**
   * Data final de disparo.
   */
  private LocalDateTime finalFireTime;

  /**
   * Instrução de misfire.
   */
  private Long misFireInstr;

  /**
   * Prioridade do trigger.
   */
  private Integer priority;

  /**
   * Contagem de repetições.
   */
  private Integer repeatCount;

  /**
   * Intervalo de repetição.
   */
  private Long repeatInterval;

  /**
   * Número de disparos efetivos.
   */
  private Integer timesTriggered;

  /**
   * Dados do job como mapa.
   */
  private Map<String, Object> jobData;

  /**
   * Retorna o request de pesquisa para este DTO.
   *
   * @return request de pesquisa
   */
  public static SearchRequestDTO getSearchRequest() {
    return new QuartzJobTriggerSearchRequestDTO();
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
  public QuartzJobTriggerDTO cloneObject() {
    return toBuilder().build();
  }

  /**
   * Cria uma cópia deste objeto DTO com id e version nulos.
   *
   * @return cópia do objeto
   */
  @Override
  public QuartzJobTriggerDTO copyObject() {
    return (QuartzJobTriggerDTO) super.copyObject();
  }

  /**
   * Verifica a igualdade entre este objeto e outro.
   * <p>
   * Dois triggers são considerados iguais se possuírem o mesmo identificador.
   * Se o identificador for nulo, utiliza a comparação de referência.
   *
   * @param obj o objeto a ser comparado
   * @return true se os objetos forem iguais, false caso contrário
   */
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

  /**
   * Calcula o código hash para este objeto.
   * <p>
   * O código hash é baseado no identificador do trigger, se disponível.
   *
   * @return código hash calculado
   */
  @Override
  public int hashCode() {
    if (triggerName != null && triggerGroup != null) {
      return Objects.hash(triggerName, triggerGroup);
    }
    return super.hashCode();
  }

  /**
   * Retorna uma representação em string deste objeto.
   *
   * @return string contendo o nome do trigger
   */
  @Override
  public String toString() {
    return String.format("%s", triggerName);
  }

  /**
   * Constantes para nomes dos campos deste DTO.
   */
  @SuppressWarnings("javadoc")
  public static class CAMPOS
    extends AbstractDTO.CAMPOS {

    /** Nome do trigger */
    public static final String TRIGGER_NAME = "triggerName";

    /** Grupo do trigger */
    public static final String TRIGGER_GROUP = "triggerGroup";

    /** Nome do job associado */
    public static final String JOB_NAME = "jobName";

    /** Grupo do job associado */
    public static final String JOB_GROUP = "jobGroup";

    /** Descrição */
    public static final String DESCRIPTION = "description";

    /** Estado do trigger */
    public static final String TRIGGER_STATE = "triggerState";

    /** Tipo do trigger */
    public static final String TRIGGER_TYPE = "triggerType";

    /** Nome do calendário */
    public static final String CALENDAR_NAME = "calendarName";

    /** Próxima data de disparo */
    public static final String NEXT_FIRE_TIME = "nextFireTime";

    /** Data do último disparo */
    public static final String PREV_FIRE_TIME = "prevFireTime";

    /** Data de início */
    public static final String START_TIME = "startTime";

    /** Data de término */
    public static final String END_TIME = "endTime";

    /** Data final de disparo */
    public static final String FINAL_FIRE_TIME = "finalFireTime";

    /** Instrução de misfire */
    public static final String MISFIRE_INSTR = "misFireInstr";

    /** Prioridade */
    public static final String PRIORITY = "priority";

    /** Contagem de repetições */
    public static final String REPEAT_COUNT = "repeatCount";

    /** Intervalo de repetição */
    public static final String REPEAT_INTERVAL = "repeatInterval";

    /** Número de disparos */
    public static final String TIMES_TRIGGERED = "timesTriggered";

    /** Dados do job */
    public static final String JOB_DATA = "jobData";

    /**
     * Retorna todos os nomes de campos deste DTO.
     *
     * @return conjunto de strings com os nomes dos campos
     */
    public static Set<String> values() {
      return Set.of(PROPERTY_CHANGE_SUPPORT, TRIGGER_NAME, TRIGGER_GROUP, JOB_NAME,
          JOB_GROUP, DESCRIPTION, TRIGGER_STATE, TRIGGER_TYPE, CALENDAR_NAME,
          NEXT_FIRE_TIME, PREV_FIRE_TIME, START_TIME, END_TIME, FINAL_FIRE_TIME,
          MISFIRE_INSTR, PRIORITY, REPEAT_COUNT, REPEAT_INTERVAL, TIMES_TRIGGERED, JOB_DATA);
    }
  }
}
