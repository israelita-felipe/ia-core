package com.ia.core.model.exception;

/**
 * Exceção lançada quando ocorre uma violação de regra de negócio.
 *
 * @author Israel Araújo
 */
public class BusinessException
  extends DomainException {

  private static final long serialVersionUID = 1L;

  private static final String DEFAULT_MESSAGE = "Violação de regra de negócio";
  private static final String DEFAULT_ERROR_CODE = "BUSINESS_RULE_VIOLATION";

  /**
   * Construtor com mensagem de erro.
   *
   * @param message mensagem de erro
   */
  public BusinessException(String message) {
    super(DEFAULT_ERROR_CODE, message);
  }

  /**
   * Construtor com código de erro e mensagem.
   *
   * @param errorCode código de erro
   * @param message   mensagem de erro
   */
  public BusinessException(String errorCode, String message) {
    super(errorCode, message);
  }

  /**
   * Construtor com código de erro, mensagem e causa.
   *
   * @param errorCode código de erro
   * @param message   mensagem de erro
   * @param cause     causa original
   */
  public BusinessException(String errorCode, String message, Throwable cause) {
    super(errorCode, message, cause);
  }
}
