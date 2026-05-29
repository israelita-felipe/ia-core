package com.ia.core.communication.view.grupocontato.form;

import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoDTO;
import com.ia.core.communication.view.grupocontato.GrupoContatoManager;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import lombok.Getter;
/**
 * Modelo de dados para a view de formulário de grupo de contato.
 * <p>
 * Responsável por gerenciar os dados e operações do formulário de grupo de contato,
 * incluindo integração com o GrupoContatoManager.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */

public class GrupoContatoFormViewModel extends FormViewModel<GrupoContatoDTO> {

  @Getter
  private final GrupoContatoManager grupoContatoManager;

  public GrupoContatoFormViewModel(GrupoContatoFormViewModelConfig config) {
    super(config);
    this.grupoContatoManager = config.getGrupoContatoManager();
  }

  @Override
  public GrupoContatoFormViewModelConfig getConfig() {
    return super.getConfig().cast();
  }
}
