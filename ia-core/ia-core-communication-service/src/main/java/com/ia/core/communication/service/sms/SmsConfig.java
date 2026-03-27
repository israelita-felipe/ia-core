package com.ia.core.communication.service.sms;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Configuração para serviço de SMS.
 *
 * @author Israel Araújo
 */
@Data
@ConfigurationProperties(prefix = "sms")
public class SmsConfig {
  private String provider = "twilio";
  private String accountSid;
  private String authToken;
  private String fromNumber;
  private int timeout = 30000;
}
