package com.ia.core.communication.service.telegram;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configurações para integração com Telegram.
 *
 * @author Israel Araújo
 */
@Data
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "communication.telegram")
@Configuration
public class TelegramConfig {

  /**
   * Token do bot do Telegram.
   */
  private String botToken;

  /**
   * ID do chat padrão para envio de mensagens.
   */
  private String chatId;

  /**
   * URL base da API do Telegram.
   */
  private String apiUrl = "https://api.telegram.org";

  /**
   * Habilitar parse mode HTML.
   */
  private boolean enableHtml = true;

  /**
   * Timeout em milissegundos para requisições.
   */
  private int timeout = 30000;

  /** Cliente do telegram */
  @Getter
  private final TelegramClient telegramClient;

}
