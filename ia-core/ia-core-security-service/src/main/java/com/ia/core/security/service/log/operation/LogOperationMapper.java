package com.ia.core.security.service.log.operation;

import com.ia.core.security.model.log.operation.LogOperation;
import com.ia.core.security.service.model.log.operation.LogOperationDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * {@link Mapper} de {@link LogOperation} para {@link LogOperationDTO}
 *
 * @author Israel Araújo
 */
/**
 * Classe que representa o mapeamento de dados para log operation.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a LogOperationMapper
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface LogOperationMapper
  extends BaseEntityMapper<LogOperation, LogOperationDTO> {

}
