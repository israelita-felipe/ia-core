package com.ia.core.quartz.service.model.scheduler.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ia.core.quartz.model.scheduler.SchedulerConfig;
import com.ia.core.quartz.service.model.periodicidade.dto.PeriodicidadeDTO;
import com.ia.core.quartz.service.model.scheduler.dto.triggers.SchedulerConfigTriggerDTO;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * DTO para configuração de scheduler do Quartz.
 * <p>
 * Representa uma configuração de agendamento de job, incluindo a classe do job,
 * periodicidade e triggers associados.
 *
 * @author Israel Araújo
 * @see SchedulerConfigTranslator
 * @since 1.0.0
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SchedulerConfigDTO
    extends AbstractBaseEntityDTO<SchedulerConfig> {

    /**
     * Serial UID
     */
    private static final long serialVersionUID = -19560738760061623L;

    /**
     * Nome da classe do Job.
     */
    @NotNull(message = SchedulerConfigTranslator.VALIDATION.JOB_CLASS_NAME_REQUIRED)
    @Pattern(regexp = "^[a-zA-Z0-9_.]+$",
        message = SchedulerConfigTranslator.VALIDATION.JOB_CLASS_NAME_PATTERN)
    private String jobClassName;

    /**
     * Periodicidade do agendamento.
     */
    @Default
    private PeriodicidadeDTO periodicidade = PeriodicidadeDTO.builder()
        .build();

    /**
     * Lista de triggers associados ao job.
     */
    @Default
    private List<SchedulerConfigTriggerDTO> triggers = new ArrayList<>();

    /**
     * Retorna o request de pesquisa para este DTO.
     *
     * @return request de pesquisa
     */
    public static SearchRequestDTO getSearchRequest() {
        return new SchedulerConfigSearchRequestDTO();
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
    public SchedulerConfigDTO cloneObject() {
        return toBuilder()
            .periodicidade(periodicidade != null ? periodicidade.cloneObject()
                : null)
            .triggers(triggers != null ? new ArrayList<>(triggers.stream()
                .filter(Objects::nonNull)
                .map(SchedulerConfigTriggerDTO::cloneObject).toList())
                : new ArrayList<>())
            .build();
    }

    /**
     * Cria uma cópia deste objeto DTO com id e version nulos.
     *
     * @return cópia do objeto
     */
    @Override
    public SchedulerConfigDTO copyObject() {
        return (SchedulerConfigDTO) super.copyObject();
    }

    /**
     * Retorna a classe do job a partir do nome.
     *
     * @return classe do job
     * @throws ClassNotFoundException se a classe não for encontrada
     */
    @JsonIgnore
    public Class<?> getJobClassNameAsClass()
        throws ClassNotFoundException {
        return Class.forName(jobClassName);
    }

    /**
     * Verifica a igualdade entre este objeto e outro.
     * <p>
     * Dois objetos são considerados iguais se possuírem o mesmo identificador.
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
        if (id == null) {
            return this == obj;
        }
        if (!(getClass().isInstance(obj))) {
            return false;
        }
        SchedulerConfigDTO other = (SchedulerConfigDTO) obj;
        return Objects.equals(id, other.id);
    }

    /**
     * Calcula o código hash para este objeto.
     * <p>
     * O código hash é baseado no identificador do DTO, se disponível.
     *
     * @return código hash calculado
     */
    @Override
    public int hashCode() {
        if (id != null) {
            return Objects.hash(id);
        }
        return super.hashCode();
    }

    /**
     * Retorna uma representação em string deste objeto.
     *
     * @return string contendo o nome do job e periodicidade
     */
    @Override
    public String toString() {
        return String.format("%s (%s)", jobClassName, periodicidade);
    }

    /**
     * Constantes para nomes dos campos deste DTO.
     */
    @SuppressWarnings("javadoc")
    public static class CAMPOS
        extends AbstractBaseEntityDTO.CAMPOS {

        /**
         * Nome da classe do job
         */
        public static final String JOB_CLASS_NAME = "jobClassName";

        /**
         * Periodicidade
         */
        public static final String PERIODICIDADE = "periodicidade";

        /**
         * Triggers associados
         */
        public static final String TRIGGERS = "triggers";

        /**
         * Retorna todos os nomes de campos deste DTO incluindo os da superclasse.
         *
         * @return conjunto de strings com os nomes dos campos
         */
        public static Set<String> values() {
            var baseValues = AbstractBaseEntityDTO.CAMPOS.values();
            var currentValues = Set.of(JOB_CLASS_NAME, PERIODICIDADE, TRIGGERS);
            var allValues = new HashSet<String>();
            allValues.addAll(baseValues);
            allValues.addAll(currentValues);
            return Collections.unmodifiableSet(allValues);
        }
    }
}
