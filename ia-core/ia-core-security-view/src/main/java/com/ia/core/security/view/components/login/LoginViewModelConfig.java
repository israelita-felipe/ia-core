package com.ia.core.security.view.components.login;

import com.ia.core.security.model.authentication.AuthenticationRequest;
import com.ia.core.security.view.authentication.AuthenticationDetails;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;

import lombok.Getter;

/**
 *
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
