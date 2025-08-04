package com.ia.core.security.service.role;

import org.mapstruct.Mapper;

import com.ia.core.security.model.role.Role;
import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.security.service.privilege.PrivilegeMapper;
import com.ia.core.security.service.user.UserMapper;
import com.ia.core.service.mapper.BaseMapper;

/**
 * {@link Mapper} de {@link Autor} para {@link RoleDTO}
 *
 * @author Israel Araújo
 */
@Mapper(componentModel = "spring",
        uses = { UserMapper.class, PrivilegeMapper.class })
public interface RoleMapper
  extends BaseMapper<Role, RoleDTO> {

}
