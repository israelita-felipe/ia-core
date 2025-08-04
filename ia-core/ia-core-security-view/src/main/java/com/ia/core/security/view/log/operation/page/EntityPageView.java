package com.ia.core.security.view.log.operation.page;

import java.io.Serializable;
import java.util.Collection;

import com.ia.core.security.view.log.operation.list.LogOperationListView;
import com.ia.core.security.view.log.operation.list.LogOperationListViewModel;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.view.components.dialog.DialogHeaderBar;
import com.ia.core.view.components.page.PageView;
import com.ia.core.view.components.page.viewModel.IPageViewModel;
import com.ia.core.view.components.page.viewModel.IPageViewModel.PageAction;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.VaadinIcon;

/**
 * Página de entidade
 *
 * @author Israel Araújo
 * @param <T> Tipo da entidade
 */
public abstract class EntityPageView<T extends AbstractBaseEntityDTO<? extends Serializable>>
  extends PageView<T>
  implements HasViewHistoryAction<T> {
  /** Serial UID */
  private static final long serialVersionUID = -2144402990363908880L;

  /**
   * @param viewModel View Model da página
   */
  public EntityPageView(IPageViewModel<T> viewModel) {
    super(viewModel);
  }

  /**
   * Se o histórico pode ser visto
   *
   * @param object Objeto a ser visualizado o histórico.
   * @return por padrão retorna <code>true</code> se o objeto não for
   *         <code>null</code>
   */
  protected boolean canViewHistory(T object) {
    return object != null;
  }

  @Override
  public Collection<PageAction<T>> createDefaultPageActions() {
    Collection<PageAction<T>> defaultPageActions = super.createDefaultPageActions();
    if (isHistoryVisible()) {
      defaultPageActions.add(createHistoryPageAction());
    }
    return defaultPageActions;
  }

  /**
   * @return {@link PageAction} para visualização do histórico
   */
  protected PageAction<T> createHistoryPageAction() {
    return PageAction.<T> builder().icon(VaadinIcon.USER_CLOCK)
        .enableFunction(this::canViewHistory).action(this::viewHistory)
        .build();
  }

  /**
   * Visualizar o histórico
   */
  protected void viewHistory() {
    T object = this.getListView().getSelectedItem();
    viewHistory(object);
  }

  @Override
  public void viewHistoryAction(T object) {
    try {
      if (canViewHistory(object)) {
        Dialog dialog = new Dialog();
        DialogHeaderBar.addTo(dialog);
        dialog.getFooter().add(new Button($("Fechar"), onClick -> {
          dialog.close();
        }));
        LogOperationListViewModel logOperationListViewModel = getViewModel()
            .viewHistory(object);
        dialog.add(new LogOperationListView(logOperationListViewModel));
        dialog
            .setHeaderTitle($(getViewModel().getType().getCanonicalName()));
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

  @Override
  public EntityPageViewModel<T> getViewModel() {
    return super.getViewModel().cast();
  }

  /**
   * Indicativo de visibilidade do botão de histórico.
   *
   * @return <code>true</code> por padrão
   */
  public boolean isHistoryVisible() {
    return true;
  }
}
