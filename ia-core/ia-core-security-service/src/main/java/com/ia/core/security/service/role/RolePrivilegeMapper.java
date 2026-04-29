package com.ia.core.security.service.role;

import com.ia.core.security.model.role.RolePrivilege;
import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.security.service.model.role.RolePrivilegeDTO;
import com.ia.core.security.service.privilege.PrivilegeMapper;
import com.ia.core.security.service.privilege.PrivilegeOperationMapper;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * {@link Mapper} de {@link Autor} para {@link RoleDTO}
 *
 * @author Israel Araújo
 */
@Mapper(componentModel = "spring",
        uses = { PrivilegeOperationMapper.class, PrivilegeMapper.class })
/**
 * Classe que representa o mapeamento de dados para role privilege.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a RolePrivilegeMapper
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public interface RolePrivilegeMapper
  extends BaseEntityMapper<RolePrivilege, RolePrivilegeDTO> {

}
