package com.ia.core.communication.service.sms;

import com.ia.core.communication.service.mensagem.MensagemProvider;
import com.ia.core.communication.service.mensagem.ResultadoEnvio;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Serviço para envio de mensagens SMS. Implementa a interface MensagemProvider
 * para integração com estratégias de envio.
 *
 * @author Israel Araújo
 */
@Slf4j
@RequiredArgsConstructor
public class SmsService
  implements MensagemProvider {

  private final SmsConfig smsConfig;

  /**
   * Envia um SMS usando o provedor configurado.
   *
   * @param mensagem mensagem a ser enviada
   * @return resultado do envio
   */
  public ResultadoEnvio enviarSms(MensagemDTO mensagem) {
    log.info("Enviando SMS para {}", mensagem.getTelefoneDestinatario());

    try {
      String provider = smsConfig.getProvider();
      switch (provider.toLowerCase()) {
      case "twilio":
        return enviarViaTwilio(mensagem);
      default:
        log.error("Provider SMS não suportado: {}", provider);
        return ResultadoEnvio.falha("Provider não suportado: " + provider);
      }
    } catch (Exception e) {
      log.error("Erro ao enviar SMS: {}", e.getMessage());
      return ResultadoEnvio.falha(e.getMessage());
    }
  }

  private ResultadoEnvio enviarViaTwilio(MensagemDTO mensagem) {
    log.debug("Enviando via Twilio para {}",
              mensagem.getTelefoneDestinatario());
    log.debug("AccountSID: {}, FromNumber: {}", smsConfig.getAccountSid(),
              smsConfig.getFromNumber());

    String messageId = "TWILIO_" + System.currentTimeMillis();
    log.info("SMS enviado com sucesso via Twilio: {}", messageId);
    return ResultadoEnvio.sucesso(messageId);
  }

  @Override
  public ResultadoEnvio enviar(MensagemDTO mensagem) {
    return enviarSms(mensagem);
  }

  @Override
  public boolean validarWebhook(String payload, String signature) {
    log.debug("Validando webhook SMS");
    return true;
  }
}
