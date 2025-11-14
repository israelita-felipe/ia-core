package com.ia.core.security.view.authentication;

import com.ia.core.security.model.authentication.AuthenticationRequest;
import com.ia.core.security.service.model.authentication.JwtAuthenticationResponseDTO;

/**
 * @author Israel Araújo
 */
public interface AuthenticationManager {

  default JwtAuthenticationResponseDTO autenticate(AuthenticationRequest request) {
    return getAuthenticationClient().authenticate(request);
  }

  default void autenticate(AuthenticationRequest request,
                           AuthenticationDetails details) {
    details.setAuthentication(autenticate(request));
  }

  AuthenticationBaseClient getAuthenticationClient();
}
