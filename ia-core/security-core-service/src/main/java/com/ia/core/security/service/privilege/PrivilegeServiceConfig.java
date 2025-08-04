package com.ia.core.security.service.privilege;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ia.core.security.model.privilege.Privilege;
import com.ia.core.security.service.DefaultSecuredBaseService.DefaultSecuredBaseServiceConfig;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.service.mapper.BaseMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;

/**
 *
 */
@Component
public class PrivilegeServiceConfig
  extends DefaultSecuredBaseServiceConfig<Privilege, PrivilegeDTO> {

  /**
   * @param repository
   * @param mapper
   * @param searchRequestMapper
   * @param translator
   * @param authorizationManager
   * @param logOperationService
   */
  public PrivilegeServiceConfig(BaseEntityRepository<Privilege> repository,
                                BaseMapper<Privilege, PrivilegeDTO> mapper,
                                SearchRequestMapper searchRequestMapper,
                                Translator translator,
                                CoreSecurityAuthorizationManager authorizationManager,
                                LogOperationService logOperationService,
                                List<IServiceValidator<PrivilegeDTO>> validators) {
    super(repository, mapper, searchRequestMapper, translator,
          authorizationManager, logOperationService, validators);
  }

}
