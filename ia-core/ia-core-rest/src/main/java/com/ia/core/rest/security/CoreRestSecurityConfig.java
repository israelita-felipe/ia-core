package com.ia.core.rest.security;

import java.util.Collections;
import java.util.Map;

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.transaction.PlatformTransactionManager;

import com.ia.core.rest.filter.CoreJwtAuthenticationFilter;
import com.ia.core.rest.filter.OncePerRequestAuthenticationFilter;
import com.ia.core.security.model.authentication.JwtCoreManager;
import com.ia.core.security.service.authorization.CoreAuthorizationManager;
import com.ia.core.security.service.authorization.JWTPrivilegeContext;
import com.ia.core.security.service.config.CoreSecurityServiceConfiguration;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.user.UserPasswordEncoder;
import com.ia.core.security.service.user.UserRepository;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe abstrata de configuração de segurança para APIs REST que utiliza
 * autenticação JWT. Configura múltiplas cadeias de filtro de segurança para
 * diferentes padrões de endpoints.
 * <p>
 * Esta classe estabelece três cadeias de segurança distintas:
 * <ol>
 * <li>Cadeia de login - para endpoints de autenticação</li>
 * <li>Cadeia pública - para endpoints acessíveis sem autenticação</li>
 * <li>Cadeia de segurança - para endpoints protegidos que requerem autenticação
 * JWT</li>
 * </ol>
 * </p>
 *
 * @author Israel Araújo
 * @version 1.0
 * @see SecurityFilterChain
 * @see AuthenticationManager
 * @see CoreSecurityAuthorizationManager
 */
