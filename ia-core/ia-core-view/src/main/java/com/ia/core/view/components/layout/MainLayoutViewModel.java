package com.ia.core.view.components.layout;

import com.ia.core.view.components.layout.menu.AbstractMenuLayoutViewModel;
import com.ia.core.view.components.properties.HasTranslator;
import com.ia.core.view.properties.HasErrorHandle;

import lombok.Getter;

/**
 * Abstração do view model para um layout
 *
 * @author Israel Araújo
 */

public abstract class MainLayoutViewModel
  implements HasErrorHandle, HasTranslator {
  /** Menu do layout */
  @Getter
  private final AbstractMenuLayoutViewModel menuViewModel;

  /**
   * @param menuViewModel menu do layout
   */
  public MainLayoutViewModel(AbstractMenuLayoutViewModel menuViewModel) {
    super();
    this.menuViewModel = menuViewModel;
  }

}
