package com.ia.core.security.model.authentication;

import java.util.Collection;

/**
 * @author Israel Araújo
 */
public interface JwtManager {

  static JwtManager get() {
    return JwtCoreManager.get();
  }

  String generateToken(String userCode, String userName, long expiration,
                       Collection<String> functionalities);

  /**
   * @param token
   * @return
   */
  Collection<String> getFunctionalitiesFromJWT(String token);

  /**
   * @param token
   * @return
   */
  String getUserCodeFromJWT(String token);

  /**
   * @param token
   * @return
   */
  String getUserNameFromJWT(String token);

  boolean validateToken(String token);
}
