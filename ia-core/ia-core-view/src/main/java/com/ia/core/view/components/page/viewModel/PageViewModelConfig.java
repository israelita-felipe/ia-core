package com.ia.core.view.components.page.viewModel;

import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.list.viewModel.ListViewModelConfig;
import com.ia.core.view.manager.DefaultBaseManager;
import com.ia.core.view.properties.AutoCastable;
import lombok.Getter;

import java.io.Serializable;

/**
 *
 */
/**
 * Classe de configuração para page view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PageViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public abstract class PageViewModelConfig<D extends Serializable>
  implements AutoCastable {
  @Getter
  private final DefaultBaseManager<D> service;
  @Getter
  private ListViewModelConfig<D> listViewModelConfig;
  @Getter
  private FormViewModelConfig<D> formViewModelConfig;

  /**
   *
   */
  public PageViewModelConfig(DefaultBaseManager<D> service) {
    this.service = service;
  }

  /**
   * @return
   */
  protected FormViewModelConfig<D> createFormViewModelConfig(boolean readOnly) {
    return this.formViewModelConfig = new FormViewModelConfig<>(readOnly);
  }

  /**
   * @return
   */
  protected ListViewModelConfig<D> createListViewModelConfig(boolean readOnly) {
    return this.listViewModelConfig = new ListViewModelConfig<>(readOnly);
  }
}
