package com.ia.core.llm.view.validacao.page;

import com.ia.core.llm.view.validacao.ValidacaoManager;
import org.springframework.stereotype.Component;

/**
 * Configuração Spring do ViewModel de ValidacaoPageView.
 * <p>
 * Segue o padrão ADR-004 para injeção de dependências.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Component
public class ValidacaoPageViewModelConfig {

  public ValidacaoPageViewModel validacaoPageViewModel(ValidacaoManager manager) {
    return new ValidacaoPageViewModel(manager);
  }
}
