package com.ia.core.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Interface que salva um {@link BaseEntity} por meio de um
 * {@link BaseEntityRepository}
 *
 * @author Israel Araújo
 * @param <T> {@link BaseEntity}
 * @param <D> {@link DTO}
 */
public interface SaveBaseService<T extends BaseEntity, D extends DTO<T>>
  extends BaseService<T, D>, ValidationBaseService<T, D> {

  /**
   * Verifica se o objeto pode ser criado
   *
   * @param toSave Objeto a ser salvo
   * @return <code>true</code> por padrão
   */
  default boolean canCreate(D toSave) {
    return true;
  }

  /**
   * Verifica se o objeto pode ser atualizado
   *
   * @param toSave objeto a ser salvo
   * @return <code>true</code> por padrão
   */
  default boolean canUpdate(D toSave) {
    return true;
  }

  /**
   * Salva um {@link DTO} e retorna um {@link DTO}.
   *
   * @param toSave Objeto a ser salvo (criado ou atualizado).
   * @return {@link DTO}
   * @throws ServiceException exceção lançada ao validar o dto
   * @see ValidationBaseService
   */
  @Transactional(propagation = Propagation.NESTED)
  default D save(D toSave)
    throws ServiceException {
    validate(toSave);
    T model = toModel(toSave);
    model = synchronize(model);
    if (model.getId() != null && !canUpdate(toSave)) {
      return null;
    } else if (model.getId() == null && !canCreate(toSave)) {
      return null;
    }
    T saved = getRepository().save(model);
    return toDTO(saved);
  }

  /**
   * Realiza a syncronização do modelo com a base de dados, ou outras
   * associações
   *
   * @param model Objeto a ser sincronizado
   * @return por padrão retorna o modelo passado como parâmetro sem modificações
   * @throws ServiceException caso ocorra algum erro no processo
   */
  default T synchronize(T model)
    throws ServiceException {
    return model;
  }

}
