package com.ia.core.service.configuracao.dto;

import com.ia.core.model.configuracao.ConfiguracaoSistema;
import com.ia.core.model.configuracao.TipoConfiguracao;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

/**
 * DTO para configuração do sistema.
 * <p>
 * Representa uma configuração chave-valor que pode ser extendida por módulos específicos.
 * Cada configuração pertence a um módulo e categoria para organização na UI.
 *
 * @param <T> tipo da entidade de configuração
 * @author Israel Araújo
 * @see ConfiguracaoSistemaTranslator
 * @see ConfiguracaoSistema
 * @since 1.0.0
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ConfiguracaoSistemaDTO<T extends ConfiguracaoSistema> extends AbstractBaseEntityDTO<T> {

    /**
     * Serial UID
     */
    private static final long serialVersionUID = -6543210987654321098L;


    /**
     * Chave única da configuração.
     */
    @NotBlank(message = ConfiguracaoSistemaTranslator.VALIDATION.CHAVE_NOT_BLANK)
    private String chave;

    /**
     * Valor da configuração.
     */
    private String valor;

    /**
     * Módulo ao qual a configuração pertence.
     */
    @NotBlank(message = ConfiguracaoSistemaTranslator.VALIDATION.MODULO_NOT_NULL)
    private String modulo;

    /**
     * Categoria para agrupamento na UI (ex: "Geral", "Segurança", "Notificações").
     */
    @NotBlank(message = ConfiguracaoSistemaTranslator.VALIDATION.CATEGORIA_NOT_NULL)
    private String categoria;

    /**
     * Descrição da configuração.
     */
    private String descricao;

    /**
     * Tipo de dado da configuração.
     */
    @NotNull(message = ConfiguracaoSistemaTranslator.VALIDATION.TIPO_NOT_NULL)
    private TipoConfiguracao tipo;

    /**
     * Retorna o request de pesquisa para este DTO.
     *
     * @return request de pesquisa
     */
    public static SearchRequestDTO getSearchRequest() {
        return new ConfiguracaoSistemaSearchRequestDTO();
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
    @SuppressWarnings("unchecked")
    public ConfiguracaoSistemaDTO<T> cloneObject() {
        return (ConfiguracaoSistemaDTO<T>) toBuilder().build();
    }

    /**
     * Constantes para nomes dos campos deste DTO.
     * <p>
     * Segundo ADR-040 (CAMPOS nested class pattern).
     */
    @SuppressWarnings("javadoc")
    public static class CAMPOS extends AbstractBaseEntityDTO.CAMPOS {

        /**
         * Chave da configuração
         */
        public static final String CHAVE = "chave";

        /**
         * Valor da configuração
         */
        public static final String VALOR = "valor";

        /**
         * Módulo da configuração
         */
        public static final String MODULO = "modulo";

        /**
         * Categoria da configuração
         */
        public static final String CATEGORIA = "categoria";

        /**
         * Descrição da configuração
         */
        public static final String DESCRICAO = "descricao";

        /**
         * Tipo da configuração
         */
        public static final String TIPO = "tipo";

        /**
         * Retorna todos os nomes de campos deste DTO incluindo os da superclasse.
         *
         * @return conjunto de strings com os nomes dos campos
         */
        public static Set<String> values() {
            var baseValues = AbstractBaseEntityDTO.CAMPOS.values();
            var currentValues = Set.of(CHAVE, VALOR, MODULO, CATEGORIA, DESCRICAO, TIPO);
            var allValues = new java.util.HashSet<String>();
            allValues.addAll(baseValues);
            allValues.addAll(currentValues);
            return java.util.Collections.unmodifiableSet(allValues);
        }
    }
}
