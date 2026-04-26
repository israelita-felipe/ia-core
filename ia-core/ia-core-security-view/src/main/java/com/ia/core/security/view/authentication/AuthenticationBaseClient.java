package com.ia.core.security.view.authentication;

import com.ia.core.security.model.authentication.AuthenticationRequest;
import com.ia.core.security.service.model.authentication.JwtAuthenticationResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Israel Araújo
 */
public interface AuthenticationBaseClient {

  @PostMapping("/authenticate")
  JwtAuthenticationResponseDTO authenticate(@RequestBody AuthenticationRequest request);

  @PostMapping("/firstuser")
  JwtAuthenticationResponseDTO createFirstUser(@RequestBody AuthenticationRequest request);

  @GetMapping("/initialize_security")
  Boolean initializeSecurity();

}
