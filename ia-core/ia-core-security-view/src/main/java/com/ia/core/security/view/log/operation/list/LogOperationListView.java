package com.ia.core.security.view.log.operation.list;

import com.ia.core.model.util.FormatUtils;
import com.ia.core.security.service.model.log.operation.LogOperationDTO;
import com.ia.core.security.service.model.log.operation.LogOperationTranslator;
import com.ia.core.security.view.log.operation.form.LogOperationFormView;
import com.ia.core.security.view.log.operation.form.LogOperationFormViewModel;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.utils.DataProviderFactory;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;

/**
 * @author Israel Araújo
 */
public class LogOperationListView
  extends ListView<LogOperationDTO> {

  /** Serial UID */
  private static final long serialVersionUID = -4775874302915529579L;

  /**
   * @param viewModel
   */
  public LogOperationListView(IListViewModel<LogOperationDTO> viewModel) {
    super(viewModel);
    createDetails();
    createDataProvider();
    setSizeFull();
  }

  @Override
  protected void createColumns() {
    super.createColumns();
    addColumn(log -> FormatUtils
        .formatDateTime(FormatUtils.DATE_TIME_FULL,
                        log.getDateTimeOperation()))
                            .setHeader($(LogOperationTranslator.DATE_TIME));
    addColumn("userCode").setHeader($(LogOperationTranslator.USER_CODE));
    addColumn(log -> $(log.getOperation().name()))
        .setHeader($(LogOperationTranslator.OPERATION));
  }

  /**
   *
   */
  private void createDataProvider() {
    DataProvider<LogOperationDTO, SearchRequestDTO> dataProvider = DataProviderFactory
        .createDataProviderFromManager(getViewModel().getConfig()
            .getLogOperationService());
    ConfigurableFilterDataProvider<LogOperationDTO, Void, SearchRequestDTO> withConfigurableFilter = dataProvider
        .withConfigurableFilter();
    withConfigurableFilter.setFilter(getViewModel().getLogSearchRequest());
    setDataProvider(withConfigurableFilter);
    refreshAll();
  }

  /**
   * Cria o painel de detalhes de um log
   */
  protected void createDetails() {
    setItemDetailsRenderer(createDetailsRenderer());
    setDetailsVisibleOnClick(true);
  }

  protected LogOperationFormView createDetails(LogOperationDTO log) {
    LogOperationFormViewModel logOperationFormViewModel = getViewModel()
        .getLogOperationFormViewModel();
    logOperationFormViewModel.setModel(log);
    return new LogOperationFormView(logOperationFormViewModel);
  }

  /**
   * @return {@link Renderer} para visualização do log de operação
   */
  protected ComponentRenderer<Component, LogOperationDTO> createDetailsRenderer() {
    return new ComponentRenderer<>(log -> createDetails(log));
  }

  @Override
  public LogOperationListViewModel getViewModel() {
    return super.getViewModel().cast();
  }
}
