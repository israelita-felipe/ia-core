package com.ia.core.service.usecase;

import com.ia.core.service.exception.ServiceException;

import java.io.Serializable;

/**
 * Interface de Use Case para operações de CRUD.
 * <p>
 * Define as operações básicas de criação, leitura, atualização e exclusão que
 * podem ser implementadas por qualquer service de domínio.
 * <p>
 * Esta interface estende {@link ReadOnlyUseCase} para herdar todas as operações
 * de leitura e consulta.
 *
 * @param <D> tipo do DTO
 * @author Israel Araújo
 * @see ReadOnlyUseCase
 */
public interface CrudUseCase<D extends Serializable>
  extends ReadOnlyUseCase<D> {

  /**
   * Salva um registro (cria ou atualiza).
   *
   * @param dto dados a serem salvos
   * @return DTO salvo
   */
  D save(D toSave)
    throws ServiceException;

  /**
   * Exclui um registro.
   *
   * @param id ID do registro
   */
  void delete(Long id)
    throws ServiceException;

}
