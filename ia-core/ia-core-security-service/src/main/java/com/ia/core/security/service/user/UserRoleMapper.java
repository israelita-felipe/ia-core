package com.ia.core.security.service.user;

import org.mapstruct.Mapper;

import com.ia.core.security.model.role.Role;
import com.ia.core.security.service.model.user.UserRoleDTO;
import com.ia.core.security.service.role.RolePrivilegeMapper;
import com.ia.core.service.mapper.BaseEntityMapper;

/**
 * {@link Mapper} de {@link Role} para {@link UserRoleDTO}
 *
 * @author Israel Araújo
 */
@Mapper(componentModel = "spring", uses = { RolePrivilegeMapper.class })
public interface UserRoleMapper
  extends BaseEntityMapper<Role, UserRoleDTO> {

}
