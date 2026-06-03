package com.ia.core.communication.service.email;

import com.ia.core.communication.model.mensagem.Mensagem;
import com.ia.core.communication.model.mensagem.StatusMensagem;
import com.ia.core.communication.service.mensagem.MensagemProvider;
import com.ia.core.communication.service.mensagem.ResultadoEnvio;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Serviço para envio de e-mails.
 * <p>
 * Implementa a interface MensagemProvider para envio de mensagens
 * através do canal E-mail. Suporta envio de e-mails simples e em
 * formato HTML.
 * <p>
 * Principais funcionalidades:
 * <ul>
 *   <li>Envio de e-mails simples</li>
 *   <li>Envio de e-mails em formato HTML</li>
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
public class EmailService
  implements MensagemProvider {

  private final EmailConfig emailConfig;

  /**
   * Envia um e-mail simples.
   *
   * @return resultado do envio
   */
  @Tool(description = "Envia um e-mail simples em formato texto para um destinatário específico. " +
             "Utiliza o campo telefoneDestinatario como endereço de e-mail, nomeDestinatario como assunto " +
             "e corpoMensagem como conteúdo do e-mail. Útil para notificações simples, alertas e comunicações diretas. " +
             "Retorna resultado do envio com ID da mensagem ou detalhes da falha.")
  @Resilient(ResilienceProfile.EXTERNAL_API)
  public ResultadoEnvio enviarEmail(
          @ToolParam(description = "Dados da mensagem a ser enviada (MensagemDTO, obrigatório). " +
                          "Inclui telefoneDestinatario (e-mail), nomeDestinatario (assunto) e corpoMensagem (conteúdo).", required = true) MensagemDTO mensagem) {
    Objects.requireNonNull(mensagem, "Mensagem não pode ser null");
    Objects.requireNonNull(mensagem.getTelefoneDestinatario(), "Destinatário não pode ser null");
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

    } catch (org.springframework.mail.MailException e) {
      log.error("Erro ao enviar e-mail: {}", e.getMessage(), e);
      return ResultadoEnvio.falha(e.getMessage());
    }
  }

  /**
   * Envia um e-mail em formato HTML.
   *
   * @param mensagem mensagem a ser enviada
   * @return resultado do envio
   */
  @Tool(description = "Envia um e-mail em formato HTML para um destinatário específico. " +
             "Suporta formatação rica, estilos CSS e estrutura HTML no corpo da mensagem. " +
             "Utiliza o campo telefoneDestinatario como endereço de e-mail, nomeDestinatario como assunto " +
             "e corpoMensagem como conteúdo HTML. Útil para newsletters, e-mails marketing e comunicações formatadas. " +
             "Retorna resultado do envio com ID da mensagem ou detalhes da falha.")
  @Resilient(ResilienceProfile.EXTERNAL_API)
  public ResultadoEnvio enviarEmailHtml(
          @ToolParam(description = "Dados da mensagem HTML a ser enviada (MensagemDTO, obrigatório). " +
                          "Inclui telefoneDestinatario (e-mail), nomeDestinatario (assunto) e corpoMensagem (conteúdo HTML).", required = true) MensagemDTO mensagem) {
    Objects.requireNonNull(mensagem, "Mensagem não pode ser null");
    Objects.requireNonNull(mensagem.getTelefoneDestinatario(), "Destinatário não pode ser null");
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

    } catch (org.springframework.mail.MailException e) {
      log.error("Erro ao enviar e-mail HTML: {}", e.getMessage(), e);
      return ResultadoEnvio.falha(e.getMessage());
    } catch (MessagingException e) {
      log.error("Erro ao enviar e-mail HTML: {}", e.getMessage(), e);
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
    Objects.requireNonNull(mensagem, "Mensagem não pode ser null");
    Objects.requireNonNull(mensagem.getTelefoneDestinatario(), "Destinatário não pode ser null");
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

    } catch (org.springframework.mail.MailException e) {
      log.error("Erro ao enviar e-mail: {}", e.getMessage(), e);
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
    Objects.requireNonNull(para, "Destinatário não pode ser null");
    Objects.requireNonNull(assunto, "Assunto não pode ser null");
    Objects.requireNonNull(htmlBody, "Corpo do e-mail não pode ser null");
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
    } catch (org.springframework.mail.MailException e) {
      log.error("Erro ao enviar e-mail HTML: {}", e.getMessage());
      throw new RuntimeException("Falha ao enviar e-mail", e);
    }catch (MessagingException e) {
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
