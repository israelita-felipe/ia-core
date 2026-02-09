package com.ia.core.service;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.event.CrudOperationType;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Interface que salva um {@link BaseEntity} por meio de um
 * {@link BaseEntityRepository}
 * <p>
 * Para publicação de eventos de domínio, sobrescreva os métodos:
 * </p>
 * <ul>
 *   <li>{@link #beforeSave(D)} - Chamado antes de salvar</li>
 *   <li>{@link #afterSave(D, D, CrudOperationType)} - Chamado após salvar</li>
 * </ul>
 *
 * @author Israel Araújo
 * @param <T> {@link BaseEntity}
 * @param <D> {@link DTO}
 */
public interface SaveBaseService<T extends BaseEntity, D extends DTO<?>>
  extends BaseService<T, D>, ValidationBaseService<T, D> {

  /**
   * Método chamado antes de salvar o DTO.
   * <p>
   * Sobrescreva este método para executar lógica antes do save,
   * como validações adicionais ou publicação de eventos.
   * </p>
   *
   * @param toSave DTO a ser salvo
   * @throws ServiceException em caso de erro
   */
  default void beforeSave(D toSave) throws ServiceException {
    // Default: sem ação
  }

  /**
   * Método chamado após salvar o DTO.
   * <p>
   * Sobrescreva este método para executar lógica após o save,
   * como publicação de eventos de domínio.
   * </p>
   *
   * @param original     DTO original (antes do save)
   * @param saved       DTO salvo (após o save)
   * @param operationType Tipo de operação (CREATED ou UPDATED)
   * @throws ServiceException em caso de erro
   */
  default void afterSave(D original, D saved, CrudOperationType operationType)
    throws ServiceException {
    // Default: sem ação
  }

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
  default D save(D toSave)
    throws ServiceException {
    beforeSave(toSave);
    ServiceException ex = new ServiceException();
    D savedEntity = onTransaction(() -> {
      try {
        validate(toSave);
        T model = toModel(toSave);
        model = synchronize(model);
        boolean isUpdate = model.getId() != null;
        if (isUpdate && !canUpdate(toSave)) {
          return null;
        } else if (!isUpdate && !canCreate(toSave)) {
          return null;
        }
        T saved = getRepository().save(model);
        return toDTO(saved);
      } catch (Exception e) {
        ex.add(e);
      }
      return null;
    });
    checkErrors(ex);
    if (savedEntity != null) {
      CrudOperationType operationType = determineOperationType(toSave, savedEntity);
      afterSave(toSave, savedEntity, operationType);
    }
    return savedEntity;
  }

  /**
   * Determina o tipo de operação (CREATE ou UPDATE) com base nos DTOs.
   */
  private CrudOperationType determineOperationType(D original, D saved) {
    if (saved instanceof AbstractBaseEntityDTO dto && dto.getId() != null) {
      return CrudOperationType.UPDATED;
    }
    return CrudOperationType.CREATED;
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
