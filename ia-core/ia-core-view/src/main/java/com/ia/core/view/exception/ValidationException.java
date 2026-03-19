package com.ia.core.view.exception;

import com.ia.core.service.exception.ServiceException;

/**
 * Exception de validação.
 *
 * @author Israel Araújo
 */
public class ValidationException
  extends ServiceException {
  /**
   * Serial UID.
   */
  private static final long serialVersionUID = -176099666014084747L;

  /**
   * Construtor padrão.
   */
  public ValidationException() {
    super();
  }

  /**
   * Construtor padrão.
   *
   * @param message Mensagem padrão.
   */
  public ValidationException(String message) {
    super(message);
  }

}
