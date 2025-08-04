package com.ia.core.rest.error;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.translator.CoreApplicationTranslator;
import com.ia.core.service.translator.Translator;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Israel Araújo
 */
@RequiredArgsConstructor
@Slf4j
public class CoreRestControllerAdvice {

  /***/
  private static final String HEADER_SERVICE_ERROR = "x-service-error";
  /***/
  private static final String HEADER_INTERNAL_ERROR = "x-error";
  /** Tradutor */
  private final Translator translator;

  /**
   * Chamada na inicialização
   */
  @PostConstruct
  public void init() {
    log.info("Controller advice inicilizado {}", getClass());
  }

  /**
   * Captura de exceção de {@link AccessDeniedException}
   *
   * @param ex      {@link AccessDeniedException}
   * @param request {@link WebRequest}
   * @return {@link ResponseEntity}
   */
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex,
                                                            WebRequest request) {
    log.error(ex.getLocalizedMessage(), ex);
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }

  /**
   * Captura de exceção de {@link AccessDeniedException}
   *
   * @param ex      {@link AccessDeniedException}
   * @param request {@link WebRequest}
   * @return {@link ResponseEntity}
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Object> handleDataIntegrityException(DataIntegrityViolationException ex,
                                                             WebRequest request) {
    log.error(ex.getLocalizedMessage(), ex);
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .header(HEADER_SERVICE_ERROR, translator
            .getTranslation(CoreApplicationTranslator.ERRO_INTEGRIDADE_OBJETO_POSSUI_DEPENDENCIAS))
        .build();
  }

  /**
   * Captura de exceção de {@link ServiceException}
   *
   * @param ex      {@link ServiceException}
   * @param request {@link WebRequest}
   * @return {@link ResponseEntity}
   */
  @ExceptionHandler(ServiceException.class)
  public ResponseEntity<Object> handleServiceException(ServiceException ex,
                                                       WebRequest request) {
    log.error(ex.getLocalizedMessage(), ex);
    return ResponseEntity.badRequest()
        .header(HEADER_SERVICE_ERROR, ex.getErrors().toArray(String[]::new))
        .build();
  }

  /**
   * Captura de exceção de {@link ServiceException}
   *
   * @param ex      {@link ServiceException}
   * @param request {@link WebRequest}
   * @return {@link ResponseEntity}
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleServiceException(Exception ex,
                                                       WebRequest request) {
    log.error(ex.getLocalizedMessage(), ex);
    return ResponseEntity.internalServerError()
        .header(HEADER_INTERNAL_ERROR, ex.getLocalizedMessage()).build();
  }
}
