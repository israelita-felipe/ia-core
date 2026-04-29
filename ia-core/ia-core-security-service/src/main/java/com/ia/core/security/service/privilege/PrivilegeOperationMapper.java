package com.ia.core.security.service.privilege;

import com.ia.core.security.model.privilege.PrivilegeOperation;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.privilege.PrivilegeOperationDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * {@link Mapper} de {@link Autor} para {@link PrivilegeDTO}
 *
 * @author Israel Araújo
 */
@Mapper(componentModel = "spring",
        uses = { PrivilegeOperationContextMapper.class })
/**
 * Classe que representa o mapeamento de dados para privilege operation.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PrivilegeOperationMapper
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public interface PrivilegeOperationMapper
  extends BaseEntityMapper<PrivilegeOperation, PrivilegeOperationDTO> {

}
