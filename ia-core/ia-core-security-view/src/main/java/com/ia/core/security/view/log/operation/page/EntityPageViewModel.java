package com.ia.core.security.view.log.operation.page;

import java.io.Serializable;
import java.util.UUID;

import com.ia.core.security.view.log.operation.LogOperationManager;
import com.ia.core.security.view.log.operation.list.LogOperationListViewModel;
import com.ia.core.security.view.log.operation.list.LogOperationListViewModelConfig;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.view.components.page.viewModel.PageViewModel;

import lombok.Getter;

/**
 * View Model para página de entidades
 *
 * @author Israel Araújo
 * @param <T> Tipo da entidade
 */
public abstract class EntityPageViewModel<T extends AbstractBaseEntityDTO<? extends Serializable> & Serializable>
  extends PageViewModel<T> {
  /**
   * View Model de log de operações
   */
  @Getter
  private LogOperationListViewModel logOperationListViewModel;

  /**
   * @param service             serviço da entidade
   * @param logOperationService serviço de log de operação
   */
  public EntityPageViewModel(EntityPageViewModelConfig<T> config) {
    super(config);
    this.logOperationListViewModel = createLogOperationListViewModel();
  }

  @Override
  public EntityPageViewModelConfig<T> getConfig() {
    return (EntityPageViewModelConfig<T>) super.getConfig();
  }

  /**
   * Cria o viewModel para log de operação
   *
   * @param logOperationService {@link LogOperationManager}
   * @return {@link LogOperationListViewModel} criado
   */
  protected LogOperationListViewModel createLogOperationListViewModel() {
    return new LogOperationListViewModel(new LogOperationListViewModelConfig(isReadOnly(),
                                                                             getConfig()
                                                                                 .getLogOperationService()));
  }

  @Override
  public void setReadOnly(boolean readOnly) {
    super.setReadOnly(readOnly);
    if (this.logOperationListViewModel != null) {
      this.logOperationListViewModel.setReadOnly(readOnly);
    }
  }

  /**
   * @param object Objeto a ser visualizado o histórico
   * @return {@link LogOperationListViewModel}
   */
  public LogOperationListViewModel viewHistory(T object) {
    logOperationListViewModel.refreshLog(getType(), getId(object));
    return this.logOperationListViewModel;
  }

  @SuppressWarnings("unchecked")
  @Override
  public T copyObject(T object) {
    return (T) object.copyObject();
  }

  @SuppressWarnings("unchecked")
  @Override
  public T cloneObject(T object) {
    return (T) object.cloneObject();
  }

  @Override
  public UUID getId(T object) {
    return object.getId();
  }
}
