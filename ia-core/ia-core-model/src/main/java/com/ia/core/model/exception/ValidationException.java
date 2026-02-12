package com.ia.core.model.exception;

/**
 * Exceção lançada quando ocorre um erro de validação de dados.
 *
 * @author Israel Araújo
 */
public class ValidationException
  extends DomainException {

  private static final long serialVersionUID = 1L;

  private static final String DEFAULT_MESSAGE = "Erro de validação";
  private static final String DEFAULT_ERROR_CODE = "VALIDATION_ERROR";

  /**
   * Campo(s) que falharam na validação.
   */
  private final String field;

  /**
   * Valor que falhou na validação.
   */
  private final Object rejectedValue;

  /**
   * Construtor com mensagem de erro.
   *
   * @param message mensagem de erro
   */
  public ValidationException(String message) {
    super(DEFAULT_ERROR_CODE, message);
    this.field = null;
    this.rejectedValue = null;
  }

  /**
   * Construtor com campo e valor rejeitado.
   *
   * @param field         campo que falhou
   * @param rejectedValue valor rejeitado
   * @param message       mensagem de erro
   */
  public ValidationException(String field, Object rejectedValue, String message) {
    super(DEFAULT_ERROR_CODE, message);
    this.field = field;
    this.rejectedValue = rejectedValue;
  }

  /**
   * @return campo que falhou na validação
   */
  public String getField() {
    return field;
  }

  /**
   * @return valor que falhou na validação
   */
  public Object getRejectedValue() {
    return rejectedValue;
  }
}
