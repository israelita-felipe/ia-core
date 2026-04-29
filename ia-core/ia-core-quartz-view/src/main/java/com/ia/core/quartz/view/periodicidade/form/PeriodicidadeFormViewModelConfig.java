package com.ia.core.quartz.view.periodicidade.form;

import com.ia.core.quartz.service.model.periodicidade.dto.PeriodicidadeDTO;
import com.ia.core.quartz.view.quartz.QuartzManager;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
/**
 * Classe de configuração para periodicidade form view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PeriodicidadeFormViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class PeriodicidadeFormViewModelConfig
  extends FormViewModelConfig<PeriodicidadeDTO> {

  private final QuartzManager schedulerService;

  public PeriodicidadeFormViewModelConfig(boolean readOnly,
                                          QuartzManager schedulerService) {
    super(readOnly);
    this.schedulerService = schedulerService;
  }

  public QuartzManager getSchedulerService() {
    return schedulerService;
  }
}
