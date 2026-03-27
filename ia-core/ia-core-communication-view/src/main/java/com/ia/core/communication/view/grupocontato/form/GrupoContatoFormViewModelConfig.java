package com.ia.core.communication.view.grupocontato.form;

import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoDTO;
import com.ia.core.communication.view.grupocontato.GrupoContatoManager;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import lombok.Getter;

public class GrupoContatoFormViewModelConfig extends FormViewModelConfig<GrupoContatoDTO> {

  @Getter
  private final GrupoContatoManager grupoContatoManager;

  public GrupoContatoFormViewModelConfig(boolean readOnly, GrupoContatoManager grupoContatoManager) {
    super(readOnly);
    this.grupoContatoManager = grupoContatoManager;
  }
}
