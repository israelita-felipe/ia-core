package com.ia.core.security.service.user;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ia.core.security.model.user.User;
import com.ia.core.security.service.DefaultSecuredBaseService.DefaultSecuredBaseServiceConfig;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.model.user.UserPasswordEncoder;
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
                           BaseMapper<User, UserDTO> mapper,
                           SearchRequestMapper searchRequestMapper,
                           Translator translator,
                           CoreSecurityAuthorizationManager authorizationManager,
                           LogOperationService logOperationService,
                           UserPasswordEncoder passwordEncoder,
                           List<IServiceValidator<UserDTO>> validators) {
    super(repository, mapper, searchRequestMapper, translator,
          authorizationManager, logOperationService, validators);
    this.passwordEncoder = passwordEncoder;
  }

}
