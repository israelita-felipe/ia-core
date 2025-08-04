package com.ia.core.security.view.authentication;

import java.util.Collection;
import java.util.stream.Collectors;

import com.ia.core.security.model.authentication.JwtManager;
import com.ia.core.security.service.model.authentication.JwtAuthenticationResponseDTO;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.view.login.UserProvider;

/**
 * @author Israel Araújo
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
  protected Collection<PrivilegeDTO> getPrivileges(Collection<String> functionalities) {
    return functionalities.stream().map(functionality -> {
      return PrivilegeDTO.builder().name(functionality).build();
    }).collect(Collectors.toList());
  }

}
