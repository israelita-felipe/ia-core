package com.ia.core.quartz.view.periodicidade.form;

import com.ia.core.quartz.service.model.recorrencia.dto.RecorrenciaDTO;
import com.ia.core.view.components.form.viewModel.FormViewModel;
/**
 * Model de dados para a view de recorrencia form.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a RecorrenciaFormViewModel
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class RecorrenciaFormViewModel
  extends FormViewModel<RecorrenciaDTO> {

  /**
   * @param config
   */
  public RecorrenciaFormViewModel(RecorrenciaFormViewModelConfig config) {
    super(config);
  }

  @Override
  public RecorrenciaFormViewModelConfig getConfig() {
    return super.getConfig().cast();
  }
}
