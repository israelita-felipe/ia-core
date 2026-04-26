package com.ia.core.flyway.service.model.flywayexecution;

import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.usecase.ReadOnlyUseCase;
import org.springframework.data.domain.Page;

/**
 * Interface de Use Case para FlywayExecution.
 * <p>
 * Define as operações específicas do domínio de execuções de migrations do
 * Flyway. Como a tabela flyway_schema_history é gerenciada automaticamente pelo
 * Flyway, esta interface fornece apenas operações de leitura.
 * </p>
 *
 * @author Israel Araújo
 */
public interface FlywayExecutionUseCase
  extends ReadOnlyUseCase<FlywayExecutionDTO> {

  /**
   * Lista apenas as execuções bem-sucedidas.
   *
   * @return lista de execuções bem-sucedidas
   */
  Page<FlywayExecutionDTO> listSuccessful(SearchRequestDTO request);

  /**
   * Lista apenas as execuções falhadas.
   *
   * @return lista de execuções falhadas
   */
  Page<FlywayExecutionDTO> listFailed(SearchRequestDTO request);

}
