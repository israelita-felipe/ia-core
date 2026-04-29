package com.ia.core.communication.service.telegram;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * Cliente Feign para API do Telegram.
 * <p>
 * Use este cliente para enviar mensagens via Telegram.
 * O CircuitBreaker pode ser habilitado via configuration.
 * </p>
 *
 * @author Israel Araújo
 * @see <a href="https://core.telegram.org/bots/api">Telegram Bot API</a>
 */

@FeignClient(name = "telegramClient", url = "${telegram.api.url:https://api.telegram.org}")
public interface TelegramClient {

  /**
   * Envia uma mensagem via Telegram.
   *
   * @param request o request com os parâmetros da mensagem
   * @return resposta da API do Telegram
   */
  @PostMapping("/bot${telegram.bot.token}/sendMessage")
  Map<String, Object> sendMessage(@RequestBody TelegramMessageRequest request);
}

/**
 * Record para representar o corpo da requisição de mensagem do Telegram.
 *
 * @author Israel Araújo
 */
record TelegramMessageRequest(String chat_id, String text, String parse_mode) {
}
