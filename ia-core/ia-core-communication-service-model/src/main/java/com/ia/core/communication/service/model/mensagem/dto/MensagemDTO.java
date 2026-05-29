package com.ia.core.communication.service.model.mensagem.dto;

import com.ia.core.communication.model.mensagem.Mensagem;
import com.ia.core.communication.model.mensagem.StatusMensagem;
import com.ia.core.communication.model.mensagem.TipoCanal;
import com.ia.core.communication.service.model.modelomensagem.dto.HasVariavel;
import com.ia.core.communication.service.model.modelomensagem.dto.Variavel;
import com.ia.core.communication.service.model.modelomensagem.dto.VariavelTemplate;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * DTO para a entidade Mensagem.
 * <p>
 * Representa os dados de transferência para mensagens de comunicação,
 * incluindo telefone destinatário, nome destinatário, corpo da mensagem,
 * tipo de canal, status, datas de envio/entrega/leitura e motivo de falha.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de uma mensagem de comunicação")
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

   @NotBlank(message = MensagemTranslator.VALIDATION.TELEFONE_DESTINATARIO_NOT_BLANK)
    @Size(max = 20, message = MensagemTranslator.VALIDATION.TELEFONE_DESTINATARIO_SIZE)
    @Schema(description = "Telefone do destinatário", example = "+5511999999999", required = true)
    private String telefoneDestinatario;

    @Size(max = 100, message = MensagemTranslator.VALIDATION.NOME_DESTINATARIO_SIZE)
    @Schema(description = "Nome do destinatário", example = "João Silva")
    private String nomeDestinatario;

    @NotBlank(message = MensagemTranslator.VALIDATION.CORPO_MENSAGEM_NOT_BLANK)
    @Schema(description = "Corpo da mensagem", example = "Olá, esta é uma mensagem de teste", required = true)
    private String corpoMensagem;

    @NotNull(message = MensagemTranslator.VALIDATION.TIPO_CANAL_NOT_NULL)
    @Schema(description = "Tipo do canal de comunicação", example = "WHATSAPP", required = true)
    private TipoCanal tipoCanal;

    @NotNull(message = MensagemTranslator.VALIDATION.STATUS_MENSAGEM_NOT_NULL)
    @Schema(description = "Status da mensagem", example = "ENVIADA", required = true)
    private StatusMensagem statusMensagem;

    @Size(max = 100, message = MensagemTranslator.VALIDATION.ID_EXTERNO_SIZE)
    @Schema(description = "ID externo da mensagem", example = "msg-12345")
    private String idExterno;

   @Schema(description = "Data de envio da mensagem", example = "2024-01-15T10:30:00")
   private LocalDateTime dataEnvio;

   @Schema(description = "Data de entrega da mensagem", example = "2024-01-15T10:31:00")
   private LocalDateTime dataEntrega;

   @Schema(description = "Data de leitura da mensagem", example = "2024-01-15T10:35:00")
   private LocalDateTime dataLeitura;

    @Size(max = 500, message = MensagemTranslator.VALIDATION.MOTIVO_FALHA_SIZE)
    @Schema(description = "Motivo da falha no envio", example = "Número inválido")
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
    public static final String MOTIVO_FALHA = "motivoFalha";

    public static Set<String> values() {
      return Set.of(
        TELEFONE_DESTINATARIO,
        NOME_DESTINATARIO,
        CORPO_MENSAGEM,
        TIPO_CANAL,
        STATUS_MENSAGEM,
        ID_EXTERNO,
        DATA_ENVIO,
        DATA_ENTREGA,
        DATA_LEITURA,
        MOTIVO_FALHA
      );
    }
  }
}
