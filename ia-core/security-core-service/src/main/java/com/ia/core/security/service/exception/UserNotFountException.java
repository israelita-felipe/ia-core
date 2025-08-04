package com.ia.core.security.service.exception;

import com.ia.core.service.exception.ServiceException;

/**
 * @author Israel Araújo
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
