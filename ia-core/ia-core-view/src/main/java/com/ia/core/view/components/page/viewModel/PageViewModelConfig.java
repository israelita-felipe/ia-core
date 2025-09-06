package com.ia.core.view.components.page.viewModel;

import java.io.Serializable;

import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.list.viewModel.ListViewModelConfig;
import com.ia.core.view.properties.AutoCastable;
import com.ia.core.view.service.DefaultBaseService;

import lombok.Getter;

/**
 *
 */
public abstract class PageViewModelConfig<D extends Serializable>
  implements AutoCastable {
  @Getter
  private final DefaultBaseService<D> service;
  @Getter
  private ListViewModelConfig<D> listViewModelConfig;
  @Getter
  private FormViewModelConfig<D> formViewModelConfig;

  /**
   *
   */
  public PageViewModelConfig(DefaultBaseService<D> service) {
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
