package com.ia.core.security.view.config;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.security.web.SecurityFilterChain;

import com.ia.core.security.service.model.functionality.FunctionalityManager;
import com.ia.core.security.service.model.functionality.HasFunctionality;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.model.user.UserPasswordEncoder;
import com.ia.core.security.view.authentication.AuthenticationBaseClient;
import com.ia.core.security.view.authentication.AuthenticationManager;
import com.ia.core.security.view.authentication.DefaultAuthenticationManager;
import com.ia.core.security.view.authorization.CoreSecurityViewAuthorizationManager;
import com.ia.core.security.view.authorization.CoreViewAuthorizationManager;
import com.ia.core.security.view.components.login.LoginView;
import com.ia.core.security.view.functionality.DefaultFunctionalityManager;
import com.ia.core.security.view.login.CustomAccessDeniedError;
import com.ia.core.security.view.privilege.PrivilegeManager;
import com.vaadin.flow.router.RouteAccessDeniedError;
import com.vaadin.flow.spring.security.NavigationAccessControlConfigurer;
import com.vaadin.flow.spring.security.VaadinAwareSecurityContextHolderStrategy;
import com.vaadin.flow.spring.security.VaadinAwareSecurityContextHolderStrategyConfiguration;
import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Configuração de segurança para views do Vaadin integrada com Spring Security.
 *
 * @author Israel Araújo
 */
@RequiredArgsConstructor
@Slf4j
@Import(VaadinAwareSecurityContextHolderStrategyConfiguration.class)
public abstract class CoreSecurityViewConfiguration {
  /**
   * Path de login
   */
  public static final String LOGIN_PATH = "/login";

  /** {@link CoreSecurityViewAuthorizationManager} */
  private final CoreSecurityViewAuthorizationManager authorizationManager;
  /** {@link NavigationAccessControlConfigurer} */
  private final NavigationAccessControlConfigurer navigationAccessControlConfigurer;
  /** {@link FunctionalityManager} */
  private final FunctionalityManager functionalityManager;
  /** {@link AuthenticationManager} */
  private final AuthenticationManager authenticationService;
  /** {@link RouteAccessDeniedError} */
  private final RouteAccessDeniedError accessDeniedError;
  /** {@link PasswordEncoder} */
  private final PasswordEncoder passwordEncoder;

  /**
   * * Configura a página de acesso negado
   *
   * @return {@link RouteAccessDeniedError}
   */
  @Bean
  static RouteAccessDeniedError accessDeniedError() {
    return new CustomAccessDeniedError();
  }

  /**
   * * Configura o serviço de autenticação
   *
   * @param client {@link AuthenticationBaseClient}
   * @return {@link AuthenticationManager}
   */
  @Bean
  static AuthenticationManager authenticationService(AuthenticationBaseClient client) {
    return new DefaultAuthenticationManager(client);
  }

  /**
   * * Configura o gerenciador de funcionalidades
   *
   * @param service            {@link PrivilegeManager}
   * @param hasFunctionalities Lista de funcionalidades {@link HasFunctionality}
   * @return {@link FunctionalityManager}
   */
  @Bean
  static FunctionalityManager functionalityManager(PrivilegeManager service,
                                                   List<HasFunctionality> hasFunctionalities) {
    return new DefaultFunctionalityManager(service, hasFunctionalities);
  }

  /**
   * Configura o gerenciador de autorização de visualizações
   *
   * @param authorizationManager {@link CoreSecurityViewAuthorizationManager}
   * @return {@link NavigationAccessControlConfigurer}
   */
  @Bean
  static NavigationAccessControlConfigurer navigationAccessControlConfigurer(CoreSecurityViewAuthorizationManager authorizationManager) {
    log.info("CONFIGURA navigationAccessControlConfigurer");
    return new NavigationAccessControlConfigurer()
        .withDecisionResolver(new ViewAcessDecisionResolver(authorizationManager));
  }

