package com.ia.core.service.dto.properties;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Possui suporte a mudança de propriedade
 */
public interface HasPropertyChangeSupport {

  /**
   * @return {@link PropertyChangeSupport}
   */
  PropertyChangeSupport getPropertyChangeSupport();

  /**
   * Adiciona escutador de mudança de propriedade
   *
   * @param property Propriedade
   * @param listener {@link PropertyChangeListener}
   * @return {@link PropertyChangeRegistry} para remoção da propriedade
   */
  default PropertyChangeRegistry addPropertyChangeListener(String property,
                                                           PropertyChangeListener listener) {
    getPropertyChangeSupport().addPropertyChangeListener(property,
                                                         listener);
    return () -> getPropertyChangeSupport()
        .removePropertyChangeListener(property, listener);
  }

  /**
   * Adiciona escutador de mudança de propriedade
   *
   * @param listener {@link PropertyChangeListener}
   * @return {@link PropertyChangeRegistry} para remoção da propriedade
   */
  default PropertyChangeRegistry addPropertyChangeListener(PropertyChangeListener listener) {
    getPropertyChangeSupport().addPropertyChangeListener(listener);
    return () -> getPropertyChangeSupport()
        .removePropertyChangeListener(listener);
  }

  /**
   * Dispara um evento sobre uma propriedade
   *
   * @param property Propriedade
   * @param oldValue Valor antigo
   * @param newValue Novo valor
   */
  default void firePropertyChange(String property, Object oldValue,
                                  Object newValue) {
    getPropertyChangeSupport().firePropertyChange(property, oldValue,
                                                  newValue);
  }

  /**
   * Registro de uma mudança de propriedade
   */
  @FunctionalInterface
  public static interface PropertyChangeRegistry {
    /**
     * Remove o escutador
     */
    void remove();
  }
}
