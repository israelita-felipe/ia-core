package com.ia.core.communication.service.model.contatomensagem.dto;

import com.ia.core.communication.model.contato.ContatoMensagem;
import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoDTO;
import com.ia.core.communication.service.model.modelomensagem.dto.HasVariavel;
import com.ia.core.communication.service.model.modelomensagem.dto.Variavel;
import com.ia.core.communication.service.model.modelomensagem.dto.VariavelTemplate;
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

import java.util.Map;
import java.util.Set;

/**
 * DTO para a entidade ContatoMensagem.
 * <p>
 * Representa os dados de transferência para contatos de mensagens,
 * incluindo informações de grupo, telefone e nome.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ContatoMensagemDTO extends AbstractBaseEntityDTO<ContatoMensagem> implements HasVariavel {
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

   @NotNull(message = ContatoMensagemTranslator.VALIDATION.GRUPO_CONTATO_NOT_NULL)
   private GrupoContatoDTO grupoContato;

   @NotBlank(message = ContatoMensagemTranslator.VALIDATION.TELEFONE_NOT_BLANK)
   @Size(max = 20, message = ContatoMensagemTranslator.VALIDATION.TELEFONE_SIZE)
   private String telefone;

   @Size(max = 100, message = ContatoMensagemTranslator.VALIDATION.NOME_SIZE)
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

  @Override
  public Map<Variavel, Object> getContext() {
    return Map.of(
        VariavelTemplate.TELEFONE, telefone,
        VariavelTemplate.NOME, nome
    );
  }

  @SuppressWarnings("javadoc")
  public static class CAMPOS extends com.ia.core.service.dto.entity.AbstractBaseEntityDTO.CAMPOS {
    public static final String GRUPO_CONTATO = "grupoContato";
    public static final String TELEFONE = "telefone";
    public static final String NOME = "nome";

    public static Set<String> values() {
      return Set.of(ID, VERSION, GRUPO_CONTATO, TELEFONE, NOME);
    }
  }
}
