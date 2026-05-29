package com.ia.core.security.service.authentication;

import com.ia.core.security.model.authentication.AuthenticationRequest;
import com.ia.core.security.model.functionality.OperationEnum;
import com.ia.core.security.model.privilege.PrivilegeOperation;
import com.ia.core.security.model.user.User;
import com.ia.core.security.model.user.UserPrivilege;
import com.ia.core.security.service.exception.UserNotFountException;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.privilege.PrivilegeRepository;
import com.ia.core.security.service.user.UserRepository;
import com.ia.core.service.annotations.TransactionalReadOnly;
import com.ia.core.service.annotations.TransactionalWrite;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.mapper.BaseEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Israel Araújo
 */
/**
 * Classe que representa os serviços de negócio para core jwt authentication.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a CoreJwtAuthenticationService
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@RequiredArgsConstructor
public class CoreJwtAuthenticationService
  implements JwtAuthenticationService {

  private final UserRepository userRepository;
  private final PrivilegeRepository privilegeRepository;
  private final BaseEntityMapper<User, UserDTO> mapper;

  @Override
  public long getExpirationTime() {
    return TimeUnit.HOURS.toMillis(1);
  }

  @Override
  public long getRefreshExpirationTime() {
    // Refresh tokens should have a longer expiration time (e.g., 7 days)
    return TimeUnit.DAYS.toMillis(7);
  }

    @TransactionalReadOnly
    @Override
    public UserDTO getUser(AuthenticationRequest request)
      throws UserNotFountException {
      Objects.requireNonNull(request, "Request não pode ser null");
      Objects.requireNonNull(request.getCodUsuario(), "Código de usuário não pode ser null");
      return userRepository.findByUserCode(request.getCodUsuario())
        .map(mapper::toDTO)
        .orElseThrow(() -> new UserNotFountException(request.getCodUsuario()));
    }

  @Override
  public boolean initializeSecurity() {
    return userRepository.count() == 0l;
  }

  @TransactionalWrite
    @Override
    public UserDTO createFirstUser(AuthenticationRequest request)
      throws ServiceException {
    Objects.requireNonNull(request, "Request não pode ser null");
    Objects.requireNonNull(request.getCodUsuario(), "Código de usuário não pode ser null");
    Objects.requireNonNull(request.getSenha(), "Senha não pode ser null");
    if (!initializeSecurity()) {
      throw new ServiceException("Security já foi inicializado");
    }
    User user = User.builder().accountNotExpired(true)
        .accountNotLocked(true).credentialsNotExpired(true).enabled(true)
        .userCode(request.getCodUsuario()).userName(request.getCodUsuario())
        .password(request.getSenha()).build();
    Set<UserPrivilege> privileges = privilegeRepository.findAll().stream()
        .map(privilege -> UserPrivilege.builder().user(user).privilege(privilege)
            .operations(Stream.of(OperationEnum.values()).map(op -> PrivilegeOperation.builder().operation(op).build()).collect(Collectors.toSet())).build()).collect(Collectors.toSet());
    user.setPrivileges(privileges);
    return mapper.toDTO(userRepository.save(user));
  }

}
