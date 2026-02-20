package com.ia.core.quartz.view.periodicidade.form;

import com.ia.core.quartz.service.model.periodicidade.dto.PeriodicidadeDTO;
import com.ia.core.view.components.form.viewModel.FormViewModel;

import lombok.Getter;

/**
 * @author Israel Araújo
 */
public class PeriodicidadeFormViewModel
  extends FormViewModel<PeriodicidadeDTO> {
  @Getter
  private IntervaloTemporalFormViewModel intervaloTemporalFormViewModel;
  @Getter
  private RecorrenciaFormViewModel recorrenciaFormViewModel;

  /**
   * @param config
   */
  public PeriodicidadeFormViewModel(PeriodicidadeFormViewModelConfig config) {
    super(config);
    createIntervaloTemporalFormViewModel(config);
    createRecorrenciaFormViewModel(config);

  }

  @Override
  public PeriodicidadeFormViewModelConfig getConfig() {
    return super.getConfig().cast();
  }

  /**
   * Cria o ViewModel do formulário de intervalo temporal
   */
  protected void createIntervaloTemporalFormViewModel(PeriodicidadeFormViewModelConfig config) {
    this.intervaloTemporalFormViewModel = new IntervaloTemporalFormViewModel(new IntervaloTemporalFormViewModelConfig(config
        .isReadOnly()));
  }

  /**
   * Cria o ViewModel do formulário de recorrência
   */
  protected void createRecorrenciaFormViewModel(PeriodicidadeFormViewModelConfig config) {
    this.recorrenciaFormViewModel = new RecorrenciaFormViewModel(new RecorrenciaFormViewModelConfig(config
        .isReadOnly()));
  }

  @Override
  public void setModel(PeriodicidadeDTO model) {
    super.setModel(model);
    if (model != null) {
      if (this.intervaloTemporalFormViewModel != null) {
        this.intervaloTemporalFormViewModel
            .setModel(model.getIntervaloBase());
      }
      if (this.recorrenciaFormViewModel != null) {
        this.recorrenciaFormViewModel.setModel(model.getRegra());
      }

    }
  }

  @Override
  public void setReadOnly(boolean readOnly) {
    super.setReadOnly(readOnly);
    if (this.intervaloTemporalFormViewModel != null) {
      this.intervaloTemporalFormViewModel.setReadOnly(readOnly);
    }
    if (this.recorrenciaFormViewModel != null) {
      this.recorrenciaFormViewModel.setReadOnly(readOnly);
    }

  }

}
