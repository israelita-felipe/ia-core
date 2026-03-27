package com.ia.core.communication.service.whatsapp;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ia.core.communication.model.StatusMensagem;
import com.ia.core.communication.service.mensagem.MensagemProvider;
import com.ia.core.communication.service.mensagem.ResultadoEnvio;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Serviço para interação com a API do WhatsApp Business.
 *
 * @author Israel Araújo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WhatsAppService
  implements MensagemProvider {

  private final WhatsAppConfig whatsAppConfig;

  @Override
  public ResultadoEnvio enviar(MensagemDTO mensagem) {
    try {
      mensagem = enviarMensagem(mensagem);
      return ResultadoEnvio.sucesso(mensagem.getIdExterno());
    } catch (Exception e) {
      return ResultadoEnvio.falha(e.getLocalizedMessage());
    }
  }

  /**
   * Envia uma mensagem via WhatsApp.
   *
   * @param mensagem Entidade Mensagem a ser enviada
   * @return Mensagem atualizada com o status
   */
  public MensagemDTO enviarMensagem(MensagemDTO mensagem) {
    try {
      String url = String.format("%s/%s/messages",
                                 whatsAppConfig.getApiUrl(),
                                 whatsAppConfig.getPhoneNumberId());

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.setBearerAuth(whatsAppConfig.getAccessToken());

      Map<String, Object> body = new HashMap<>();
      body.put("messaging_product", "whatsapp");
      body.put("to", mensagem.getTelefoneDestinatario());
      body.put("type", "text");

      Map<String, String> textMessage = new HashMap<>();
      textMessage.put("body", mensagem.getCorpoMensagem());
      body.put("text", textMessage);

      HttpEntity<Map<String, Object>> request = new HttpEntity<>(body,
                                                                 headers);

      ResponseEntity<Map> response = whatsAppConfig.getRestTemplate()
          .exchange(url, HttpMethod.POST, request, Map.class);

      if (response.getStatusCode().is2xxSuccessful()) {
        mensagem.setStatusMensagem(StatusMensagem.ENVIADA);
        mensagem.setDataEnvio(LocalDateTime.now());

        // Extrai o ID da mensagem da resposta
        Map<String, Object> responseBody = response.getBody();
        if (responseBody != null && responseBody.containsKey("messages")) {
          @SuppressWarnings("unchecked")
          java.util.List<Map<String, Object>> messages = (java.util.List<Map<String, Object>>) responseBody
              .get("messages");
          if (!messages.isEmpty()) {
            mensagem.setIdExterno((String) messages.get(0).get("id"));
          }
        }
      } else {
        mensagem.setStatusMensagem(StatusMensagem.FALHA);
        mensagem
            .setMotivoFalha("Erro ao enviar mensagem para API do WhatsApp");
      }

    } catch (Exception e) {
      log.error("Erro ao enviar mensagem WhatsApp para {}: {}",
                mensagem.getTelefoneDestinatario(), e.getMessage());
      mensagem.setStatusMensagem(StatusMensagem.FALHA);
      mensagem.setMotivoFalha(e.getMessage());
    }

    return mensagem;
  }

  @Override
  public boolean validarWebhook(String payload, String signature) {
    return false;
  }

}
