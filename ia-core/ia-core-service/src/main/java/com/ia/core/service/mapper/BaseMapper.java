package com.ia.core.service.mapper;

import java.io.Serializable;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.dto.DTO;

/**
 * Mapeador bidirecional {@link BaseEntity} <-> {@link DTO} utilizando
 * Mapstruct.
 *
 * @author Israel Araújo
 * @param <T> Tipo que extende {@link BaseEntity}
 * @param <D> Tipo que extende {@link DTO}
 */
public interface BaseMapper<T extends Serializable, D extends DTO<T>> {

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
