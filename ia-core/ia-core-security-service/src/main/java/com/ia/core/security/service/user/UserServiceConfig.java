package com.ia.core.security.service.user;

import com.ia.core.security.model.user.User;
import com.ia.core.security.service.DefaultSecuredBaseService.DefaultSecuredBaseServiceConfig;
import com.ia.core.security.service.SecurityContextService;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.model.user.UserPasswordEncoder;
import com.ia.core.service.mapper.BaseEntityMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Classe que representa as configurações para user service.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a UserServiceConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

@Component
public class UserServiceConfig
  extends DefaultSecuredBaseServiceConfig<User, UserDTO> {

  @Getter
  private final UserPasswordEncoder passwordEncoder;

  /**
   * @param repository
   * @param mapper
   * @param searchRequestMapper
   * @param translator
   * @param authorizationManager
   * @param logOperationService
   * @param passwordEncoder
   */
  public UserServiceConfig(BaseEntityRepository<User> repository,
                           BaseEntityMapper<User, UserDTO> mapper,
                           SearchRequestMapper searchRequestMapper,
                           Translator translator,
                           CoreSecurityAuthorizationManager authorizationManager,
                           SecurityContextService securityContextService,
                           LogOperationService logOperationService,
                           UserPasswordEncoder passwordEncoder,
                           List<IServiceValidator<UserDTO>> validators) {
    super(repository, mapper, searchRequestMapper, translator,
          authorizationManager, securityContextService, logOperationService,
          validators);
    this.passwordEncoder = passwordEncoder;
  }

}
