package com.ia.core.security.view.user;

import org.springframework.stereotype.Component;

import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.model.user.UserPasswordEncoder;
import com.ia.core.security.view.manager.DefaultSecuredViewBaseMangerConfig;

import lombok.Getter;

/**
 *
 */
@Component
public class UserManagerConfig
  extends DefaultSecuredViewBaseMangerConfig<UserDTO> {
  /**
   * Codificador de senha do usuário
   */
  @Getter
  private final UserPasswordEncoder passwordEncoder;

  /**
   * @param client {@link UserClient} de comunicação
   */
  public UserManagerConfig(UserClient client,
                           CoreSecurityAuthorizationManager authorizationManager,
                           UserPasswordEncoder passwordEncoder) {
    super(client, authorizationManager);
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserClient getClient() {
    return (UserClient) super.getClient();
  }
}
