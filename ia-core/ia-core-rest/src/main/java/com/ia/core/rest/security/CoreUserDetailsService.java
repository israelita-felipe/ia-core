package com.ia.core.rest.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.PlatformTransactionManager;

import com.ia.core.security.model.privilege.Privilege;
import com.ia.core.security.model.user.User;
import com.ia.core.security.service.user.UserRepository;
import com.ia.core.service.HasTransaction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Israel Araújo
 */
@RequiredArgsConstructor
public class CoreUserDetailsService
  implements UserDetailsService, HasTransaction {

  @Getter
  private final PlatformTransactionManager transactionManager;
  private final UserRepository repository;

  /**
   * @param user
   * @return
   */
  protected Collection<SimpleGrantedAuthority> getPrivilegesFromUser(User user) {
    return onTransaction(() -> {
      return user.getRoles().stream()
          .flatMap(role -> role.getPrivileges().stream())
          .map(Privilege::getName).map(SimpleGrantedAuthority::new)
          .collect(Collectors.toUnmodifiableSet());
    });
  }

  @Override
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
    return onTransaction(() -> {
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
    });
  }

}
