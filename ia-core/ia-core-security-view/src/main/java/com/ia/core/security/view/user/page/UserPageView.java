package com.ia.core.security.view.user.page;

import java.util.ArrayList;
import java.util.Collection;

import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.view.log.operation.list.AuditOperationListView;
import com.ia.core.security.view.log.operation.list.AuditOperationListViewModel;
import com.ia.core.security.view.log.operation.page.EntityPageView;
import com.ia.core.security.view.user.form.UserFormView;
import com.ia.core.security.view.user.list.UserListView;
import com.ia.core.view.components.dialog.DialogHeaderBar;
import com.ia.core.view.components.form.IFormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.IListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.page.viewModel.IPageViewModel;
import com.ia.core.view.components.page.viewModel.IPageViewModel.PageAction;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.VaadinIcon;

/**
 * @author Israel Araújo
 */
public class UserPageView
  extends EntityPageView<UserDTO> {
  /** Serial UID */
  private static final long serialVersionUID = -3947821037852296568L;
  /** Rotas de usuário */
  public static final String ROUTE = "user";

  /**
   * @param viewModel view model da página
   */
  public UserPageView(IPageViewModel<UserDTO> viewModel) {
    super(viewModel);
  }

  @Override
  public Collection<PageAction<UserDTO>> createDefaultPageActions() {
    Collection<PageAction<UserDTO>> defaultPageActions = new ArrayList<>(super.createDefaultPageActions());
    defaultPageActions.add(createResetPasswordAction());
    if (isAuditonVisible()) {
      defaultPageActions.add(createAuditPageAction());
    }
    return defaultPageActions;
  }

  @Override
  public IFormView<UserDTO> createFormView(IFormViewModel<UserDTO> formViewModel) {
    return new UserFormView(formViewModel);
  }

  @Override
  public IListView<UserDTO> createListView(IListViewModel<UserDTO> listViewModel) {
    return new UserListView(listViewModel);
  }

  /**
   * @return {@link PageAction} para resetar a senha do usuário.
   */
  public PageAction<UserDTO> createResetPasswordAction() {
    return PageAction.<UserDTO> builder()
        .enableFunction(object -> object != null
            && !getViewModel().isReadOnly())
        .icon(VaadinIcon.PASSWORD).action(() -> {
          confirm(() -> {
            UserDTO user = getListView().getSelectedItem();
            if (user != null) {
              getViewModel().resetPassword(user);
            }
            showSucessMessage($("Senha resetada com sucesso"));
          }, $("Resetar senha"),
                  $("Deseja realmente resetar a senha do usuário?"));
        }).build();
  }

  @Override
  public UserPageViewModel getViewModel() {
    return (UserPageViewModel) super.getViewModel();
  }

  /**
   * Se a auditoria pode ser visto
   *
   * @param object Objeto a ser visualizado a auditoria.
   * @return por padrão retorna <code>true</code> se o objeto não for
   *         <code>null</code>
   */
  protected boolean canViewAudit(UserDTO object) {
    return object != null;
  }

  /**
   * @return {@link PageAction} para visualização da audição
   */
  protected PageAction<UserDTO> createAuditPageAction() {
    return PageAction.<UserDTO> builder().icon(VaadinIcon.USER_CLOCK)
        .enableFunction(this::canViewAudit).action(this::viewAudit).build();
  }

  /**
   * Visualizar a audição
   */
  protected void viewAudit() {
    UserDTO object = this.getListView().getSelectedItem();
    viewAudit(object);
  }

  public void viewAuditAction(UserDTO object) {
    try {
      if (canViewAudit(object)) {
        Dialog dialog = new Dialog();
        DialogHeaderBar.addTo(dialog);
        dialog.getFooter().add(new Button($("Fechar"), onClick -> {
          dialog.close();
        }));
        AuditOperationListViewModel logOperationListViewModel = getViewModel()
            .viewAudit(object);
        dialog.add(new AuditOperationListView(logOperationListViewModel));
        dialog.setHeaderTitle($("Audição"));
        var width = getEditorWidth();
        dialog.setWidth(width.getSize(), width.getUnit());
        var height = getEditorHeight();
        dialog.setHeight(height.getSize(), width.getUnit());
        dialog.open();
      }
    } catch (Exception e) {
      handleError(e);
    }
  }

  /**
   * Indicativo de visibilidade do botão de audição.
   *
   * @return <code>true</code> por padrão
   */
  public boolean isAuditonVisible() {
    return true;
  }

  /**
   * Após visualizar a auditoria.
   *
   * @param item Item a visualizar a audição.
   */
  public void afterViewAudit(UserDTO item) {

  }

  /**
   * Antes de visualizar a audição.
   *
   * @param item Item a ser visualizado a audição.
   */
  public void beforeViewAudit(UserDTO item) {

  }

  /**
   * Ação de visualizar histórico
   *
   * @param item item a ser visualizado a audição
   */
  public void viewAudit(UserDTO item) {
    beforeViewHistory(item);
    viewAuditAction(item);
    afterViewHistory(item);
  }

}
