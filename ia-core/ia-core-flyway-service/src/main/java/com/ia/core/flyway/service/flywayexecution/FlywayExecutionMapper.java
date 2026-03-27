package com.ia.core.flyway.service.flywayexecution;

import com.ia.core.flyway.model.FlywayExecution;
import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionDTO;
import com.ia.core.service.mapper.BaseEntityMapper;

/**
 * Mapper para conversão entre FlywayExecution e FlywayExecutionDTO.
 * <p>
 * Utiliza o MapStruct para geração automática de implementação de mapeamento.
 * </p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see FlywayExecution
 * @see FlywayExecutionDTO
 */
public interface FlywayExecutionMapper
  extends BaseEntityMapper<FlywayExecution, FlywayExecutionDTO> {

}
