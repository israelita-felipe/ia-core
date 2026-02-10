package com.ia.core.rest.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Códigos de erro padronizados para a API REST.
 * 
 * Cada código contém:
 * - code: identificador técnico do erro
 * - httpStatus: status HTTP correspondente
 * - translationKey: chave para internacionalização
 * 
 * @author Israel Araújo
 */
@Getter
@RequiredArgsConstructor
public enum RestErrorCode {

  // 4xx Client Errors
  AUTHENTICATION_ERROR(
    "AUTHENTICATION_ERROR",
    401,
    "error.authentication"
  ),
  ACCESS_DENIED(
    "ACCESS_DENIED",
    403,
    "error.access.denied"
  ),
  ENTITY_NOT_FOUND(
    "ENTITY_NOT_FOUND",
    404,
    "error.entity.not.found"
  ),
  VALIDATION_ERROR(
    "VALIDATION_ERROR",
    400,
    "error.validation"
  ),
  DATA_INTEGRITY_VIOLATION(
    "DATA_INTEGRITY_VIOLATION",
    409,
    "error.data.integrity"
  ),
  SERVICE_ERROR(
    "SERVICE_ERROR",
    400,
    "error.service"
  ),

  // 5xx Server Errors
  INTERNAL_ERROR(
    "INTERNAL_ERROR",
    500,
    "error.internal"
  );

  private final String code;
  private final int httpStatus;
  private final String translationKey;

  /**
   * Obtém o HTTP status correspondente ao código.
   * 
   * @return int HTTP status
   */
  public int getHttpStatusValue() {
    return httpStatus;
  }
}
