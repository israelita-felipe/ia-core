package com.ia.core.security.view.log.operation.form;

import com.ia.core.security.service.model.log.operation.LogOperationDetails;
import com.ia.core.security.service.model.log.operation.OperationItemDetails;
import com.ia.core.security.view.log.operation.list.OperationItemDetailsListViewModel;
import com.ia.core.security.view.log.operation.list.OperationItemDetailsListViewModelConfig;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import lombok.Getter;

import java.util.Collection;
/**
 * Model de dados para a view de log operation details form.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a LogOperationDetailsFormViewModel
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class LogOperationDetailsFormViewModel
  extends FormViewModel<LogOperationDetails> {
  @Getter
  private OperationItemDetailsListViewModel logOperationItemDetailsListViewModel = null;

  /**
   * @param readOnly
   */
  public LogOperationDetailsFormViewModel(FormViewModelConfig<LogOperationDetails> config) {
    super(config);
    this.logOperationItemDetailsListViewModel = createLogOperationItemDetailsListViewModel(config
        .isReadOnly());
  }

  /**
   * @param readOnly
   * @return
   */
  private OperationItemDetailsListViewModel createLogOperationItemDetailsListViewModel(boolean readOnly) {
    return new OperationItemDetailsListViewModel(new OperationItemDetailsListViewModelConfig(readOnly));
  }

  /**
   * @return
   */
  public Collection<OperationItemDetails> getLogOperationItensDetails() {
    return getModel().getItens();
  }

  @Override
  public void setReadOnly(boolean readOnly) {
    super.setReadOnly(readOnly);
    if (this.logOperationItemDetailsListViewModel != null) {
      this.logOperationItemDetailsListViewModel.setReadOnly(readOnly);
    }
  }

}
