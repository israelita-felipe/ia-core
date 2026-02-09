package com.ia.core.security.view.authentication;

import com.ia.core.security.model.authentication.AuthenticationRequest;
import com.ia.core.security.service.model.authentication.JwtAuthenticationResponseDTO;

/**
 * @author Israel Araújo
 */
public interface AuthenticationManager {

  default JwtAuthenticationResponseDTO authenticate(AuthenticationRequest request) {
    return getAuthenticationClient().authenticate(request);
  }

  default JwtAuthenticationResponseDTO createFirstUser(AuthenticationRequest request) {
    return getAuthenticationClient().createFirstUser(request);
  }

  default Boolean initializeSecurity() {
    return getAuthenticationClient().initializeSecurity();
  }

  default void authenticate(AuthenticationRequest request,
                           AuthenticationDetails details) {
    details.setAuthentication(authenticate(request));
  }

  default void createFirstUser(AuthenticationRequest request,
                              AuthenticationDetails details) {
    details.setAuthentication(createFirstUser(request));
  }

  AuthenticationBaseClient getAuthenticationClient();
}
