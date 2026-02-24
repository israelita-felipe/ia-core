package com.ia.core.view.components.dialog.information;

import com.ia.core.view.components.properties.HasTranslator;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;

/**
 * Fábrica para diálogos de confirmação
 *
 * @author Israel Araújo
 */
public class InformationDialogViewFactory
  implements HasTranslator {
  /** Instância singleton */
  private static InformationDialogViewFactory instance = null;

  /**
   * Captura o singleton desta classe
   *
   * @return {@link #instance}
   */
  private static InformationDialogViewFactory get() {
    if (instance == null) {
      instance = new InformationDialogViewFactory();
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
    get().showInfo(action, title, message);
  }

  public static void show(Runnable action, String title,
                          Component message) {
    get().showInfo(action, title, message);
  }

  public static void show(String title, Component message) {
    get().showInfo(title, message);
  }

  /**
   * Construtor privado
   */
  private InformationDialogViewFactory() {
  }

  /**
   * Cria um diálog de confirmação e abre o diálogo
   *
   * @param action  {@link Runnable} da ação
   * @param title   Título do diálogo
   * @param message Mensagem do diálogo
   */
  private void showInfo(Runnable action, String title, String message) {
    ConfirmDialog confirm = new ConfirmDialog(title, message, $("Ok"),
                                              click -> action.run());
    confirm.open();
  }

  private void showInfo(Runnable action, String title, Component message) {
    ConfirmDialog confirm = new ConfirmDialog();
    confirm.setHeader(title);
    confirm.setText(message);
    confirm.setConfirmButton("Ok", evt -> {
      action.run();
      confirm.close();
    });
    confirm.open();
  }

  private void showInfo(String title, Component message) {
    ConfirmDialog confirm = new ConfirmDialog();
    confirm.setHeader(title);
    confirm.setText(message);
    confirm.setConfirmButton("Ok", evt -> confirm.close());
    confirm.open();
  }
}
