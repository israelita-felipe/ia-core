package com.ia.core.llm.view.comando.form;

import com.ia.core.llm.service.model.comando.ComandoSistemaDTO;
import com.ia.core.view.components.form.viewModel.FormViewModel;
/**
 * Model de dados para a view de comando sistema form.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a ComandoSistemaFormViewModel
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class ComandoSistemaFormViewModel
  extends FormViewModel<ComandoSistemaDTO> {

  /**
   * @param readOnly
   */
  public ComandoSistemaFormViewModel(ComandoSistemaFormViewModelConfig config) {
    super(config);
  }

  @Override
  public ComandoSistemaFormViewModelConfig getConfig() {
    return (ComandoSistemaFormViewModelConfig) super.getConfig();
  }
}
