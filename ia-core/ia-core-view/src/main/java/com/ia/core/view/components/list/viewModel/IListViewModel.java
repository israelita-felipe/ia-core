package com.ia.core.view.components.list.viewModel;

import java.io.Serializable;

import com.ia.core.service.dto.properties.HasPropertyChangeSupport;
import com.ia.core.view.components.IViewModel;
import com.ia.core.view.components.properties.HasTranslator;

/**
 * Interface que define o contrato de um View Model para lista
 *
 * @author Israel Araújo
 * @param <T> Tipo do dado da lista
 */
public interface IListViewModel<T extends Serializable>
  extends HasTranslator, IViewModel<T>, HasPropertyChangeSupport {
  /**
   * Tipo do dado
   *
   * @return {@link Class} do dado manipulado
   */
  Class<T> getType();
}
