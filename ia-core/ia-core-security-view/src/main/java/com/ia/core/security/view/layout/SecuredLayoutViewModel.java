package com.ia.core.security.view.layout;

import com.ia.core.security.view.authentication.AuthenticationDetails;
import com.ia.core.view.components.layout.MainLayoutViewModel;
import com.ia.core.view.components.layout.menu.AbstractMenuLayoutViewModel;

import lombok.Getter;

/**
 * @author Israel Araújo
 */

public class SecuredLayoutViewModel
  extends MainLayoutViewModel {
  /** Indicativo de inicialização */
  private boolean initialized = false;

  @Getter
  private final AuthenticationDetails authenticationDetails;

  /**
   * @param menuViewModel
   * @param authorizationManager
   * @param authenticationDetails
   * @param userService
   */
  public SecuredLayoutViewModel(AbstractMenuLayoutViewModel menuViewModel,
                                AuthenticationDetails authenticationDetails) {
    super(menuViewModel);
    this.authenticationDetails = authenticationDetails;
  }

}
