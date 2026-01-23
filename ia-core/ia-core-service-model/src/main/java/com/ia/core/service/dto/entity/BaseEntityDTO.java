package com.ia.core.service.dto.entity;

import java.io.Serializable;
import java.util.UUID;

import com.ia.core.model.BaseEntity;
import com.ia.core.model.HasVersion;
import com.ia.core.service.dto.DTO;

/**
 * Interface para Data Transport Object de um {@link Serializable}
 *
 * @author Israel Araújo
 * @param <T> Tipo do dado que extende {@link Serializable}
 */
public interface BaseEntityDTO<T extends BaseEntity>
  extends DTO<T>, HasVersion {
  /**
   * @return {@link UUID} do objeto.
   */
  Long getId();

}
