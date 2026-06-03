package com.ia.core.llm.view.validacao.page;

import com.ia.core.llm.service.model.validacao.ResultadoValidacao;
import com.ia.core.llm.view.validacao.ValidacaoManager;
import com.ia.core.view.components.IViewModel;
import lombok.extern.slf4j.Slf4j;

/**
 * ViewModel para ValidacaoPageView.
 * <p>
 * Segue o padrão ADR-008 MVVM com separação View-ViewModel.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
public class ValidacaoPageViewModel implements IViewModel {

  private final ValidacaoManager manager;
  private ResultadoValidacao resultadoAtual;

  public ValidacaoPageViewModel(ValidacaoManager manager) {
    this.manager = manager;
  }

  public void validarOntologia() {
    // Na implementação completa, chamaria o serviço de validação
  }

  public ResultadoValidacao getResultadoAtual() {
    return resultadoAtual;
  }

  @Override
  public boolean isReadOnly() {
    return true;
  }

  @Override
  public void setReadOnly(boolean readOnly) {
    // Validação é sempre read-only
  }
}
