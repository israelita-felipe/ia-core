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
 * Serviço de autenticação JWT para o core.
 *
 * <p>Responsável por gerenciar a autenticação de usuários através de tokens JWT,
 * incluindo geração de tokens, validação de credenciais e inicialização de segurança.
 *
 * <p><b>Por quê usar CoreJwtAuthenticationService?</b></p>
 * <ul>
 *   <li>Gerencia tokens de acesso e refresh com tempos de expiração configuráveis</li>
 *   <li>Valida credidenciais de usuários no sistema</li>
 *   <li>Inicializa o primeiro usuário administrador do sistema</li>
 *   <li>Concede todos os privilégios ao primeiro usuário criado</li>
 * </ul>
 *
 * <p><b>Exemplo de uso:</b></p>
 * {@code
 * AuthenticationRequest request = new AuthenticationRequest("admin", "password");
 * UserDTO user = authenticationService.getUser(request);
 * }
 *
 * @author Israel Araújo
 * @see JwtAuthenticationService
 * @see AuthenticationRequest
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class CoreJwtAuthenticationService
  implements JwtAuthenticationService {

  /**
   * Tempo de expiração do token de acesso em horas.
   */
  private static final int TOKEN_EXPIRATION_HOURS = 1;

  /**
   * Tempo de expiração do token de refresh em dias.
   */
  private static final int REFRESH_TOKEN_EXPIRATION_DAYS = 7;

  private final UserRepository userRepository;
  private final PrivilegeRepository privilegeRepository;
  private final BaseEntityMapper<User, UserDTO> mapper;

  /**
   * Obtém o tempo de expiração do token de acesso em milissegundos.
   *
   * @return Tempo de expiração em milissegundos
   */
  @Override
  public long getExpirationTime() {
    return TimeUnit.HOURS.toMillis(TOKEN_EXPIRATION_HOURS);
  }

  /**
   * Obtém o tempo de expiração do token de refresh em milissegundos.
   *
   * <p>Os tokens de refresh têm um tempo de expiração maior que os tokens de acesso
   * para permitir que os usuários permaneçam autenticados por períodos mais longos.
   *
   * @return Tempo de expiração em milissegundos
   */
  @Override
  public long getRefreshExpirationTime() {
    return TimeUnit.DAYS.toMillis(REFRESH_TOKEN_EXPIRATION_DAYS);
  }

  /**
   * Obtém um usuário pelo código de usuário.
   *
   * <p>Valida se o request e o código de usuário não são nulos antes de buscar.
   *
   * @param request Requisição de autenticação contendo o código do usuário
   * @return DTO do usuário encontrado
   * @throws UserNotFountException se o usuário não for encontrado
   * @throws NullPointerException se request ou codUsuario forem nulos
   */
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

  /**
   * Verifica se a segurança do sistema já foi inicializada.
   *
   * <p>A segurança é considerada inicializada quando existe pelo menos um usuário no sistema.
   *
   * @return {@code true} se não houver usuários no sistema, {@code false} caso contrário
   */
  @Override
  public boolean initializeSecurity() {
    return userRepository.count() == 0l;
  }

  /**
   * Cria o primeiro usuário administrador do sistema.
   *
   * <p>Este método só pode ser chamado quando o sistema ainda não foi inicializado
   * (não existem usuários). O primeiro usuário criado recebe todos os privilégios
   * disponíveis no sistema com todas as operações.
   *
   * @param request Requisição de autenticação contendo código de usuário e senha
   * @return DTO do usuário criado
   * @throws ServiceException se a segurança já foi inicializada
   * @throws NullPointerException se request, codUsuario ou senha forem nulos
   */
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
    User user = buildUserWithPrivileges(request);
    return mapper.toDTO(userRepository.save(user));
  }

  /**
   * Constrói um usuário com todos os privilégios disponíveis no sistema.
   *
   * <p>O usuário criado recebe todos os privilégios existentes com todas as
   * operações habilitadas. Isso é necessário para o primeiro usuário administrador.
   *
   * @param request Requisição de autenticação contendo código de usuário e senha
   * @return Usuário configurado com todos os privilégios
   */
  private User buildUserWithPrivileges(AuthenticationRequest request) {
    User user = User.builder()
        .accountNotExpired(true)
        .accountNotLocked(true)
        .credentialsNotExpired(true)
        .enabled(true)
        .userCode(request.getCodUsuario())
        .userName(request.getCodUsuario())
        .password(request.getSenha())
        .build();

    var privileges = privilegeRepository.findAll();
    if (privileges == null) {
      user.setPrivileges(Set.of());
      return user;
    }
    Set<UserPrivilege> userPrivileges = privileges.stream()
        .map(privilege -> buildUserPrivilegeWithAllOperations(user, privilege))
        .collect(Collectors.toSet());

    user.setPrivileges(userPrivileges);
    return user;
  }

  /**
   * Constrói um UserPrivilege com todas as operações habilitadas.
   *
   * @param user Usuário ao qual o privilégio será associado
   * @param privilege Privilégio base do sistema
   * @return UserPrivilege configurado com todas as operações
   */
  private UserPrivilege buildUserPrivilegeWithAllOperations(User user, com.ia.core.security.model.privilege.Privilege privilege) {
    Set<PrivilegeOperation> operations = Stream.of(OperationEnum.values())
        .map(op -> PrivilegeOperation.builder().operation(op).build())
        .collect(Collectors.toSet());

    return UserPrivilege.builder()
        .user(user)
        .privilege(privilege)
        .operations(operations)
        .build();
  }

}
