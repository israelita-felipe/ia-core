package com.ia.core.security.service.config;

import com.ia.core.security.model.user.User;
import com.ia.core.security.service.authentication.AuthenticationService;
import com.ia.core.security.service.authentication.CoreJwtAuthenticationService;
import com.ia.core.security.service.functionality.DefaultFunctionalityManager;
import com.ia.core.security.service.model.functionality.FunctionalityManager;
import com.ia.core.security.service.model.functionality.FunctionalityMapper;
import com.ia.core.security.service.model.functionality.HasFunctionality;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.privilege.PrivilegeRepository;
import com.ia.core.security.service.user.UserRepository;
import com.ia.core.service.mapper.BaseEntityMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

import java.util.Collection;

/**
 * @author Israel Araújo
 */

/**
 * Classe que representa as configurações para core security service.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a CoreSecurityServiceConfiguration
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public abstract class CoreSecurityServiceConfiguration {

    @Getter
    private final SecurityConfigurationProvider securityConfigurationProvider;


    @Bean
    AuthenticationService<?> authenticationService(UserRepository userRepository,
                                                   PrivilegeRepository privilegeRepository,
                                                   BaseEntityMapper<User, UserDTO> mapper) {
        log.info("INICIALIZANDO AUTHENTICATION SERVICE");
        return new CoreJwtAuthenticationService(userRepository,
            privilegeRepository, mapper);
    }

    @Bean
    FunctionalityManager defaultFunctionalityManager(PrivilegeRepository repository,
                                                     Collection<HasFunctionality> hasFunctionalities,
                                                     FunctionalityMapper functionalityMapper) {
        log.info("INICIALIZANDO FUNCTIONALITY MANAGER");
        return new DefaultFunctionalityManager(repository, hasFunctionalities, functionalityMapper);
    }

}
