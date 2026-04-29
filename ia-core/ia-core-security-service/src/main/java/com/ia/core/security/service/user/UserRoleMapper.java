package com.ia.core.security.service.user;

import com.ia.core.security.model.role.Role;
import com.ia.core.security.service.model.user.UserRoleDTO;
import com.ia.core.security.service.role.RolePrivilegeMapper;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * {@link Mapper} de {@link Role} para {@link UserRoleDTO}
 *
 * @author Israel Araújo
 */
/**
 * Classe que representa o mapeamento de dados para user role.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a UserRoleMapper
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@Mapper(componentModel = "spring", uses = { RolePrivilegeMapper.class })
public interface UserRoleMapper
  extends BaseEntityMapper<Role, UserRoleDTO> {

}
