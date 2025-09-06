package com.ia.core.view.components.form.viewModel;

import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementação padrão de um view model para formulário
 *
 * @author Israel Araújo
 * @param <T> Tipo do dado do formulário
 */
@Slf4j
public abstract class FormViewModel<T extends Serializable>
  implements IFormViewModel<T> {

  /** Indicativo de somente leitura */
  @Getter
  private boolean readOnly;
  /** Modelo do viewModel */
  @Getter
  private T model;
  /** Suporte a mudança de propriedade */
  @Getter
  private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
  @Getter
  private FormViewModelConfig<T> config;

  /**
   * @param readOnly Indicativo de somente leitura
   */
  public FormViewModel(FormViewModelConfig<T> config) {
    this.readOnly = config.isReadOnly();
    this.config = config;
  }

  @Override
  public void setModel(T model) {
    firePropertyChange(CAMPOS.MODEL, this.model, model);
    this.model = model;
  }

  /**
   * @param readOnly atualiza {@link #readOnly}.
   */
  @Override
  public void setReadOnly(boolean readOnly) {
    firePropertyChange(CAMPOS.READ_ONLY, this.readOnly, readOnly);
    this.readOnly = readOnly;
  }

  @SuppressWarnings("javadoc")
  public static class CAMPOS {
    public static final String MODEL = "model";
    public static final String READ_ONLY = "readOnly";
  }
}
