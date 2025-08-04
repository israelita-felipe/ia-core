package com.ia.core.view.components.properties;

import java.util.List;
import java.util.Locale;

import com.vaadin.flow.component.UI;

/**
 * Possui local
 *
 * @author Israel Araújo
 */
public interface HasLocale
  extends HasI18NProvider {

  /**
   * @return {@link Locale}
   */

  default Locale getLocale() {
    UI currentUi = UI.getCurrent();
    Locale locale = currentUi == null ? null : currentUi.getLocale();
    if (locale == null) {
      List<Locale> locales = getI18NProvider().getProvidedLocales();
      if (locales != null && !locales.isEmpty()) {
        locale = locales.get(0);
      } else {
        locale = Locale.getDefault();
      }
    }
    return locale;
  }

}
