package com.ia.core.communication.service.telegram;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.ia.core.communication.service.mensagem.MensagemProvider;
import com.ia.core.communication.service.mensagem.ResultadoEnvio;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Serviço para interação com a API do Telegram. Implementa a interface
 * MensagemProvider para integração com estratégias de envio.
 *
 * @author Israel Araújo
 */
@Slf4j
@RequiredArgsConstructor
public class TelegramService
  implements MensagemProvider {

  private final TelegramConfig telegramConfig;

  @Override
  public ResultadoEnvio enviar(MensagemDTO mensagem) {
    return enviarTelegram(mensagem);
  }

  public ResultadoEnvio enviarTelegram(MensagemDTO mensagem) {
    try {
      String url = String.format("%s/bot%s/sendMessage",
                                 telegramConfig.getApiUrl(),
                                 telegramConfig.getBotToken());

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);

      // Determina o chat_id - usa o campo telefoneDestinatario como chat_id do
      // Telegram
      String chatId = mensagem.getTelefoneDestinatario() != null ? mensagem
          .getTelefoneDestinatario() : telegramConfig.getChatId();

      Map<String, Object> body = new HashMap<>();
      body.put("chat_id", chatId);
      body.put("text", mensagem.getCorpoMensagem());

      // Adiciona parse_mode se HTML estiver habilitado
      if (telegramConfig.isEnableHtml()) {
        body.put("parse_mode", "HTML");
      }

      HttpEntity<Map<String, Object>> request = new HttpEntity<>(body,
                                                                 headers);

      ResponseEntity<Map> response = telegramConfig.getRestTemplate()
          .postForEntity(url, request, Map.class);

      if (response.getStatusCode().is2xxSuccessful()
          && response.getBody() != null) {
        Boolean ok = (Boolean) response.getBody().get("ok");
        if (Boolean.TRUE.equals(ok)) {
          Map<String, Object> result = (Map<String, Object>) response
              .getBody().get("result");
          Long messageId = ((Number) result.get("message_id")).longValue();
          log.info("Mensagem Telegram enviada com sucesso para {}: message_id={}",
                   chatId, messageId);
          return ResultadoEnvio.sucesso(String.valueOf(messageId));
        } else {
          String errorDescription = (String) response.getBody()
              .get("description");
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
      String url = String.format("%s/bot%s/sendMessage",
                                 telegramConfig.getApiUrl(),
                                 telegramConfig.getBotToken());

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);

      String chatId = mensagem.getTelefoneDestinatario() != null ? mensagem
          .getTelefoneDestinatario() : telegramConfig.getChatId();

      Map<String, Object> body = new HashMap<>();
      body.put("chat_id", chatId);
      body.put("text", mensagem.getCorpoMensagem());
      body.put("parse_mode", "HTML");

      HttpEntity<Map<String, Object>> request = new HttpEntity<>(body,
                                                                 headers);

      ResponseEntity<Map> response = telegramConfig.getRestTemplate()
          .postForEntity(url, request, Map.class);

      if (response.getStatusCode().is2xxSuccessful()
          && response.getBody() != null) {
        Boolean ok = (Boolean) response.getBody().get("ok");
        if (Boolean.TRUE.equals(ok)) {
          Map<String, Object> result = (Map<String, Object>) response
              .getBody().get("result");
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
