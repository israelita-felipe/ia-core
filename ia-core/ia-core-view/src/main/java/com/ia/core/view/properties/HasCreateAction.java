package com.ia.core.view.properties;

import java.io.Serializable;

/**
 * Propriedade que indica salvar um items.
 *
 * @author Israel Araújo
 * @param <T> Tipo de dado.
 */
public interface HasCreateAction<T extends Serializable> {

  /**
   * Após criar o objeto.
   *
   * @param item Item salvo.
   */
  default void afterCreate(T item) {

  }

  /**
   * Antes de criar.
   */
  default void beforeCreate() {

  }

  /**
   * Cria o objeto
   */
  default void create() {
    beforeCreate();
    T object = createAction();
    afterCreate(object);
  }

  /**
   * Ação de criar o objeto
   *
   * @return Objeto instanciado
   */
  T createAction();
}
