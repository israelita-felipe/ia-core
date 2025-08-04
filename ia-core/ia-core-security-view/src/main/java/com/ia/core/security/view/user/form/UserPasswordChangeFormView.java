package com.ia.core.security.view.user.form;

import com.ia.core.security.view.user.UserService.UserPasswordChangeSuportDTO;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.component.textfield.PasswordField;

/**
 * Formulário para troca de senha
 *
 * @author Israel Araújo
 */
public class UserPasswordChangeFormView
  extends FormView<UserPasswordChangeSuportDTO> {
  /** Serial UID */
  private static final long serialVersionUID = -7809249564741837547L;

  /**
   * @param viewModel view model do formulário
   */
  public UserPasswordChangeFormView(IFormViewModel<UserPasswordChangeSuportDTO> viewModel) {
    super(viewModel);
  }

  /**
   * Cria o campo de confirmação de senha
   *
   * @param label título
   * @param help  texto de ajuda
   * @return {@link PasswordField}
   */
  public PasswordField createConfirmPasswordField(String label,
                                                  String help) {
    PasswordField field = createPasswordField(label, help);
    add(field, 6);
    return field;
  }

  /**
   * Cria o campo de senha atual
   *
   * @param label título
   * @param help  texto de ajuda
   * @return {@link PasswordField}
   */
  public PasswordField createCurrentPasswordField(String label,
                                                  String help) {
    PasswordField field = createPasswordField(label, help);
    add(field, 6);
    return field;
  }

  @Override
  public void createLayout() {
    super.createLayout();
    bind("oldPassword",
         createCurrentPasswordField($("Senha atual"), $("Senha atual")));
    bind("newPassword",
         createNewPasswordField($("Senha nova"), $("Senha nova")));
    bind("confirmPassword",
         createConfirmPasswordField($("Repita a nova senha"),
                                    $("Confirmação de nova senha")));
  }

  /**
   * Cria o campo de nova senha
   *
   * @param label título
   * @param help  texto de ajuda
   * @return {@link PasswordField}
   */
  public PasswordField createNewPasswordField(String label, String help) {
    PasswordField field = createPasswordField(label, help);
    add(field, 6);
    return field;
  }
}
