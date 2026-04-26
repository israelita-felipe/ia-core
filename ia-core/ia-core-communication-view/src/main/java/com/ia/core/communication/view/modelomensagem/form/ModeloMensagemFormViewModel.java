package com.ia.core.communication.view.modelomensagem.form;

import com.ia.core.communication.service.model.modelomensagem.dto.HasVariavel;
import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.communication.service.model.modelomensagem.dto.Variavel;
import com.ia.core.communication.view.modelomensagem.ModeloMensagemManager;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

public class ModeloMensagemFormViewModel extends FormViewModel<ModeloMensagemDTO> {
  @Getter
  private final ModeloMensagemManager modeloMensagemManager;

  public ModeloMensagemFormViewModel(ModeloMensagemFormViewModelConfig config) {
    super(config);
    this.modeloMensagemManager = config.getModeloMensagemManager();
  }

  @Override
  public ModeloMensagemFormViewModelConfig getConfig() {
    return super.getConfig().cast();
  }

  /**
   * Retorna a lista de variáveis disponíveis para edição de templates.
   * Cada variável contém chave e descrição para exibição na UI.
   *
   * @return lista de Variavel
   */
  public List<Variavel> getVariaveisDisponiveis() {
    if (getModel() != null && getModel() instanceof HasVariavel) {
      return ((HasVariavel) getModel()).getVariaveis();
    }
    return Collections.emptyList();
  }
}

