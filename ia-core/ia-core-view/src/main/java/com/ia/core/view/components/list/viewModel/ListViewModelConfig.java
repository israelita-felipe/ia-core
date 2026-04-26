package com.ia.core.view.components.list.viewModel;

import com.ia.core.view.properties.AutoCastable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 *
 */
@Getter
@RequiredArgsConstructor
public class ListViewModelConfig<T extends Serializable>
  implements AutoCastable {
  private final boolean readOnly;
}
