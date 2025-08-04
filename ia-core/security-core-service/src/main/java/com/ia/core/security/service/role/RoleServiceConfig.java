package com.ia.core.security.service.role;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ia.core.security.model.role.Role;
import com.ia.core.security.service.DefaultSecuredBaseService.DefaultSecuredBaseServiceConfig;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.security.service.user.UserRoleMapper;
import com.ia.core.service.mapper.BaseMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;

import lombok.Getter;

/**
 *
 */
@Component
public class RoleServiceConfig
  extends DefaultSecuredBaseServiceConfig<Role, RoleDTO> {
  @Getter
  private final UserRoleMapper userRoleMapper;

  /**
   * @param repository
   * @param mapper
   * @param searchRequestMapper
   * @param translator
   * @param authorizationManager
   * @param logOperationService
   */
  public RoleServiceConfig(BaseEntityRepository<Role> repository,
                           BaseMapper<Role, RoleDTO> mapper,
                           SearchRequestMapper searchRequestMapper,
                           Translator translator,
                           CoreSecurityAuthorizationManager authorizationManager,
                           LogOperationService logOperationService,
                           List<IServiceValidator<RoleDTO>> validators,
                           UserRoleMapper userRoleMapper) {
    super(repository, mapper, searchRequestMapper, translator,
          authorizationManager, logOperationService, validators);
    this.userRoleMapper = userRoleMapper;
  }

}
