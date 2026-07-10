package com.ia.core.llm.view.agenteconstrutor.page;

import com.ia.core.llm.service.model.agente.RequisicaoConstrucaoOntologiaDTO;
import com.ia.core.llm.service.model.agente.ResultadoConstrucaoOntologiaDTO;
import com.ia.core.llm.view.agenteconstrutor.AgenteConstrutorManager;
import com.ia.core.view.components.IViewModel;
import lombok.extern.slf4j.Slf4j;

/**
 * ViewModel para ConstrutorOntologiaPageView.
 * <p>
 * Segue o padrão ADR-008 MVVM com separação View-ViewModel.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
public class ConstrutorOntologiaPageViewModel implements IViewModel {

  private final AgenteConstrutorManager manager;
  private ResultadoConstrucaoOntologiaDTO resultadoAtual;
  private String jobIdAtual;

  public ConstrutorOntologiaPageViewModel(AgenteConstrutorManager manager) {
    this.manager = manager;
  }

  public void iniciarConstrucao(RequisicaoConstrucaoOntologiaDTO requisicao) {
    // Na implementação completa, chamaria o serviço REST
  }

  public void verificarProgresso(String jobId) {
    // Na implementação completa, chamaria o serviço REST
  }

  public ResultadoConstrucaoOntologiaDTO getResultadoAtual() {
    return resultadoAtual;
  }

  public String getJobIdAtual() {
    return jobIdAtual;
  }

  @Override
  public boolean isReadOnly() {
    return false;
  }

  @Override
  public void setReadOnly(boolean readOnly) {
    // Implementação opcional
  }
}
