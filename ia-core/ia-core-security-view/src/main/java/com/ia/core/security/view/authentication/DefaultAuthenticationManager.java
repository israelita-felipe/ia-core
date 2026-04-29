package com.ia.core.security.view.authentication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Israel Araújo
 */
/**
 * Classe que representa o gerenciamento de default authentication.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a DefaultAuthenticationManager
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@RequiredArgsConstructor
public class DefaultAuthenticationManager
  implements AuthenticationManager {

  @Getter
  private final AuthenticationBaseClient authenticationClient;

}
