package com.ia.core.communication.service.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.ia.core.communication.model.Mensagem;
import com.ia.core.communication.model.StatusMensagem;
import com.ia.core.communication.service.mensagem.MensagemProvider;
import com.ia.core.communication.service.mensagem.ResultadoEnvio;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Serviço para envio de e-mails. Implementa a interface MensagemProvider para
 * integração com estratégias de envio.
 *
 * @author Israel Araújo
 */
@Slf4j
@RequiredArgsConstructor
public class EmailService
  implements MensagemProvider {

  private final EmailConfig emailConfig;

  /**
   * Envia um e-mail simples.
   *
   * @return resultado do envio
   */
  public ResultadoEnvio enviarEmail(MensagemDTO mensagem) {
    log.info("Enviando e-mail para {}", mensagem.getTelefoneDestinatario());

    try {
      SimpleMailMessage email = new SimpleMailMessage();
      email.setTo(mensagem.getTelefoneDestinatario());
      email.setSubject(mensagem.getNomeDestinatario() != null ? mensagem
          .getNomeDestinatario() : "Mensagem");
      email.setText(mensagem.getCorpoMensagem());

      if (emailConfig.getFromAddress() != null) {
        email.setFrom(emailConfig.getFromAddress());
      }

      emailConfig.getMailSender().send(email);

      String messageId = "EMAIL_" + System.currentTimeMillis();
      log.info("E-mail enviado com sucesso: {}", messageId);
      return ResultadoEnvio.sucesso(messageId);

    } catch (Exception e) {
      log.error("Erro ao enviar e-mail: {}", e.getMessage());
      return ResultadoEnvio.falha(e.getMessage());
    }
  }

  /**
   * Envia um e-mail em formato HTML.
   *
   * @param mensagem mensagem a ser enviada
   * @return resultado do envio
   */
  public ResultadoEnvio enviarEmailHtml(MensagemDTO mensagem) {
    log.info("Enviando e-mail HTML para {}",
             mensagem.getTelefoneDestinatario());

    try {
      MimeMessage mimeMessage = getSender().createMimeMessage();
      mimeMessage.setRecipients(jakarta.mail.Message.RecipientType.TO,
                                mensagem.getTelefoneDestinatario());
      mimeMessage
          .setSubject(mensagem.getNomeDestinatario() != null ? mensagem
              .getNomeDestinatario() : "Mensagem");
      mimeMessage.setContent(mensagem.getCorpoMensagem(),
                             "text/html; charset=UTF-8");

      if (emailConfig.getFromAddress() != null) {
        mimeMessage.setFrom(emailConfig.getFromAddress());
      }

      getSender().send(mimeMessage);

      String messageId = "EMAIL_HTML_" + System.currentTimeMillis();
      log.info("E-mail HTML enviado com sucesso: {}", messageId);
      return ResultadoEnvio.sucesso(messageId);

    } catch (Exception e) {
      log.error("Erro ao enviar e-mail HTML: {}", e.getMessage());
      return ResultadoEnvio.falha(e.getMessage());
    }
  }

  /**
   * Envia um e-mail usando a entidade Mensagem.
   *
   * @param mensagem entidade mensagem
   * @return mensagem atualizada
   */
  public Mensagem enviarEmailEntity(Mensagem mensagem) {
    log.info("Enviando e-mail para {}", mensagem.getTelefoneDestinatario());

    try {

      SimpleMailMessage email = new SimpleMailMessage();
      email.setTo(mensagem.getTelefoneDestinatario());
      email.setSubject(mensagem.getNomeDestinatario() != null ? mensagem
          .getNomeDestinatario() : "Mensagem");
      email.setText(mensagem.getCorpoMensagem());

      if (emailConfig.getFromAddress() != null) {
        email.setFrom(emailConfig.getFromAddress());
      }

      getSender().send(email);

      mensagem.setStatusMensagem(StatusMensagem.ENVIADA);
      mensagem.setDataEnvio(java.time.LocalDateTime.now());

    } catch (Exception e) {
      log.error("Erro ao enviar e-mail: {}", e.getMessage());
      mensagem.setStatusMensagem(StatusMensagem.FALHA);
      mensagem.setMotivoFalha(e.getMessage());
    }

    return mensagem;
  }

  /**
   * Envia um e-mail HTML usando a entidade Mensagem.
   *
   * @param para     destinatário
   * @param assunto  assunto do e-mail
   * @param htmlBody corpo HTML do e-mail
   */
  public void enviarEmailHtml(String para, String assunto,
                              String htmlBody) {
    log.info("Enviando e-mail HTML para {}", para);

    try {
      MimeMessage mimeMessage = getSender().createMimeMessage();
      mimeMessage.setRecipients(jakarta.mail.Message.RecipientType.TO,
                                para);
      mimeMessage.setSubject(assunto);
      mimeMessage.setContent(htmlBody, "text/html; charset=UTF-8");

      if (emailConfig.getFromAddress() != null) {
        mimeMessage.setFrom(emailConfig.getFromAddress());
      }

      getSender().send(mimeMessage);
      log.info("E-mail HTML enviado com sucesso para {}", para);
    } catch (Exception e) {
      log.error("Erro ao enviar e-mail HTML: {}", e.getMessage());
      throw new RuntimeException("Falha ao enviar e-mail", e);
    }
  }

  /**
   * @return
   */
  public JavaMailSender getSender() {
    return emailConfig.getMailSender();
  }

  @Override
  public ResultadoEnvio enviar(MensagemDTO mensagem) {
    return enviarEmail(mensagem);
  }

  @Override
  public boolean validarWebhook(String payload, String signature) {
    log.debug("Validando webhook de e-mail");
    return true;
  }
}
