package com.ia.core.service;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.event.CrudOperationType;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Interface que deleta um {@link BaseEntity} por meio de um
 * {@link BaseEntityRepository}
 * <p>
 * Para publicação de eventos de domínio, sobrescreva os métodos:
 * </p>
 * <ul>
 *   <li>{@link #beforeDelete(Long)} - Chamado antes de deletar</li>
 *   <li>{@link #afterDelete(Long, D)} - Chamado após deletar</li>
 * </ul>
 *
 * @author Israel Araújo
 * @param <T> {@link BaseEntity}
 * @param <D> {@link DTO}
 */
public interface DeleteBaseService<T extends BaseEntity, D extends DTO<?>>
  extends BaseService<T, D> {

  /**
   * Método chamado antes de deletar o registro.
   * <p>
   * Sobrescreva este método para executar lógica antes do delete,
   * como validações adicionais ou publicação de eventos.
   * </p>
   *
   * @param id Identificador do registro a ser deletado
   * @throws ServiceException em caso de erro
   */
  default void beforeDelete(Long id) throws ServiceException {
    // Default: sem ação
  }

  /**
   * Método chamado após deletar o registro.
   * <p>
   * Sobrescreva este método para executar lógica após o delete,
   * como publicação de eventos de domínio.
   * </p>
   *
   * @param id  Identificador do registro deletado
   * @param dto DTO do registro deletado
   * @throws ServiceException em caso de erro
   */
  default void afterDelete(Long id, D dto) throws ServiceException {
    // Default: sem ação
  }

  /**
   * Verifica se um objeto pode ser excluído
   *
   * @param id {@link UUID} do objeto a ser excluído
   * @return por padrão retorna <code>true</code>
   */
  default boolean canDelete(Long id) {
    return true;
  }

  /**
   * Deleta um {@link DTO} pelo seu {@link UUID}.
   *
   * @param id {@link UUID} da entidade <T>
   * @throws ServiceException caso ocorra alguma exceção
   */

  default void delete(Long id)
    throws ServiceException {
    beforeDelete(id);
    ServiceException ex = new ServiceException();
    AtomicReference<D> dtoRef = new AtomicReference<>();
    onTransaction(() -> {
      if (canDelete(id)) {
        try {
          dtoRef.set(toDTO(getRepository().findById(id).orElse(null)));
          getRepository().deleteById(id);
        } catch (Exception e) {
          ex.add(e);
        }
      }
      return id;
    });
    checkErrors(ex);
    afterDelete(id, dtoRef.get());
  }

}
