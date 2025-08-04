package com.ia.core.security.view.log.operation.form;

import com.ia.core.security.service.model.log.operation.LogOperationDTO;
import com.ia.core.security.service.model.log.operation.LogOperationTranslator;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.component.textfield.TextField;

/**
 * @author Israel Araújo
 */
public class LogOperationFormView
  extends FormView<LogOperationDTO> {

  /**
   * @param viewModel
   */
  public LogOperationFormView(IFormViewModel<LogOperationDTO> viewModel) {
    super(viewModel);
  }

  /**
   * @return {@link LogOperationDetailsFormView}
   */
  protected LogOperationDetailsFormView createDetailsFormField() {
    LogOperationDetailsFormView field = new LogOperationDetailsFormView(getViewModel()
        .getLogOperationDetailsViewModel());
    add(field, 6);
    return field;

  }

  @Override
  public void createLayout() {
    super.createLayout();
    bind("userName", createUserNameField());
    createDetailsFormField();
  }

  /**
   * @return
   */
  protected TextField createUserNameField() {
    TextField field = createTextField($(LogOperationTranslator.USER_NAME),
                                      $(LogOperationTranslator.HELP.USER_NAME));
    add(field, 6);
    return field;
  }

  @Override
  public LogOperationFormViewModel getViewModel() {
    return super.getViewModel().cast();
  }
}
