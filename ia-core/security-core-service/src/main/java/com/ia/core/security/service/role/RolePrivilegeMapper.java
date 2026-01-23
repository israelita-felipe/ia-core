package com.ia.core.security.service.role;

import org.mapstruct.Mapper;

import com.ia.core.security.model.role.RolePrivilege;
import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.security.service.model.role.RolePrivilegeDTO;
import com.ia.core.security.service.privilege.PrivilegeMapper;
import com.ia.core.security.service.privilege.PrivilegeOperationMapper;
import com.ia.core.service.mapper.BaseEntityMapper;

/**
 * {@link Mapper} de {@link Autor} para {@link RoleDTO}
 *
 * @author Israel Araújo
 */
@Mapper(componentModel = "spring",
        uses = { PrivilegeOperationMapper.class, PrivilegeMapper.class })
public interface RolePrivilegeMapper
  extends BaseEntityMapper<RolePrivilege, RolePrivilegeDTO> {

}
