package com.ia.core.communication.service.whatsapp;

import com.ia.core.communication.model.mensagem.StatusMensagem;
import com.ia.core.communication.service.mensagem.MensagemProvider;
import com.ia.core.communication.service.mensagem.ResultadoEnvio;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Serviço para interação com a API do WhatsApp Business.
 * <p>
 * Este serviço agora usa Feign Client em vez de RestTemplate para maior
 * resiliência e integração com Resilience4j.
 * </p>
 *
 * @author Israel Araújo
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WhatsAppService implements MensagemProvider {

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
      // Cria o request
      Map<String, String> textMessage = new HashMap<>();
      textMessage.put("body", mensagem.getCorpoMensagem());

      WhatsAppMessageRequest request = new WhatsAppMessageRequest(
          "whatsapp",
          mensagem.getTelefoneDestinatario(),
          "text",
          textMessage
      );

      // Envia via Feign Client com token Bearer
      String bearerToken = "Bearer " + whatsAppConfig.getAccessToken();
      Map<String, Object> response = whatsAppConfig.getWhatsAppClient()
          .sendMessage(bearerToken, request);

      if (response != null) {
        mensagem.setStatusMensagem(StatusMensagem.ENVIADA);
        mensagem.setDataEnvio(LocalDateTime.now());

        // Extrai o ID da mensagem da resposta
        if (response.containsKey("messages")) {
          @SuppressWarnings("unchecked")
          java.util.List<Map<String, Object>> messages = (java.util.List<Map<String, Object>>) response
              .get("messages");
          if (!messages.isEmpty()) {
            mensagem.setIdExterno((String) messages.get(0).get("id"));
          }
        }
      } else {
        mensagem.setStatusMensagem(StatusMensagem.FALHA);
        mensagem.setMotivoFalha("Erro ao enviar mensagem para API do WhatsApp");
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
