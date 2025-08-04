package com.ia.core.security.service.privilege;

import org.mapstruct.Mapper;

import com.ia.core.security.model.privilege.Privilege;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.service.mapper.BaseMapper;

/**
 * {@link Mapper} de {@link Autor} para {@link PrivilegeDTO}
 *
 * @author Israel Araújo
 */
@Mapper(componentModel = "spring")
public interface PrivilegeMapper
  extends BaseMapper<Privilege, PrivilegeDTO> {

}
