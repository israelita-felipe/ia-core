package com.ia.core.quartz.view.quartz.list;

import com.ia.core.quartz.service.model.periodicidade.dto.PeriodicidadeDTO;
import com.ia.core.quartz.service.model.scheduler.dto.SchedulerConfigDTO;
import com.ia.core.quartz.service.model.scheduler.dto.SchedulerConfigTranslator;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;

/**
 *
 */
/**
 * View para exibição e manipulação de scheduler config list.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a SchedulerConfigListView
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class SchedulerConfigListView
  extends ListView<SchedulerConfigDTO> {

  /**
   * @param viewModel
   */
  public SchedulerConfigListView(IListViewModel<SchedulerConfigDTO> viewModel) {
    super(viewModel);
  }

  @Override
  protected void createColumns() {
    super.createColumns();
    addColumn(SchedulerConfigDTO.CAMPOS.JOB_CLASS_NAME);
    addColumn(scheduler -> formatPeriodicidade(scheduler.getPeriodicidade()))
        .setHeader($(SchedulerConfigTranslator.PERIODICIDADE));
  }

  /**
   * Formata a periodicidade para exibição
   */
  protected String formatPeriodicidade(PeriodicidadeDTO periodicidade) {
    if (periodicidade == null) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("Ativo: ").append(periodicidade.getAtivo() != null && periodicidade.getAtivo() ? "Sim" : "Não");
    if (periodicidade.getRegra() != null) {
      sb.append(", Frequencia: ").append(periodicidade.getRegra().getFrequency());
    }
    return sb.toString();
  }
}
