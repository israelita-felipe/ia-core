package com.ia.core.communication.service.model.enviomensagem.dto;

import com.ia.core.communication.model.mensagem.TipoCanal;
import com.ia.core.communication.service.model.modelomensagem.dto.HasVariavel;
import com.ia.core.communication.service.model.modelomensagem.dto.Variavel;
import com.ia.core.communication.service.model.modelomensagem.dto.VariavelTemplate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

/**
 * DTO para requisição de envio de mensagem em massa.
 *
 * @author Israel Araújo
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class EnvioMensagemRequestDTO implements HasVariavel {

  @NotNull(message = "Tipo do canal é obrigatório")
  private TipoCanal tipoCanal;

  @NotBlank(message = "Corpo da mensagem é obrigatório")
  private String corpoMensagem;

  private Long modeloMensagemId;

  private Map<String, String> parametrosTemplate;

  private List<String> telefones;

  private List<Long> gruposContatoIds;

  private Boolean agendado = false;

  private java.time.LocalDateTime dataAgendamento;

  @Override
  public Map<Variavel, Object> getContext() {
    return Map.of(
        VariavelTemplate.TIPO_CANAL, tipoCanal,
        VariavelTemplate.CORPO_MENSAGEM, corpoMensagem
    );
  }
}
