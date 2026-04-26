package com.ia.core.communication.model.mensagem;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representando o canal de comunicação para envio de mensagens.
 *
 * @author Israel Araújo
 */
@Getter
@AllArgsConstructor
public enum TipoCanal {
  WHATSAPP("WhatsApp"),
  SMS("SMS"),
  EMAIL("E-mail"),
  TELEGRAM("Telegram"),
  WEBHOOK("Webhook");

  private final String descricao;
}
