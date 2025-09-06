package com.ia.core.view.components.list.viewModel;

import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import lombok.Getter;

/**
 * Implementação padrão de um View Model de lista
 *
 * @author Israel Araújo
 * @param <T> Tipo do dado da lista
 */
public abstract class ListViewModel<T extends Serializable>
  implements IListViewModel<T> {
  /** Indicativo de somente leitura */
  @Getter
  private boolean readOnly = false;
  /** Suporte a mudança de propriedade */
  @Getter
  private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
  @Getter
  private ListViewModelConfig<T> config;

  /**
   * 
   */
  public ListViewModel(ListViewModelConfig<T> config) {
    this.config = config;
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
    public static final String READ_ONLY = "readOnly";
  }
}
