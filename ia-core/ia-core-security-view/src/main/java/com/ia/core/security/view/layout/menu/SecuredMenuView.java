package com.ia.core.security.view.layout.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.ia.core.security.service.model.privilege.PrivilegeTranslator;
import com.ia.core.security.service.model.role.RoleTranslator;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.model.user.UserTranslator;
import com.ia.core.security.view.privilege.page.PrivilegePageView;
import com.ia.core.security.view.role.page.RolePageView;
import com.ia.core.security.view.user.UserManager.UserPasswordChangeSuportDTO;
import com.ia.core.security.view.user.form.UserPasswordChangeFormView;
import com.ia.core.security.view.user.page.UserPageView;
import com.ia.core.security.view.util.SecurityUtils;
import com.ia.core.view.components.IViewModel;
import com.ia.core.view.components.editor.formEditor.FormEditorView;
import com.ia.core.view.components.editor.viewModel.IEditorViewModel.EditorAction;
import com.ia.core.view.components.form.IFormView;
import com.ia.core.view.components.layout.menu.AbstractMenuLayoutView;
import com.ia.core.view.components.layout.menu.AbstractMenuLayoutViewModel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.dom.Style.TextAlign;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Israel Araújo
 */
@Slf4j
public abstract class SecuredMenuView
  extends AbstractMenuLayoutView {

  private VerticalLayout userInformationLayout;
  private VerticalLayout userDetailsLayout;
  private FlexLayout userActionsLayout;
  @Getter
  private SideNav grupoSeguranca;

  /**
   * @param viewModel
   */
  public SecuredMenuView(AbstractMenuLayoutViewModel viewModel) {
    super(viewModel);
  }

  @Override
  protected SideNavItem addItem(SideNav parent, VaadinIcon viewIcon,
                                String viewName,
                                Class<? extends Component> viewClass) {
    if (getViewModel().hasAccess(viewClass)) {
      return super.addItem(parent, viewIcon, viewName, viewClass);
    }
    return null;
  }

  /**
   * @param grupoSeguranca
   * @return
   */
  protected SideNavItem addMenuItemPrivilege(String title) {
    return addItem(grupoSeguranca, VaadinIcon.KEY, title,
                   getPrivilegeTarget());
  }

  /**
   * @param grupoSeguranca
   * @return
   */
  protected SideNavItem addMenuItemRole(String title) {
    return addItem(grupoSeguranca, VaadinIcon.DIPLOMA_SCROLL, title,
                   getRoleTarget());
  }

  /**
   * @param grupoSeguranca
   * @return
   */
  protected SideNavItem addMenuItemUser(String title) {
    return addItem(grupoSeguranca, VaadinIcon.USER, title, getUserTarget());
  }

  protected void bindMenuSecurity() {
    criarGrupoSeguranca($("Segurança"));
    addMenuItemUser($(UserTranslator.USER));
    addMenuItemRole($(RoleTranslator.ROLE));
    addMenuItemPrivilege($(PrivilegeTranslator.PRIVILEGE));
  }

  protected void bindUser() {
    UserDTO user = getViewModel().getAuthenticatedUser();
    createUserInformationLayout();
    crateUserPicture(user);
    createUsernameField(user.toString());
    createChangePasswordButton(() -> showChangePasswordDialog(user));
    createLogoutButton(() -> SecurityUtils.logout(UI.getCurrent()));
  }

  /**
   * @param user
   */
  private void crateUserPicture(UserDTO user) {
    Avatar avatar = new Avatar(user.getUserName());
    FlexLayout layout = new FlexLayout(avatar);
    layout.setWidthFull();
    layout.setJustifyContentMode(JustifyContentMode.CENTER);
    layout.setAlignItems(Alignment.CENTER);
    avatar.addThemeVariants(AvatarVariant.LUMO_XLARGE);
    this.userDetailsLayout.add(layout);
  }

  /**
   * @return
   */
  private Collection<EditorAction<UserPasswordChangeSuportDTO>> createChangePasswordActions(UserDTO authenticatedUser) {
    return Arrays
        .asList(createConfirmChangePasswordAction(authenticatedUser));
  }

  /**
   * @return
   */
  protected Component createChangePasswordButton(Runnable action) {
    Button button = new Button($("Alterar Senha"),
                               VaadinIcon.PASSWORD.create(), onClick -> {
                                 action.run();
                               });
    userActionsLayout.add(button);
    return button;
  }

  /**
   * @return
   */
  private EditorAction<UserPasswordChangeSuportDTO> createConfirmChangePasswordAction(UserDTO authenticatedUser) {
    return EditorAction.<UserPasswordChangeSuportDTO> builder()
        .label($("Confirmar")).icon(VaadinIcon.PASSWORD)
        .action(userPasswordChange -> {
          confirm(() -> {
            try {
              getViewModel().changePassword(authenticatedUser,
                                            userPasswordChange.copy());
              showSucessMessage($("Senha alterada com sucesso"));
            } catch (Exception e) {
              handleError(e);
            }
          }, $("Alterar senha"), $("Deseja realmente alterar a senha?"));
        }).build();
  }

  @Override
  public void createLayout() {
    super.createLayout();
    bindUser();
    bindMenuSecurity();
  }

  /**
   * @return
   */
  protected Component createLogoutButton(Runnable action) {
    Button button = new Button($("Sair"), VaadinIcon.SIGN_OUT.create(),
                               onClick -> {
                                 action.run();
                               });
    this.userActionsLayout.add(button);
    return button;
  }

  /**
   * @param layout
   */
  protected void createUserActionsLayout(VerticalLayout layout) {
    userActionsLayout = new FlexLayout();
    userActionsLayout.setFlexDirection(FlexDirection.COLUMN);
    userActionsLayout.setWidthFull();
    layout.add(userActionsLayout);
  }

  /**
   * @param layout
   */
  protected void createUserDetails(VerticalLayout layout) {
    userDetailsLayout = new VerticalLayout();
    layout.add(userDetailsLayout);
  }

  /**
   * @param authenticatedUser
   * @return
   */
  public VerticalLayout createUserInformationLayout() {
    userInformationLayout = new VerticalLayout();
    add(userInformationLayout);
    createUserDetails(userInformationLayout);
    createUserActionsLayout(userInformationLayout);
    return userInformationLayout;
  }

  /**
   * @param authenticatedUser
   * @return
   */
  protected Component createUsernameField(String userName) {
    Span span = new Span(userName);
    span.getStyle().setTextAlign(TextAlign.CENTER);
    span.setWidthFull();
    this.userDetailsLayout.add(span);
    return span;
  }

  /**
   * Cria o menu de administração
   *
   * @param seguranca {@link SegurancaServico}
   * @return
   */
  SideNav criarGrupoSeguranca(String titulo) {
    return grupoSeguranca = addGroup(titulo);
  }

  /**
   * @return
   */
  public Class<? extends Component> getPrivilegeTarget() {
    return PrivilegePageView.class;
  }

  /**
   * @return
   */
  public Class<? extends Component> getRoleTarget() {
    return RolePageView.class;
  }

  /**
   * @return
   */
  public Class<? extends Component> getUserTarget() {
    return UserPageView.class;
  }

  @Override
  public SecuredMenuViewModel getViewModel() {
    return (SecuredMenuViewModel) super.getViewModel();
  }

  public void showChangePasswordDialog(UserDTO user) {
    FormEditorView<UserPasswordChangeSuportDTO> editorView = new FormEditorView<>(getViewModel()
        .createChangePasswordViewModel(user)) {

      @Override
      public IFormView<UserPasswordChangeSuportDTO> createContentView(IViewModel<UserPasswordChangeSuportDTO> viewModel) {
        return new UserPasswordChangeFormView(viewModel.cast());
      }

      @Override
      public Collection<EditorAction<UserPasswordChangeSuportDTO>> createDefaultEditorActions() {
        Collection<EditorAction<UserPasswordChangeSuportDTO>> actions = new ArrayList<>(SecuredMenuView.this
            .createChangePasswordActions(user));
        actions.addAll(super.createDefaultEditorActions());
        return actions;
      }
    };
    editorView.show();
  }

  public abstract Class<? extends Component> getLoginPage();
}
