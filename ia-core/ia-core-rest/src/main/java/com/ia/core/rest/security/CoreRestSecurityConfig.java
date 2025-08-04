package com.ia.core.rest.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ia.core.rest.filter.CoreJwtAuthenticationFilter;
import com.ia.core.rest.filter.OncePerRequestAuthenticationFilter;
import com.ia.core.security.model.authentication.JwtCoreManager;
import com.ia.core.security.service.authorization.CoreAuthorizationManager;
import com.ia.core.security.service.config.CoreSecurityServiceConfiguration;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.user.UserPasswordEncoder;
import com.ia.core.security.service.user.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Israel Araújo
 */

@Slf4j
public abstract class CoreRestSecurityConfig
  extends CoreSecurityServiceConfiguration {

  public static final String AUTHORIZATION_ENDPOINT_PATTERN = "/api/${api.version}/authentication/**";
  public static final String PUBLIC_ENDPOINT_PATTERN = "/api/${api.version}/public/**";
  public static final String LOGIN_FILTER_CHAIN_BEAN_NAME = "filter.chain.login";
  public static final String SECURITY_FILTER_CHAIN_BEAN_NAME = "filter.chain.security";
  public static final String PUBLIC_CHAIN_BEAN_NAME = "filter.chain.public";

  protected static AuthenticationEntryPoint authenticationEntryPoint() {
    log.info("INICIALIZANDO AUTHENTICATION ENTRY POINT");
    return new CoreJwtAuthenticationEntryPoint();
  }

  protected static OncePerRequestAuthenticationFilter authenticationFilter(UserDetailsService userDetailsService) {
    log.info("INICIALIZANDO AUTHORIZATION FILTER");
    return new CoreJwtAuthenticationFilter(userDetailsService);
  }

  @Bean
  static AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
    throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  static CoreSecurityAuthorizationManager authorizationManager() {
    log.info("INICIALIZANDO AUTHORIZATION MANAGER");
    return new CoreAuthorizationManager() {
      @Override
      public HasRoles getCurrentRoles() {
        return () -> {
          Authentication user = getAuthentication();
          if (user != null) {
            return user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();
          }
          return Collections.emptyList();
        };
      }

    };
  }

  /**
   * @return
   */
  public static Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  @Bean
  static UserPasswordEncoder passwordEncoder() {
    log.info("INICIALIZANDO PASSWORD ENCODER");
    return new RestPasswordEncoder();
  }

  @Bean(name = SECURITY_FILTER_CHAIN_BEAN_NAME)
  @Order(3)
  static SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                 UserDetailsService userDetailsService)
    throws Exception {

    http.securityMatcher(new AntPathRequestMatcher("/**"))
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authorize -> authorize.anyRequest()
            .authenticated())
        .exceptionHandling(handling -> handling
            .authenticationEntryPoint(authenticationEntryPoint()))
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .httpBasic(Customizer.withDefaults())
        .addFilterBefore(authenticationFilter(userDetailsService),
                         UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean(name = LOGIN_FILTER_CHAIN_BEAN_NAME)
  @Order(1)
  static SecurityFilterChain securityLoginFilterChain(HttpSecurity http,
                                                      @Value(AUTHORIZATION_ENDPOINT_PATTERN) String authorizationPattern)
    throws Exception {
    http.securityMatcher(authorizationPattern).csrf(csrf -> csrf.disable())
        .authorizeHttpRequests((requests) -> requests.anyRequest()
            .permitAll());
    return http.build();
  }

  @Bean(name = PUBLIC_CHAIN_BEAN_NAME)
  @Order(2)
  static SecurityFilterChain securityPublicFilterChain(HttpSecurity http,
                                                       @Value(PUBLIC_ENDPOINT_PATTERN) String authorizationPattern)
    throws Exception {
    http.securityMatcher(authorizationPattern, "/actuator/**")
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests((requests) -> requests.anyRequest()
            .permitAll());
    return http.build();
  }

  @Bean
  static UserDetailsService userDetailsService(UserRepository repository) {
    log.info("INICIALIZANDO USER DETAIL SERVICE");
    return new CoreUserDetailsService(repository);
  }

  private final CoreSecurityAuthorizationManager authorizationManager;

  private final UserPasswordEncoder passwordEncoder;

  private final UserDetailsService userDetailsService;

  private final AuthenticationManager authenticationManager;

  private final SecurityFilterChain loginFilterChain;

  private final SecurityFilterChain securityFilterChain;

  private final LogOperationService logOperationService;

  /**
   * @param authorizationManager
   * @param passwordEncoder
   * @param userDetailsService
   * @param authenticationManager
   * @param loginFilterChain
   * @param securityFilterChain
   * @param logOperationService
   */
  public CoreRestSecurityConfig(CoreSecurityAuthorizationManager authorizationManager,
                                UserPasswordEncoder passwordEncoder,
                                UserDetailsService userDetailsService,
                                AuthenticationManager authenticationManager,
                                @Qualifier(LOGIN_FILTER_CHAIN_BEAN_NAME) SecurityFilterChain loginFilterChain,
                                @Qualifier(SECURITY_FILTER_CHAIN_BEAN_NAME) SecurityFilterChain securityFilterChain,
                                LogOperationService logOperationService) {
    super();
    this.authorizationManager = authorizationManager;
    this.passwordEncoder = passwordEncoder;
    this.userDetailsService = userDetailsService;
    this.authenticationManager = authenticationManager;
    this.loginFilterChain = loginFilterChain;
    this.securityFilterChain = securityFilterChain;
    this.logOperationService = logOperationService;
  }

  /**
   * @param authenticationEntryPoint2
   */
  public void configure(AuthenticationEntryPoint authenticationEntryPoint2) {
    // TODO Auto-generated method stub

  }

  /**
   * @param authenticationManager2
   */
  public void configure(AuthenticationManager authenticationManager2) {
    // TODO Auto-generated method stub

  }

  /**
   * @param authorizationManager2
   */
  public void configure(CoreSecurityAuthorizationManager authorizationManager2) {

  }

  /**
   * @param logOperationService
   */
  public void configure(LogOperationService logOperationService) {
    logOperationService
        .setUserDetails((userCodeLabel, userNameLabel, map) -> {
          Authentication authentication = getAuthentication();
          if (authentication == null) {
            map.put(userCodeLabel, "anonymous");
            map.put(userNameLabel, "anonymous");
          } else {
            String token = authentication.getName();
            String userName = JwtCoreManager.get()
                .getUserNameFromJWT(token);
            String userCode = JwtCoreManager.get()
                .getUserCodeFromJWT(token);
            map.put(userCodeLabel, userCode);
            map.put(userNameLabel, userName);
          }
        });
  }

  /**
   * @param authenticationFilter2
   */
  public void configure(OncePerRequestAuthenticationFilter authenticationFilter2) {
    // TODO Auto-generated method stub

  }

  /**
   * @param securityFilterChain2
   */
  public void configure(SecurityFilterChain securityFilterChain2) {
    // TODO Auto-generated method stub

  }

  /**
   * @param userDetailsService2
   */
  public void configure(UserDetailsService userDetailsService2) {
    // TODO Auto-generated method stub

  }

  /**
   * @param passwordEncoder2
   */
  public void configure(UserPasswordEncoder passwordEncoder2) {
    // TODO Auto-generated method stub

  }

  /**
   * @param loginFilterChain2
   */
  public void configureLogin(SecurityFilterChain loginFilterChain2) {
    // TODO Auto-generated method stub

  }

  @PostConstruct
  public void restSecurityInit() {
    configure(authorizationManager);
    configure(passwordEncoder);
    configure(userDetailsService);
    configure(authenticationManager);
    configure(securityFilterChain);
    configureLogin(loginFilterChain);
    configure(logOperationService);
  }

}
