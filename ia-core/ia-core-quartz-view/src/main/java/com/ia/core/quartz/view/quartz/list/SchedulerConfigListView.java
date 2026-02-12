package com.ia.core.quartz.view.quartz.list;

import com.ia.core.quartz.service.model.scheduler.SchedulerConfigDTO;
import com.ia.core.quartz.service.model.scheduler.SchedulerConfigTranslator;
import com.ia.core.quartz.service.periodicidade.dto.PeriodicidadeDTO;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;

/**
 *
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
