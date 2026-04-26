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
public interface PrivilegeOperationMapper
  extends BaseEntityMapper<PrivilegeOperation, PrivilegeOperationDTO> {

}
