package com.ia.core.security.service.role;

import com.ia.core.security.model.role.Role;
import com.ia.core.security.service.DefaultSecuredBaseService.DefaultSecuredBaseServiceConfig;
import com.ia.core.security.service.SecurityContextService;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.security.service.user.UserRoleMapper;
import com.ia.core.service.mapper.BaseEntityMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Classe que representa as configurações para role service.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a RoleServiceConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
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
                           BaseEntityMapper<Role, RoleDTO> mapper,
                           SearchRequestMapper searchRequestMapper,
                           Translator translator,
                           CoreSecurityAuthorizationManager authorizationManager,
                           SecurityContextService securityContextService,
                           LogOperationService logOperationService,
                           List<IServiceValidator<RoleDTO>> validators,
                           UserRoleMapper userRoleMapper) {
    super(repository, mapper, searchRequestMapper, translator,
          authorizationManager, securityContextService, logOperationService,
          validators);
    this.userRoleMapper = userRoleMapper;
  }

}
