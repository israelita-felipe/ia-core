package com.ia.core.security.service.exception;

import com.ia.core.service.exception.ServiceException;

/**
 * @author Israel Araújo
 */
public class InvalidPasswordException
  extends ServiceException {
  private final String userCode;

  /**
   *
   */
  public InvalidPasswordException(String userCode) {
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
