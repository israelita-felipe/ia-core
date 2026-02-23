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
public interface BaseEntityMapper<T extends Serializable, D extends DTO<T>>
  extends Mapper<T, D> {
}
