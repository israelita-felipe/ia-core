package com.ia.core.communication.service.model.grupocontato.dto;

import java.util.Set;

import com.ia.core.communication.model.GrupoContato;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * DTO para a entidade GrupoContato.
 *
 * @author Israel Araújo
 */
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class GrupoContatoDTO extends AbstractBaseEntityDTO<GrupoContato> {
  /** Serial UID */
  private static final long serialVersionUID = 1L;

  /**
   * Request de pesquisa
   *
   * @return {@link SearchRequestDTO}
   */
  public static SearchRequestDTO getSearchRequest() {
    return new GrupoContatoSearchRequest();
  }

  /**
   * Filtros
   *
   * @return {@link Set} de filtros do DTO
   */
  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  @NotBlank(message = "Nome é obrigatório")
  @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
  private String nome;

  @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
  private String descricao;

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
  public String toString() {
    return String.format("%s", nome);
  }

  @SuppressWarnings("javadoc")
  public static class CAMPOS {
    public static final String NOME = "nome";
    public static final String DESCRICAO = "descricao";
    public static final String ATIVO = "ativo";
  }
}