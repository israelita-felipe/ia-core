package com.ia.core.view.config;

import com.ia.core.view.exception.CoreErrorHandler;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.startup.VaadinAppShellInitializer;

/**
 * Configuração do APP.
 *
 * @author Israel Araújo
 */

public abstract class AppShellInitializer
  extends VaadinAppShellInitializer
  implements VaadinServiceInitListener {

  /**
   * Serial UID.
   */
  private static final long serialVersionUID = 9109031802358510895L;

  @Override
  public void serviceInit(ServiceInitEvent event) {
    event.getSource().addSessionInitListener(sessionEvent -> {
      sessionEvent.getSession().setErrorHandler(createErrorHandler());
    });

  }

  /**
   * @return {@link CoreErrorHandler}
   */
  @SuppressWarnings("serial")
  protected CoreErrorHandler createErrorHandler() {
    return new CoreErrorHandler() {
      @Override
      public void handleError(UI ui, Throwable exception)
        throws Throwable {
        AppShellInitializer.this.handleError(ui, exception);
      }
    };
  }

  /**
   * @param ui        {@link UI}
   * @param exception {@link Throwable}
   * @throws Throwable
   */
  protected abstract void handleError(UI ui, Throwable exception)
    throws Throwable;
}
