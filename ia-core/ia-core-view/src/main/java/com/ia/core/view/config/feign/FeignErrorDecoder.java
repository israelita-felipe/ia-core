package com.ia.core.view.config.feign;

import java.util.Collection;
import java.util.Objects;

import com.ia.core.service.exception.ServiceException;

import feign.Response;
import feign.codec.ErrorDecoder;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

/**
 * Decodificador de erros do feign.
 *
 * @author Israel Araújo
 */
@Slf4j
public class FeignErrorDecoder
  implements ErrorDecoder {
  /***/
  private static final String HEADER_INVALID_TOKEN_SERVICE_ERROR = "x-service-error-invalid-token";
  /***/
  private static final String HEADER_ERROR = "x-error";
  /***/
  private static final String HEADER_SERVICE_ERROR = "x-service-error";

  @Override
  public Exception decode(String methodKey, Response response) {
    log.info(methodKey);
    log.info(response.headers().toString());
    // erro de serviço (conhecido)
    Collection<String> erros = response.headers().get(HEADER_SERVICE_ERROR);
    ServiceException serviceException = new ServiceException();
    if (!Objects.isNull(erros)) {
      ServiceException ex = serviceException;
      erros.forEach(ex::add);
      return ex;
    }

    // erro identificado
    erros = response.headers().get(HEADER_ERROR);
    if (!Objects.isNull(erros)) {
      serviceException.add(erros.toString());
      return serviceException;
    }
    // erro identificado
    erros = response.headers().get(HEADER_INVALID_TOKEN_SERVICE_ERROR);
    if (!Objects.isNull(erros)) {
      return new ExpiredJwtException(null, null, erros.toString());
    }
    // erro não identificado
    serviceException.add(response.body().toString());
    return serviceException;
  }

}
