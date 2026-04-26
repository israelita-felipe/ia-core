package com.ia.core.security.service.user;

import com.ia.core.security.model.user.User;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.privilege.PrivilegeMapper;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * {@link Mapper} de {@link Autor} para {@link UserDTO}
 *
 * @author Israel Araújo
 */
@Mapper(componentModel = "spring",
        uses = { UserRoleMapper.class, PrivilegeMapper.class })
public interface UserMapper
  extends BaseEntityMapper<User, UserDTO> {

}
