package com.ia.core.view.components.list.viewModel;

import com.ia.core.view.properties.AutoCastable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 *
 */
/**
 * Classe que representa as configurações para list view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a ListViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@Getter
@RequiredArgsConstructor
public class ListViewModelConfig<T extends Serializable>
  implements AutoCastable {
  private final boolean readOnly;
}
