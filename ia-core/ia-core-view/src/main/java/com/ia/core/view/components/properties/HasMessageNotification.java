package com.ia.core.view.components.properties;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;

/**
 * Propriedade de notificar por meio de mensagem
 *
 * @author Israel Araújo
 */
public interface HasMessageNotification {
  /**
   * Exibe mensagem de erro
   *
   * @param message Mensagem a ser exibida
   */
  default void showErrorMessage(String message) {
    showMessage(NotificationVariant.LUMO_ERROR, message);
  }

  /**
   * Exibe uma mensagem
   *
   * @param variant {@link NotificationVariant} da mensagem
   * @param message Texto da mensagem
   */
  default void showMessage(NotificationVariant variant, String message) {
    Notification notification = new Notification(message, 3000,
                                                 Position.BOTTOM_END);
    notification.addThemeVariants(variant);
    notification.open();
  }

  /**
   * Exibe mensagem padrão
   *
   * @param message Mensagem a ser exibida
   */
  default void showMessage(String message) {
    showMessage(NotificationVariant.LUMO_CONTRAST, message);
  }

  /**
   * Exibe mensagem primária
   *
   * @param message Mensagem a ser exibida
   */
  default void showPrimaryMessage(String message) {
    showMessage(NotificationVariant.LUMO_PRIMARY, message);
  }

  /**
   * Exibe mensagem de sucesso
   *
   * @param message Mensagem a ser exibida
   */
  default void showSucessMessage(String message) {
    showMessage(NotificationVariant.LUMO_SUCCESS, message);
  }

  /**
   * Exibe mensagem de aviso
   *
   * @param message Mensagem a ser exibida
   */
  default void showWarnMessage(String message) {
    showMessage(NotificationVariant.LUMO_WARNING, message);
  }
}
