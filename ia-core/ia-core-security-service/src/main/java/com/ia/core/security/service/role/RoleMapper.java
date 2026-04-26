package com.ia.core.security.service.role;

import com.ia.core.security.model.role.Role;
import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.security.service.user.UserMapper;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * {@link Mapper} de {@link Autor} para {@link RoleDTO}
 *
 * @author Israel Araújo
 */
@Mapper(componentModel = "spring",
        uses = { UserMapper.class, RolePrivilegeMapper.class })
public interface RoleMapper
  extends BaseEntityMapper<Role, RoleDTO> {

}
