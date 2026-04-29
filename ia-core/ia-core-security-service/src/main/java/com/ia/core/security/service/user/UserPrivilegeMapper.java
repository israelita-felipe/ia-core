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
/**
 * Classe que representa o mapeamento de dados para user privilege.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a UserPrivilegeMapper
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public interface UserPrivilegeMapper
  extends BaseEntityMapper<UserPrivilege, UserPrivilegeDTO> {

}
