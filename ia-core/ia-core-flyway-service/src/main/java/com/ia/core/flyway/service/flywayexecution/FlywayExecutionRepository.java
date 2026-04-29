package com.ia.core.flyway.service.flywayexecution;

import com.ia.core.flyway.model.FlywayExecution;
import com.ia.core.service.repository.BaseEntityRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Repository para entidade FlywayExecution.
 * <p>
 * Fornece métodos específicos para buscar execuções de migrations do Flyway. Os
 * dados são somente leitura pois são gerenciados automaticamente pelo Flyway.
 * </p>
 *
 * @author Israel Araújo
 */
/**
 * Classe que representa o acesso a dados de flyway execution.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a FlywayExecutionRepository
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@NoRepositoryBean
public interface FlywayExecutionRepository
  extends BaseEntityRepository<FlywayExecution> {

}
