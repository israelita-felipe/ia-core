package com.ia.core.security.service.user;

import com.ia.core.security.model.role.Role;
import com.ia.core.security.model.user.UserPrivilege;
import com.ia.core.security.service.model.user.UserPrivilegeDTO;
import com.ia.core.security.service.model.user.UserRoleDTO;
import com.ia.core.security.service.privilege.PrivilegeMapper;
import com.ia.core.security.service.privilege.PrivilegeOperationMapper;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * {@link Mapper} de {@link Role} para {@link UserRoleDTO}
 *
 * @author Israel Araújo
 */
@Mapper(componentModel = "spring",
        uses = { PrivilegeMapper.class, PrivilegeOperationMapper.class })
public interface UserPrivilegeMapper
  extends BaseEntityMapper<UserPrivilege, UserPrivilegeDTO> {

}
