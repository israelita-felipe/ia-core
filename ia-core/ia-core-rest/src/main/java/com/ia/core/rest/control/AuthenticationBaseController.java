package com.ia.core.rest.control;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ia.core.security.model.authentication.AuthenticationRequest;
import com.ia.core.security.model.authentication.AuthenticationResponse;
import com.ia.core.security.service.authentication.AuthenticationService;
import com.ia.core.security.service.exception.InvalidPasswordException;
import com.ia.core.security.service.exception.UserNotFountException;
import com.ia.core.service.exception.ServiceException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Israel Araújo
 */
@Tag(name = "Autenticação",
     description = "Endpoints para autenticação de usuários")
public interface AuthenticationBaseController {

  /**
   * URL de autenticação
   */
  public static final String AUTHENTICATE_PATH = "/authenticate";
  /**
   * URL de criação de primeiro usuário
   */
  public static final String CREATE_FIRST_USER_PATH = "/firstuser";
  /**
   * URL de inicialização do security
   */
  public static final String INITIALIZE_SECURITY_PATH = "/initialize_security";

  @Operation(summary = "Autentica um usuário e retorna um token JWT")
  @PostMapping(AUTHENTICATE_PATH)
  default ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest body,
                                                              HttpServletRequest request)
    throws InvalidPasswordException, UserNotFountException {
    AuthenticationResponse login = getAuthenticationService()
        .login(body, this::validatePassword);
    return ResponseEntity.ok(login);
  }

  @Operation(summary = "Cria e configura o primeiro usuário")
  @PostMapping(CREATE_FIRST_USER_PATH)
  default ResponseEntity<AuthenticationResponse> createFirstUser(@RequestBody AuthenticationRequest body,
                                                                 HttpServletRequest request)
    throws ServiceException {
    String senha = body.getSenha();
    body.setSenha(getPasswordEncoder().encode(senha));
    var user = getAuthenticationService().createFirstUser(body);
    body.setSenha(senha);
    return authenticate(body, request);
  }

  @Operation(summary = "Verifica se o security precisa ser inicializado")
  @GetMapping(INITIALIZE_SECURITY_PATH)
  default ResponseEntity<Boolean> initializeSecurity(HttpServletRequest request) {
    return ResponseEntity
        .ok(getAuthenticationService().initializeSecurity());
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
