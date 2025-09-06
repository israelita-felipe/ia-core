package com.ia.core.view.components.list.viewModel;

import java.io.Serializable;

import com.ia.core.view.properties.AutoCastable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 */
@Getter
@RequiredArgsConstructor
public class ListViewModelConfig<T extends Serializable>
  implements AutoCastable {
  private final boolean readOnly;
}
