package com.ia.core.security.service.log.operation;

import org.mapstruct.Mapper;

import com.ia.core.security.model.log.operation.LogOperation;
import com.ia.core.security.service.model.log.operation.LogOperationDTO;
import com.ia.core.service.mapper.BaseEntityMapper;

/**
 * {@link Mapper} de {@link LogOperation} para {@link LogOperationDTO}
 *
 * @author Israel Araújo
 */
@Mapper(componentModel = "spring")
public interface LogOperationMapper
  extends BaseEntityMapper<LogOperation, LogOperationDTO> {

}
