package com.ia.core.view.config.feign;

import com.ia.core.service.util.EnumModule;
import com.ia.core.service.util.LocalDateTimeModule;
import com.ia.core.view.utils.FeignHierarchicalContract;
import feign.Contract;
import feign.Feign;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.httpclient.ApacheHttpClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.openfeign.support.PageJacksonModule;
import org.springframework.cloud.openfeign.support.SortJacksonModule;
import org.springframework.http.converter.HttpMessageConverter;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Configuração do Feign usando Jackson 3.x (tools.jackson)
 *
 * @author Israel Araújo
 */
public class FeignConfig {

    public List<HttpMessageConverter<?>> messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return converters.orderedStream()
                .collect(Collectors.toList());
    }

    /**
     * @return {@link Encoder} para serialização de requisições usando Jackson 3.x
     */
    public Encoder encoder() {
        JsonMapper.Builder builder = JsonMapper.builder()
                .addModule(pageJacksonModule())
                .addModule(sortJacksonModule())
                .addModule(localDateTimeModule())
                .addModule(enumModule());
        return new Jackson3Encoder(builder.build());
    }

    /**
     * @return {@link Decoder} para desserialização de respostas usando Jackson 3.x
     */
    public Decoder decoder() {
        JsonMapper.Builder builder = JsonMapper.builder()
                .addModule(pageJacksonModule())
                .addModule(sortJacksonModule())
                .addModule(localDateTimeModule())
                .addModule(enumModule());
        return new Jackson3Decoder(builder.build());
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
     * &#64;FeignClient(name = "service", circuitbreaker = "true")
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
     * @return {@link PageJacksonModule} para suporte a Page do Spring Data com Jackson 3.x
     */
    public PageJacksonModule pageJacksonModule() {
        return new PageJacksonModule();
    }

    /**
     * @return {@link SortJacksonModule} para suporte a Sort do Spring Data com Jackson 3.x
     */
    public SortJacksonModule sortJacksonModule() {
        return new SortJacksonModule();
    }

    /**
     * @return {@link LocalDateTimeModule} para conversão de data/hora com Jackson 3.x
     */
    public LocalDateTimeModule localDateTimeModule() {
        return new LocalDateTimeModule();
    }

    /**
     * @return {@link EnumModule} para conversão de enumerador com Jackson 3.x
     */
    public EnumModule enumModule() {
        return new EnumModule();
    }
}
