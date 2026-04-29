package com.ia.core.security.view.authentication;

import com.ia.core.security.model.authentication.JwtManager;
import com.ia.core.security.service.model.authentication.JwtAuthenticationResponseDTO;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.model.user.UserPrivilegeDTO;
import com.ia.core.security.view.login.UserProvider;

import java.util.Collection;
import java.util.stream.Collectors;
/**
 * Provedor de jwt user.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a JwtUserProvider
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class JwtUserProvider
  implements UserProvider<JwtAuthenticationResponseDTO> {

  @Override
  public UserDTO get(JwtAuthenticationResponseDTO response) {
    if (response != null) {
      JwtAuthenticationResponseDTO jwtResponse = response;
      String token = jwtResponse.getToken();
      String userCode = JwtManager.get().getUserCodeFromJWT(token);
      String userName = JwtManager.get().getUserNameFromJWT(token);
      Collection<String> functionalities = JwtManager.get()
          .getFunctionalitiesFromJWT(token);
      return UserDTO.builder().userCode(userCode).userName(userName)
          .password(token).privileges(getPrivileges(functionalities))
          .build();
    }
    return UserDTO.builder().build();
  }

  /**
   * @param functionalities
   * @return
   */
  protected Collection<UserPrivilegeDTO> getPrivileges(Collection<String> functionalities) {
    return functionalities.stream().map(functionality -> {
      return UserPrivilegeDTO.builder()
          .privilege(PrivilegeDTO.builder().name(functionality).build())
          .build();
    }).collect(Collectors.toList());
  }

}
