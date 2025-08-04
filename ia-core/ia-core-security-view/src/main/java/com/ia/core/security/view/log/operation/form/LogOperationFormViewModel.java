package com.ia.core.security.view.log.operation.form;

import java.util.Arrays;
import java.util.Collection;

import com.ia.core.security.model.functionality.OperationEnum;
import com.ia.core.security.service.model.log.operation.LogOperationDTO;
import com.ia.core.security.service.model.log.operation.LogOperationDetails;
import com.ia.core.view.components.form.viewModel.FormViewModel;

import lombok.Getter;

/**
 * @author Israel Araújo
 */
public class LogOperationFormViewModel
  extends FormViewModel<LogOperationDTO> {

  @Getter
  private LogOperationDetailsFormViewModel logOperationDetailsViewModel;

  /**
   * @param readOnly
   */
  public LogOperationFormViewModel(boolean readOnly) {
    super(readOnly);
    this.logOperationDetailsViewModel = createLogOperationDetailsViewModel(readOnly);
  }

  /**
   * @param readOnly
   * @return
   */
  private LogOperationDetailsFormViewModel createLogOperationDetailsViewModel(boolean readOnly) {
    return new LogOperationDetailsFormViewModel(readOnly);
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
