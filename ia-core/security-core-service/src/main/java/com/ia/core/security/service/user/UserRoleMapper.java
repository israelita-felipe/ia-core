package com.ia.core.security.service.user;

import org.mapstruct.Mapper;

import com.ia.core.security.model.role.Role;
import com.ia.core.security.service.model.user.UserRoleDTO;
import com.ia.core.security.service.privilege.PrivilegeMapper;
import com.ia.core.service.mapper.BaseMapper;

/**
 * {@link Mapper} de {@link Role} para {@link UserRoleDTO}
 *
 * @author Israel Araújo
 */
@Mapper(componentModel = "spring", uses = { PrivilegeMapper.class })
public interface UserRoleMapper
  extends BaseMapper<Role, UserRoleDTO> {

}
