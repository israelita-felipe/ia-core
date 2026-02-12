package com.ia.core.model.exception;

/**
 * Exceção base para todas as exceções do domínio.
 * Todas as exceções específicas do domínio devem estender esta classe.
 *
 * @author Israel Araújo
 */
public abstract class DomainException
  extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * Código de erro associado à exceção.
   */
  private final String errorCode;

  /**
   * Construtor padrão.
   *
   * @param message a mensagem de detalhe
   */
  protected DomainException(String message) {
    super(message);
    this.errorCode = determineErrorCode();
  }

  /**
   * Construtor com mensagem e causa.
   *
   * @param message a mensagem de detalhe
   * @param cause   a causa original
   */
  protected DomainException(String message, Throwable cause) {
    super(message, cause);
    this.errorCode = determineErrorCode();
  }

  /**
   * Construtor com código de erro e mensagem.
   *
   * @param errorCode código de erro
   * @param message   a mensagem de detalhe
   */
  protected DomainException(String errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  /**
   * Construtor com código de erro, mensagem e causa.
   *
   * @param errorCode código de erro
   * @param message   a mensagem de detalhe
   * @param cause     a causa original
   */
  protected DomainException(String errorCode, String message, Throwable cause) {
    super(message, cause);
    this.errorCode = errorCode;
  }

  /**
   * Determina o código de erro baseado no nome da classe.
   *
   * @return código de erro em formato snake_case
   */
  private String determineErrorCode() {
    String className = getClass().getSimpleName();
    return className.replaceAll("(?<!^)(?=[A-Z])", "_").toUpperCase();
  }

  /**
   * @return o código de erro associado à exceção
   */
  public String getErrorCode() {
    return errorCode;
  }
}
