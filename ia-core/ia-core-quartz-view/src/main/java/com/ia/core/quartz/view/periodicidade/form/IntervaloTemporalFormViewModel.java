package com.ia.core.quartz.view.periodicidade.form;

import com.ia.core.quartz.service.model.periodicidade.dto.IntervaloTemporalDTO;
import com.ia.core.view.components.form.viewModel.FormViewModel;
/**
 * Model de dados para a view de intervalo temporal form.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a IntervaloTemporalFormViewModel
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class IntervaloTemporalFormViewModel
  extends FormViewModel<IntervaloTemporalDTO> {

  /**
   * @param config
   */
  public IntervaloTemporalFormViewModel(IntervaloTemporalFormViewModelConfig config) {
    super(config);
  }

  @Override
  public IntervaloTemporalFormViewModelConfig getConfig() {
    return super.getConfig().cast();
  }
}
