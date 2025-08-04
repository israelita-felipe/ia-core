package com.ia.core.view.config.feign;

import org.springframework.cloud.openfeign.support.PageJacksonModule;
import org.springframework.cloud.openfeign.support.SortJacksonModule;

import com.fasterxml.jackson.databind.Module;
import com.ia.core.service.util.EnumModule;
import com.ia.core.service.util.LocalDateTimeModule;
import com.ia.core.view.utils.FeignHierarchicalContract;

import feign.Contract;
import feign.Feign;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import feign.httpclient.ApacheHttpClient;

/**
 * Configuração do Feign
 *
 * @author Israel Araújo
 */

public class FeignConfig {
  /**
   * @return {@link ErrorDecoder}. A implementação padrão é realizada através de
   *         {@link FeignErrorDecoder}
   */

  public ErrorDecoder errorDecoder() {
    return new FeignErrorDecoder();
  }

  /**
   * @return {@link Feign#builder()}
   */
  public Feign.Builder feignBuilder() {
    return Feign.builder().retryer(new Retryer.Default())
        .client(new ApacheHttpClient());
  }

  /**
   * @return {@link Contract} para hierarquia.
   */

  public Contract feignContract() {
    return new FeignHierarchicalContract();
  }

  /**
   * @return {@link PageJacksonModule}
   */

  public Module pageJacksonModule() {
    return new PageJacksonModule();
  }

  /**
   * @return {@link SortJacksonModule}
   */

  public Module sortJacksonModule() {
    return new SortJacksonModule();
  }

  /**
   * @return {@link LocalDateTimeModule}
   */
  public Module localDateTimeModule() {
    return new LocalDateTimeModule();
  }

  /**
   * @return {@link Module} para enumerador
   */
  public Module enumModule() {
    return new EnumModule();
  }
}
