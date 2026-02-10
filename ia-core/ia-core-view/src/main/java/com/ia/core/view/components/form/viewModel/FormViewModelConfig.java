package com.ia.core.view.components.form.viewModel;

import java.io.Serializable;

import com.ia.core.view.components.form.FormValidator;
import com.ia.core.view.properties.AutoCastable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Configuração base para FormViewModel.
 *
 * @author Israel Araújo
 */
@RequiredArgsConstructor
@Getter
public class FormViewModelConfig<T extends Serializable>
  implements AutoCastable {

  private final boolean readOnly;

  /**
   * Cria uma configuração padrão.
   *
   * @param readOnly Indicativo de somente leitura
   * @return Nova instância de FormViewModelConfig
   */
  public static <T extends Serializable> FormViewModelConfig<T> of(boolean readOnly) {
    return new FormViewModelConfig<>(readOnly);
  }
}
