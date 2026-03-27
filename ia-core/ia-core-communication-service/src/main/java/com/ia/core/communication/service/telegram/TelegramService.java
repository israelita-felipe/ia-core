package com.ia.core.communication.service.telegram;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ia.core.communication.service.mensagem.MensagemProvider;
import com.ia.core.communication.service.mensagem.ResultadoEnvio;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Serviço para interação com a API do Telegram. Implementa a interface
 * MensagemProvider para integração com estratégias de envio.
 * <p>
 * Este serviço agora usa Feign Client em vez de RestTemplate para maior
 * resiliência e integração com Resilience4j.
 * </p>
 *
 * @author Israel Araújo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramService implements MensagemProvider {

  
  private final TelegramConfig telegramConfig;

  @Override
  public ResultadoEnvio enviar(MensagemDTO mensagem) {
    return enviarTelegram(mensagem);
  }

  public ResultadoEnvio enviarTelegram(MensagemDTO mensagem) {
    try {
      // Determina o chat_id - usa o campo telefoneDestinatario como chat_id do
      // Telegram
      String chatId = mensagem.getTelefoneDestinatario() != null
          ? mensagem.getTelefoneDestinatario()
          : telegramConfig.getChatId();

      // Determina o parse_mode
      String parseMode = telegramConfig.isEnableHtml() ? "HTML" : null;

      // Cria o request
      TelegramMessageRequest request = new TelegramMessageRequest(
          chatId, mensagem.getCorpoMensagem(), parseMode);

      // Envia via Feign Client
      Map<String, Object> response = telegramConfig.getTelegramClient().sendMessage(request);

      if (response != null) {
        Boolean ok = (Boolean) response.get("ok");
        if (Boolean.TRUE.equals(ok)) {
          Map<String, Object> result = (Map<String, Object>) response
              .get("result");
          Long messageId = ((Number) result.get("message_id")).longValue();
          log.info("Mensagem Telegram enviada com sucesso para {}: message_id={}",
                   chatId, messageId);
          return ResultadoEnvio.sucesso(String.valueOf(messageId));
        } else {
          String errorDescription = (String) response.get("description");
          log.error("Falha ao enviar mensagem Telegram: {}",
                    errorDescription);
          return ResultadoEnvio.falha(errorDescription);
        }
      }

      return ResultadoEnvio.falha("Erro ao enviar mensagem Telegram");

    } catch (Exception e) {
      log.error("Erro ao enviar mensagem Telegram: {}", e.getMessage());
      return ResultadoEnvio.falha(e.getMessage());
    }
  }

  public ResultadoEnvio enviarTelegramHtml(MensagemDTO mensagem) {
    try {
      String chatId = mensagem.getTelefoneDestinatario() != null
          ? mensagem.getTelefoneDestinatario()
          : telegramConfig.getChatId();

      TelegramMessageRequest request = new TelegramMessageRequest(
          chatId, mensagem.getCorpoMensagem(), "HTML");

      Map<String, Object> response = telegramConfig.getTelegramClient().sendMessage(request);

      if (response != null) {
        Boolean ok = (Boolean) response.get("ok");
        if (Boolean.TRUE.equals(ok)) {
          Map<String, Object> result = (Map<String, Object>) response
              .get("result");
          Long messageId = ((Number) result.get("message_id")).longValue();
          return ResultadoEnvio.sucesso(String.valueOf(messageId));
        }
      }

      return ResultadoEnvio.falha("Erro ao enviar mensagem HTML Telegram");

    } catch (Exception e) {
      log.error("Erro ao enviar mensagem HTML Telegram: {}",
                e.getMessage());
      return ResultadoEnvio.falha(e.getMessage());
    }
  }

  @Override
  public boolean validarWebhook(String payload, String signature) {
    // Implementação básica - em produção usar HMAC validation
    log.debug("Validando webhook Telegram");
    return payload != null && !payload.isBlank();
  }

  /**
   * Envia mensagem usando o chat padrão configurado.
   *
   * @param mensagem mensagem a ser enviada
   * @return resultado do envio
   */
  public ResultadoEnvio enviarParaChatDefault(MensagemDTO mensagem) {
    mensagem.setTelefoneDestinatario(telegramConfig.getChatId());
    return enviarTelegram(mensagem);
  }
}
