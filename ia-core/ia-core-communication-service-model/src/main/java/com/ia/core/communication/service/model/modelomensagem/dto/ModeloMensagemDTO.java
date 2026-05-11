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

import java.util.Map;
import java.util.Set;

/**
 * DTO para a entidade ModeloMensagem.
 *
 * @author Israel Araújo
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
   * Request de pesquisa
   *
   * @return {@link SearchRequestDTO}
   */
  public static SearchRequestDTO getSearchRequest() {
    return new ModeloMensagemSearchRequest();
  }

  /**
   * Filtros
   *
   * @return {@link Set} de filtros do DTO
   */
  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

   @NotBlank(message = ModeloMensagemTranslator.VALIDATION.NOME_NOT_BLANK)
    @Size(max = 100, message = ModeloMensagemTranslator.VALIDATION.NOME_SIZE)
    @Schema(description = "Nome do modelo de mensagem", example = "Modelo de Boas Vindas", required = true)
    private String nome;

    @Size(max = 500, message = ModeloMensagemTranslator.VALIDATION.DESCRICAO_SIZE)
    @Schema(description = "Descrição do modelo", example = "Modelo para mensagem de boas vindas a novos usuários")
    private String descricao;

    @NotBlank(message = ModeloMensagemTranslator.VALIDATION.CORPO_MODELO_NOT_BLANK)
    @Schema(description = "Corpo do modelo com variáveis", example = "Olá {{nome}}, seja bem-vindo!", required = true)
    private String corpoModelo;

    @NotNull(message = ModeloMensagemTranslator.VALIDATION.TIPO_CANAL_NOT_NULL)
    @Schema(description = "Tipo do canal de comunicação", example = "WHATSAPP", required = true)
    private TipoCanal tipoCanal;

   @Schema(description = "Indica se o modelo está ativo", example = "true")
   private Boolean ativo;

  @Override
  public ModeloMensagemDTO cloneObject() {
    return toBuilder().build();
  }

  @Override
  public ModeloMensagemDTO copyObject() {
    return toBuilder().id(null).version(null).build();
  }

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

  @Override
  public String toString() {
    return String.format("%s", nome);
  }

  @SuppressWarnings("javadoc")
  public static class CAMPOS {
    public static final String NOME = "nome";
    public static final String DESCRICAO = "descricao";
    public static final String CORPO_MODELO = "corpoModelo";
    public static final String TIPO_CANAL = "tipoCanal";
    public static final String ATIVO = "ativo";

    public static Set<String> values() {
      return Set.of(NOME, DESCRICAO, CORPO_MODELO, TIPO_CANAL, ATIVO);
    }
  }
}
