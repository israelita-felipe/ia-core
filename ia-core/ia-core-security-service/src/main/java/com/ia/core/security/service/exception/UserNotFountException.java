package com.ia.core.security.service.exception;

import com.ia.core.service.exception.ServiceException;
/**
 * Exceção para erros relacionados a user not fount.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a UserNotFountException
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class UserNotFountException
  extends ServiceException {
  private final String userCode;

  /**
   * @param userCode
   */
  public UserNotFountException(String userCode) {
    super();
    this.userCode = userCode;
  }

  /**
   * @return {@link #userCode}
   */
  public String getUserCode() {
    return userCode;
  }
}
