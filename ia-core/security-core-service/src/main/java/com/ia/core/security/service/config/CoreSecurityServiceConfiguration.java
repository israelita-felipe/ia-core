package com.ia.core.security.service.config;

import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

import com.ia.core.security.model.user.User;
import com.ia.core.security.service.authentication.AuthenticationService;
import com.ia.core.security.service.authentication.CoreJwtAuthenticationService;
import com.ia.core.security.service.functionality.DefaultFunctionalityManager;
import com.ia.core.security.service.model.functionality.FunctionalityManager;
import com.ia.core.security.service.model.functionality.HasFunctionality;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.privilege.PrivilegeRepository;
import com.ia.core.security.service.user.UserRepository;
import com.ia.core.service.mapper.BaseEntityMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Israel Araújo
 */
@Slf4j
public abstract class CoreSecurityServiceConfiguration {

  @Bean
  static AuthenticationService<?> authenticationService(UserRepository userRepository,
                                                        PrivilegeRepository privilegeRepository,
                                                        BaseEntityMapper<User, UserDTO> mapper,
                                                        PlatformTransactionManager transactionManager) {
    log.info("INICIALIZANDO AUTHENTICATION SERVICE");
    return new CoreJwtAuthenticationService(userRepository,
                                            privilegeRepository, mapper,
                                            transactionManager);
  }

  @Bean
  static FunctionalityManager defaultFunctionalityManager(PrivilegeRepository repository,
                                                          Collection<HasFunctionality> hasFunctionalities) {
    log.info("INICIALIZANDO FUNCTIONALITY MANAGER");
    return new DefaultFunctionalityManager(repository, hasFunctionalities);
  }

}
