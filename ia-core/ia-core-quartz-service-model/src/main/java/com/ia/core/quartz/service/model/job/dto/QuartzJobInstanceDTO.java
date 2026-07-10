package com.ia.core.quartz.service.model.job.dto;

import com.ia.core.service.dto.AbstractDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * DTO para representação de uma execução de Job do Quartz.
 * <p>
 * Representa os dados de uma instância de execução de job agendado,
 * incluindo timestamps, resultado e dados da execução.
 *
 * @author Israel Araújo
 * @see QuartzJobTranslator
 * @since 1.0.0
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class QuartzJobInstanceDTO
    extends AbstractDTO<QuartzJobInstanceDTO>
    implements Serializable {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = -19560738760061626L;

    /**
     * ID da instância de execução.
     */
    private String instanceId;

    /**
     * Nome do job associado.
     */
    private String jobName;

    /**
     * Grupo do job associado.
     */
    private String jobGroup;

    /**
     * Nome do trigger que disparou a execução.
     */
    private String triggerName;

    /**
     * Grupo do trigger que disparou a execução.
     */
    private String triggerGroup;

    /**
     * Data e hora do disparo.
     */
    private LocalDateTime fireTime;

    /**
     * Data e hora programada para o disparo.
     */
    private LocalDateTime scheduledFireTime;

    /**
     * Data e hora da conclusão da execução.
     */
    private LocalDateTime completedExecutionTime;

    /**
     * Resultado da execução.
     */
    private String result;

    /**
     * Indica se o job foi executado com sucesso.
     */
    private boolean jobExecuted;

    /**
     * Mensagem de exceção caso haja erro.
     */
    private String exceptionMessage;

    /**
     * Dados do job como mapa.
     */
    private Map<String, Object> jobDataMap;

    /**
     * Indica se o job foi recuperado após falha.
     */
    @Default
    private boolean recovered = false;

    /**
     * Retorna o request de pesquisa para este DTO.
     *
     * @return request de pesquisa
     */
    public static SearchRequestDTO getSearchRequest() {
        return new QuartzJobInstanceSearchRequestDTO();
    }

    /**
     * Cria uma cópia superficial (clone) deste objeto DTO.
     *
     * @return novo objeto com os mesmos valores
     */
    @Override
    public QuartzJobInstanceDTO cloneObject() {
        return toBuilder().build();
    }

    /**
     * Cria uma cópia deste objeto DTO com id e version nulos.
     *
     * @return cópia do objeto
     */
    @Override
    public QuartzJobInstanceDTO copyObject() {
        return toBuilder().jobDataMap(null).build();
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

    /**
     * Calcula o código hash para este objeto.
     *
     * @return código hash calculado
     */
    @Override
    public int hashCode() {
        if (instanceId != null && fireTime != null) {
            return Objects.hash(instanceId, fireTime);
        }
        return super.hashCode();
    }

    /**
     * Constantes para nomes dos campos deste DTO.
     */
    @SuppressWarnings("javadoc")
    public static class CAMPOS
        extends AbstractDTO.CAMPOS {

        /**
         * ID da instância
         */
        public static final String INSTANCE_ID = "instanceId";

        /**
         * Nome do job
         */
        public static final String JOB_NAME = "jobName";

        /**
         * Grupo do job
         */
        public static final String JOB_GROUP = "jobGroup";

        /**
         * Nome do trigger
         */
        public static final String TRIGGER_NAME = "triggerName";

        /**
         * Grupo do trigger
         */
        public static final String TRIGGER_GROUP = "triggerGroup";

        /**
         * Data do disparo
         */
        public static final String FIRE_TIME = "fireTime";

        /**
         * Data programada do disparo
         */
        public static final String SCHEDULED_FIRE_TIME = "scheduledFireTime";

        /**
         * Data de conclusão
         */
        public static final String COMPLETED_EXECUTION_TIME = "completedExecutionTime";

        /**
         * Resultado
         */
        public static final String RESULT = "result";

        /**
         * Job executado
         */
        public static final String JOB_EXECUTED = "jobExecuted";

        /**
         * Mensagem de exceção
         */
        public static final String EXCEPTION_MESSAGE = "exceptionMessage";

        /**
         * Dados do job
         */
        public static final String JOB_DATA_MAP = "jobDataMap";

        /**
         * Recuperado
         */
        public static final String RECOVERED = "recovered";

        /**
         * Retorna todos os nomes de campos deste DTO.
         *
         * @return conjunto de strings com os nomes dos campos
         */
        public static Set<String> values() {
            return Set.of(PROPERTY_CHANGE_SUPPORT, INSTANCE_ID, JOB_NAME, JOB_GROUP, TRIGGER_NAME,
                TRIGGER_GROUP, FIRE_TIME, SCHEDULED_FIRE_TIME,
                COMPLETED_EXECUTION_TIME, RESULT, JOB_EXECUTED,
                EXCEPTION_MESSAGE, JOB_DATA_MAP, RECOVERED);
        }
    }
}
