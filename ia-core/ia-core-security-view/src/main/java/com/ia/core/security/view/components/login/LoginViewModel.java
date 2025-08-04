package com.ia.core.security.view.components.login;

import java.util.function.Consumer;

import com.ia.core.security.model.authentication.AuthenticationRequest;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.view.authentication.AuthenticationDetails;
import com.ia.core.view.components.form.viewModel.FormViewModel;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Israel Araújo
 */
@Slf4j
public abstract class LoginViewModel
  extends FormViewModel<AuthenticationRequest> {

  private AuthenticationDetails details;

  /**
   * @param readOnly
   */
  public LoginViewModel(AuthenticationDetails details) {
    super(false);
    this.details = details;
  }

  public void login(Consumer<UserDTO> onSucess, Runnable onFail) {
    try {
      details.autenticate(getModel());
      UserDTO user = details.getAuthenticatedUser();
      if (user != null) {
        onSucess.accept(user);
        log.info("Usuário {} logado", user);
      }
    } catch (Exception error) {
      onFail.run();
      log.info("Falha de autenticação.\n{}", error.getLocalizedMessage());
    }
  }

}
