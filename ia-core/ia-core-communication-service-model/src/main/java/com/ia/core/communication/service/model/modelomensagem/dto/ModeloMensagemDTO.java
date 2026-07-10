package com.ia.core.communication.service.model.modelomensagem.dto;

import com.ia.core.communication.model.mensagem.ModeloMensagem;
import com.ia.core.communication.model.mensagem.TipoCanal;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * DTO para a entidade ModeloMensagem.
 * <p>
 * Representa os dados de transferência para modelos de mensagens,
 * incluindo nome, descrição, corpo do modelo com variáveis e tipo de canal.
 * </p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de um modelo de mensagem")
public class ModeloMensagemDTO extends AbstractBaseEntityDTO<ModeloMensagem> implements HasVariavel {

    /** Serial UID */
    private static final long serialVersionUID = 1L;

    /**
     * Retorna o request de pesquisa para este DTO.
     *
     * @return o {@link SearchRequestDTO} associado
     */
    public static SearchRequestDTO getSearchRequest() {
        return new ModeloMensagemSearchRequestDTO();
    }

    /**
     * Retorna os filtros de propriedade disponíveis para pesquisa.
     *
     * @return conjunto de nomes de filtros
     */
    public static Set<String> propertyFilters() {
        return getSearchRequest().propertyFilters();
    }

    /**
     * Nome do modelo de mensagem.
     * Campo obrigatório, máximo 100 caracteres.
     */
    @NotBlank(message = ModeloMensagemTranslator.VALIDATION.NOME_NOT_BLANK)
    @Size(max = 100, message = ModeloMensagemTranslator.VALIDATION.NOME_SIZE)
    @Schema(description = "Nome do modelo de mensagem", example = "Modelo de Boas Vindas", required = true)
    private String nome;

    /**
     * Descrição do modelo.
     * Campo opcional, máximo 500 caracteres.
     */
    @Size(max = 500, message = ModeloMensagemTranslator.VALIDATION.DESCRICAO_SIZE)
    @Schema(description = "Descrição do modelo", example = "Modelo para mensagem de boas vindas a novos usuários")
    private String descricao;

    /**
     * Corpo do modelo com variáveis.
     * Campo obrigatório, pode conter placeholders no formato {{chave}}.
     */
    @NotBlank(message = ModeloMensagemTranslator.VALIDATION.CORPO_MODELO_NOT_BLANK)
    @Schema(description = "Corpo do modelo com variáveis", example = "Olá {{nome}}, seja bem-vindo!", required = true)
    private String corpoModelo;

    /**
     * Tipo do canal de comunicação.
     * Campo obrigatório (WHATSAPP, SMS, EMAIL, etc).
     */
    @NotNull(message = ModeloMensagemTranslator.VALIDATION.TIPO_CANAL_NOT_NULL)
    @Schema(description = "Tipo do canal de comunicação", example = "WHATSAPP", required = true)
    private TipoCanal tipoCanal;

    /**
     * Indica se o modelo está ativo para uso.
     */
    @Schema(description = "Indica se o modelo está ativo", example = "true")
    private Boolean ativo;

    /**
     * Cria uma cópia superficial (clone) deste objeto DTO.
     */
    @Override
    public ModeloMensagemDTO cloneObject() {
        return toBuilder().build();
    }

    /**
     * Cria uma cópia deste objeto DTO com id e version nulos.
     */
    @Override
    public ModeloMensagemDTO copyObject() {
        return toBuilder().id(null).version(null).build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Variavel, Object> getContext() {
        return Map.of(
            VariavelTemplate.NOME_MODELO, nome,
            VariavelTemplate.DESCRICAO_MODELO, descricao,
            VariavelTemplate.CORPO_MODELO, corpoModelo,
            VariavelTemplate.TIPO_CANAL, tipoCanal,
            VariavelTemplate.ATIVO_MODELO, ativo
        );
    }

    /**
     * Retorna a representação em string do DTO.
     */
    @Override
    public String toString() {
        return String.format("ModeloMensagemDTO{nome=%s, tipoCanal=%s}", nome, tipoCanal);
    }

    /**
     * Constantes para nomes dos campos deste DTO, conforme ADR-040.
     */
    @SuppressWarnings("javadoc")
    public static class CAMPOS extends AbstractBaseEntityDTO.CAMPOS {

        public static final String NOME = "nome";
        public static final String DESCRICAO = "descricao";
        public static final String CORPO_MODELO = "corpoModelo";
        public static final String TIPO_CANAL = "tipoCanal";
        public static final String ATIVO = "ativo";

        /**
         * Retorna todos os nomes de campos deste DTO incluindo os da superclasse.
         */
        public static Set<String> values() {
            var baseValues = AbstractBaseEntityDTO.CAMPOS.values();
            var currentValues = Set.of(NOME, DESCRICAO, CORPO_MODELO, TIPO_CANAL, ATIVO);
            var allValues = new java.util.HashSet<String>();
            allValues.addAll(baseValues);
            allValues.addAll(currentValues);
            return Collections.unmodifiableSet(allValues);
        }
    }
}