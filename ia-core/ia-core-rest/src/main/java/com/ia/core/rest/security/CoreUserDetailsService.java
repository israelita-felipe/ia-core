package com.ia.core.rest.security;

import com.ia.core.security.model.privilege.Privilege;
import com.ia.core.security.model.role.RolePrivilege;
import com.ia.core.security.model.user.User;
import com.ia.core.security.model.user.UserPrivilege;
import com.ia.core.security.service.user.UserRepository;
import com.ia.core.service.annotations.TransactionalReadOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service de detalhes de usuário para autenticação JWT.
 * <p>
 * Implementa UserDetailsService do Spring Security para carregar informações do
 * usuário durante a autenticação.
 *
 * @author Israel Araújo
 * @see UserDetailsService
 */
@RequiredArgsConstructor
public class CoreUserDetailsService
  implements UserDetailsService {

  private final UserRepository repository;

  /**
   * Extrai privilégios de um usuário.
   * <p>
   * Combina privilégios de roles e privilégios diretos do usuário.
   *
   * @param user Usuário
   * @return Coleção de autoridades
   */
  @TransactionalReadOnly
  protected Collection<SimpleGrantedAuthority> getPrivilegesFromUser(User user) {
    return Stream
        .concat(user.getRoles().stream()
            .flatMap(role -> role.getPrivileges().stream())
            .map(RolePrivilege::getPrivilege).map(Privilege::getName),
                user.getPrivileges().stream()
                    .map(UserPrivilege::getPrivilege)
                    .map(Privilege::getName))
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toUnmodifiableSet());
  }

  @TransactionalReadOnly
  @Override
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
    User user = repository.findByUserCode(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    UserDetails userDetails = new org.springframework.security.core.userdetails.User(user
        .getUserCode(), user.getPassword(), user.isEnabled(),
                                                                                     user.isAccountNotExpired(),
                                                                                     user.isAccountNotLocked(),
                                                                                     user.isCredentialsNotExpired(),
                                                                                     getPrivilegesFromUser(user));
    return userDetails;
  }

}
