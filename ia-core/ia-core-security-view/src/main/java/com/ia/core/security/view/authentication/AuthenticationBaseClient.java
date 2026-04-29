package com.ia.core.security.view.authentication;

import com.ia.core.security.model.authentication.AuthenticationRequest;
import com.ia.core.security.service.model.authentication.JwtAuthenticationResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
/**
 * Cliente Feign para comunicação com o serviço de authentication base.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a AuthenticationBaseClient
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public interface AuthenticationBaseClient {

  @PostMapping("/authenticate")
  JwtAuthenticationResponseDTO authenticate(@RequestBody AuthenticationRequest request);

  @PostMapping("/firstuser")
  JwtAuthenticationResponseDTO createFirstUser(@RequestBody AuthenticationRequest request);

  @GetMapping("/initialize_security")
  Boolean initializeSecurity();

}
