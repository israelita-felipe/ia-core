package com.ia.core.view.components.form.viewModel;

import java.io.Serializable;

import com.ia.core.view.properties.AutoCastable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 */
@RequiredArgsConstructor
@Getter
public class FormViewModelConfig<T extends Serializable>
  implements AutoCastable {
  private final boolean readOnly;
}
