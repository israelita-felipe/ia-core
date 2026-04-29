package com.ia.core.security.view.log.operation.form;

import com.ia.core.security.model.functionality.OperationEnum;
import com.ia.core.security.service.model.log.operation.LogOperationDTO;
import com.ia.core.security.service.model.log.operation.LogOperationDetails;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;
/**
 * Model de dados para a view de log operation form.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a LogOperationFormViewModel
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class LogOperationFormViewModel
  extends FormViewModel<LogOperationDTO> {

  @Getter
  private LogOperationDetailsFormViewModel logOperationDetailsViewModel;

  /**
   * @param readOnly
   */
  public LogOperationFormViewModel(FormViewModelConfig<LogOperationDTO> config) {
    super(config);
    this.logOperationDetailsViewModel = createLogOperationDetailsViewModel(config
        .isReadOnly());
  }

  /**
   * @param readOnly
   * @return
   */
  private LogOperationDetailsFormViewModel createLogOperationDetailsViewModel(boolean readOnly) {
    return new LogOperationDetailsFormViewModel(new FormViewModelConfig<>(readOnly));
  }

  /**
   * @return
   */
  public Collection<OperationEnum> getOperations() {
    return Arrays.asList(getModel().getOperation());
  }

  @Override
  public void setModel(LogOperationDTO model) {
    super.setModel(model);
    if (this.logOperationDetailsViewModel != null) {
      this.logOperationDetailsViewModel
          .setModel(new LogOperationDetails(getModel()));
    }
  }

  @Override
  public void setReadOnly(boolean readOnly) {
    super.setReadOnly(readOnly);
    if (this.logOperationDetailsViewModel != null) {
      this.logOperationDetailsViewModel.setReadOnly(readOnly);
    }
  }

}
