package com.ia.core.communication.service.model.grupocontato.dto;

import com.ia.core.communication.model.contato.GrupoContato;
import com.ia.core.communication.service.model.modelomensagem.dto.HasVariavel;
import com.ia.core.communication.service.model.modelomensagem.dto.Variavel;
import com.ia.core.communication.service.model.modelomensagem.dto.VariavelTemplate;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * DTO para a entidade GrupoContato.
 * <p>
 * Representa os dados de transferência para grupos de contatos,
 * incluindo nome, descrição e status ativo.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de um grupo de contatos")
public class GrupoContatoDTO extends AbstractBaseEntityDTO<GrupoContato> implements HasVariavel {

    /** Serial UID */
    private static final long serialVersionUID = 1L;

    /**
     * Request de pesquisa
     */
    public static SearchRequestDTO getSearchRequest() {
        return new GrupoContatoSearchRequestDTO();
    }

    /**
     * Filtros
     */
    public static Set<String> propertyFilters() {
        return getSearchRequest().propertyFilters();
    }

    @NotBlank(message = GrupoContatoTranslator.VALIDATION.NOME_NOT_BLANK)
    @Size(max = 100, message = GrupoContatoTranslator.VALIDATION.NOME_SIZE)
    @Schema(description = "Nome do grupo de contatos", example = "Clientes VIP", required = true)
    private String nome;

    @Size(max = 500, message = GrupoContatoTranslator.VALIDATION.DESCRICAO_SIZE)
    @Schema(description = "Descrição do grupo", example = "Grupo de clientes VIP com benefícios especiais")
    private String descricao;

    @Schema(description = "Indica se o grupo está ativo", example = "true")
    private Boolean ativo;

    @Override
    public GrupoContatoDTO cloneObject() {
        return toBuilder().build();
    }

    @Override
    public GrupoContatoDTO copyObject() {
        return toBuilder().id(null).version(null).build();
    }

    @Override
    public Map<Variavel, Object> getContext() {
        return Map.of(
            VariavelTemplate.NOME, nome,
            VariavelTemplate.DESCRICAO_GRUPO, descricao,
            VariavelTemplate.ATIVO_GRUPO, ativo
        );
    }

    @Override
    public String toString() {
        return String.format("GrupoContatoDTO{nome=%s, ativo=%s}", nome, ativo);
    }

    @SuppressWarnings("javadoc")
    public static class CAMPOS extends AbstractBaseEntityDTO.CAMPOS {
        public static final String NOME = "nome";
        public static final String DESCRICAO = "descricao";
        public static final String ATIVO = "ativo";

        public static Set<String> values() {
            var baseValues = AbstractBaseEntityDTO.CAMPOS.values();
            var currentValues = Set.of(NOME, DESCRICAO, ATIVO);
            var allValues = new HashSet<String>();
            allValues.addAll(baseValues);
            allValues.addAll(currentValues);
            return Collections.unmodifiableSet(allValues);
        }
    }
}