package com.ia.core.communication.model.mensagem;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enumeração que representa os canais de comunicação disponíveis para envio de mensagens.
 * <p>
 * Define os canais de comunicação suportados pelo sistema para o envio de notificações,
 * alertas e mensagens aos usuários. Cada canal possui características específicas de
 * entrega, formato e tempo de resposta.
 * <p>
 * Esta enumeração é utilizada em todo o sistema para identificar o canal preferido
 * ou disponível para comunicação com os usuários.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum TipoCanal {
  /**
   * Canal de comunicação via WhatsApp.
   * <p>
   * Permite o envio de mensagens de texto, mídia e documentos através da
   * plataforma WhatsApp. Suporta formatos ricos e botões interativos.
   */
  WHATSAPP("WhatsApp"),

  /**
   * Canal de comunicação via SMS (Short Message Service).
   * <p>
   * Envio de mensagens de texto curtas através de operadoras de telefonia móvel.
   * Limitação de 160 caracteres por mensagem. Alta taxa de entrega e alcance global.
   */
  SMS("SMS"),

  /**
   * Canal de comunicação via E-mail.
   * <p>
   * Envio de mensagens através de correio eletrônico. Suporta conteúdo HTML,
   * anexos e formatação avançada. Adequado para comunicações formais e detalhadas.
   */
  EMAIL("E-mail"),

  /**
   * Canal de comunicação via Telegram.
   * <p>
   * Envio de mensagens através da plataforma Telegram. Suporta mensagens
   * criptografadas, grupos e canais com alta capacidade de transmissão.
   */
  TELEGRAM("Telegram"),

  /**
   * Canal de comunicação via Webhook.
   * <p>
   * Integração através de chamadas HTTP POST para endpoints configurados.
   * Permite integração com sistemas externos e automação de fluxos de trabalho.
   */
  WEBHOOK("Webhook");

  /**
   * Descrição legível do canal de comunicação.
   * <p>
   * Representa o nome exibido do canal para fins de apresentação em interfaces
   * de usuário e relatórios. Este campo é imutável e é definido no momento da
   * construção de cada constante do enum.
   */
  private final String descricao;
}
