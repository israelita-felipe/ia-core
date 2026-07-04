package com.ia.core.communication.service.model.enviomensagem.dto;

import com.ia.core.communication.model.mensagem.TipoCanal;
import com.ia.core.communication.service.model.modelomensagem.dto.HasVariavel;
import com.ia.core.communication.service.model.modelomensagem.dto.Variavel;
import com.ia.core.communication.service.model.modelomensagem.dto.VariavelTemplate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * DTO para requisição de envio de mensagem em massa.
 * <p>
 * Representa os dados de transferência para envio de mensagens,
 * incluindo tipo de canal, corpo da mensagem, modelo, parâmetros de template,
 * telefones e grupos de contatos.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class EnvioMensagemRequestDTO implements HasVariavel {

   @NotNull(message = EnvioMensagemTranslator.VALIDATION.TIPO_CANAL_NOT_NULL)
   private TipoCanal tipoCanal;

   @NotBlank(message = EnvioMensagemTranslator.VALIDATION.CORPO_MENSAGEM_NOT_BLANK)
   private String corpoMensagem;

  private Long modeloMensagemId;

  private Map<String, String> parametrosTemplate;

  private List<String> telefones;

  private List<Long> gruposContatoIds;

  @Builder.Default
  private Boolean agendado = false;

  private java.time.LocalDateTime dataAgendamento;

  @Override
  public Map<Variavel, Object> getContext() {
    return Map.of(
        VariavelTemplate.TIPO_CANAL, tipoCanal,
        VariavelTemplate.CORPO_MENSAGEM, corpoMensagem
    );
  }

  @SuppressWarnings("javadoc")
  public static class CAMPOS {
    public static final String TIPO_CANAL = "tipoCanal";
    public static final String CORPO_MENSAGEM = "corpoMensagem";
    public static final String MODELO_MENSAGEM_ID = "modeloMensagemId";
    public static final String PARAMETROS_TEMPLATE = "parametrosTemplate";
    public static final String TELEFONES = "telefones";
    public static final String GRUPOS_CONTATO_IDS = "gruposContatoIds";
    public static final String AGENDADO = "agendado";
    public static final String DATA_AGENDAMENTO = "dataAgendamento";

    public static Set<String> values() {
      return Set.of(TIPO_CANAL, CORPO_MENSAGEM, MODELO_MENSAGEM_ID, PARAMETROS_TEMPLATE,
                     TELEFONES, GRUPOS_CONTATO_IDS, AGENDADO, DATA_AGENDAMENTO);
    }
  }
}
