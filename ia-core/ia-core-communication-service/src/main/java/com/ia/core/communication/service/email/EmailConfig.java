package com.ia.core.communication.service.email;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSender;

import lombok.Data;

/**
 * Configuração para serviço de E-mail.
 *
 * @author Israel Araújo
 */
@Data
@ConfigurationProperties(prefix = "email")
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
