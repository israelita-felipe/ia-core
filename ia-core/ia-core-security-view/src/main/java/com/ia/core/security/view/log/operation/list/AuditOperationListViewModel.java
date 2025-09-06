package com.ia.core.security.view.log.operation.list;

import java.util.Arrays;
import java.util.List;

import com.ia.core.security.service.model.log.operation.LogOperationDTO;
import com.ia.core.security.view.log.operation.form.LogOperationFormViewModel;
import com.ia.core.service.dto.filter.FieldTypeDTO;
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
public class AuditOperationListViewModel
  extends ListViewModel<LogOperationDTO> {
  @Getter
  private LogOperationFormViewModel logOperationFormViewModel;

  @Getter
  private SearchRequestDTO auditSearchRequest;
  private FilterRequestDTO userFilter;

  /**
   *
   */
  public AuditOperationListViewModel(AuditOperationListViewModelConfig config) {
    super(config);
    this.logOperationFormViewModel = createLogOperationFormViewModel();
    this.auditSearchRequest = createAuditSearchRequest();
  }

  @Override
  public AuditOperationListViewModelConfig getConfig() {
    return (AuditOperationListViewModelConfig) super.getConfig();
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
  protected SearchRequestDTO createAuditSearchRequest() {
    userFilter = FilterRequestDTO.builder().fieldType(FieldTypeDTO.STRING)
        .key(LogOperationDTO.CAMPOS.USER_CODE).operator(OperatorDTO.EQUAL)
        .build();
    List<FilterRequestDTO> filters = Arrays.asList(userFilter);
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
  public void refreshAudit(String userCode) {
    this.userFilter.setValue(userCode);
  }

}
