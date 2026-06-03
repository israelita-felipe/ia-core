package com.ia.core.llm.view.ontologia.visualizador;

import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.ia.core.llm.view.ontologia.OntologiaManager;
import com.ia.core.view.components.IViewModel;
import lombok.extern.slf4j.Slf4j;

/**
 * ViewModel para OntologiaVisualizerView.
 * <p>
 * Segue o padrão ADR-008 MVVM com separação View-ViewModel.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
public class OntologiaVisualizerViewModel implements IViewModel {

  private final OntologiaManager manager;
  private OntologiaDTO ontologiaAtual;

  public OntologiaVisualizerViewModel(OntologiaManager manager) {
    this.manager = manager;
  }

  public void carregarOntologia(Long id) {
    this.ontologiaAtual = manager.find(id);
  }

  public OntologiaDTO getOntologiaAtual() {
    return ontologiaAtual;
  }

  @Override
  public boolean isReadOnly() {
    return true;
  }

  @Override
  public void setReadOnly(boolean readOnly) {
    // Visualizador é sempre read-only
  }
}
