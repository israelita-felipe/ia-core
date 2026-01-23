package com.ia.core.security.view.log.operation.list;

import java.util.Arrays;
import java.util.List;

import com.ia.core.model.filter.FieldType;
import com.ia.core.security.service.model.log.operation.LogOperationDTO;
import com.ia.core.security.view.log.operation.form.LogOperationFormViewModel;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.dto.sort.SortDirectionDTO;
import com.ia.core.service.dto.sort.SortRequestDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.list.viewModel.ListViewModel;

import lombok.Getter;

/**
 * @author Israel Araújo
 */
public class LogOperationListViewModel
  extends ListViewModel<LogOperationDTO> {
  @Getter
  private LogOperationFormViewModel logOperationFormViewModel;
  @Getter
  private SearchRequestDTO logSearchRequest;
  private FilterRequestDTO typeFilter;
  private FilterRequestDTO idFilter;

  /**
   *
   */
  public LogOperationListViewModel(LogOperationListViewModelConfig config) {
    super(config);
    this.logOperationFormViewModel = createLogOperationFormViewModel();
    this.logSearchRequest = createLogSearchRequest();
  }

  @Override
  public LogOperationListViewModelConfig getConfig() {
    return (LogOperationListViewModelConfig) super.getConfig();
  }

  /**
   * @param logOperationService
   * @return
   */
  protected LogOperationFormViewModel createLogOperationFormViewModel() {
    return new LogOperationFormViewModel(new FormViewModelConfig<>(true));
  }

  /**
   * @param type
   * @param id
   * @return
   */
  protected SearchRequestDTO createLogSearchRequest() {
    typeFilter = FilterRequestDTO.builder().fieldType(FieldType.STRING)
        .key(LogOperationDTO.CAMPOS.TYPE).operator(OperatorDTO.EQUAL)
        .build();
    idFilter = FilterRequestDTO.builder().fieldType(FieldType.LONG)
        .key(LogOperationDTO.CAMPOS.VALUE_ID).operator(OperatorDTO.EQUAL)
        .build();
    List<FilterRequestDTO> filters = Arrays.asList(typeFilter, idFilter);
    var dateTimeDesc = SortRequestDTO.builder()
        .key(LogOperationDTO.CAMPOS.DATE_TIME_OPERATION)
        .direction(SortDirectionDTO.DESC).build();
    return SearchRequestDTO.builder().filters(filters)
        .sorts(Arrays.asList(dateTimeDesc)).build();
  }

  @Override
  public Class<LogOperationDTO> getType() {
    return LogOperationDTO.class;
  }

  /**
   * @param type
   * @param id
   */
  public void refreshLog(Class<?> type, Long id) {
    this.typeFilter.setValue(type);
    this.idFilter.setValue(id);
  }

}
