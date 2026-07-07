package com.ia.core.security.service.exception;

import com.ia.core.service.exception.ServiceException;

/**
 * Exceção para erros relacionados a invalid password.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a InvalidPasswordException
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class InvalidPasswordException
  extends ServiceException {
  private final String userCode;

  /**
   * @param userCode código do usuário
   */
  public InvalidPasswordException(String userCode) {
    super("Senha inválida para o usuário: " + userCode);
    this.userCode = userCode;
  }

  /**
   * @return {@link #userCode}
   */
  public String getUserCode() {
    return userCode;
  }
}
