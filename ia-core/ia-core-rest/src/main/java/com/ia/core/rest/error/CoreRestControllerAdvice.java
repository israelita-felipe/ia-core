package com.ia.core.rest.error;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ia.core.model.exception.BusinessException;
import com.ia.core.model.exception.DomainException;
import com.ia.core.model.exception.ResourceNotFoundException;
import com.ia.core.model.exception.ValidationException;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.translator.CoreApplicationTranslator;
import com.ia.core.service.translator.Translator;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Centralizador de tratamento de exceções para a API REST. Fornece tratamento
 * padronizado de exceções em todos os controllers REST. Utiliza estrutura
 * {@link ErrorResponse} para comunicação consistente de erros.
 *
 * @author Israel Araújo
 */
@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class CoreRestControllerAdvice
  extends ResponseEntityExceptionHandler {

  /** Service error header name */
  private static final String HEADER_SERVICE_ERROR = "x-service-error";
  /** Internal error header name */
  private static final String HEADER_INTERNAL_ERROR = "x-error";
  /** Trace ID header name */
  private static final String HEADER_TRACE_ID = "x-trace-id";

  /** Tradutor */
  private final Translator translator;

  /**
   * Chamada na inicialização
   */
  @PostConstruct
  public void init() {
    log.info("Controller advice inicializado {}", getClass());
  }

  /**
   * Trata exceção de autenticação.
   *
   * @param ex      {@link AuthenticationException}
   * @param request {@link WebRequest}
   * @return {@link ResponseEntity} com {@link ErrorResponse}
   */
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex,
                                                                  WebRequest request) {
    String traceId = generateTraceId();
    log.warn("Erro de autenticação [{}]: {}", traceId, ex.getMessage());

    ErrorResponse error = ErrorResponse.of(
        HttpStatus.UNAUTHORIZED.value(),
        "AUTHENTICATION_ERROR",
        "Credenciais inválidas ou sessão expirada",
        getPath(request),
        traceId
    );

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .header(HEADER_INTERNAL_ERROR, "Não autorizado")
        .header(HEADER_TRACE_ID, traceId)
        .body(error);
  }

  /**
   * Captura de exceção de {@link AccessDeniedException}
   *
   * @param ex      {@link AccessDeniedException}
   * @param request {@link WebRequest}
   * @return {@link ResponseEntity} com {@link ErrorResponse}
   */
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex,
                                                                    WebRequest request) {
    String traceId = generateTraceId();
    log.warn("Acesso negado [{}]: {}", traceId, ex.getMessage());

    ErrorResponse error = ErrorResponse.of(
        HttpStatus.FORBIDDEN.value(),
        "ACCESS_DENIED",
        "Acesso negado ao recurso solicitado",
        getPath(request),
        traceId
    );

    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .header(HEADER_INTERNAL_ERROR, "Acesso negado")
        .header(HEADER_TRACE_ID, traceId)
        .body(error);
  }

  /**
   * Captura de exceção de {@link ResourceNotFoundException}
   *
   * @param ex      {@link ResourceNotFoundException}
   * @param request {@link WebRequest}
   * @return {@link ResponseEntity} com {@link ErrorResponse}
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                                         WebRequest request) {
    String traceId = generateTraceId();
    log.warn("Recurso não encontrado [{}]: {}", traceId, ex.getMessage());

    ErrorResponse error = ErrorResponse.of(
        HttpStatus.NOT_FOUND.value(),
        ex.getErrorCode(),
        ex.getMessage(),
        getPath(request),
        traceId
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .header(HEADER_TRACE_ID, traceId)
        .body(error);
  }

  /**
   * Captura de exceção de {@link EntityNotFoundException}
   *
   * @param ex      {@link EntityNotFoundException}
   * @param request {@link WebRequest}
   * @return {@link ResponseEntity} com {@link ErrorResponse}
   */
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex,
                                                                       WebRequest request) {
    String traceId = generateTraceId();
    log.warn("Recurso não encontrado [{}]: {}", traceId, ex.getMessage());

    ErrorResponse error = ErrorResponse.of(
        HttpStatus.NOT_FOUND.value(),
        "ENTITY_NOT_FOUND",
        ex.getMessage() != null ? ex.getMessage() : "Recurso não encontrado",
        getPath(request),
        traceId
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .header(HEADER_INTERNAL_ERROR,
                ex.getMessage() != null ? ex.getMessage() : "Recurso não encontrado")
        .header(HEADER_TRACE_ID, traceId)
        .body(error);
  }

  /**
   * Captura de exceção de {@link BusinessException}
   *
   * @param ex      {@link BusinessException}
   * @param request {@link WebRequest}
   * @return {@link ResponseEntity} com {@link ErrorResponse}
   */
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex,
                                                                WebRequest request) {
    String traceId = generateTraceId();
    log.warn("Violação de regra de negócio [{}]: {}", traceId, ex.getMessage());

    ErrorResponse error = ErrorResponse.of(
        HttpStatus.BAD_REQUEST.value(),
        ex.getErrorCode(),
        ex.getMessage(),
        getPath(request),
        traceId
    );

    return ResponseEntity.badRequest()
        .header(HEADER_TRACE_ID, traceId)
        .body(error);
  }

  /**
   * Captura de exceção de {@link ValidationException}
   *
   * @param ex      {@link ValidationException}
   * @param request {@link WebRequest}
   * @return {@link ResponseEntity} com {@link ErrorResponse}
   */
  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex,
                                                                 WebRequest request) {
    String traceId = generateTraceId();
    log.warn("Erro de validação [{}]: {}", traceId, ex.getMessage());

    List<ErrorResponse.ErrorDetail> details = new ArrayList<>();
    details.add(ErrorResponse.ErrorDetail.builder()
        .withCode(ex.getErrorCode())
        .withMessage(ex.getMessage())
        .withField(ex.getField())
        .withRejectedValue(ex.getRejectedValue())
        .build());

    ErrorResponse error = ErrorResponse.withDetails(
        HttpStatus.BAD_REQUEST.value(),
        ex.getErrorCode(),
        ex.getMessage(),
        getPath(request),
        traceId,
        details
    );

    return ResponseEntity.badRequest()
        .header(HEADER_TRACE_ID, traceId)
        .body(error);
  }

  /**
   * Captura de exceção de {@link DataIntegrityViolationException}
   *
   * @param ex      {@link DataIntegrityViolationException}
   * @param request {@link WebRequest}
   * @return {@link ResponseEntity} com {@link ErrorResponse}
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleDataIntegrityException(DataIntegrityViolationException ex,
                                                                     WebRequest request) {
    String traceId = generateTraceId();
    log.error("Erro de integridade de dados [{}]: {}", traceId, ex.getMessage(), ex);

    ErrorResponse error = ErrorResponse.of(
        HttpStatus.CONFLICT.value(),
        "DATA_INTEGRITY_VIOLATION",
        translator.getTranslation(CoreApplicationTranslator.ERRO_INTEGRIDADE_OBJETO_POSSUI_DEPENDENCIAS),
        getPath(request),
        traceId
    );

    return ResponseEntity.status(HttpStatus.CONFLICT)
        .header(HEADER_SERVICE_ERROR, translator
            .getTranslation(CoreApplicationTranslator.ERRO_INTEGRIDADE_OBJETO_POSSUI_DEPENDENCIAS))
        .header(HEADER_TRACE_ID, traceId)
        .body(error);
  }

  /**
   * Captura de exceção de {@link ServiceException}
   *
   * @param ex      {@link ServiceException}
   * @param request {@link WebRequest}
   * @return {@link ResponseEntity} com {@link ErrorResponse}
   */
  @ExceptionHandler(ServiceException.class)
  public ResponseEntity<ErrorResponse> handleServiceException(ServiceException ex,
                                                              WebRequest request) {
    String traceId = generateTraceId();
    log.error("Erro de serviço [{}]: {}", traceId, ex.getMessage(), ex);

    List<String> errors = ex.getErrors().collect(Collectors.toList());

    List<ErrorResponse.ErrorDetail> details = errors.stream()
        .map(error -> ErrorResponse.ErrorDetail.builder()
            .withCode("SERVICE_ERROR")
            .withMessage(error)
            .build())
        .collect(Collectors.toList());

    ErrorResponse error = ErrorResponse.withDetails(
        HttpStatus.BAD_REQUEST.value(),
        "SERVICE_ERROR",
        "Erro(s) de serviço",
        getPath(request),
        traceId,
        details
    );

    return ResponseEntity.badRequest()
        .header(HEADER_SERVICE_ERROR, ex.getErrors().toArray(String[]::new))
        .header(HEADER_TRACE_ID, traceId)
        .body(error);
  }

  /**
   * Captura de erros de validação do Spring (@Valid).
   *
   * @param ex      {@link MethodArgumentNotValidException}
   * @param request {@link WebRequest}
   * @return {@link ResponseEntity} com {@link ErrorResponse}
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex,
      WebRequest request) {

    String traceId = generateTraceId();
    log.warn("Erro de validação de argumento [{}]: {}", traceId, ex.getMessage());

    Map<String, Set<String>> fieldErrors = new HashMap<>();
    List<ErrorResponse.ErrorDetail> details = new ArrayList<>();

    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      fieldErrors.computeIfAbsent(fieldError.getField(), k -> new java.util.HashSet<>())
          .add(fieldError.getDefaultMessage());

      details.add(ErrorResponse.ErrorDetail.builder()
          .withCode("VALIDATION_ERROR")
          .withMessage(fieldError.getDefaultMessage())
          .withField(fieldError.getField())
          .withRejectedValue(fieldError.getRejectedValue())
          .build());
    }

    ErrorResponse error = ErrorResponse.withFieldErrors(
        HttpStatus.BAD_REQUEST.value(),
        "VALIDATION_ERROR",
        "Erro(s) de validação",
        getPath(request),
        traceId,
        fieldErrors
    );

    return ResponseEntity.badRequest()
        .header(HEADER_TRACE_ID, traceId)
        .body(error);
  }

  /**
   * Captura genérica de exceções não tratadas.
   *
   * @param ex      {@link Exception}
   * @param request {@link WebRequest}
   * @return {@link ResponseEntity} com {@link ErrorResponse}
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception ex,
                                                               WebRequest request) {
    String traceId = generateTraceId();
    log.error("Erro não tratado [{}]: {}", traceId, ex.getMessage(), ex);

    ErrorResponse error = ErrorResponse.of(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "INTERNAL_ERROR",
        "Ocorreu um erro interno. Por favor, tente novamente mais tarde.",
        getPath(request),
        traceId
    );

    return ResponseEntity.internalServerError()
        .header(HEADER_INTERNAL_ERROR, ex.getLocalizedMessage())
        .header(HEADER_TRACE_ID, traceId)
        .body(error);
  }

  /**
   * Gera um identificador único para rastreamento.
   *
   * @return UUID para trace
   */
  public String generateTraceId() {
    return UUID.randomUUID().toString();
  }

  /**
   * Extrai o caminho da requisição.
   *
   * @param request {@link WebRequest}
   * @return caminho da requisição
   */
  private String getPath(WebRequest request) {
    String description = request.getDescription(false);
    return description.replace("uri=", "");
  }
}
