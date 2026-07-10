package com.ia.core.llm.service.model.template;

import com.ia.core.llm.model.template.Template;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe que representa o objeto de transferência de dados para template.
 * <p>
 * Representa o template para prompts enviados ao modelo de linguagem, incluindo título,
 * identificador único, conteúdo e parâmetros configuráveis.
 *
 * @author Israel Araújo
 * @see Template
 * @see TemplateTranslator
 * @since 1.0.0
 */
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TemplateDTO extends AbstractBaseEntityDTO<Template> {

    /**
     * Serial UID
     */
    private static final long serialVersionUID = -19560738760061625L;
    /**
     * Título do template.
     */
    @NotNull(message = TemplateTranslator.VALIDATION.TITULO_NOT_BLANK)
    @Size(min = 3, max = 200, message = TemplateTranslator.VALIDATION.TITULO_SIZE)
    private String titulo;
    /**
     * Identificador único do template.
     */
    @NotNull(message = TemplateTranslator.VALIDATION.IDENTIFICADOR_NOT_BLANK)
    @Size(max = 255, message = TemplateTranslator.VALIDATION.IDENTIFICADOR_SIZE)
    private String identificador;
    /**
     * Conteúdo do template.
     */
    @NotNull(message = TemplateTranslator.VALIDATION.CONTEUDO_NOT_BLANK)
    @Size(max = 10000, message = TemplateTranslator.VALIDATION.CONTEUDO_SIZE)
    private String conteudo;
    /**
     * Flag indicativa de exigência de contexto.
     */
    @Default
    private boolean exigeContexto = false;
    /**
     * Parâmetros do template.
     */
    @Default
    private List<TemplateParameterDTO> parametros = new ArrayList<>();

    /**
     * Retorna o request de pesquisa para este DTO.
     *
     * @return request de pesquisa
     */
    public static SearchRequestDTO getSearchRequest() {
        return new TemplateSearchRequest();
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
    public TemplateDTO cloneObject() {
        return toBuilder().parametros(new ArrayList<>(parametros.stream()
            .map(TemplateParameterDTO::cloneObject).toList())).build();
    }

    /**
     * Cria uma cópia deste objeto DTO com id e version nulos.
     *
     * @return cópia do objeto
     */
    @Override
    public TemplateDTO copyObject() {
        return (TemplateDTO) super.copyObject();
    }

    /**
     * Retorna uma representação em string deste objeto.
     *
     * @return string contendo o título do template
     */
    @Override
    public String toString() {
        return String.format("%s", titulo);
    }

    /**
     * Constantes de campos para referência type-safe.
     */
    @SuppressWarnings("javadoc")
    public static class CAMPOS extends AbstractBaseEntityDTO.CAMPOS {
        /**
         * Título do template
         */
        public static final String TITULO = "titulo";
        /**
         * Identificador único
         */
        public static final String IDENTIFICADOR = "identificador";
        /**
         * Conteúdo do template
         */
        public static final String CONTEUDO = "conteudo";
        /**
         * Flag exige contexto
         */
        public static final String EXIGE_CONTEXTO = "exigeContexto";
        /**
         * Parâmetros do template
         */
        public static final String PARAMETROS = "parametros";

        /**
         * Retorna todos os nomes de campos deste DTO incluindo os da superclasse.
         *
         * @return conjunto de strings com os nomes dos campos
         */
        public static Set<String> values() {
            var baseValues = AbstractBaseEntityDTO.CAMPOS.values();
            var currentValues = Set.of(TITULO, IDENTIFICADOR, CONTEUDO, EXIGE_CONTEXTO, PARAMETROS);
            var allValues = new HashSet<String>();
            allValues.addAll(baseValues);
            allValues.addAll(currentValues);
            return allValues;
        }
    }
}
