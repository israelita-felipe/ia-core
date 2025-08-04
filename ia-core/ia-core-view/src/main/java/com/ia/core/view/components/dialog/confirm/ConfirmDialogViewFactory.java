package com.ia.core.view.components.dialog.confirm;

import com.ia.core.view.components.properties.HasTranslator;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;

/**
 * Fábrica para diálogos de confirmação
 *
 * @author Israel Araújo
 */
public class ConfirmDialogViewFactory
  implements HasTranslator {
  /** Instância singleton */
  private static ConfirmDialogViewFactory instance = null;

  /**
   * Captura o singleton desta classe
   *
   * @return {@link #instance}
   */
  private static ConfirmDialogViewFactory get() {
    if (instance == null) {
      instance = new ConfirmDialogViewFactory();
    }
    return instance;
  }

  /**
   * Exibe o diálogo de confirmação
   *
   * @param action  {@link Runnable} da ação
   * @param title   Título da ação
   * @param message Mensagem da ação
   */
  public static void show(Runnable action, String title, String message) {
    get().showConfirm(action, title, message);
  }

  /**
   * Construtor privado
   */
  private ConfirmDialogViewFactory() {
  }

  /**
   * Cria um diálog de confirmação e abre o diálogo
   *
   * @param action  {@link Runnable} da ação
   * @param title   Título do diálogo
   * @param message Mensagem do diálogo
   */
  private void showConfirm(Runnable action, String title, String message) {
    ConfirmDialog confirm = new ConfirmDialog(title, message, $("Sim"),
                                              click -> action.run(),
                                              $("Não"), click -> {
                                              });
    confirm.open();
  }
}
