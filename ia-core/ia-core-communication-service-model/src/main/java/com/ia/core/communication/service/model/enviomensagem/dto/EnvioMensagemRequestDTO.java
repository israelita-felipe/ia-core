package com.ia.core.communication.service.model.enviomensagem.dto;

import java.util.List;
import java.util.Map;

import com.ia.core.communication.model.TipoCanal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * DTO para requisição de envio de mensagem em massa.
 *
 * @author Israel Araújo
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class EnvioMensagemRequestDTO {

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
}
