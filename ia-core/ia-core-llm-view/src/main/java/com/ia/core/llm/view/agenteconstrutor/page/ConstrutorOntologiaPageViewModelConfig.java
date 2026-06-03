package com.ia.core.llm.view.agenteconstrutor.page;

import com.ia.core.llm.view.agenteconstrutor.AgenteConstrutorManager;
import org.springframework.stereotype.Component;

/**
 * Configuração Spring do ViewModel de ConstrutorOntologiaPageView.
 * <p>
 * Segue o padrão ADR-004 para injeção de dependências.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Component
public class ConstrutorOntologiaPageViewModelConfig {

  public ConstrutorOntologiaPageViewModel construtorOntologiaPageViewModel(AgenteConstrutorManager manager) {
    return new ConstrutorOntologiaPageViewModel(manager);
  }
}
