package com.ia.core.security.view.config;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ia.core.security.service.model.functionality.FunctionalityManager;
import com.ia.core.security.service.model.functionality.HasFunctionality;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.model.user.UserPasswordEncoder;
import com.ia.core.security.view.authentication.AuthenticationBaseClient;
import com.ia.core.security.view.authentication.AuthenticationService;
import com.ia.core.security.view.authentication.DefaultAuthenticationService;
import com.ia.core.security.view.authorization.CoreSecurityViewAuthorizationManager;
import com.ia.core.security.view.authorization.CoreViewAuthorizationManager;
import com.ia.core.security.view.components.login.LoginView;
import com.ia.core.security.view.functionality.DefaultFunctionalityManager;
import com.ia.core.security.view.login.CustomAccessDeniedError;
import com.ia.core.security.view.privilege.PrivilegeService;
import com.vaadin.flow.router.RouteAccessDeniedError;
import com.vaadin.flow.spring.security.NavigationAccessControlConfigurer;
import com.vaadin.flow.spring.security.VaadinWebSecurity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class CoreSecurityViewConfiguration
  extends VaadinWebSecurity {
  /**
   * Path de login
   */
  public static final String LOGIN_PATH = "/login";

  @Bean
  static RouteAccessDeniedError accessDeniedError() {
    return new CustomAccessDeniedError();
  }

  @Bean
  static AuthenticationService authenticationService(AuthenticationBaseClient client) {
    return new DefaultAuthenticationService(client);
  }

  @Bean
  static FunctionalityManager functionalityManager(PrivilegeService service,
                                                   List<HasFunctionality> hasFunctionalities) {
    return new DefaultFunctionalityManager(service, hasFunctionalities);
  }

  @Bean
  static NavigationAccessControlConfigurer navigationAccessControlConfigurer(CoreSecurityViewAuthorizationManager authorizationManager) {
    log.info("CONFIGURA navigationAccessControlConfigurer");
    return new NavigationAccessControlConfigurer()
        .withRoutePathAccessChecker()
        .withDecisionResolver(new ViewAcessDecisionResolver(authorizationManager));
  }

  @Bean
  static UserPasswordEncoder passwordEncoder() {
    log.info("CONFIGURA passwordEncoder");
    return new ViewPasswordEncoder();
  }

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

  private final CoreSecurityViewAuthorizationManager authorizationManager;

  private final NavigationAccessControlConfigurer navigationAccessControlConfigurer;

  private final FunctionalityManager functionalityManager;

  private final AuthenticationService authenticationService;

  private final RouteAccessDeniedError accessDeniedError;

  private final PasswordEncoder passwordEncoder;

  /**
   * @param authenticationService2
   */
  public void configure(AuthenticationService authenticationService2) {

  }

  public void configure(CoreSecurityViewAuthorizationManager authorizationManager) {

  }

  /**
   * @param functionalityManager2
   */
  public void configure(FunctionalityManager functionalityManager2) {

  }

  @Override
  protected void configure(HttpSecurity http)
    throws Exception {
    log.info("CONFIGURA HTTPSECURITY");
    http.authorizeHttpRequests(auth -> auth
        .requestMatchers(new AntPathRequestMatcher(loginPath()))
        .permitAll())
    // .authorizeHttpRequests(auth -> auth.requestMatchers("/public/**")
    // .permitAll())
    ;
    super.configure(http);
    configure(authorizationManager);
    configure(navigationAccessControlConfigurer);
    configure(functionalityManager);
    configure(authenticationService);
    configure(accessDeniedError);
    configure(passwordEncoder);
    setLoginView(http, getLoginClass());
  }

  /**
   * @param navigationAccessControlConfigurer2
   */
  public void configure(NavigationAccessControlConfigurer navigationAccessControlConfigurer2) {

  }

  /**
   * @param passwordEncoder2
   */
  public void configure(PasswordEncoder passwordEncoder2) {

  }

  /**
   * @param accessDeniedError2
   */
  public void configure(RouteAccessDeniedError accessDeniedError2) {

  }

  /**
   * @return
   */
  protected Class<? extends com.vaadin.flow.component.Component> getLoginClass() {
    log.info("GET LOGIN CLASS");
    return LoginView.class;
  }

  /**
   * @return
   */
  protected String loginPath() {
    log.info("GET LOGIN PATH");
    return String.format("%s**", LOGIN_PATH);
  }

}
