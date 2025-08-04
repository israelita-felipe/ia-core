package com.ia.core.view.components;

import com.ia.core.view.properties.AutoCastable;

/**
 * Classe que representa um ViewModel de algum objeto
 *
 * @author Israel Araújo
 * @param <T> Tipo do objeto
 */
public interface IViewModel<T>
  extends AutoCastable {

  /**
   * @return <code>true</code> se for somente leitura
   */
  boolean isReadOnly();

  /**
   * Atribui o status de somente leitura
   *
   * @param readOnly indicativo de somente leitura
   */
  void setReadOnly(boolean readOnly);
}
