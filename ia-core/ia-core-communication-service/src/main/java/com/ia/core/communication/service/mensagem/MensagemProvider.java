package com.ia.core.communication.service.mensagem;

import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;

/**
 * Interface para provedores de mensagem.
 *
 * @author Israel Araújo
 */
public interface MensagemProvider {

  /**
   * Envia uma mensagem.
   *
   * @param mensagem mensagem a ser enviada
   * @return resultado do envio
   */
  ResultadoEnvio enviar(MensagemDTO mensagem);

  /**
   * Valida webhook.
   *
   * @param payload payload do webhook
   * @param signature assinatura do webhook
   * @return true se válido
   */
  boolean validarWebhook(String payload, String signature);
}
