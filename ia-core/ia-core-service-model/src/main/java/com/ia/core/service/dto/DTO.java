package com.ia.core.service.dto;

import java.io.Serializable;

import com.ia.core.model.BaseEntity;

/**
 * Interface para Data Transport Object de uma {@link BaseEntity}
 *
 * @author Israel Araújo
 * @param <T> Tipo do dado que extende {@link BaseEntity}
 */
public interface DTO<T extends Serializable>
  extends Serializable {
  /**
   * @return {@link DTO} clone deste objeto
   */
  DTO<T> cloneObject();

  /**
   * @return Cópia deste objeto. Por padrão retorna o mesmo que
   *         {@link #cloneObject()}
   * @param <R> Tipo covariante do DTO
   */
  @SuppressWarnings("unchecked")
  default <R extends DTO<T>> R copyObject() {
    return (R) cloneObject();
  }
}
