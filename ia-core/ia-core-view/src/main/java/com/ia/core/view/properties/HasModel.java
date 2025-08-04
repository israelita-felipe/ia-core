package com.ia.core.view.properties;

/**
 * Interface de propriedade que indica possuir um objeto.
 *
 * @author Israel Araújo
 * @param <T> Tipo de dado.
 */
public interface HasModel<T> {

  /**
   * @return <T> O objeto.
   */
  T getModel();

  /**
   * @param model Objeto <T>
   */
  void setModel(T model);

  /**
   * @return "model" por padrão.
   */
  default String getModelPrefix() {
    return "model";
  }
}
