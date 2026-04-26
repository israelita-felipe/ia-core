package com.ia.core.communication.service.model.mensagem.dto;

import com.ia.core.communication.model.mensagem.Mensagem;
import com.ia.core.communication.model.mensagem.StatusMensagem;
import com.ia.core.communication.model.mensagem.TipoCanal;
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

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * DTO para a entidade Mensagem.
 *
 * @author Israel Araújo
 */
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class MensagemDTO extends AbstractBaseEntityDTO<Mensagem> implements HasVariavel {
  /** Serial UID */
  private static final long serialVersionUID = 1L;

  /**
   * Request de pesquisa
   *
   * @return {@link SearchRequestDTO}
   */
  public static SearchRequestDTO getSearchRequest() {
    return new MensagemSearchRequest();
  }

  /**
   * Filtros
   *
   * @return {@link Set} de filtros do DTO
   */
  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  @NotBlank(message = "Telefone do destinatário é obrigatório")
  @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
  private String telefoneDestinatario;

  @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
  private String nomeDestinatario;

  @NotBlank(message = "Corpo da mensagem é obrigatório")
  private String corpoMensagem;

  @NotNull(message = "Tipo do canal é obrigatório")
  private TipoCanal tipoCanal;

  @NotNull(message = "Status da mensagem é obrigatório")
  private StatusMensagem statusMensagem;

  @Size(max = 100, message = "ID externo deve ter no máximo 100 caracteres")
  private String idExterno;

  private LocalDateTime dataEnvio;

  private LocalDateTime dataEntrega;

  private LocalDateTime dataLeitura;

  @Size(max = 500, message = "Motivo da falha deve ter no máximo 500 caracteres")
  private String motivoFalha;

  @Override
  public MensagemDTO cloneObject() {
    return toBuilder().build();
  }

  @Override
  public MensagemDTO copyObject() {
    return toBuilder().id(null).version(null).build();
  }

  @Override
  public Map<Variavel, Object> getContext() {
    return Map.of(
        VariavelTemplate.TELEFONE, telefoneDestinatario,
        VariavelTemplate.NOME, nomeDestinatario,
        VariavelTemplate.CORPO_MENSAGEM, corpoMensagem,
        VariavelTemplate.DATA_ENVIO, dataEnvio,
        VariavelTemplate.STATUS, statusMensagem
    );
  }

  @Override
  public String toString() {
    return String.format("%s -> %s", telefoneDestinatario, tipoCanal);
  }

  @SuppressWarnings("javadoc")
  public static class CAMPOS {
    public static final String TELEFONE_DESTINATARIO = "telefoneDestinatario";
    public static final String NOME_DESTINATARIO = "nomeDestinatario";
    public static final String CORPO_MENSAGEM = "corpoMensagem";
    public static final String TIPO_CANAL = "tipoCanal";
    public static final String STATUS_MENSAGEM = "statusMensagem";
    public static final String ID_EXTERNO = "idExterno";
    public static final String DATA_ENVIO = "dataEnvio";
    public static final String DATA_ENTREGA = "dataEntrega";
    public static final String DATA_LEITURA = "dataLeitura";
  }
}
