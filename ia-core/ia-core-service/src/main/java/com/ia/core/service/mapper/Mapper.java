package com.ia.core.service.mapper;

import java.io.Serializable;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.dto.DTO;

/**
 * @param <T>
 * @param <D>
 */
public interface Mapper<T extends Serializable, D extends DTO<?>> {

  /**
   * Mapeamento {@link BaseEntity} to {@link DTO}
   *
   * @param t {@link BaseEntity}
   * @return {@link DTO}
   */
  D toDTO(T t);

  /**
   * Mapeamento {@link DTO} para {@link BaseEntity}
   *
   * @param dto {@link DTO}
   * @return {@link BaseEntity}
   */
  T toModel(D dto);

}
