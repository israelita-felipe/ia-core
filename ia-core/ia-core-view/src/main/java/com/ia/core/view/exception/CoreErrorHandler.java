package com.ia.core.view.exception;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.ErrorHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * Capturador de erros genéricos da view
 */
@Slf4j
public abstract class CoreErrorHandler
  implements ErrorHandler {
  /** Serial UID */
  private static final long serialVersionUID = 3665080427109738331L;

  @Override
  public void error(ErrorEvent event) {
    try {
      handleError(UI.getCurrent(), event.getThrowable());
    } catch (Throwable e) {
      log.error(e.getLocalizedMessage(), e);
    }
  }

  /**
   * @param ui
   * @param exception
   * @throws Throwable
   */
  public abstract void handleError(UI ui, Throwable exception)
    throws Throwable;

}
