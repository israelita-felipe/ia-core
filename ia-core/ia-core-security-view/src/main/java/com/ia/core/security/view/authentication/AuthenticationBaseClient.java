package com.ia.core.security.view.authentication;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ia.core.security.model.authentication.AuthenticationRequest;
import com.ia.core.security.service.model.authentication.JwtAuthenticationResponseDTO;

/**
 * @author Israel Araújo
 */
public interface AuthenticationBaseClient {

  @PostMapping("/authenticate")
  JwtAuthenticationResponseDTO authenticate(@RequestBody AuthenticationRequest request);
}
