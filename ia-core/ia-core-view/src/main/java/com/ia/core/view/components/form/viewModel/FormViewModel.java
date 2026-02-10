package com.ia.core.view.components.form.viewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import com.ia.core.service.dto.properties.HasPropertyChangeSupport;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementação padrão de um view model para formulário.
 *
 * @author Israel Araújo
 * @param <T> Tipo do dado do formulário
 */
@Slf4j
public abstract class FormViewModel<T extends Serializable>
  implements IFormViewModel<T>, HasPropertyChangeSupport {

  /** Indicativo de somente leitura */
  @Getter
  private boolean readOnly;
  /** Modelo do viewModel */
  @Getter
  private T model;
  /** Suporte a mudança de propriedade */
  private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
  @Getter
  private FormViewModelConfig<T> config;

  /** Constantes de campos */
  public static final String CAMPO_MODEL = "model";
  public static final String CAMPO_READ_ONLY = "readOnly";

  /**
   * @param readOnly Indicativo de somente leitura
   */
  public FormViewModel(FormViewModelConfig<T> config) {
    this.readOnly = config.isReadOnly();
    this.config = config;
  }

  @Override
  public PropertyChangeSupport getPropertyChangeSupport() {
    return propertyChangeSupport;
  }

  @Override
  public void setModel(T model) {
    firePropertyChange(CAMPO_MODEL, this.model, model);
    this.model = model;
  }

  /**
   * @param readOnly atualiza {@link #readOnly}.
   */
  @Override
  public void setReadOnly(boolean readOnly) {
    firePropertyChange(CAMPO_READ_ONLY, this.readOnly, readOnly);
    this.readOnly = readOnly;
  }
}
