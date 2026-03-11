package com.ia.core.service.usecase;

import java.util.List;

import com.ia.core.service.dto.DTO;

/**
 * Interface de Use Case para operações de CRUD.
 * <p>
 * Define as operações básicas de criação, leitura, atualização e exclusão
 * que podem ser implementadas por qualquer service de domínio.
 * <p>
 * Esta interface estende {@link ReadOnlyUseCase} para herdar todas as
 * operações de leitura e consulta.
 *
 * @param <D> tipo do DTO
 * @author Israel Araújo
 * @see ReadOnlyUseCase
 */
public interface CrudUseCase<D extends DTO<?>> extends ReadOnlyUseCase<D> {

  /**
   * Salva um registro (cria ou atualiza).
   *
   * @param dto dados a serem salvos
   * @return DTO salvo
   */
  D save(D dto);

  /**
   * Atualiza um registro existente.
   *
   * @param id ID do registro
   * @param dto dados a serem atualizados
   * @return DTO atualizado
   */
  D update(Long id, D dto);

  /**
   * Exclui um registro.
   *
   * @param id ID do registro
   */
  void delete(Long id);

  /**
   * Exclui múltiplos registros.
   *
   * @param ids IDs dos registros
   */
  void deleteAll(List<Long> ids);
}
