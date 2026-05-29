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

  String generateRefreshToken(String userCode, String userName, long expiration);

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

  boolean validateRefreshToken(String token);

  /**
   * Contexto associado a um token JWT.
   * <p>
   * Classe abstrata que representa o contexto de funcionalidades associado
   * a um token JWT. O contexto armazena informações adicionais que podem
   * ser utilizadas para autorização granular.
   *
   * @bugfix SECURITY: Campo 'context' tornado privado com getter/setter.
   *         Anteriormente era público, permitindo modificação não autorizada.
   *         Agora usa Lombok @Data para gerar getters/setters de forma segura.
   *
   * @param <T> tipo do contexto
   */
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static abstract class Context<T> {
    /**
     * Contexto associado ao token.
     * <p>
     * ⚠️ SECURITY: Este campo é mutable. Certifique-se de que o contexto
     * não contenha informações sensíveis ou que sejam modificadas após a
     * geração do token.
     *
     * @bugfix SECURITY: Tornado privado (era público) para evitar
     *         modificação externa direta. Use getter/setter.
     */
    private Collection<T> context;
  }
}
