package com.ia.core.llm.view.agente.form;

import com.ia.core.llm.service.model.agente.AgenteDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe de configuração para agente form view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a AgenteFormViewModelConfig
 * dentro do sistema.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
public class AgenteFormViewModelConfig
  extends FormViewModelConfig<AgenteDTO> {

  /**
   * @param readOnly indica se o formulário é somente leitura
   */
  public AgenteFormViewModelConfig(boolean readOnly) {
    super(readOnly);
    log.debug("AgenteFormViewModelConfig inicializado: readOnly={}", readOnly);
  }
}
