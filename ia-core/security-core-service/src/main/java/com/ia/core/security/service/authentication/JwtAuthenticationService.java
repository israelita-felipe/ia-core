package com.ia.core.security.service.authentication;

import java.util.function.BiFunction;
import java.util.stream.Collectors;

import com.ia.core.security.model.authentication.AuthenticationRequest;
import com.ia.core.security.model.authentication.AuthenticationResponse;
import com.ia.core.security.model.authentication.JwtManager;
import com.ia.core.security.service.exception.InvalidPasswordException;
import com.ia.core.security.service.exception.UserNotFountException;
import com.ia.core.security.service.model.authentication.JwtAuthenticationResponseDTO;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.user.UserDTO;

/**
 * @author Israel Araújo
 */
public interface JwtAuthenticationService
  extends AuthenticationService<AuthenticationRequest> {

  default JwtAuthenticationResponseDTO generateToken(UserDTO user) {
    return JwtAuthenticationResponseDTO.builder()
        .token(JwtManager.get()
            .generateToken(user.getUserCode(), user.getUserName(),
                           getExpirationTime(),
                           user.getAllPrivileges().stream()
                               .map(PrivilegeDTO::getName)
                               .collect(Collectors.toUnmodifiableSet())))
        .build();
  }

  /**
   * @return
   */
  long getExpirationTime();

  UserDTO getUser(AuthenticationRequest request)
    throws UserNotFountException;

  @Override
  default AuthenticationResponse login(AuthenticationRequest request,
                                       BiFunction<String, String, Boolean> passwordChecker)
    throws InvalidPasswordException, UserNotFountException {
    UserDTO user = getUser(request);
    if (passwordChecker.apply(user.getPassword(), request.getSenha())) {
      return generateToken(user);
    }
    throw new InvalidPasswordException(request.getCodUsuario());
  }
}
