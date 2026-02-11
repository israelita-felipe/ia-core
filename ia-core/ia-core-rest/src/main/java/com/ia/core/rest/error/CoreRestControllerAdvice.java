package com.ia.core.rest.error;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ia.core.model.exception.BusinessException;
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
 * {@link ErrorResponse} para comunicação consistente de erros. Documentação
 * OpenAPI: Todos os handlers incluem anotações @Operation e
 *
 * @ApiResponse para automatic documentation de códigos de erro.
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
   * Obtém a mensagem traduzida para o código de erro.
   *
   * @param errorCode código de erro
   * @return mensagem traduzida
   */
  private String getTranslatedMessage(RestErrorCode errorCode) {
    return translator.getTranslation(errorCode.getTranslationKey());
  }

  /**
   * Obtém a mensagem traduzida para o código de erro com sufixo _message.
   *
   * @param errorCode código de erro
   * @return mensagem traduzida
   */
  private String getTranslatedMessageWithSuffix(RestErrorCode errorCode) {
    String messageKey = errorCode.getTranslationKey() + ".message";
    return translator.getTranslation(messageKey);
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

    ErrorResponse error = ErrorResponse
        .of(HttpStatus.UNAUTHORIZED.value(),
            RestErrorCode.AUTHENTICATION_ERROR.getCode(),
            getTranslatedMessageWithSuffix(RestErrorCode.AUTHENTICATION_ERROR),
            getPath(request), traceId);

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .header(HEADER_INTERNAL_ERROR,
                getTranslatedMessage(RestErrorCode.AUTHENTICATION_ERROR))
        .header(HEADER_TRACE_ID, traceId).body(error);
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

    ErrorResponse error = ErrorResponse
        .of(HttpStatus.FORBIDDEN.value(),
            RestErrorCode.ACCESS_DENIED.getCode(),
            getTranslatedMessageWithSuffix(RestErrorCode.ACCESS_DENIED),
            getPath(request), traceId);

    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .header(HEADER_INTERNAL_ERROR,
                getTranslatedMessage(RestErrorCode.ACCESS_DENIED))
        .header(HEADER_TRACE_ID, traceId).body(error);
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

    ErrorResponse error = ErrorResponse.of(HttpStatus.NOT_FOUND.value(),
                                           RestErrorCode.ENTITY_NOT_FOUND
                                               .getCode(),
                                           ex.getMessage(),
                                           getPath(request), traceId);

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .header(HEADER_TRACE_ID, traceId).body(error);
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

    ErrorResponse error = ErrorResponse
        .of(HttpStatus.NOT_FOUND.value(),
            RestErrorCode.ENTITY_NOT_FOUND.getCode(),
            ex.getMessage() != null ? ex.getMessage()
                                    : getTranslatedMessage(RestErrorCode.ENTITY_NOT_FOUND),
            getPath(request), traceId);

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .header(HEADER_INTERNAL_ERROR, ex
            .getMessage() != null ? ex.getMessage()
                                  : getTranslatedMessage(RestErrorCode.ENTITY_NOT_FOUND))
        .header(HEADER_TRACE_ID, traceId).body(error);
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
    log.warn("Violação de regra de negócio [{}]: {}", traceId,
             ex.getMessage());

    ErrorResponse error = ErrorResponse.of(HttpStatus.BAD_REQUEST.value(),
                                           RestErrorCode.VALIDATION_ERROR
                                               .getCode(),
                                           ex.getMessage(),
                                           getPath(request), traceId);

    return ResponseEntity.badRequest().header(HEADER_TRACE_ID, traceId)
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
    details
        .add(ErrorResponse.ErrorDetail.builder().withCode(ex.getErrorCode())
            .withMessage(ex.getMessage()).withField(ex.getField())
            .withRejectedValue(ex.getRejectedValue()).build());

    ErrorResponse error = ErrorResponse
        .withDetails(HttpStatus.BAD_REQUEST.value(),
                     RestErrorCode.VALIDATION_ERROR.getCode(),
                     ex.getMessage(), getPath(request), traceId, details);

    return ResponseEntity.badRequest().header(HEADER_TRACE_ID, traceId)
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
    log.error("Erro de integridade de dados [{}]: {}", traceId,
              ex.getMessage(), ex);

    String errorMessage = translator
        .getTranslation(CoreApplicationTranslator.ERROR_DATA_INTEGRITY_MESSAGE);

    ErrorResponse error = ErrorResponse
        .of(HttpStatus.CONFLICT.value(),
            RestErrorCode.DATA_INTEGRITY_VIOLATION.getCode(), errorMessage,
            getPath(request), traceId);

    return ResponseEntity.status(HttpStatus.CONFLICT)
        .header(HEADER_SERVICE_ERROR, errorMessage)
        .header(HEADER_TRACE_ID, traceId).body(error);
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
            .withCode(RestErrorCode.SERVICE_ERROR.getCode())
            .withMessage(error).build())
        .collect(Collectors.toList());

    ErrorResponse error = ErrorResponse
        .withDetails(HttpStatus.BAD_REQUEST.value(),
                     RestErrorCode.SERVICE_ERROR.getCode(),
                     getTranslatedMessageWithSuffix(RestErrorCode.SERVICE_ERROR),
                     getPath(request), traceId, details);

    return ResponseEntity.badRequest()
        .header(HEADER_SERVICE_ERROR, ex.getErrors().toArray(String[]::new))
        .header(HEADER_TRACE_ID, traceId).body(error);
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

    ErrorResponse error = ErrorResponse
        .of(HttpStatus.INTERNAL_SERVER_ERROR.value(),
            RestErrorCode.INTERNAL_ERROR.getCode(),
            getTranslatedMessageWithSuffix(RestErrorCode.INTERNAL_ERROR),
            getPath(request), traceId);

    return ResponseEntity.internalServerError()
        .header(HEADER_INTERNAL_ERROR, ex.getLocalizedMessage())
        .header(HEADER_TRACE_ID, traceId).body(error);
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
