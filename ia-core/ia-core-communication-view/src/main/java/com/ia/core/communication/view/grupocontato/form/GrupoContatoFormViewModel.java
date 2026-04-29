package com.ia.core.communication.view.grupocontato.form;

import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoDTO;
import com.ia.core.communication.view.grupocontato.GrupoContatoManager;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import lombok.Getter;
/**
 * Classe que representa o modelo de dados para a view de grupo contato form.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a GrupoContatoFormViewModel
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
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
