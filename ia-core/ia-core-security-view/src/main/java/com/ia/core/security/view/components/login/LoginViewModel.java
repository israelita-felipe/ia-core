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

  /**
   * @param readOnly
   */
  public LoginViewModel(LoginViewModelConfig config) {
    super(config);
  }

  @Override
  public LoginViewModelConfig getConfig() {
    return super.getConfig().cast();
  }

  public boolean isFirstLogin() {
    return getConfig().getDetails().initializeSecurity();
  }

  public void createFirstUser(Consumer<UserDTO> onSucess, Runnable onFail) {
    try {
      AuthenticationDetails details = getConfig().getDetails();
      details.createFirstUser(getModel());
      UserDTO user = details.getAuthenticatedUser();
      if (user != null) {
        onSucess.accept(user);
        log.info("Usuário {} logado", user);
      }
    } catch (Exception error) {
      onFail.run();
      log.info("Falha ao criar primeiro usuário.\n{}",
               error.getLocalizedMessage());
    }
  }

  public void login(Consumer<UserDTO> onSucess, Runnable onFail) {
    try {
      AuthenticationDetails details = getConfig().getDetails();
      details.authenticate(getModel());
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
