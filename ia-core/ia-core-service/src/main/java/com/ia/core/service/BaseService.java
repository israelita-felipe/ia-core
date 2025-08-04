package com.ia.core.service;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.mapper.BaseMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;

/**
 * Interface base para criação de um serviço.
 *
 * @author Israel Araújo
 * @param <T> {@link BaseEntity}
 * @param <D> {@link DTO}
 */
public interface BaseService<T extends BaseEntity, D extends DTO<T>> {

  /**
   * {@link BaseMapper}
   *
   * @param <M> Tipo do Mapper
   * @return {@link BaseMapper}
   */
  <M extends BaseMapper<T, D>> M getMapper();

  /**
   * {@link BaseEntityRepository}
   *
   * @param <R> Tipo do Repositório.
   * @return {@link BaseEntityRepository}
   */
  <R extends BaseEntityRepository<T>> R getRepository();

  /**
   * @return {@link SearchRequestMapper}
   */
  SearchRequestMapper getSearchRequestMapper();

  /**
   * @return {@link Translator} padrão
   */
  Translator getTranslator();

  /**
   * Realiza o mapeamento para {@link DTO}
   *
   * @param model {@link BaseEntity}
   * @return {@link DTO}
   */
  default D toDTO(T model) {
    return getMapper().toDTO(model);
  }

  /**
   * Realiza o mapeamento para o modelo
   *
   * @param dto {@link DTO}
   * @return {@link BaseEntity}
   */
  default T toModel(D dto) {
    return getMapper().toModel(dto);
  }
}
