package com.ia.core.security.service.authentication;

import com.ia.core.security.model.authentication.AuthenticationRequest;
import com.ia.core.security.model.authentication.AuthenticationResponse;
import com.ia.core.security.model.authentication.JwtManager;
import com.ia.core.security.service.authorization.JWTPrivilegeContext;
import com.ia.core.security.service.exception.InvalidPasswordException;
import com.ia.core.security.service.exception.UserNotFountException;
import com.ia.core.security.service.model.authentication.JwtAuthenticationResponseDTO;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.service.annotations.TransactionalReadOnly;

import java.util.function.BiFunction;
import java.util.stream.Collectors;
/**
 * Serviço de negócio para gerenciamento de jwt authentication.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a JwtAuthenticationService
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public interface JwtAuthenticationService
  extends AuthenticationService<AuthenticationRequest> {

  default JwtAuthenticationResponseDTO generateToken(UserDTO user) {
    String accessToken = JwtManager.get()
            .generateToken(user.getUserCode(), user.getUserName(),
                           getExpirationTime(),
                           user.getAllPrivileges().stream()
                               .map(PrivilegeDTO::getName)
                               .collect(Collectors.toUnmodifiableSet()),
                           new JWTPrivilegeContext(user.getAllContexts()));

    String refreshToken = JwtManager.get()
            .generateRefreshToken(user.getUserCode(), user.getUserName(),
                                  getRefreshExpirationTime());

    return JwtAuthenticationResponseDTO.builder()
        .token(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

    default AuthenticationResponse refreshToken(AuthenticationRequest request)
        throws InvalidPasswordException, UserNotFountException{
        String token = request.getRefreshToken();
        if (token == null || token.isEmpty()) {
            throw new InvalidPasswordException("Refresh token não informado");
        }

        // Validate the refresh token
        if (!JwtManager.get().validateRefreshToken(token)) {
            throw new InvalidPasswordException("Refresh token inválido ou expirado");
        }

        // Extract user code from refresh token
        String userCode = JwtManager.get().getUserCodeFromJWT(token);
        if (userCode == null) {
            throw new InvalidPasswordException("Não foi possível extrair usuário do refresh token");
        }

        // Load user details
        UserDTO user = getUser(new AuthenticationRequest(userCode, null));
        if (user == null) {
            throw new UserNotFountException(userCode);
        }

        // Generate new access token (keep the same refresh token)
        String accessToken = JwtManager.get()
            .generateToken(user.getUserCode(), user.getUserName(),
                           getExpirationTime(),
                           user.getAllPrivileges().stream()
                               .map(PrivilegeDTO::getName)
                               .collect(Collectors.toUnmodifiableSet()),
                           new JWTPrivilegeContext(user.getAllContexts()));

    return JwtAuthenticationResponseDTO.builder()
        .token(accessToken)
        .refreshToken(token)
        .build();
  }
  /**
   * @return
   */
  long getExpirationTime();

  /**
   * @return
   */
  long getRefreshExpirationTime();

  UserDTO getUser(AuthenticationRequest request)
    throws UserNotFountException;

  @TransactionalReadOnly
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
