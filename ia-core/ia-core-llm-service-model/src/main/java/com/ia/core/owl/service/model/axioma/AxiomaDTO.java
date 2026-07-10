package com.ia.core.owl.service.model.axioma;

import com.ia.core.owl.model.axiom.Axioma;
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

import java.util.Set;

/**
 * DTO (Data Transfer Object) para representar axiomas de ontologia OWL.
 * <p>
 * Esta classe é utilizada para transferir dados de axiomas entre as camadas
 * de apresentação e serviço, contendo informações sobre URI, prefixo,
 * expressão e status de consistência/inferência.
 *
 * @author Israel Araújo
 * @see Axioma
 * @see AxiomaTranslator
 * @since 1.0.0
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AxiomaDTO
    extends AbstractBaseEntityDTO<Axioma> {

    /**
     * Serial UID para controle de versão da serialização.
     */
    private static final long serialVersionUID = -19560738760061624L;
    /**
     * URI do axioma para identificação única.
     * <p>
     * Representa o identificador único do axioma na ontologia.
     */
    private String uri;
    /**
     * Prefixo do axioma para identificação curta.
     * <p>
     * Deve conter entre 1 e 50 caracteres e não pode ser nulo.
     */
    @NotNull(message = AxiomaTranslator.VALIDATION.PREFIX_REQUIRED)
    @Size(min = 1, max = 50, message = AxiomaTranslator.VALIDATION.PREFIX_SIZE)
    private String prefix;
    /**
     * Versão da URI do axioma.
     * <p>
     * Representa a versão ou variante da URI para controle de versões.
     */
    @NotNull(message = AxiomaTranslator.VALIDATION.URI_VERSION_REQUIRED)
    private String uriVersion;
    /**
     * Expressão do axioma em sintaxe Manchester.
     * <p>
     * Representa o conteúdo do axioma na sintaxe Manchester OWL.
     * Deve conter no máximo 2000 caracteres.
     */
    @NotNull(message = AxiomaTranslator.VALIDATION.EXPRESSAO_REQUIRED)
    @Size(max = 2000, message = AxiomaTranslator.VALIDATION.EXPRESSAO_SIZE)
    private String expressao;
    /**
     * Indica se o axioma é consistente.
     * <p>
     * Valor padrão é true. Representa se o axioma foi validado semanticamente.
     */
    @Default
    private Boolean consistente = true;
    /**
     * Indica se o axioma foi inferido automaticamente.
     * <p>
     * Valor padrão é false. Marca axiomas gerados automaticamente.
     */
    @Default
    private Boolean inferido = false;
    /**
     * Indica se o axioma está ativo.
     * <p>
     * Valor padrão é true. Axiomas inativos são ignorados nas inferências.
     */
    @Default
    private Boolean ativo = true;

    /**
     * Retorna uma requisição de busca padrão para axiomas.
     *
     * @return instância de {@link SearchRequestDTO} configurada para busca de axiomas
     */
    public static SearchRequestDTO getSearchRequest() {
        return new AxiomaSearchRequestDTO();
    }

    /**
     * Retorna o conjunto de propriedades disponíveis para filtragem.
     *
     * @return conjunto de strings representando as propriedades que podem ser usadas como filtros
     */
    public static Set<String> propertyFilters() {
        return getSearchRequest().propertyFilters();
    }

    /**
     * Retorna a expressão Manchester Syntax do axioma.
     *
     * @return a expressão do axioma
     */
    public String getManchesterSyntax() {
        return expressao;
    }

    /**
     * Cria uma cópia superficial (clone) deste objeto DTO.
     *
     * @return novo objeto {@link AxiomaDTO} com os mesmos valores de atributos
     */
    @Override
    public AxiomaDTO cloneObject() {
        return toBuilder().build();
    }

    /**
     * Retorna uma representação em string deste objeto.
     *
     * @return string contendo o prefixo do axioma
     */
    @Override
    public String toString() {
        return String.format("%s", prefix);
    }

    /**
     * Constantes para nomes dos campos deste DTO.
     */
    @SuppressWarnings("javadoc")
    public static class CAMPOS
        extends AbstractBaseEntityDTO.CAMPOS {

        /**
         * URI do axioma
         */
        public static final String URI = "uri";

        /**
         * Prefixo do axioma
         */
        public static final String PREFIX = "prefix";

        /**
         * Versão da URI
         */
        public static final String URI_VERSION = "uriVersion";

        /**
         * Expressão do axioma
         */
        public static final String EXPRESSAO = "expressao";

        /**
         * Flag de consistência
         */
        public static final String IS_CONSISTENTE = "consistente";

        /**
         * Flag de inferência
         */
        public static final String IS_INFERIDO = "inferido";

        /**
         * Flag de ativo
         */
        public static final String IS_ATIVO = "ativo";

        /**
         * Retorna todos os nomes de campos deste DTO incluindo os da superclasse.
         *
         * @return conjunto de strings com os nomes dos campos
         */
        public static Set<String> values() {
            var baseValues = AbstractBaseEntityDTO.CAMPOS.values();
            var currentValues = Set.of(URI, PREFIX, URI_VERSION, EXPRESSAO, IS_CONSISTENTE, IS_INFERIDO, IS_ATIVO);
            var allValues = new java.util.HashSet<String>();
            allValues.addAll(baseValues);
            allValues.addAll(currentValues);
            return java.util.Collections.unmodifiableSet(allValues);
        }
    }
}
