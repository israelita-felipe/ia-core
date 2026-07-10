package com.ia.core.llm.service.model.template;

import com.ia.core.llm.model.template.TemplateParameter;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe que representa o objeto de transferência de dados para template parameter.
 * <p>
 * Representa um parâmetro parametrizado em template ({nome} no conteúdo).
 *
 * @author Israel Araújo
 * @see TemplateParameter
 * @see TemplateParameterTranslator
 * @since 1.0.0
 */
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TemplateParameterDTO extends AbstractBaseEntityDTO<TemplateParameter> {

    /**
     * Serial UID
     */
    private static final long serialVersionUID = -19560738760061626L;
    /**
     * Nome do parâmetro.
     */
    @NotNull(message = TemplateParameterTranslator.VALIDATION.NOME_REQUIRED)
    @Size(min = 1, max = 100, message = TemplateParameterTranslator.VALIDATION.NOME_SIZE)
    private String nome;

    /**
     * Retorna o request de pesquisa para este DTO.
     *
     * @return request de pesquisa
     */
    public static SearchRequestDTO getSearchRequest() {
        return new TemplateParameterSearchRequest();
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
    public TemplateParameterDTO cloneObject() {
        return toBuilder().build();
    }

    /**
     * Cria uma cópia deste objeto DTO com id e version nulos.
     *
     * @return cópia do objeto
     */
    @Override
    public TemplateParameterDTO copyObject() {
        return (TemplateParameterDTO) super.copyObject();
    }

    /**
     * Retorna uma representação em string deste objeto.
     *
     * @return string contendo o nome do parâmetro
     */
    @Override
    public String toString() {
        return String.format("%s", nome);
    }

    /**
     * Constantes de campos para referência type-safe.
     */
    @SuppressWarnings("javadoc")
    public static class CAMPOS extends AbstractBaseEntityDTO.CAMPOS {
        /**
         * Nome do parâmetro
         */
        public static final String NOME = "nome";

        /**
         * Retorna todos os nomes de campos deste DTO incluindo os da superclasse.
         *
         * @return conjunto de strings com os nomes dos campos
         */
        public static Set<String> values() {
            var baseValues = AbstractBaseEntityDTO.CAMPOS.values();
            var currentValues = Set.of(NOME);
            var allValues = new HashSet<String>();
            allValues.addAll(baseValues);
            allValues.addAll(currentValues);
            return allValues;
        }
    }
}
