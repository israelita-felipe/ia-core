package com.ia.core.view.manager;

import java.io.Serializable;
import java.util.Collection;

import com.ia.core.service.dto.DTO;
import com.ia.core.view.client.SaveBaseClient;
import com.ia.core.view.exception.ValidationException;

/**
 * Interface base para serviços do tipo save.
 *
 * @author Israel Araújo
 * @param <D> Tipo de dado {@link DTO}
 */
public interface SaveBaseManager<D extends Serializable>
  extends BaseManager<D> {

  /**
   * Se é possível editar um objeto
   *
   * @param toSave Objeto a ser editado
   * @return <code>true</code> por padrão
   */
  default boolean canEdit(D toSave) {
    return true;
  }

  /**
   * Se é possível inserir o objeto
   *
   * @param toInsert Objeto a ser inserido
   * @return <code>true</code> por padrão
   */
  default boolean canInsert(D toInsert) {
    return true;
  }

  /**
   * @param dto O objeto {@link Serializable} a ser salvo.
   * @return Objeto <D> do tipo {@link Serializable} salvo.
   * @throws ValidationException exceção de validação.
   * @see SaveBaseClient#save(Serializable)
   */
  default D save(D dto)
    throws ValidationException {
    if (canInsert(dto)) {
      try {
        validate(dto);
        return ((SaveBaseClient<D>) getClient()).save(dto);
      } catch (Exception e) {
        Throwable cause = e.getCause();
        if (cause != null) {
          throw new ValidationException(cause.getMessage());
        }
        throw new ValidationException(e.getMessage());
      }
    }
    return dto;
  }

  /**
   * Atualiza um determinado objeto
   *
   * @param old     Objeto antigo
   * @param updated Novo objeto
   * @return O novo objeto atualizado
   * @throws ValidationException caso ocorram erros de validação
   */
  default D update(D old, D updated)
    throws ValidationException {
    if (canEdit(old)) {
      try {
        validate(updated);
        return ((SaveBaseClient<D>) getClient()).save(updated);
      } catch (Exception e) {
        Throwable cause = e.getCause();
        if (cause != null) {
          throw new ValidationException(cause.getMessage());
        }
        throw new ValidationException(e.getMessage());
      }
    }
    return old;
  }

  /**
   * Realiza a validação de um objeto.
   *
   * @param dto {@link DTO} a ser validado.
   * @return Coleção de erros apurados.
   */
  default Collection<String> validate(D dto) {
    return ((SaveBaseClient<D>) getClient()).validate(dto);
  }
}
