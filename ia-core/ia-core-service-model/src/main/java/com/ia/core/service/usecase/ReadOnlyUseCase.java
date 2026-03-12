package com.ia.core.service.usecase;

import java.io.Serializable;

import org.springframework.data.domain.Page;

import com.ia.core.service.dto.request.SearchRequestDTO;

/**
 * Interface de Use Case para operações apenas de leitura e consulta.
 * <p>
 * Define as operações de busca e consulta que podem ser implementadas por
 * qualquer service de domínio que necessite apenas de operações de leitura.
 *
 * @param <D> tipo do DTO
 * @author Israel Araújo
 */
public interface ReadOnlyUseCase<D extends Serializable> {

  /**
   * Conta o total de registros com filtros.
   *
   * @param requestDTO requisição de busca
   * @return total de registros
   */
  int count(SearchRequestDTO requestDTO);

  D find(Long id);

  Page<D> findAll(SearchRequestDTO requestDTO);
}
