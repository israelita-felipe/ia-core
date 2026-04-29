package com.ia.core.security.view.layout;

import com.ia.core.security.view.authentication.AuthenticationDetails;
import com.vaadin.flow.router.AccessDeniedException;
import io.jsonwebtoken.ExpiredJwtException;

import java.util.function.Consumer;
/**
 * Componente de interface visual para has user authentication.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a HasUserAuthentication
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
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
