package com.ia.core.communication.service.sms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração para serviço de SMS.
 * <p>
 * Define as propriedades de configuração para envio de SMS,
 * incluindo provider, credenciais e timeouts.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "sms")
@Configuration
public class SmsConfig {
  private String provider = "twilio";
  private String accountSid;
  private String authToken;
  private String fromNumber;
  private int timeout = 30000;
}
