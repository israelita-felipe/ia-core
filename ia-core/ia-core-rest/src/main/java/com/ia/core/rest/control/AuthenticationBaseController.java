package com.ia.core.rest.control;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ia.core.security.model.authentication.AuthenticationRequest;
import com.ia.core.security.model.authentication.AuthenticationResponse;
import com.ia.core.security.service.authentication.AuthenticationService;
import com.ia.core.security.service.exception.InvalidPasswordException;
import com.ia.core.security.service.exception.UserNotFountException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Israel Araújo
 */
public interface AuthenticationBaseController {

  /**
   * URL de autenticação
   */
  public static final String AUTHENTICATE_PATH = "/authenticate";

  @PostMapping(AUTHENTICATE_PATH)
  default ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest body,
                                                              HttpServletRequest request)
    throws InvalidPasswordException, UserNotFountException {
    AuthenticationResponse login = getAuthenticationService()
        .login(body, this::validatePassword);
    return ResponseEntity.ok(login);
  }

  AuthenticationService<AuthenticationRequest> getAuthenticationService();

  PasswordEncoder getPasswordEncoder();

  /**
   * @param dbPassword
   * @param userPassword
   * @return
   */
  default Boolean validatePassword(String dbPassword, String userPassword) {
    return getPasswordEncoder().matches(userPassword, dbPassword);
  }
}
