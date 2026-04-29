package com.ia.core.security.model.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
/**
 * Interface que define o contrato para jwt.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a JwtManager
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public interface JwtManager {

  static JwtManager get() {
    return JwtCoreManager.get();
  }

  <T> String generateToken(String userCode, String userName,
                           long expiration,
                           Collection<String> functionalities,
                           Context<T> context);

  /**
   * @param token
   * @return
   */
  Collection<String> getFunctionalitiesFromJWT(String token);

  /**
   * Mapa: funcionalidade -> operação -> propriedade -> valores
   *
   * @param token
   * @return
   */
  <T> T getFunctionalitiesContextFromJWT(String token, Class<T> type);

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

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static abstract class Context<T> {
    Collection<T> context;
  }
}
