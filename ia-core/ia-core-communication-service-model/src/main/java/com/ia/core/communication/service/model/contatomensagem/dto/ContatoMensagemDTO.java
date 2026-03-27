package com.ia.core.communication.service.model.contatomensagem.dto;

import java.util.Set;

import com.ia.core.communication.model.ContatoMensagem;
import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoDTO;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * DTO para a entidade ContatoMensagem.
 *
 * @author Israel Araújo
 */
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ContatoMensagemDTO extends AbstractBaseEntityDTO<ContatoMensagem> {
  /** Serial UID */
  private static final long serialVersionUID = 1L;

  /**
   * Request de pesquisa
   *
   * @return {@link SearchRequestDTO}
   */
  public static SearchRequestDTO getSearchRequest() {
    return new ContatoMensagemSearchRequest();
  }

  /**
   * Filtros
   *
   * @return {@link Set} de filtros do DTO
   */
  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  @NotNull(message = "Grupo de contato é obrigatório")
  private GrupoContatoDTO grupoContato;

  @NotBlank(message = "Telefone é obrigatório")
  @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
  private String telefone;

  @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
  private String nome;

  @Override
  public ContatoMensagemDTO cloneObject() {
    return toBuilder().build();
  }

  @Override
  public ContatoMensagemDTO copyObject() {
    return toBuilder().id(null).version(null).build();
  }

  @Override
  public String toString() {
    return String.format("%s - %s", telefone, nome);
  }

  @SuppressWarnings("javadoc")
  public static class CAMPOS {
    public static final String GRUPO_CONTATO = "grupoContato";
    public static final String TELEFONE = "telefone";
    public static final String NOME = "nome";
  }
}