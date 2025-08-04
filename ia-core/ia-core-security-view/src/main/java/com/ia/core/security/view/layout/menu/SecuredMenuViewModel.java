package com.ia.core.security.view.layout.menu;

import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.view.authentication.AuthenticationDetails;
import com.ia.core.security.view.authorization.CoreSecurityViewAuthorizationManager;
import com.ia.core.security.view.user.UserService;
import com.ia.core.security.view.user.UserService.UserPasswordChangeSuportDTO;
import com.ia.core.view.components.IViewModel;
import com.ia.core.view.components.editor.formEditor.viewModel.FormEditorViewModel;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import com.ia.core.view.components.layout.menu.AbstractMenuLayoutViewModel;

/**
 * @author Israel Araújo
 */

public class SecuredMenuViewModel
  extends AbstractMenuLayoutViewModel {

  private final AuthenticationDetails authenticationDetails;
  private final CoreSecurityViewAuthorizationManager authorizationManager;
  private final UserService userService;

  /**
   * @param authorizationManager
   * @param authenticationDetails
   * @param userService
   */
  public SecuredMenuViewModel(CoreSecurityViewAuthorizationManager authorizationManager,
                              AuthenticationDetails authenticationDetails,
                              UserService userService) {
    super();
    this.authorizationManager = authorizationManager;
    this.authenticationDetails = authenticationDetails;
    this.userService = userService;
  }

  /**
   * @param authenticatedUser
   * @param copy
   */
  public void changePassword(UserDTO authenticatedUser,
                             UserPasswordChangeSuportDTO copy) {
    userService.changePassword(authenticatedUser, copy);
  }

  /**
   * @param authenticatedUser
   * @return
   */
  protected FormEditorViewModel<UserPasswordChangeSuportDTO> createChangePasswordViewModel(UserDTO authenticatedUser) {
    return new FormEditorViewModel<>() {

      @Override
      protected IViewModel<UserPasswordChangeSuportDTO> createContentViewModel() {
        FormViewModel<UserPasswordChangeSuportDTO> formViewModel = new FormViewModel<>(false) {
        };
        formViewModel
            .setModel(UserPasswordChangeSuportDTO.builder().build());
        return formViewModel;
      }

    };
  }

  /**
   * @return
   */
  public UserDTO getAuthenticatedUser() {
    return authenticationDetails.getAuthenticatedUser();
  }

  public boolean hasAccess(Class<? extends com.vaadin.flow.component.Component> target) {
    return authorizationManager
        .check(target, authenticationDetails.isAuthenticated());
  }

}
