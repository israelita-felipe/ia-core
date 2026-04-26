package com.ia.core.communication.service.whatsapp;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

/**
 * Cliente Feign para API do WhatsApp Business.
 * <p>
 * Use este cliente para enviar mensagens via WhatsApp.
 * O CircuitBreaker pode ser habilitado via configuration.
 * </p>
 *
 * @author Israel Araújo
 * @see <a href="https://developers.facebook.com/docs/whatsapp</a>
 */
@FeignClient(name = "whatsAppClient", url = "${whatsapp.api.url:https://graph.facebook.com/v18.0}")
public interface WhatsAppClient {

  /**
   * Envia uma mensagem via WhatsApp.
   *
   * @param accessToken o token de acesso
   * @param request o request com os parâmetros da mensagem
   * @return resposta da API do WhatsApp
   */
  @PostMapping("/${whatsapp.phone-number-id}/messages")
  Map<String, Object> sendMessage(
      @RequestHeader("Authorization") String accessToken,
      @RequestBody WhatsAppMessageRequest request);
}

/**
 * Record para representar o corpo da requisição de mensagem do WhatsApp.
 *
 * @author Israel Araújo
 */
record WhatsAppMessageRequest(String messaging_product, String to, String type,
                              Map<String, String> text) {
}
