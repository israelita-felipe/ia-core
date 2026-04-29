package com.ia.core.quartz.view.periodicidade.form;

import com.ia.core.quartz.service.model.recorrencia.dto.RecorrenciaDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
/**
 * Classe de configuração para recorrencia form view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a RecorrenciaFormViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class RecorrenciaFormViewModelConfig
  extends FormViewModelConfig<RecorrenciaDTO> {

  public RecorrenciaFormViewModelConfig(boolean readOnly) {
    super(readOnly);
  }
}