@Slf4j
public abstract class CoreRestSecurityConfig
  extends CoreSecurityServiceConfiguration {

  /**
   * Padrão de URL para endpoints de autorização e autenticação. Utiliza
   * placeholders do Spring para versionamento da API. Exemplo:
   * /api/v1/authentication/login
   */
  public static final String AUTHORIZATION_ENDPOINT_PATTERN = "/api/${api.version}/authentication/**";

  /**
   * Padrão de URL para endpoints públicos que não requerem autenticação.
   * Exemplo: /api/v1/public/info
   */
  public static final String PUBLIC_ENDPOINT_PATTERN = "/api/${api.version}/public/**";

  /**
   * Nome do bean para a cadeia de filtros de login. Utilizado para qualificação
   * e injeção de dependência específica.
   */
  public static final String LOGIN_FILTER_CHAIN_BEAN_NAME = "filter.chain.login";

  /**
   * Nome do bean para a cadeia de filtros de segurança principal. Utilizado
   * para qualificação e injeção de dependência específica.
   */
  public static final String SECURITY_FILTER_CHAIN_BEAN_NAME = "filter.chain.security";

  /**
   * Nome do bean para a cadeia de filtros públicos. Utilizado para qualificação
   * e injeção de dependência específica.
   */
  public static final String PUBLIC_CHAIN_BEAN_NAME = "filter.chain.public";

  /**
   * Mensagem de log para inicialização do ponto de entrada de autenticação.
   */
  private static final String LOG_INICIALIZANDO_ENTRY_POINT = "Inicializando Authentication Entry Point para tratamento de falhas de autenticação";

  /**
   * Mensagem de log para inicialização do filtro de autorização JWT.
   */
  private static final String LOG_INICIALIZANDO_AUTH_FILTER = "Inicializando Authorization Filter para processamento JWT";

  /**
   * Mensagem de log para inicialização do gerenciador de autorização.
   */
  private static final String LOG_INICIALIZANDO_AUTH_MANAGER = "Inicializando Authorization Manager para controle de acesso";

  /**
   * Mensagem de log para inicialização do codificador de senhas.
   */
  private static final String LOG_INICIALIZANDO_PASSWORD_ENCODER = "Inicializando Password Encoder para codificação de senhas";

  /**
   * Mensagem de log para inicialização do serviço de detalhes do usuário.
   */
  private static final String LOG_INICIALIZANDO_USER_DETAILS_SERVICE = "Inicializando User Details Service para carregamento de dados do usuário";

  /**
   * Mensagem de log para configuração dos detalhes do usuário.
   */
  private static final String LOG_CONFIGURANDO_USER_DETAILS = "Configurando User Details para operações de log";

  /**
   * Mensagem de log para inicialização geral da configuração de segurança.
   */
  private static final String LOG_INICIALIZANDO_SEGURANCA = "Inicializando configuração de segurança REST";

  /**
   * Valor padrão para usuário não autenticado em operações de log.
   */
  private static final String USUARIO_ANONIMO = "anonymous";

  /**
   * Nome do esquema de segurança para documentação OpenAPI.
   */
  private static final String TOKEN_AUTENTICACAO = "Token de Autenticação";

  /**
   * Título da API para documentação OpenAPI.
   */
  private static final String API_GESTAO_TITULO = "API de Gestão";

  /**
   * Descrição da API para documentação OpenAPI.
   */
  private static final String API_GESTAO_DESCRICAO = "Documentação da API com JWT";

  /**
   * Gerenciador de autorização para controle de acesso baseado em roles.
   * Responsável por determinar as permissões do usuário autenticado.
   */
  private final CoreSecurityAuthorizationManager authorizationManager;

  /**
   * Codificador de senhas para processamento seguro de credenciais. Utilizado
   * para hash e verificação de senhas.
   */
  private final UserPasswordEncoder passwordEncoder;

  /**
   * Serviço para carregar dados específicos do usuário durante a autenticação.
   * Interface entre a camada de segurança e o repositório de usuários.
   */
  private final UserDetailsService userDetailsService;

  /**
   * Gerenciador de autenticação do Spring Security. Coordena o processo de
   * autenticação e validação de credenciais.
   */
  private final AuthenticationManager authenticationManager;

  /**
   * Cadeia de filtros específica para endpoints de login. Configurada com ordem
   * de prioridade 1 para processar requisições de autenticação.
   */
  private final SecurityFilterChain loginFilterChain;

  /**
   * Cadeia de filtros principal para endpoints protegidos. Configurada com
   * ordem de prioridade 3 para requisições que requerem autenticação.
   */
  private final SecurityFilterChain securityFilterChain;

  /**
   * Serviço para registro de operações de segurança. Responsável pelo log de
   * atividades e auditoria de segurança.
   */
  private final LogOperationService logOperationService;

  /**
   * Cria e retorna o AuthenticationEntryPoint para tratamento de falhas de
   * autenticação.
   *
   * @return AuthenticationEntryPoint configurado para respostas de erro de
   *         autenticação
   */
  protected static AuthenticationEntryPoint authenticationEntryPoint() {
    log.info(LOG_INICIALIZANDO_ENTRY_POINT);
    return new CoreJwtAuthenticationEntryPoint();
  }

  /**
   * Cria e retorna o filtro de autenticação JWT para processamento de tokens.
   *
   * @param userDetailsService serviço para carregar detalhes do usuário
   * @return OncePerRequestAuthenticationFilter configurado para validação JWT
   */
  protected static OncePerRequestAuthenticationFilter authenticationFilter(UserDetailsService userDetailsService) {
    log.info(LOG_INICIALIZANDO_AUTH_FILTER);
    return new CoreJwtAuthenticationFilter(userDetailsService);
  }

  /**
   * Bean para fornecer o AuthenticationManager do Spring Security.
   *
   * @param authenticationConfiguration configuração de autenticação do Spring
   * @return AuthenticationManager instância do gerenciador de autenticação
   * @throws Exception se a configuração falhar
   */
  @Bean
  static AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
    throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  /**
   * Bean para fornecer o gerenciador de autorização com suporte a roles.
   *
   * @return CoreSecurityAuthorizationManager gerenciador de autorização
   *         configurado
   */
  @Bean
  static CoreSecurityAuthorizationManager authorizationManager() {
    log.info(LOG_INICIALIZANDO_AUTH_MANAGER);
    return new CoreAuthorizationManager() {
      @Override
      public HasRoles getCurrentRoles() {
        return () -> {
          Authentication authentication = SecurityContextHolder.getContext()
              .getAuthentication();
          if (authentication == null) {
            return Collections.emptyList();
          }
          Object principal = authentication.getPrincipal();
          if (principal == null) {
            return Collections.emptyList();
          }
          return JwtCoreManager.get()
              .getFunctionalitiesFromJWT((String) principal);
        };
      }

      @Override
      public HasContextDefinitions getCurrentContextDefinitions() {
        return () -> {
          Authentication authentication = SecurityContextHolder.getContext()
              .getAuthentication();
          if (authentication == null) {
            return new JWTPrivilegeContext(Collections.emptySet());
          }
          Object principal = authentication.getPrincipal();
          if (principal == null) {
            return new JWTPrivilegeContext(Collections.emptySet());
          }
          return JwtCoreManager.get()
              .getFunctionalitiesContextFromJWT((String) principal,
                                                JWTPrivilegeContext.class);
        };
      }
    };
  }

  /**
   * Obtém a autenticação atual do contexto de segurança.
   *
   * @return Authentication objeto de autenticação atual ou null se não
   *         autenticado
   */
  public static Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  /**
   * Bean para fornecer o codificador de senhas.
   *
   * @return UserPasswordEncoder instância do codificador de senhas
   */
  @Bean
  static UserPasswordEncoder passwordEncoder() {
    log.info(LOG_INICIALIZANDO_PASSWORD_ENCODER);
    return new RestPasswordEncoder();
  }

  /**
   * Configura a cadeia de filtros de segurança principal para endpoints
   * protegidos.
   *
   * @param http               configuração de segurança HTTP
   * @param userDetailsService serviço de detalhes do usuário
   * @return SecurityFilterChain cadeia de filtros de segurança configurada
   * @throws Exception se a configuração falhar
   */
  @Bean(name = SECURITY_FILTER_CHAIN_BEAN_NAME)
  @Order(3)
  static SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                 UserDetailsService userDetailsService)
    throws Exception {
    http.securityMatcher(PathPatternRequestMatcher.withDefaults()
        .matcher("/**")).csrf(csrf -> csrf.disable())
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

  /**
   * Configura a cadeia de filtros para endpoints de login (autenticação).
   *
   * @param http                 configuração de segurança HTTP
   * @param authorizationPattern padrão de URL para endpoints de autorização
   * @return SecurityFilterChain cadeia de filtros de login configurada
   * @throws Exception se a configuração falhar
   */
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

  /**
   * Configura a cadeia de filtros para endpoints públicos.
   *
   * @param http                 configuração de segurança HTTP
   * @param authorizationPattern padrão de URL para endpoints públicos
   * @return SecurityFilterChain cadeia de filtros públicos configurada
   * @throws Exception se a configuração falhar
   */
  @Bean(name = PUBLIC_CHAIN_BEAN_NAME)
  @Order(2)
  static SecurityFilterChain securityPublicFilterChain(HttpSecurity http,
                                                       @Value(PUBLIC_ENDPOINT_PATTERN) String authorizationPattern)
    throws Exception {
    http.securityMatcher(authorizationPattern, "/actuator/**",
                         "/swagger-ui/**", "/v3/api-docs/**",
                         "/v3/api-docs.yaml", "/swagger-ui.html")
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests((requests) -> requests.anyRequest()
            .permitAll());
    return http.build();
  }

  /**
   * Bean para fornecer o serviço de detalhes do usuário.
   *
   * @param transactionManager gerenciador de transações
   * @param repository         repositório de usuários
   * @return UserDetailsService serviço de detalhes do usuário configurado
   */
  @Bean
  static UserDetailsService userDetailsService(PlatformTransactionManager transactionManager,
                                               UserRepository repository) {
    log.info(LOG_INICIALIZANDO_USER_DETAILS_SERVICE);
    return new CoreUserDetailsService(transactionManager, repository);
  }

  /**
   * Construtor principal para injeção de dependências.
   *
   * @param authorizationManager  gerenciador de autorização
   * @param passwordEncoder       codificador de senhas
   * @param userDetailsService    serviço de detalhes do usuário
   * @param authenticationManager gerenciador de autenticação
   * @param loginFilterChain      cadeia de filtros de login
   * @param securityFilterChain   cadeia de filtros de segurança
   * @param logOperationService   serviço de operações de log
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
   * Método de configuração para AuthenticationEntryPoint. Pode ser sobrescrito
   * por classes filhas para customização.
   *
   * @param authenticationEntryPoint ponto de entrada de autenticação a ser
   *                                 configurado
   */
  public void configure(AuthenticationEntryPoint authenticationEntryPoint) {
    // Implementação pode ser fornecida por classes filhas
  }

  /**
   * Método de configuração para AuthenticationManager. Pode ser sobrescrito por
   * classes filhas para customização.
   *
   * @param authenticationManager gerenciador de autenticação a ser configurado
   */
  public void configure(AuthenticationManager authenticationManager) {
    // Implementação pode ser fornecida por classes filhas
  }

  /**
   * Método de configuração para AuthorizationManager. Pode ser sobrescrito por
   * classes filhas para customização.
   *
   * @param authorizationManager gerenciador de autorização a ser configurado
   */
  public void configure(CoreSecurityAuthorizationManager authorizationManager) {
    // Implementação pode ser fornecida por classes filhas
  }

  /**
   * Configura o serviço de operações de log com provedor de detalhes do
   * usuário.
   *
   * @param logOperationService serviço de operações de log a ser configurado
   */
  public void configure(LogOperationService logOperationService) {
    log.info(LOG_CONFIGURANDO_USER_DETAILS);
    logOperationService.setUserDetails(this::configureUserDetails);
  }

  /**
   * Configura os detalhes do usuário para registro em log. Extrai informações
   * do usuário do token JWT e popula o mapa fornecido.
   *
   * @param userCodeLabel rótulo para o código do usuário
   * @param userNameLabel rótulo para o nome do usuário
   * @param map           mapa a ser populado com detalhes do usuário
   */
  protected void configureUserDetails(String userCodeLabel,
                                      String userNameLabel,
                                      Map<String, Object> map) {
    Authentication authentication = getAuthentication();
    if (authentication != null) {
      String token = authentication.getName();
      String userName = JwtCoreManager.get().getUserNameFromJWT(token);
      String userCode = JwtCoreManager.get().getUserCodeFromJWT(token);
      map.put(userCodeLabel, userCode);
      map.put(userNameLabel, userName);
    } else {
      map.put(userCodeLabel, USUARIO_ANONIMO);
      map.put(userNameLabel, USUARIO_ANONIMO);
    }
  }

  /**
   * Método de configuração para AuthenticationFilter. Pode ser sobrescrito por
   * classes filhas para customização.
   *
   * @param authenticationFilter filtro de autenticação a ser configurado
   */
  public void configure(OncePerRequestAuthenticationFilter authenticationFilter) {
    // Implementação pode ser fornecida por classes filhas
  }

  /**
   * Método de configuração para SecurityFilterChain. Pode ser sobrescrito por
   * classes filhas para customização.
   *
   * @param securityFilterChain cadeia de filtros de segurança a ser configurada
   */
  public void configure(SecurityFilterChain securityFilterChain) {
    // Implementação pode ser fornecida por classes filhas
  }

  /**
   * Método de configuração para UserDetailsService. Pode ser sobrescrito por
   * classes filhas para customização.
   *
   * @param userDetailsService serviço de detalhes do usuário a ser configurado
   */
  public void configure(UserDetailsService userDetailsService) {
    // Implementação pode ser fornecida por classes filhas
  }

  /**
   * Método de configuração para PasswordEncoder. Pode ser sobrescrito por
   * classes filhas para customização.
   *
   * @param passwordEncoder codificador de senhas a ser configurado
   */
  public void configure(UserPasswordEncoder passwordEncoder) {
    // Implementação pode ser fornecida por classes filhas
  }

  /**
   * Método de configuração específico para cadeia de filtros de login. Pode ser
   * sobrescrito por classes filhas para customização.
   *
   * @param loginFilterChain cadeia de filtros de login a ser configurada
   */
  public void configureLogin(SecurityFilterChain loginFilterChain) {
    // Implementação pode ser fornecida por classes filhas
  }

  /**
   * Inicializa a configuração de segurança após a construção do bean. Configura
   * todos os componentes de segurança através dos métodos configure.
   */
  @PostConstruct
  public void restSecurityInit() {
    log.info(LOG_INICIALIZANDO_SEGURANCA);
    configure(authorizationManager);
    configure(passwordEncoder);
    configure(userDetailsService);
    configure(authenticationManager);
    configure(securityFilterChain);
    configureLogin(loginFilterChain);
    configure(logOperationService);
  }

  /**
   * Configura a documentação OpenAPI com suporte a autenticação JWT.
   *
   * @return OpenAPI configuração da documentação da API com esquema de
   *         segurança JWT
   */
  @Bean
  static OpenAPI customOpenAPI() {
    SecurityScheme securityScheme = new SecurityScheme()
        .name(TOKEN_AUTENTICACAO).type(SecurityScheme.Type.HTTP)
        .scheme("Bearer").bearerFormat("JWT");

    return new OpenAPI()
        .info(new Info().title(API_GESTAO_TITULO).version("1.0")
            .description(API_GESTAO_DESCRICAO))
        .addSecurityItem(new SecurityRequirement()
            .addList(TOKEN_AUTENTICACAO))
        .components(new io.swagger.v3.oas.models.Components()
            .addSecuritySchemes(TOKEN_AUTENTICACAO, securityScheme));
  }
}
