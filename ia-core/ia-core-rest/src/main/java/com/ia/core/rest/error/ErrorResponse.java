package com.ia.core.rest.error;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * DTO padronizado para resposta de erro da API REST.
 * Fornece estrutura consistente para comunicação de erros ao cliente.
 *
 * @author Israel Araújo
 */
@Getter
@ToString
@Builder(setterPrefix = "with")
@JsonInclude(Include.NON_NULL)
public class ErrorResponse {

  /**
   * Timestamp do momento em que o erro ocorreu.
   */
  private final Instant timestamp;

  /**
   * Código de status HTTP.
   */
  private final int status;

  /**
   * Código de erro de negócio (ex: RESOURCE_NOT_FOUND).
   */
  private final String errorCode;

  /**
   * Mensagem de erro legível para o usuário.
   */
  private final String message;

  /**
   * Caminho da requisição que originou o erro.
   */
  private final String path;

  /**
   * Trace ID para correlação de logs.
   */
  private final String traceId;

  /**
   * Lista de detalhes de erro (para erros de validação).
   */
  @Builder.Default
  private final List<ErrorDetail> details;

  /**
   * Mapa de erros de campo (para validação de DTOs).
   */
  @Builder.Default
  private final Map<String, Set<String>> fieldErrors;

  /**
   * Exceção original (apenas em ambiente de desenvolvimento).
   */
  @Builder.Default
  private final String exception;

  /**
   * Detalhe individual de erro.
   */
  @Getter
  @ToString
  @Builder(setterPrefix = "with")
  @JsonInclude(Include.NON_NULL)
  public static class ErrorDetail {

    /**
     * Código do erro específico.
     */
    private final String code;

    /**
     * Mensagem de erro detalhada.
     */
    private final String message;

    /**
     * Caminho do campo que contém o erro (se aplicável).
     */
    private final String field;

    /**
     * Valor rejeitado (se aplicável).
     */
    private final Object rejectedValue;
  }

  /**
   * Cria um ErrorResponse básico.
   *
   * @param status    código de status HTTP
   * @param errorCode código de erro
   * @param message   mensagem de erro
   * @param path     caminho da requisição
   * @param traceId  ID de trace
   * @return ErrorResponse configurado
   */
  public static ErrorResponse of(int status, String errorCode, String message,
                                  String path, String traceId) {
    return ErrorResponse.builder()
        .withTimestamp(Instant.now())
        .withStatus(status)
        .withErrorCode(errorCode)
        .withMessage(message)
        .withPath(path)
        .withTraceId(traceId)
        .withDetails(null)
        .withFieldErrors(null)
        .withException(null)
        .build();
  }

  /**
   * Cria um ErrorResponse com detalhes de validação.
   *
   * @param status     código de status HTTP
   * @param errorCode  código de erro
   * @param message    mensagem de erro
   * @param path      caminho da requisição
   * @param traceId   ID de trace
   * @param details   lista de detalhes de erro
   * @return ErrorResponse configurado
   */
  public static ErrorResponse withDetails(int status, String errorCode, String message,
                                          String path, String traceId,
                                          List<ErrorDetail> details) {
    return ErrorResponse.builder()
        .withTimestamp(Instant.now())
        .withStatus(status)
        .withErrorCode(errorCode)
        .withMessage(message)
        .withPath(path)
        .withTraceId(traceId)
        .withDetails(details)
        .withFieldErrors(null)
        .withException(null)
        .build();
  }

  /**
   * Cria um ErrorResponse com erros de campo.
   *
   * @param status      código de status HTTP
   * @param errorCode   código de erro
   * @param message     mensagem de erro
   * @param path       caminho da requisição
   * @param traceId    ID de trace
   * @param fieldErrors mapa de erros de campo
   * @return ErrorResponse configurado
   */
  public static ErrorResponse withFieldErrors(int status, String errorCode, String message,
                                               String path, String traceId,
                                               Map<String, Set<String>> fieldErrors) {
    return ErrorResponse.builder()
        .withTimestamp(Instant.now())
        .withStatus(status)
        .withErrorCode(errorCode)
        .withMessage(message)
        .withPath(path)
        .withTraceId(traceId)
        .withDetails(null)
        .withFieldErrors(fieldErrors)
        .withException(null)
        .build();
  }
}
