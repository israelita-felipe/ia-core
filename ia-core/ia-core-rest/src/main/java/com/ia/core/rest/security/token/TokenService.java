package com.ia.core.rest.security.token;

import com.ia.core.security.model.authentication.JwtToken;
import com.ia.core.security.model.authentication.TokenValidationResult;

/**
 * Interface para operações com tokens JWT na camada REST.
 *
 * @author Israel Araújo
 * @version 1.0
 * @since 1.0
 */
public interface TokenService {

  /**
   * Gera um token JWT.
   *
   * @param username nome do usuário
   * @param userCode código do usuário
   * @return token JWT
   */
  JwtToken generateToken(String username, String userCode);

  /**
   * Valida um token JWT.
   *
   * @param token token a ser validado
   * @return resultado da validação
   */
  TokenValidationResult validateToken(JwtToken token);

  /**
   * Extrai o nome de usuário do token.
   *
   * @param token token JWT
   * @return nome de usuário
   */
  String getUsernameFromToken(JwtToken token);

  /**
   * Extrai o código do usuário do token.
   *
   * @param token token JWT
   * @return código do usuário
   */
  String getUserCodeFromToken(JwtToken token);
}
