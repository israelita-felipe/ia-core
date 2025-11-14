package com.ia.core.security.service.authentication;

import java.util.concurrent.TimeUnit;

import com.ia.core.security.model.authentication.AuthenticationRequest;
import com.ia.core.security.model.user.User;
import com.ia.core.security.service.exception.UserNotFountException;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.user.UserRepository;
import com.ia.core.service.mapper.BaseEntityMapper;

import lombok.RequiredArgsConstructor;

/**
 * @author Israel Araújo
 */
@RequiredArgsConstructor
public class CoreJwtAuthenticationService
  implements JwtAuthenticationService {

  private final UserRepository userRepository;

  private final BaseEntityMapper<User, UserDTO> mapper;

  @Override
  public long getExpirationTime() {
    return TimeUnit.HOURS.toMillis(1);
  }

  @Override
  public UserDTO getUser(AuthenticationRequest request)
    throws UserNotFountException {
    User user = userRepository.findByUserCode(request.getCodUsuario());
    if (user == null) {
      throw new UserNotFountException(request.getCodUsuario());
    }
    return mapper.toDTO(user);
  }
}
