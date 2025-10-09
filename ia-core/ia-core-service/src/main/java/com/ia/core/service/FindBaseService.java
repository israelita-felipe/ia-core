package com.ia.core.service;

import java.util.UUID;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Interface que busca uma {@link BaseEntity} por meio de um
 * {@link BaseEntityRepository}
 *
 * @author Israel Araújo
 * @param <T> {@link BaseEntity}
 * @param <D> {@link DTO}
 */
public interface FindBaseService<T extends BaseEntity, D extends DTO<T>>
  extends BaseService<T, D> {
  /**
   * Verifica se o objeto de id passado como parâmetro pode ser buscado
   *
   * @param id {@link UUID}
   * @return <code>true</code> por padrão
   */
  default boolean canFind(UUID id) {
    return true;
  }

  /**
   * Deleta um {@link DTO} pelo seu {@link UUID}.
   *
   * @param id {@link UUID} da entidade <T>
   * @return {@link DTO} da entidade <T>, ou <code>null</code> caso a mesma não
   *         exista.
   */
  default D find(UUID id) {
    return onTransaction(() -> {
      if (canFind(id)) {
        return toDTO(getRepository().findById(id).orElse(null));
      }
      return null;
    });
  }

}
