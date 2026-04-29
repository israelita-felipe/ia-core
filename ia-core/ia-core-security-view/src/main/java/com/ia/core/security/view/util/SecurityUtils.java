package com.ia.core.security.view.util;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
/**
 * Classe utilitária para operações com security.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a SecurityUtils
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class SecurityUtils {

  /**
   * Realiza o logout e atualiza a página atual
   *
   * @param ui {@link UI} de origem
   */
  public static void logout(UI ui) {
    try {
      logout();
    } finally {
      if (ui != null) {
        ui.getPage().reload();
      }
    }
  }

  /**
   * Realiza o logout
   */
  protected static void logout() {
    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
    logoutHandler
        .logout(VaadinServletRequest.getCurrent().getHttpServletRequest(),
                null, null);
  }

}
