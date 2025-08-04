package com.ia.core.view.utils;

import com.vaadin.flow.component.Unit;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Representação de um tamanho;
 *
 * @author Israel Araújo
 */
@Data
@RequiredArgsConstructor
@SuperBuilder
public class Size {
  /** Tamanho */
  private final float size;
  /** Unidade */
  private final Unit unit;
}
