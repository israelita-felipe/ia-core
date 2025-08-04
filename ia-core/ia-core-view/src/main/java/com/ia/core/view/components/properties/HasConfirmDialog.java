package com.ia.core.view.components.properties;

import com.ia.core.view.components.dialog.confirm.ConfirmDialogViewFactory;

/**
 * Propriedade de confirmação via diálogo
 *
 * @author Israel Araújo
 */
public interface HasConfirmDialog {
  /**
   * Exibe o diálogo de confirmação
   *
   * @param action  {@link Runnable} com a ação a ser executada
   * @param title   Título da mensagem
   * @param message Texto da mensagem
   */
  default void confirm(Runnable action, String title, String message) {
    ConfirmDialogViewFactory.show(action, title, message);
  }
}
