package com.ia.core.rest.error;

import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.translator.CoreApplicationTranslator;
import com.ia.core.service.translator.Translator;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Centralizador de tratamento de exceções para a API REST. Fornece tratamento
 * padronizado de exceções em todos os controllers REST. Utiliza headers
 * padronizados para comunicação de erros com clientes.
 *
 * @author Israel Araújo
 */
@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class CoreRestControllerAdvice
  extends ResponseEntityExceptionHandler {

  /***/
  private static final String HEADER_SERVICE_ERROR = "x-service-error";
  /***/
  private static final String HEADER_INTERNAL_ERROR = "x-error";
  /***/
  private static final String HEADER_TRACE_ID = "x-trace-id";

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
   * Trata exceção de autenticação.
   *
   * @param ex      {@link AuthenticationException}
   * @param request {@link WebRequest}
   * @return {@link ResponseEntity}
   */
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex,
                                                              WebRequest request) {
    String traceId = generateTraceId();
    log.warn("Erro de autenticação [{}]: {}", traceId, ex.getMessage());

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .header(HEADER_INTERNAL_ERROR, "Não autorizado")
        .header(HEADER_TRACE_ID, traceId).build();
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
    String traceId = generateTraceId();
    log.warn("Acesso negado [{}]: {}", traceId, ex.getMessage());

    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .header(HEADER_INTERNAL_ERROR, "Acesso negado")
        .header(HEADER_TRACE_ID, traceId).build();
  }

  /**
   * Captura de exceção de {@link EntityNotFoundException}
   *
   * @param ex      {@link EntityNotFoundException}
   * @param request {@link WebRequest}
   * @return {@link ResponseEntity}
   */
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex,
                                                              WebRequest request) {
    String traceId = generateTraceId();
    log.warn("Recurso não encontrado [{}]: {}", traceId, ex.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .header(HEADER_INTERNAL_ERROR,
                ex.getMessage() != null ? ex.getMessage()
                                        : "Recurso não encontrado")
        .header(HEADER_TRACE_ID, traceId).build();
  }

  /**
   * Captura de exceção de {@link DataIntegrityViolationException}
   *
   * @param ex      {@link DataIntegrityViolationException}
   * @param request {@link WebRequest}
   * @return {@link ResponseEntity}
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Object> handleDataIntegrityException(DataIntegrityViolationException ex,
                                                             WebRequest request) {
    String traceId = generateTraceId();
    log.error("Erro de integridade de dados [{}]: {}", traceId,
              ex.getMessage(), ex);

    return ResponseEntity.status(HttpStatus.CONFLICT)
        .header(HEADER_SERVICE_ERROR, translator
            .getTranslation(CoreApplicationTranslator.ERRO_INTEGRIDADE_OBJETO_POSSUI_DEPENDENCIAS))
        .header(HEADER_TRACE_ID, traceId).build();
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
    String traceId = generateTraceId();
    log.error("Erro de negócio [{}]: {}", traceId, ex.getMessage(), ex);

    return ResponseEntity.badRequest()
        .header(HEADER_SERVICE_ERROR, ex.getErrors().toArray(String[]::new))
        .header(HEADER_TRACE_ID, traceId).build();
  }

  /**
   * Captura genérica de exceções não tratadas.
   *
   * @param ex      {@link Exception}
   * @param request {@link WebRequest}
   * @return {@link ResponseEntity}
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleGenericException(Exception ex,
                                                       WebRequest request) {
    String traceId = generateTraceId();
    log.error("Erro não tratado [{}]: {}", traceId, ex.getMessage(), ex);

    return ResponseEntity.internalServerError()
        .header(HEADER_INTERNAL_ERROR, ex.getLocalizedMessage())
        .header(HEADER_TRACE_ID, traceId).build();
  }

  /**
   * @return String com identificador único para ser utilizado no log
   */
  public String generateTraceId() {
    return UUID.randomUUID().toString();
  }
}
