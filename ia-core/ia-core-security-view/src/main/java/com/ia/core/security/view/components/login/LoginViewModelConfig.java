package com.ia.core.security.view.components.login;

import com.ia.core.security.model.authentication.AuthenticationRequest;
import com.ia.core.security.view.authentication.AuthenticationDetails;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import lombok.Getter;

/**
 *
 */
/**
 * Classe de configuração para login view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a LoginViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class LoginViewModelConfig
  extends FormViewModelConfig<AuthenticationRequest> {

  @Getter
  private final AuthenticationDetails details;

  /**
   * @param readOnly
   * @param details
   */
  public LoginViewModelConfig(AuthenticationDetails details) {
    super(false);
    this.details = details;
  }

}
