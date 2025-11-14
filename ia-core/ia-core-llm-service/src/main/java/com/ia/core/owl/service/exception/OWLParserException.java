package com.ia.core.owl.service.exception;

/**
 * Exceção lançada quando ocorrem erros durante o parsing de expressões
 * Manchester OWL Syntax.
 */
public class OWLParserException
  extends Exception {
  /** Serial UID */
  private static final long serialVersionUID = -2184866556658714549L;

  /**
   * Cria uma nova exceção de parser com a mensagem especificada.
   *
   * @param message a mensagem de detalhe da exceção
   */
  public OWLParserException(String message) {
    super(message);
  }

  /**
   * Cria uma nova exceção de parser com a mensagem e causa especificadas.
   *
   * @param message a mensagem de detalhe da exceção
   * @param cause   a causa original da exceção
   */
  public OWLParserException(String message, Throwable cause) {
    super(message, cause);
  }
}
