package com.ia.core.view.properties;

/**
 * Interface que atribui a capacidade de cast sobre qualquer objeto
 *
 * @author Israel Araújo
 */
public interface AutoCastable {

  /**
   * Realiza cast dinâmico
   *
   * @param <T> Tipo do cast
   * @return Objeto com cast do tipo T
   */
  @SuppressWarnings("unchecked")
  default <T> T cast() {
    return (T) this;
  }

  /**
   * Realiza cast dinâmico
   *
   * @param <T>  Tipo do cast
   * @param type Classe do tipo do objeto
   * @return Objeto com cast do tipo T
   */
  default <T> T cast(Class<T> type) {
    return type.cast(this);
  }
}
