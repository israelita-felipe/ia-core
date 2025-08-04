package com.ia.core.view.components.editor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.ia.core.view.components.IView;
import com.ia.core.view.components.dialog.DialogHeaderBar;
import com.ia.core.view.components.dialog.exception.ExceptionViewFactory;
import com.ia.core.view.components.editor.viewModel.IEditorViewModel;
import com.ia.core.view.components.editor.viewModel.IEditorViewModel.EditorAction;
import com.ia.core.view.properties.HasCancelAction;
import com.ia.core.view.utils.Size;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.VaadinIcon;

import lombok.Getter;

/**
 * Implementação padrão de um editor
 *
 * @author Israel Araújo
 * @param <T> Tipo de dado do editor
 * @param <V> Tipo do conteúdo do editor
 */
public abstract class EditorView<T extends Serializable, V extends IView<T>>
  extends Dialog
  implements IEditorView<T, V>, HasCancelAction<T> {
  /** Serial UID */
  private static final long serialVersionUID = -5171343260346422988L;
  /** View Model do editor */
  @Getter
  private IEditorViewModel<T> viewModel;
  /** Mapa de botões do editor */
  private Map<EditorAction<T>, Button> editorButtons = new HashMap<>();

  /**
   * Contrutor padrão
   *
   * @param viewModel {@link IEditorViewModel}
   */
  public EditorView(IEditorViewModel<T> viewModel) {
    setId(createId());
    this.viewModel = viewModel;
    createLayout();
    refreshButtons();
  }

  @Override
  public void addAction(EditorAction<T> action) {
    Button button = createActionButton(action);
    editorButtons.put(action, button);
    getFooter().add(button);
  }

  @Override
  public void addActions(Collection<EditorAction<T>> actions) {
    actions.forEach(this::addAction);
  }

  @Override
  public void cancelAction(T item) {
    close();
  }

  /**
   * Cria o botão de ação de uma {@link EditorAction}
   *
   * @param action ação a ser executada
   * @return {@link Button}
   */
  public Button createActionButton(EditorAction<T> action) {
    return new Button(action.getLabel(), action.getIcon().create(),
                      click -> {
                        viewModel.executeEditorAction(action);
                      });
  }

  /**
   * @return {@link EditorAction}
   */
  public EditorAction<T> createCancelAction() {
    return EditorAction.<T> builder().label($("Cancelar"))
        .icon(VaadinIcon.CLOSE).action(this::cancelAction).build();
  }

  @Override
  public Collection<EditorAction<T>> createDefaultEditorActions() {
    return Arrays.asList(createCancelAction());
  }

  /**
   * Cria o layout da aplicação
   */
  protected void createLayout() {
    setCloseOnEsc(true);
    setCloseOnOutsideClick(false);
    setResizable(true);
    setDraggable(true);
    DialogHeaderBar.addTo(this);
    add(createContentView(viewModel.getContentViewModel())
        .cast(Component.class));
    addActions(createDefaultEditorActions());
  }

  @Override
  public Locale getLocale() {
    return super.getLocale();
  }

  @Override
  public void handleError(Exception ex) {
    IEditorView.super.handleError(ex);
    ExceptionViewFactory.showError(ex);
  }

  @Override
  public boolean refreshAction(EditorAction<T> action) {
    return action.getEnableFunction().apply(getViewModel().getModel());
  }

  /**
   * Atualiza o botão em relação a ação
   *
   * @param action {@link EditorAction}
   * @param button {@link Button}
   */
  public void refreshButton(EditorAction<T> action, Button button) {
    boolean status = refreshAction(action);
    button.setEnabled(status);
  }

  @Override
  public void refreshButtons() {
    editorButtons.entrySet().forEach(entry -> {
      refreshButton(entry.getKey(), entry.getValue());
    });
  }

  @Override
  public void setSize(Size width, Size height) {
    setWidth(width.getSize(), width.getUnit());
    setHeight(height.getSize(), height.getUnit());
  }

  @Override
  public void show() {
    open();
  }
}
