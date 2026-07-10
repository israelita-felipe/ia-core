package com.ia.core.llm.service.model.skill;

import com.ia.core.llm.model.skill.Skill;
import com.ia.core.llm.model.skill.SkillTipo;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * DTO para transferência de dados de Skill.
 * <p>
 * Representa uma habilidade especializada que pode ser atribuída a um agente.
 * Skills são implementações com métodos @Tool que podem ser fornecidas ao ChatModel.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see Skill
 * @see SkillTranslator
 */
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SkillDTO extends AbstractBaseEntityDTO<Skill> {

    /** Serial UID */
    private static final long serialVersionUID = -19560738760061627L;

    /**
     * Retorna o request de pesquisa para este DTO.
     *
     * @return request de pesquisa
     */
    public static SearchRequestDTO getSearchRequest() {
        return new SkillSearchRequest();
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
     * Identificador único da skill (ex: ONTOLOGY_BUILDER, KNOWLEDGE_EXTRACTION).
     */
    @NotBlank(message = SkillTranslator.VALIDATION.IDENTIFICADOR_REQUIRED)
    @Size(min = 2, max = 100, message = SkillTranslator.VALIDATION.IDENTIFICADOR_SIZE)
    private String identificador;

    /**
     * Nome apresentável da skill na UI.
     */
    @NotNull(message = SkillTranslator.VALIDATION.TITULO_REQUIRED)
    @Size(min = 2, max = 200, message = SkillTranslator.VALIDATION.TITULO_SIZE)
    private String titulo;

    /**
     * Descrição do propósito da skill.
     */
    @Size(max = 1000, message = SkillTranslator.VALIDATION.DESCRICAO_SIZE)
    private String descricao;

    /**
     * Tipo da skill.
     */
    private SkillTipo tipo;

    /**
     * Indica se a skill está disponível para uso.
     */
    @Default
    private Boolean ativo = true;

    /**
     * Módulo ou pacote fonte.
     */
    @Size(max = 200, message = SkillTranslator.VALIDATION.MODULO_ORIGEM_SIZE)
    private String moduloOrigem;

    /**
     * Cria uma cópia superficial (clone) deste objeto DTO.
     *
     * @return novo objeto com os mesmos valores
     */
    @Override
    public SkillDTO cloneObject() {
        return toBuilder().build();
    }

    /**
     * Retorna uma representação em string deste objeto.
     *
     * @return string contendo o identificador da skill
     */
    @Override
    public String toString() {
        return String.format("%s", identificador);
    }

    /**
     * Constantes de campos para referência type-safe.
     */
    @SuppressWarnings("javadoc")
    public static class CAMPOS extends AbstractBaseEntityDTO.CAMPOS {
        /** Identificador único */
        public static final String IDENTIFICADOR = "identificador";
        /** Nome da skill */
        public static final String TITULO = "titulo";
        /** Descrição da skill */
        public static final String DESCRICAO = "descricao";
        /** Tipo da skill */
        public static final String TIPO = "tipo";
        /** Status ativo */
        public static final String ATIVO = "ativo";
        /** Módulo de origem */
        public static final String MODULO_ORIGEM = "moduloOrigem";

        /**
         * Retorna todos os nomes de campos deste DTO incluindo os da superclasse.
         *
         * @return conjunto de strings com os nomes dos campos
         */
        public static Set<String> values() {
            var baseValues = AbstractBaseEntityDTO.CAMPOS.values();
            var currentValues = Set.of(IDENTIFICADOR, TITULO, DESCRICAO, TIPO, ATIVO, MODULO_ORIGEM);
            var allValues = new HashSet<String>();
            allValues.addAll(baseValues);
            allValues.addAll(currentValues);
            return allValues;
        }
    }
}
