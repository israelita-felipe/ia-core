package com.ia.core.flyway.service.flywayexecution;

import org.springframework.data.repository.NoRepositoryBean;

import com.ia.core.flyway.model.FlywayExecution;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Repository para entidade FlywayExecution.
 * <p>
 * Fornece métodos específicos para buscar execuções de migrations do Flyway. Os
 * dados são somente leitura pois são gerenciados automaticamente pelo Flyway.
 * </p>
 *
 * @author Israel Araújo
 */
@NoRepositoryBean
public interface FlywayExecutionRepository
  extends BaseEntityRepository<FlywayExecution> {

}
