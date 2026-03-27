package com.ia.core.communication.service.mensagem;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.ia.core.communication.model.StatusMensagem;

import lombok.extern.slf4j.Slf4j;

/**
 * Listener para eventos de mensagens.
 * Implementa o padrão Observer para processar eventos de webhook.
 *
 * @author Israel Araújo
 */
@Slf4j
@Component
public class WebhookEventListener {

  private final MensagemRepository mensagemRepository;

  public WebhookEventListener(MensagemRepository mensagemRepository) {
    this.mensagemRepository = mensagemRepository;
  }

  public void onMensagemEnviada(String messageId, LocalDateTime timestamp) {
    log.info("Mensagem enviada: {} em {}", messageId, timestamp);
    atualizarStatus(messageId, StatusMensagem.ENVIADA, timestamp);
  }

  public void onMensagemEntregue(String messageId, LocalDateTime timestamp) {
    log.info("Mensagem entregue: {} em {}", messageId, timestamp);
    atualizarStatus(messageId, StatusMensagem.ENTREGUE, timestamp);
  }

  public void onMensagemLida(String messageId, LocalDateTime timestamp) {
    log.info("Mensagem lida: {} em {}", messageId, timestamp);
    atualizarStatus(messageId, StatusMensagem.LIDA, timestamp);
  }

  public void onMensagemFalha(String messageId, String erro, LocalDateTime timestamp) {
    log.error("Mensagem falhou: {} - {} em {}", messageId, erro, timestamp);
    mensagemRepository.findByIdExterno(messageId).ifPresent(mensagem -> {
      mensagem.setStatusMensagem(StatusMensagem.FALHA);
      mensagem.setMotivoFalha(erro);
      mensagemRepository.save(mensagem);
    });
  }

  private void atualizarStatus(String messageId, StatusMensagem status, LocalDateTime timestamp) {
    mensagemRepository.findByIdExterno(messageId).ifPresent(mensagem -> {
      mensagem.setStatusMensagem(status);
      switch (status) {
        case ENVIADA:
          mensagem.setDataEnvio(timestamp);
          break;
        case ENTREGUE:
          mensagem.setDataEntrega(timestamp);
          break;
        case LIDA:
          mensagem.setDataLeitura(timestamp);
          break;
        default:
          break;
      }
      mensagemRepository.save(mensagem);
      log.info("Status atualizado para {}: {}", status, messageId);
    });
  }
}