  /**
   * Configura o codificador de senhas
   *
   * @return {@link UserPasswordEncoder}
   */
  @Bean
  static UserPasswordEncoder passwordEncoder() {
    log.info("CONFIGURA passwordEncoder");
    return new ViewPasswordEncoder();
  }

  /**
   * Configura o executor de tarefas assíncronas com contexto de segurança
   *
   * @param strategy {@link VaadinAwareSecurityContextHolderStrategy}
   * @return {@link DelegatingSecurityContextAsyncTaskExecutor}
   */
  @Bean
  static DelegatingSecurityContextAsyncTaskExecutor securityContextAsyncTaskExecutor(VaadinAwareSecurityContextHolderStrategy strategy) {
    var delegate = new ThreadPoolTaskExecutor();
    // configure the executor
    delegate.initialize();

    var executor = new DelegatingSecurityContextAsyncTaskExecutor(delegate);
    executor.setSecurityContextHolderStrategy(strategy);
    return executor;
  }

  /**
   * @return {@link CoreSecurityViewAuthorizationManager}
   */
  @Bean
  static CoreSecurityViewAuthorizationManager viewAuthorizationManager() {
    return new CoreViewAuthorizationManager() {
      @Override
      public HasRoles getCurrentRoles() {
        return () -> {
          Authentication authentication = SecurityContextHolder.getContext()
              .getAuthentication();
          if (authentication == null) {
            return Collections.emptyList();
          }
          Object principal = authentication.getPrincipal();
          if (principal == null || !UserDTO.class.isInstance(principal)) {
            return Collections.emptyList();
          }
          UserDTO user = (UserDTO) principal;
          return user.getAllPrivileges().stream().map(PrivilegeDTO::getName)
              .toList();
        };
      }
    };
  }

  /**
   * @param customAuthenticationService {@link AuthenticationManager}
   */
  public void configure(AuthenticationManager customAuthenticationService) {

  }

  /**
   * @param customAuthorizationManager {@link CoreSecurityViewAuthorizationManager}
   */
  public void configure(CoreSecurityViewAuthorizationManager customAuthorizationManager) {

  }

  /**
   * @param customFunctionalityManager {@link FunctionalityManager}
   */
  public void configure(FunctionalityManager customFunctionalityManager) {

  }

  /**
   * @param customNavigationAccessControlConfigurer {@link NavigationAccessControlConfigurer}
   */
  public void configure(NavigationAccessControlConfigurer customNavigationAccessControlConfigurer) {
    customNavigationAccessControlConfigurer.withAnnotatedViewAccessChecker()
        .withLoginView(getLoginClass());
  }

  /**
   * @param customPasswordEncoder {@link PasswordEncoder}
   */
  public void configure(PasswordEncoder customPasswordEncoder) {

  }

  /**
   * @param customAccessDeniedError {@link RouteAccessDeniedError}
   */
  public void configure(RouteAccessDeniedError customAccessDeniedError) {

  }

  /**
   * @return Classe de login {@link LoginView}
   */
  protected Class<? extends com.vaadin.flow.component.Component> getLoginClass() {
    log.info("GET LOGIN CLASS");
    return LoginView.class;
  }

  /**
   * @return Path de login {@link #LOGIN_PATH}
   */
  protected String loginPath() {
    log.info("GET LOGIN PATH");
    return String.format("%s**", LOGIN_PATH);
  }

  /**
   * Configura o filtro de segurança
   *
   * @param http {@link HttpSecurity} a ser configurado
   * @return {@link SecurityFilterChain} configurado
   * @throws Exception caso ocorra algum erro na configuração
   */
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http)
    throws Exception {

    return http.with(VaadinSecurityConfigurer.vaadin(), configurer -> {
      configurer.loginView(getLoginClass(), loginPath());
      configure(authorizationManager);
      configure(navigationAccessControlConfigurer);
      configure(functionalityManager);
      configure(authenticationService);
      configure(accessDeniedError);
      configure(passwordEncoder);
    }).build();
  }

}
