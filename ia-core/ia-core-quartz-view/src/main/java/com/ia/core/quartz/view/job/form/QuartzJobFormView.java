package com.ia.core.quartz.view.job.form;

import com.ia.core.quartz.service.model.job.QuartzJobDTO;
import com.ia.core.quartz.service.model.job.QuartzJobTranslator;
import com.ia.core.service.util.JsonUtil;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Formulário de Jobs do Quartz. Este formulário é somente leitura - as
 * operações de manipulação (pause, resume, delete, trigger) são realizadas via
 * PageActions na página.
 *
 * @author Israel Araújo
 */
public class QuartzJobFormView
  extends FormView<QuartzJobDTO> {

  private TabSheet mainTabSheet;
  private FormLayout basicTabLayout;
  private FormLayout stateTabLayout;
  private FormLayout dataTabLayout;

  /**
   * Construtor com o ViewModel.
   *
   * @param formViewModel ViewModel do formulário
   */
  public QuartzJobFormView(IFormViewModel<QuartzJobDTO> formViewModel) {
    super(formViewModel);
  }

  @Override
  public void createLayout() {
    super.createLayout();
    createMainTabSheet();
  }

  private void createMainTabSheet() {
    mainTabSheet = createTabSheet();

    // Aba Básico
    createTab(mainTabSheet, VaadinIcon.COG.create(),
              $(QuartzJobTranslator.QUARTZ_JOB),
              basicTabLayout = createFormLayout());

    // Aba Estado
    createTab(mainTabSheet, VaadinIcon.INFO.create(),
              $(QuartzJobTranslator.JOB_STATE),
              stateTabLayout = createFormLayout());

    // Aba Dados
    createTab(mainTabSheet, VaadinIcon.DATABASE.create(),
              $(QuartzJobTranslator.JOB_DATA),
              dataTabLayout = createFormLayout());

    add(mainTabSheet, 6);

    // Campos da aba Básico
    createJobNameField($(QuartzJobTranslator.JOB_NAME),
                       $(QuartzJobTranslator.HELP.JOB_NAME),
                       basicTabLayout);
    createJobGroupField($(QuartzJobTranslator.JOB_GROUP),
                        $(QuartzJobTranslator.HELP.JOB_GROUP),
                        basicTabLayout);
    createDescriptionField($(QuartzJobTranslator.DESCRIPTION),
                           $(QuartzJobTranslator.HELP.DESCRIPTION),
                           basicTabLayout);
    createJobClassNameField($(QuartzJobTranslator.JOB_CLASS_NAME),
                            $(QuartzJobTranslator.HELP.JOB_CLASS_NAME),
                            basicTabLayout);
    createIsDurableField($(QuartzJobTranslator.IS_DURABLE), basicTabLayout);
    createRequestsRecoveryField($(QuartzJobTranslator.REQUESTS_RECOVERY),
                                basicTabLayout);

    // Campos da aba Estado
    createJobStateField($(QuartzJobTranslator.JOB_STATE),
                        $(QuartzJobTranslator.HELP.JOB_STATE),
                        stateTabLayout);
    createNumberOfExecutionsField($(QuartzJobTranslator.NUMBER_OF_EXECUTIONS),
                                  $(QuartzJobTranslator.HELP.NUMBER_OF_EXECUTIONS),
                                  stateTabLayout);
    createLastExecutionTimeField($(QuartzJobTranslator.LAST_EXECUTION_TIME),
                                 $(QuartzJobTranslator.HELP.LAST_EXECUTION_TIME),
                                 stateTabLayout);
    createNextExecutionTimeField($(QuartzJobTranslator.NEXT_EXECUTION_TIME),
                                 $(QuartzJobTranslator.HELP.NEXT_EXECUTION_TIME),
                                 stateTabLayout);

    // Campos da aba Dados
    createJobDataField($(QuartzJobTranslator.JOB_DATA),
                       $(QuartzJobTranslator.HELP.JOB_DATA), dataTabLayout);
  }

  /**
   * Cria campo de nome do job (somente leitura).
   */
  private void createJobNameField(String label, String help,
                                  FormLayout layout) {
    TextField field = createTextField(label, help);
    field.setReadOnly(true);
    layout.add(field, 3);
    bind(QuartzJobDTO.CAMPOS.JOB_NAME, field);
  }

  /**
   * Cria campo de grupo do job (somente leitura).
   */
  private void createJobGroupField(String label, String help,
                                   FormLayout layout) {
    TextField field = createTextField(label, help);
    field.setReadOnly(true);
    layout.add(field, 3);
    bind(QuartzJobDTO.CAMPOS.JOB_GROUP, field);
  }

  /**
   * Cria campo de descrição (somente leitura).
   */
  private void createDescriptionField(String label, String help,
                                      FormLayout layout) {
    var field = createTextArea(label, help);
    field.setReadOnly(true);
    layout.add(field, 3);
    bind(QuartzJobDTO.CAMPOS.DESCRIPTION, field);
  }

  /**
   * Cria campo de nome da classe do job (somente leitura).
   */
  private void createJobClassNameField(String label, String help,
                                       FormLayout layout) {
    TextField field = createTextField(label, help);
    field.setReadOnly(true);
    layout.add(field, 3);
    bind(QuartzJobDTO.CAMPOS.JOB_CLASS_NAME, field);
  }

  /**
   * Cria campo de job é durável (somente leitura).
   */
  private void createIsDurableField(String label, FormLayout layout) {
    Checkbox field = createCheckBoxField(label, null);
    field.setReadOnly(true);
    layout.add(field, 3);
    bind(QuartzJobDTO.CAMPOS.IS_DURABLE, field);
  }

  /**
   * Cria campo de requisição de recuperação (somente leitura).
   */
  private void createRequestsRecoveryField(String label,
                                           FormLayout layout) {
    Checkbox field = createCheckBoxField(label, null);
    field.setReadOnly(true);
    layout.add(field, 3);
    bind(QuartzJobDTO.CAMPOS.REQUESTS_RECOVERY, field);
  }

  /**
   * Cria campo de estado do job (somente leitura).
   */
  private void createJobStateField(String label, String help,
                                   FormLayout layout) {
    TextField field = createTextField(label, help);
    field.setReadOnly(true);
    layout.add(field, 3);
    bind(QuartzJobDTO.CAMPOS.JOB_STATE, field);
  }

  /**
   * Cria campo de número de execuções (somente leitura).
   */
  private void createNumberOfExecutionsField(String label, String help,
                                             FormLayout layout) {
    TextField field = createTextField(label, help);
    field.setReadOnly(true);
    layout.add(field, 3);
    bindInteger(QuartzJobDTO.CAMPOS.NUMBER_OF_EXECUTIONS, field);
  }

  /**
   * Cria campo de última execução (somente leitura).
   */
  private void createLastExecutionTimeField(String label, String help,
                                            FormLayout layout) {
    var field = createDateTimePickerField(label, help);
    field.setReadOnly(true);
    layout.add(field, 3);
    bind(QuartzJobDTO.CAMPOS.LAST_EXECUTION_TIME, field);
  }

  /**
   * Cria campo de próxima execução (somente leitura).
   */
  private void createNextExecutionTimeField(String label, String help,
                                            FormLayout layout) {
    var field = createDateTimePickerField(label, help);
    field.setReadOnly(true);
    layout.add(field, 3);
    bind(QuartzJobDTO.CAMPOS.NEXT_EXECUTION_TIME, field);
  }

  /**
   * Cria campo de dados do job (somente leitura).
   */
  private void createJobDataField(String label, String help,
                                  FormLayout layout) {
    TextArea field = new TextArea();
    field.setLabel(label);
    setHelp(field, help);
    field.setReadOnly(true);
    field.setHeight("150px");
    layout.add(field);
    // Campo jobData não será bindado diretamente devido à complexidade do Map
    // O valor pode ser visualizado através de outra implementação se necessário
    field.setValue(JsonUtil.toJson(getViewModel().getModel().getJobData()));
  }

  @Override
  public QuartzJobFormViewModel getViewModel() {
    return super.getViewModel().cast();
  }
}
