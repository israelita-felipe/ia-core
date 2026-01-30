package com.ia.core.security.service.authentication;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.transaction.PlatformTransactionManager;

import com.ia.core.security.model.authentication.AuthenticationRequest;
import com.ia.core.security.model.functionality.OperationEnum;
import com.ia.core.security.model.privilege.PrivilegeOperation;
import com.ia.core.security.model.user.User;
import com.ia.core.security.model.user.UserPrivilege;
import com.ia.core.security.service.exception.UserNotFountException;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.privilege.PrivilegeRepository;
import com.ia.core.security.service.user.UserRepository;
import com.ia.core.service.HasTransaction;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.mapper.BaseEntityMapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Israel Araújo
 */
@RequiredArgsConstructor
public class CoreJwtAuthenticationService
  implements JwtAuthenticationService, HasTransaction {

  private final UserRepository userRepository;
  private final PrivilegeRepository privilegeRepository;

  private final BaseEntityMapper<User, UserDTO> mapper;
  @Getter
  private final PlatformTransactionManager transactionManager;

  @Override
  public long getExpirationTime() {
    return TimeUnit.HOURS.toMillis(1);
  }

  @Override
  public UserDTO getUser(AuthenticationRequest request)
    throws UserNotFountException {
    UserDTO userDto = onTransaction(() -> {
      User user = userRepository.findByUserCode(request.getCodUsuario());
      if (user == null) {
        return null;
      }
      return mapper.toDTO(user);
    });
    if (userDto == null) {
      throw new UserNotFountException(request.getCodUsuario());
    }
    return userDto;
  }

  @Override
  public boolean initializeSecurity() {
    return userRepository.count() == 0l;
  }

  @Override
  public UserDTO createFirstUser(AuthenticationRequest request)
    throws ServiceException {
    if (!initializeSecurity()) {
      throw new ServiceException("Security já foi inicializado");
    }
    User user = User.builder().accountNotExpired(true)
        .accountNotLocked(true).credentialsNotExpired(true).enabled(true)
        .userCode(request.getCodUsuario()).userName(request.getCodUsuario())
        .password(request.getSenha()).build();
    Set<UserPrivilege> privileges = privilegeRepository.findAll().stream()
        .map(privilege -> {
          return UserPrivilege.builder().user(user).privilege(privilege)
              .operations(Stream.of(OperationEnum.values()).map(op -> {
                return PrivilegeOperation.builder().operation(op).build();
              }).collect(Collectors.toSet())).build();
        }).collect(Collectors.toSet());
    user.setPrivileges(privileges);
    return mapper.toDTO(userRepository.save(user));
  }
}
