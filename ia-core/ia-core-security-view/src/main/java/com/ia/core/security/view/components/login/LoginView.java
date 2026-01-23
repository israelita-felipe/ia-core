package com.ia.core.security.view.components.login;

import com.ia.core.security.model.authentication.AuthenticationRequest;
import com.ia.core.security.service.model.login.LoginTranslator;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.IFormView;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginI18n.ErrorMessage;
import com.vaadin.flow.component.login.LoginI18n.Form;
import com.vaadin.flow.component.login.LoginI18n.Header;
import com.vaadin.flow.dom.Style.AlignItems;
import com.vaadin.flow.dom.Style.Display;
import com.vaadin.flow.dom.Style.JustifyContent;
import com.vaadin.flow.dom.Style.TextAlign;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;

/**
 * @author Israel Araújo
 */

public class LoginView
  extends FormView<AuthenticationRequest>
  implements IFormView<AuthenticationRequest>, BeforeEnterObserver,
  ComponentEventListener<AbstractLogin.LoginEvent> {

  public static final String ROUTE = "login";

  private LoginForm login;

  public LoginView(LoginViewModel viewModel) {
    super(viewModel);
    addClassName("login-view");
    setSizeFull();
    getStyle().setAlignItems(AlignItems.CENTER);
    getStyle().setJustifyContent(JustifyContent.CENTER);
    getStyle().setDisplay(Display.FLEX);
    getViewModel().setModel(AuthenticationRequest.builder().build());
    bindForm();
  }

  @Override
  public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
    if (beforeEnterEvent.getLocation().getQueryParameters().getParameters()
        .containsKey("error")) {
      onFail();
    }
  }

  /**
   * @param cast
   */
  private void bindForm() {
    createLoginForm(getLoginI18n());
  }

  @Override
  protected FormLayout createFormLayout() {
    FormLayout formLayout = super.createFormLayout();
    formLayout.getStyle().setDisplay(Display.FLEX);
    formLayout.getStyle().setAlignItems(AlignItems.CENTER);
    formLayout.getStyle().setJustifyContent(JustifyContent.CENTER);
    return formLayout;
  }

  protected LoginForm createLoginForm(LoginI18n loginI18n) {
    this.login = new LoginForm();
    this.login.setI18n(loginI18n);
    this.login.getStyle().setDisplay(Display.FLEX);
    this.login.getStyle().setAlignItems(AlignItems.CENTER);
    this.login.getStyle().setJustifyContent(JustifyContent.CENTER);
    this.login.addLoginListener(this);
    this.login.setForgotPasswordButtonVisible(false);
    add(this.login, 6);
    return this.login;
  }

  protected H1 createTitulo(String titulo) {
    H1 component = new H1(titulo);
    component.getStyle().setTextAlign(TextAlign.CENTER);
    add(component, 1);
    return component;
  }

  private LoginI18n getLoginI18n() {
    LoginI18n loginI18n = LoginI18n.createDefault();
    if (getViewModel().isFirstLogin()) {
      loginI18n
          .setAdditionalInformation("Primeiro login! Anote o usuário e senha utilizados, pois estes serão utilizados para login de administração");
    } else {
      loginI18n
          .setAdditionalInformation($(LoginTranslator.ADDITIONAL_INFORMATION));
    }
    ErrorMessage errorMessage = new ErrorMessage();
    errorMessage.setMessage($(LoginTranslator.ERROR_MESSAGE));
    errorMessage.setPassword($(LoginTranslator.ERROR_WRONG_PASSWORD));
    errorMessage.setUsername($(LoginTranslator.ERROR_WRONG_USERNAME));
    errorMessage.setTitle($(LoginTranslator.ERROR_TITLE));
    loginI18n.setErrorMessage(errorMessage);
    Header header = new Header();
    header.setTitle($(LoginTranslator.HEADER_TITLE));
    header.setDescription($(LoginTranslator.HEADER_DESCRIPTION));
    loginI18n.setHeader(header);
    Form form = new Form();
    form.setTitle($(LoginTranslator.FORM_TITLE));
    form.setForgotPassword($(LoginTranslator.FORM_FORGOT_PASSWORD));
    form.setSubmit($(LoginTranslator.FORM_SUBMIT));
    form.setPassword($(LoginTranslator.FORM_PASSWORD));
    form.setUsername($(LoginTranslator.FORM_USERNAME));
    loginI18n.setForm(form);
    return loginI18n;
  }

  @Override
  public LoginViewModel getViewModel() {
    return (LoginViewModel) super.getViewModel();
  }

  @Override
  public void onComponentEvent(AbstractLogin.LoginEvent loginEvent) {
    LoginViewModel viewModel = getViewModel();
    AuthenticationRequest model = viewModel.getModel();
    model.setCodUsuario(loginEvent.getUsername());
    model.setSenha(loginEvent.getPassword());
    if (viewModel.isFirstLogin()) {
      viewModel.createFirstUser(this::onSuccess, this::onFail);
    } else {
      viewModel.login(this::onSuccess, this::onFail);
    }
  }

  /**
   *
   */
  protected void onFail() {
    setError(true);
  }

  /**
   *
   */
  protected void onSuccess(UserDTO user) {
    UI.getCurrent().navigate("/");
  }

  protected void setError(boolean value) {
    this.login.setError(value);
  }
}
