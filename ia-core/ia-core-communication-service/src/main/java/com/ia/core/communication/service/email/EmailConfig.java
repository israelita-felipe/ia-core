package com.ia.core.communication.service.email;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Configuração para serviço de E-mail.
 *
 * @author Israel Araújo
 */
/**
 * Classe que representa as configurações para email.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a EmailConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@Data
@ConfigurationProperties(prefix = "email")
@Configuration
public class EmailConfig {
  private String host = "smtp.gmail.com";
  private int port = 587;
  private String username;
  private String password;
  private boolean auth = true;
  private boolean starttls = true;
  private String fromAddress;
  private String fromName;
  private int timeout = 30000;
  private final JavaMailSender mailSender;
}
