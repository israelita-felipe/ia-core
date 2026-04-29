package com.ia.core.quartz.view.periodicidade.form;

import com.ia.core.quartz.service.model.periodicidade.dto.IntervaloTemporalDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
/**
 * Classe de configuração para intervalo temporal form view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a IntervaloTemporalFormViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class IntervaloTemporalFormViewModelConfig
  extends FormViewModelConfig<IntervaloTemporalDTO> {

  public IntervaloTemporalFormViewModelConfig(boolean readOnly) {
    super(readOnly);
  }
}
