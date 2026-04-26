package com.ia.core.service.dto.filter;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * Propriedade de um filtro
 */
@Data
@RequiredArgsConstructor
@Builder
public class FilterProperty
  implements Serializable {
  /** Serial UID */
  private static final long serialVersionUID = -6067348806206192209L;
  /** Propriedade */
  private final String property;
  /** Título do filtro */
  private final String label;
}
