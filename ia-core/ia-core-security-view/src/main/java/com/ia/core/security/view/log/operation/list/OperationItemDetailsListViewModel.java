package com.ia.core.security.view.log.operation.list;

import com.ia.core.security.service.model.log.operation.OperationItemDetailsDTO;
import com.ia.core.view.components.list.viewModel.ListViewModel;
/**
 * Model de dados para a view de operation item details list.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a OperationItemDetailsListViewModel
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class OperationItemDetailsListViewModel
  extends ListViewModel<OperationItemDetailsDTO> {

  /**
   * @param config
   */
  public OperationItemDetailsListViewModel(OperationItemDetailsListViewModelConfig config) {
    super(config);
  }

  @Override
  public Class<OperationItemDetailsDTO> getType() {
    return OperationItemDetailsDTO.class;
  }

}
