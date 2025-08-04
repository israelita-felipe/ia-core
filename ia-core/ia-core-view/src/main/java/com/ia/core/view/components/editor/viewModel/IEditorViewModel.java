package com.ia.core.view.components.editor.viewModel;

import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Function;

import com.ia.core.view.components.IViewModel;
import com.ia.core.view.components.properties.HasTranslator;
import com.ia.core.view.properties.HasModel;
import com.vaadin.flow.component.icon.VaadinIcon;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;

/**
 * Interface que define o contrato de comportamento de um {@link IViewModel} de
 * um editor
 *
 * @author Israel Araújo
 * @param <T> Tipo de dado do editor
 */
public interface IEditorViewModel<T extends Serializable>
  extends HasTranslator, HasModel<T>, IViewModel<T>, Serializable {

  /**
   * Ações do editor
   *
   * @param <T> Tipo de dado do editor
   */
  @Builder
  @Getter
  public static class EditorAction<T> {
    /** Ícone */
    private VaadinIcon icon;
    /** Título */
    private String label;
    /** Ação sobre o objeto */
    private Consumer<T> action;
    /** Função de habilitação */
    @Default
    private Function<T, Boolean> enableFunction = enableFunction -> true;
  }

  /**
   * Executa uma ação do editor
   *
   * @param action {@link EditorAction} a ser executada
   */
  default void executeEditorAction(EditorAction<T> action) {
    action.getAction().accept(getModel());
  }

  /**
   * @return {@link IViewModel} do conteúdo do editor
   */
  IViewModel<T> getContentViewModel();

  @Override
  default void setReadOnly(boolean readOnly) {
    getContentViewModel().setReadOnly(readOnly);
  }
}
