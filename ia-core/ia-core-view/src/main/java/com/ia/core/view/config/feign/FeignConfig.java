package com.ia.core.view.config.feign;

import java.util.stream.Collectors;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.PageJacksonModule;
import org.springframework.cloud.openfeign.support.SortJacksonModule;
import org.springframework.http.converter.HttpMessageConverter;

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

  public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
    return new HttpMessageConverters(converters.orderedStream()
        .collect(Collectors.toList()));
  }

  /**
   * @return {@link ErrorDecoder}. A implementação padrão é realizada através de
   *         {@link FeignErrorDecoder}
   */

  public ErrorDecoder errorDecoder() {
    return new FeignErrorDecoder();
  }

  /**
   * @return {@link Contract} para hierarquia.
   */

  public Contract feignContract() {
    return new FeignHierarchicalContract();
  }

  /**
   * @return {@link Feign.Builder} com Resilience4j configurado via Spring Cloud.
   * <p>
   * Para habilitar CircuitBreaker nos clientes Feign, use a anotação:
   * </p>
   * <pre>
   * @FeignClient(name = "service", circuitbreaker = "true")
   * </pre>
   * <p>
   * A configuração é feita via application.yml com resilience4j.circuitbreaker.instances.
   * </p>
   *
   * @see <a href="https://docs.spring.io/spring-cloud-commons/reference/html#circuit-breaker">Spring Cloud Circuit Breaker</a>
   */
  public Feign.Builder feignBuilder() {
    return Feign.builder().retryer(new Retryer.Default())
        .client(new ApacheHttpClient());
  }

  /**
   * @return {@link PageJacksonModule}
   */

  public PageJacksonModule pageJacksonModule() {
    return new PageJacksonModule();
  }

  /**
   * @return {@link SortJacksonModule}
   */

  public SortJacksonModule sortJacksonModule() {
    return new SortJacksonModule();
  }

  /**
   * @return {@link LocalDateTimeModule}
   */
  public LocalDateTimeModule localDateTimeModule() {
    return new LocalDateTimeModule();
  }

  /**
   * @return {@link Module} para enumerador
   */
  public EnumModule enumModule() {
    return new EnumModule();
  }
}
