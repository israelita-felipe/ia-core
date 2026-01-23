package com.ia.core.security.service.privilege;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import com.ia.core.security.model.privilege.Privilege;
import com.ia.core.security.service.DefaultSecuredBaseService.DefaultSecuredBaseServiceConfig;
import com.ia.core.security.service.SecurityContextService;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.user.UserPrivilegeMapper;
import com.ia.core.service.mapper.BaseEntityMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;

import lombok.Getter;

/**
 *
 */
@Component
public class PrivilegeServiceConfig
  extends DefaultSecuredBaseServiceConfig<Privilege, PrivilegeDTO> {
  @Getter
  private final UserPrivilegeMapper userPrivilegeMapper;

  /**
   * @param repository
   * @param mapper
   * @param searchRequestMapper
   * @param translator
   * @param authorizationManager
   * @param logOperationService
   */
  public PrivilegeServiceConfig(PlatformTransactionManager transactionManager,
                                BaseEntityRepository<Privilege> repository,
                                BaseEntityMapper<Privilege, PrivilegeDTO> mapper,
                                SearchRequestMapper searchRequestMapper,
                                Translator translator,
                                CoreSecurityAuthorizationManager authorizationManager,
                                SecurityContextService securityContextService,
                                LogOperationService logOperationService,
                                List<IServiceValidator<PrivilegeDTO>> validators,
                                UserPrivilegeMapper userPrivilegeMapper) {
    super(transactionManager, repository, mapper, searchRequestMapper,
          translator, authorizationManager, securityContextService,
          logOperationService, validators);
    this.userPrivilegeMapper = userPrivilegeMapper;
  }

}
