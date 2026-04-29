package com.ia.core.security.service.privilege;

import com.ia.core.security.model.privilege.Privilege;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * {@link Mapper} de {@link Autor} para {@link PrivilegeDTO}
 *
 * @author Israel Araújo
 */
/**
 * Classe que representa o mapeamento de dados para privilege.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PrivilegeMapper
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface PrivilegeMapper
  extends BaseEntityMapper<Privilege, PrivilegeDTO> {

}
