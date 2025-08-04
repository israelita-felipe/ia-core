package com.ia.core.view.components;

import com.ia.core.view.components.properties.HasHelp;
import com.ia.core.view.components.properties.HasTranslator;
import com.ia.core.view.properties.AutoCastable;
import com.ia.core.view.properties.HasErrorHandle;

/**
 * Interface que define o comportamento padrão de uma view
 *
 * @author Israel Araújo
 * @param <T> Tipo do dado da view
 */
public interface IView<T>
  extends AutoCastable, HasHelp, HasTranslator, HasErrorHandle {
  /**
   * @return {@link IViewModel} desta view
   */
  IViewModel<T> getViewModel();
}
