package com.ia.core.llm.view.agenteconversacional.dialog;

import com.ia.core.llm.view.agenteconversacional.AgenteConversacionalManager;
import org.springframework.stereotype.Component;

/**
 * Configuração Spring do ViewModel de ChatOntologiaDialog.
 * <p>
 * Segue o padrão ADR-004 para injeção de dependências.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Component
public class ChatOntologiaDialogViewModelConfig {

  public ChatOntologiaDialogViewModel chatOntologiaDialogViewModel(AgenteConversacionalManager manager) {
    return new ChatOntologiaDialogViewModel(manager);
  }
}
