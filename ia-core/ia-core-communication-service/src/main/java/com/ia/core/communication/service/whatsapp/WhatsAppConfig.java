package com.ia.core.communication.service.whatsapp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.Data;

/**
 * Configuração do WhatsApp Business API.
 *
 * @author Israel Araújo
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "whatsapp")
public class WhatsAppConfig {
  /** URL base da API do WhatsApp */
  private String apiUrl = "https://graph.facebook.com/v18.0";

  /** Phone Number ID */
  private String phoneNumberId;

  /** WhatsApp Business Account ID */
  private String businessAccountId;

  /** Token de acesso à API */
  private String accessToken;

  /** URL do webhook para receber notificações */
  private String webhookUrl;

  /** Token de verificação do webhook */
  private String webhookVerifyToken;

  /** Indica se o modo de teste está ativo */
  private boolean testMode = false;

  /** Timeout em milissegundos para requisições */
  private int timeout = 30000;
  /** Rest template */
  private final RestTemplate restTemplate;
}
