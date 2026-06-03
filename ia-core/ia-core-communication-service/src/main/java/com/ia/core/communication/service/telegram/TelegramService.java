package com.ia.core.communication.service.telegram;

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

import java.util.Map;

/**
 * Serviço para interação com a API do Telegram.
 * <p>
 * Implementa a interface MensagemProvider para envio de mensagens
 * através do canal Telegram. Utiliza Feign Client para maior
 * resiliência e integração com Resilience4j.
 * <p>
 * Principais funcionalidades:
 * <ul>
 *   <li>Envio de mensagens de texto</li>
 *   <li>Envio de mensagens em formato HTML</li>
 *   <li>Validação de webhooks</li>
 *   <li>Tratamento de erros de envio</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramService implements MensagemProvider {


  private final TelegramConfig telegramConfig;

  @Override
  public ResultadoEnvio enviar(MensagemDTO mensagem) {
    return enviarTelegram(mensagem);
  }

  @Tool(description = "Envia uma mensagem de texto via Telegram Bot API para um destinatário específico. " +
             "Utiliza o campo telefoneDestinatario como chat_id do Telegram e corpoMensagem como conteúdo da mensagem. " +
             "Suporta modo de parseamento configurável (texto ou HTML). " +
             "Útil para notificações, alertas e interações com usuários via Telegram. " +
             "Retorna resultado do envio com message_id ou detalhes da falha.")
  @Resilient(ResilienceProfile.EXTERNAL_API)
  public ResultadoEnvio enviarTelegram(
          @ToolParam(description = "Dados da mensagem Telegram a ser enviada (MensagemDTO, obrigatório). " +
                          "Inclui telefoneDestinatario (chat_id) e corpoMensagem (conteúdo da mensagem).", required = true) MensagemDTO mensagem) {
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
      log.error("Erro ao enviar mensagem Telegram: {}", e.getMessage(), e);
      return ResultadoEnvio.falha(e.getMessage());
    }
  }

  @Tool(description = "Envia uma mensagem em formato HTML via Telegram Bot API para um destinatário específico. " +
             "Suporta formatação HTML rica no corpo da mensagem (negrito, itálico, links, etc.). " +
             "Utiliza o campo telefoneDestinatario como chat_id do Telegram e corpoMensagem como conteúdo HTML. " +
             "Útil para mensagens formatadas, newsletters e comunicações com estilo via Telegram. " +
             "Retorna resultado do envio com message_id ou detalhes da falha.")
  @Resilient(ResilienceProfile.EXTERNAL_API)
  public ResultadoEnvio enviarTelegramHtml(
          @ToolParam(description = "Dados da mensagem HTML Telegram a ser enviada (MensagemDTO, obrigatório). " +
                          "Inclui telefoneDestinatario (chat_id) e corpoMensagem (conteúdo HTML).", required = true) MensagemDTO mensagem) {
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
                e.getMessage(), e);
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
  @Tool(description = "Envia uma mensagem via Telegram para o chat padrão configurado no sistema. " +
             "Não requer especificar o chat_id pois utiliza o valor padrão da configuração. " +
             "Útil para notificações de sistema, alertas de monitoramento e broadcasts para canais fixos. " +
             "Retorna resultado do envio com message_id ou detalhes da falha.")
  @Resilient(ResilienceProfile.EXTERNAL_API)
  public ResultadoEnvio enviarParaChatDefault(
          @ToolParam(description = "Dados da mensagem Telegram a ser enviada (MensagemDTO, obrigatório). " +
                          "Inclui corpoMensagem (conteúdo da mensagem). O chat_id será o padrão configurado.", required = true) MensagemDTO mensagem) {
    mensagem.setTelefoneDestinatario(telegramConfig.getChatId());
    return enviarTelegram(mensagem);
  }
}
