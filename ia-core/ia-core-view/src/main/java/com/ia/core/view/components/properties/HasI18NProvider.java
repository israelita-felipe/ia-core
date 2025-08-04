package com.ia.core.view.components.properties;

import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.server.VaadinService;

/**
 * Possui i18n provider, geralmente utilizado para tradução de mensagens
 *
 * @author Israel Araújo
 */
public interface HasI18NProvider {

  /**
   * @return {@link I18NProvider}
   */
  default I18NProvider getI18NProvider() {
    return VaadinService.getCurrent().getInstantiator().getI18NProvider();
  }
}
