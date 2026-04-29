package com.ia.core.security.view.layout;

import com.ia.core.security.view.layout.menu.SecuredMenuView;
import com.ia.core.security.view.util.SecurityUtils;
import com.ia.core.view.components.layout.MainLayoutView;
import com.ia.core.view.components.layout.MainLayoutViewModel;
import com.ia.core.view.components.layout.menu.AbstractMenuLayoutViewModel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
/**
 * View para exibição e manipulação de secured layout.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a SecuredLayoutView
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public abstract class SecuredLayoutView
  extends MainLayoutView
  implements HasUserAuthentication {

  /**
   * @param viewModel
   */
  public SecuredLayoutView(MainLayoutViewModel viewModel) {
    super(viewModel);
  }

  @Override
  protected void init() {
    super.init();
    checkUserAuthentication();
  }

  @SuppressWarnings("serial")
  @Override
  protected SecuredMenuView createMenuView(AbstractMenuLayoutViewModel menuViewModel) {
    return new SecuredMenuView(menuViewModel) {
      @Override
      public Class<? extends Component> getLoginPage() {
        return getLoginPage();
      }
    };
  }

  @Override
  public void logout() {
    SecurityUtils.logout(UI.getCurrent());
  }

  /**
   * @return Página de logout
   */
  public Class<? extends Component> getLogoutPage() {
    return getLoginPage();
  }

  /**
   * @return Página de login
   */
  public abstract Class<? extends Component> getLoginPage();
}
