package com.ia.core.communication.service.telegram;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ia.core.view.config.feign.FeignConfig;

import feign.codec.ErrorDecoder;

/**
 * Configuração do Feign para o cliente Telegram.
 * <p>
 * Habilita CircuitBreaker e Resilience4j para o cliente.
 * </p>
 *
 * @author Israel Araújo
 */
@Configuration
public class TelegramFeignConfig extends FeignConfig {

  @Bean
  @Override
  public ErrorDecoder errorDecoder() {
    return super.errorDecoder();
  }
}
