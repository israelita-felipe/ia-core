package com.ia.core.security.view.layout;

import java.util.function.Consumer;

import com.ia.core.security.view.authentication.AuthenticationDetails;
import com.vaadin.flow.router.AccessDeniedException;

import io.jsonwebtoken.ExpiredJwtException;

/**
 * @author Israel Araújo
 */
public interface HasUserAuthentication {
  default void access(Consumer<AuthenticationDetails> onAccess) {
    onAccess.accept(getAuthenticationDetails());
  }

  default void checkUserAuthentication() {
    access(context -> {
      try {
        context.getAuthenticatedUser();
      } catch (ExpiredJwtException e) {
        logout();
        throw new AccessDeniedException();
      }
    });
  }

  AuthenticationDetails getAuthenticationDetails();

  void logout();
}
