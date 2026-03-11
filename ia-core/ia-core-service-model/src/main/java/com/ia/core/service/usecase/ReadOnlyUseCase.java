package com.ia.core.service.usecase;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

/**
 * Interface de Use Case para operações apenas de leitura e consulta.
 * <p>
 * Define as operações de busca e consulta que podem ser implementadas
 * por qualquer service de domínio que necessite apenas de operações
 * de leitura.
 *
 * @param <D> tipo do DTO
 * @author Israel Araújo
 */
public interface ReadOnlyUseCase<D extends DTO<?>> {

  /**
   * Busca todos os registros com paginação.
   *
   * @param requestDTO requisição de busca com paginação
   * @return página de DTOs
   */
  Page<D> findAll(SearchRequestDTO requestDTO);

  /**
   * Busca todos os registros.
   *
   * @return lista de DTOs
   */
  List<D> findAll();

  /**
   * Busca registros por filtros.
   *
   * @param requestDTO requisição de busca
   * @return página de DTOs
   */
  Page<D> findByFilters(SearchRequestDTO requestDTO);

  /**
   * Busca registros por filtros (retorna lista).
   *
   * @param filters filtros de busca
   * @return lista de DTOs
   */
  List<D> findByFilters(Object filters);

  /**
   * Busca um registro pelo ID.
   *
   * @param id ID do registro
   * @return DTO encontrado ou null
   */
  D findById(Long id);

  /**
   * Conta o total de registros com filtros.
   *
   * @param requestDTO requisição de busca
   * @return total de registros
   */
  int count(SearchRequestDTO requestDTO);

  /**
   * Conta o total de registros.
   *
   * @return total de registros
   */
  long count();

  /**
   * Verifica se existe um registro.
   *
   * @param id ID do registro
   * @return true se existir
   */
  boolean existsById(Long id);
}
