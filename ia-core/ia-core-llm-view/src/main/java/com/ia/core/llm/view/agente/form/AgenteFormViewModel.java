package com.ia.core.llm.view.agente.form;

import com.ia.core.llm.service.model.agente.AgenteDTO;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import lombok.extern.slf4j.Slf4j;

/**
 * Model de dados para a view de agente form.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a AgenteFormViewModel
 * dentro do sistema.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
public class AgenteFormViewModel
  extends FormViewModel<AgenteDTO> {

  /**
   * @param config configuração do form view model
   */
  public AgenteFormViewModel(AgenteFormViewModelConfig config) {
    super(config);
    log.debug("AgenteFormViewModel inicializado");
  }

  @Override
  public AgenteFormViewModelConfig getConfig() {
    return (AgenteFormViewModelConfig) super.getConfig();
  }
}
