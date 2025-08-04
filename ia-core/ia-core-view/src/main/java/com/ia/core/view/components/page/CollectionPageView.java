package com.ia.core.view.components.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.ia.core.view.components.page.viewModel.ICollectionPageViewModel;
import com.ia.core.view.components.page.viewModel.IPageViewModel.PageAction;
import com.vaadin.flow.component.icon.VaadinIcon;

/**
 * Implementação padrão de uma página para manipular coleções em memória
 *
 * @author Israel Araújo
 * @param <T> Tipo do objeto da página
 */
public abstract class CollectionPageView<T extends Serializable>
  extends PageView<T>
  implements ICollectionPageView<T> {
  /** Serial UID */
  private static final long serialVersionUID = 9208454918604566822L;

  /**
   * Construtor padrão
   *
   * @param viewModel View model desta página
   */
  public CollectionPageView(ICollectionPageViewModel<T> viewModel) {
    super(viewModel);
  }

  @Override
  protected void createLayout() {
    super.createLayout();
    setPadding(false);
    setMargin(false);
    setSpacing(false);
  }

  /**
   * @param object Objeto a ser descido
   * @return se o objeto pode ser descido
   */
  protected boolean canDown(T object) {
    return getViewModel().canDown(object);
  }

  /**
   * @param object Objeto a ser subido
   * @return se o objeto pode ser subido
   */
  protected boolean canUp(T object) {
    return getViewModel().canUp(object);
  }

  @Override
  public Collection<PageAction<T>> createDefaultPageActions() {
    Collection<PageAction<T>> defaultPageActions = new ArrayList<>(super.createDefaultPageActions());
    if (isUpButtonVisible()) {
      defaultPageActions.add(createUpAction());
    }
    if (isDownButtonVisible()) {
      defaultPageActions.add(createDownAction());
    }
    return defaultPageActions;
  }

  /**
   * @return {@link PageAction} para baixar um objeto em uma lista
   */
  public PageAction<T> createDownAction() {
    return PageAction.<T> builder().icon(VaadinIcon.ARROW_DOWN)
        .enableFunction(this::canDown).action(this::down).build();
  }

  /**
   * @return {@link PageAction} para subir um objeto
   */
  public PageAction<T> createUpAction() {
    return PageAction.<T> builder().icon(VaadinIcon.ARROW_UP)
        .enableFunction(this::canUp).action(this::up).build();
  }

  /**
   * Baixa um objeto em uma lista
   */
  protected void down() {
    T selected = getViewModel().getSelected();
    downAction(selected);
  }

  /**
   * Desce um objeto na lista
   *
   * @param selected Objeto selecionado
   */
  @Override
  public void downAction(T selected) {
    if (selected != null) {
      getViewModel().down(selected, (object, index) -> {
        getListView().deselectAll();
        getListView().refreshAll();
        getListView().select(selected);
      });
    }
  }

  @Override
  public ICollectionPageViewModel<T> getViewModel() {
    return super.getViewModel().cast();
  }

  @Override
  public boolean isDownButtonVisible() {
    return !getViewModel().isReadOnly();
  }

  @Override
  public boolean isUpButtonVisible() {
    return !getViewModel().isReadOnly();
  }

  /**
   * Ação de subir um item
   */
  protected void up() {
    T selected = getViewModel().getSelected();
    upAction(selected);
  }

  @Override
  public void upAction(T selected) {
    if (selected != null) {
      getViewModel().up(selected, (object, index) -> {
        getListView().deselectAll();
        getListView().refreshAll();
        getListView().select(selected);
      });
    }
  }
}
