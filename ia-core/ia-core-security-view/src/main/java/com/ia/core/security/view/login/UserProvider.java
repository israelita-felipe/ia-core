package com.ia.core.security.view.login;

import com.ia.core.security.model.authentication.AuthenticationResponse;
import com.ia.core.security.service.model.user.UserDTO;

/**
 * Provê o usuário dado uma resposta de autenticação
 *
 * @author Israel Araújo
 * @param <T> Tipo da resposta de autenticação
 */
public interface UserProvider<T extends AuthenticationResponse> {
  /**
   * @param response {@link AuthenticationResponse}
   * @return {@link UserDTO} a partir da resposta de autenticação
   */
  UserDTO get(T response);
}
