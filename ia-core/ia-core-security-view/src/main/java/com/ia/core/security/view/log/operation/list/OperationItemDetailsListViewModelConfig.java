package com.ia.core.security.view.log.operation.list;

import com.ia.core.security.service.model.log.operation.OperationItemDetails;
import com.ia.core.view.components.list.viewModel.ListViewModelConfig;

/**
 *
 */
/**
 * Classe de configuração para operation item details list view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a OperationItemDetailsListViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class OperationItemDetailsListViewModelConfig
  extends ListViewModelConfig<OperationItemDetails> {

  /**
   * @param readOnly
   */
  public OperationItemDetailsListViewModelConfig(boolean readOnly) {
    super(readOnly);
  }

}
