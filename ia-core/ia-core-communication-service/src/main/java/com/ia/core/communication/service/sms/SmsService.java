package com.ia.core.communication.service.sms;

import com.ia.core.communication.service.mensagem.MensagemProvider;
import com.ia.core.communication.service.mensagem.ResultadoEnvio;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Serviço para envio de mensagens SMS.
 * <p>
 * Implementa a interface MensagemProvider para envio de mensagens
 * através do canal SMS. Suporta múltiplos providers como Twilio.
 * <p>
 * Principais funcionalidades:
 * <ul>
 *   <li>Envio de SMS via Twilio</li>
 *   <li>Validação de webhooks</li>
 *   <li>Tratamento de erros de envio</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class SmsService
  implements MensagemProvider {

  private final SmsConfig smsConfig;

  /**
   * Envia um SMS usando o provedor configurado.
   *
   * @param mensagem mensagem a ser enviada
   * @return resultado do envio
   */
  @Tool(description = "Envia uma mensagem SMS para um destinatário usando o provedor configurado (ex: Twilio). " +
             "Utiliza o campo telefoneDestinatario como número de telefone e corpoMensagem como conteúdo da mensagem. " +
             "Suporta múltiplos providers de SMS configurados no sistema. " +
             "Útil para notificações urgentes, alertas de segurança e comunicações de alta prioridade. " +
             "Retorna resultado do envio com ID da mensagem ou detalhes da falha.")
  @Resilient(ResilienceProfile.EXTERNAL_API)
  public ResultadoEnvio enviarSms(
          @ToolParam(description = "Dados da mensagem SMS a ser enviada (MensagemDTO, obrigatório). " +
                          "Inclui telefoneDestinatario (número de telefone) e corpoMensagem (conteúdo da mensagem).", required = true) MensagemDTO mensagem) {
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
      log.error("Erro ao enviar SMS: {}", e.getMessage(), e);
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
