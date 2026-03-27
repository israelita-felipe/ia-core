package com.ia.core.flyway.service.flywayexecution;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ia.core.flyway.model.FlywayExecution;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Repository para entidade FlywayExecution.
 * <p>
 * Fornece métodos específicos para buscar execuções de migrations do Flyway.
 * Os dados são somente leitura pois são gerenciados automaticamente pelo Flyway.
 * </p>
 *
 * @author Israel Araújo
 */
public interface FlywayExecutionRepository
  extends BaseEntityRepository<FlywayExecution> {

  /**
   * Busca todas as execuções ordenadas por ID.
   *
   * @return Lista de execuções
   */
  List<FlywayExecution> findAllByOrderByIdAsc();

  /**
   * Busca execuções bem-sucedidas ordenadas por ID.
   *
   * @return Lista de execuções bem-sucedidas
   */
  List<FlywayExecution> findBySuccessTrueOrderByIdAsc();

  /**
   * Busca execuções falhadas ordenadas por ID.
   *
   * @return Lista de execuções falhadas
   */
  List<FlywayExecution> findBySuccessFalseOrderByIdAsc();

  /**
   * Busca execuções com paginação e Specification.
   *
   * @param specification a especificação de filtro
   * @param pageable      a configuração de paginação
   * @return Página de execuções
   */
  Page<FlywayExecution> findAll(
    org.springframework.data.jpa.domain.Specification<FlywayExecution> specification,
    Pageable pageable);
}