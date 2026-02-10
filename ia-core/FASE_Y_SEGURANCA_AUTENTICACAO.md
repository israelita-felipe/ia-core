# FASE Y: Segurança e Autenticação

## Plano de Refatoração para Padrões SOLID, Clean Architecture e Clean Code

**Versão:** 1.0  
**Data:** 2024-02-10  
**Autor:** Israel Araújo  
**Status:** Planejamento

---

## 1. Análise da Situação Atual

### 1.1 Arquitetura de Segurança Identificada

A implementação atual de segurança do ia-core-apps segue uma estrutura baseada em:

```
ia-core/
├── ia-core-rest/
│   └── security/
│       ├── CoreRestSecurityConfig.java      (Configuração principal)
│       ├── CoreJwtAuthenticationFilter.java  (Filtro JWT)
│       ├── CoreJwtAuthenticationEntryPoint.java
│       ├── CoreUserDetailsService.java
│       └── RestPasswordEncoder.java
├── ia-core-security-model/                   (Módulo vazio - necessário criar)
├── ia-core-security-service-model/           (Módulo vazio - necessário criar)
├── ia-core-security-view/                    (Implementação para UI)
└── security-core-service/                     (Diretório vazio)
```

### 1.2 Componentes de Segurança Existentes

| Componente | Responsabilidade | Localização |
|------------|-------------------|-------------|
| `CoreRestSecurityConfig` | Configuração de filtros de segurança | ia-core-rest |
| `CoreJwtAuthenticationFilter` | Validação de tokens JWT | ia-core-rest |
| `CoreJwtAuthenticationEntryPoint` | Tratamento de falhas de autenticação | ia-core-rest |
| `CoreUserDetailsService` | Carregamento de detalhes do usuário | ia-core-rest |
| `OncePerRequestAuthenticationFilter` | Filtro base abstrato | ia-core-rest |
| `AuthenticationBaseController` | Endpoints de autenticação | ia-core-rest |

### 1.3 Patterns SOLID Identificados

#### Satisfatórios ✅
- **SRP**: `CoreJwtAuthenticationFilter` tem responsabilidade única de extrair token
- **OCP**: `CoreRestSecurityConfig` é extensível via métodos `configure()`
- **DIP**: Filtros dependem de abstrações (`UserDetailsService`)

#### Violações Identificadas ⚠️
- **ISP**: `CoreUserDetailsService` carrega dados desnecessários
- **SRP**: `CoreRestSecurityConfig` mistura configuração e log de detalhes

---

## 2. Problemas Identificados

### 2.1 Problemas Arquiteturais

| # | Problema | Impacto | Severidade |
|---|----------|---------|------------|
| 1 | Módulos de segurança vazios | Arquitetura incompleta | Alta |
| 2 | Dependência circular entre rest e security | Acoplamento alto | Média |
| 3 | Falta de testes de segurança | Risco de vulnerabilidades | Alta |
| 4 | Token JWT hardcoded como "String" | Falta de tipo específico | Média |
| 5 | PasswordEncoder espalhado | SRP violado | Baixa |

### 2.2 Problemas de Código

```java
// PROBLEMA 1: Falta de tipo específico para Token
// Em CoreJwtAuthenticationFilter.java
String token = getTokenFromRequest(request);  // ❌ String genérica
UsernamePasswordAuthenticationToken tokenObj = ...;

// PROBLEMA 2: SRP Violado em CoreRestSecurityConfig
// Mistura configuração + logging + detalhes do usuário
public void configureUserDetailsForLogging(...) {  // ❌ Não é configuração
    // Lógica de extração de dados do usuário
}

// PROBLEMA 3: Exceções de segurança em package errado
// Em ia-core-rest/error/CoreRestControllerAdvice.java
@ExceptionHandler(AuthenticationException.class)  // ❌ Exceção de security em rest
```

---

## 3. Plano de Refatoração Granular

### **PASSO Y1: Criar Módulos de Segurança Corretos**

**Objetivo:** Estabelecer estrutura de módulos seguindo Clean Architecture

**Entregáveis:**
1. `ia-core-security-model/` - Entidades e modelos de domínio
2. `ia-core-security-service/` - Serviços de negócio (NOVO)
3. `ia-core-security-service-model/` - DTOs e translators
4. `ia-core-security-rest/` - Controllers e configuração REST

**Refatoração Y1.1: Mover modelos de domínio**
```
De: ia-core-rest/security/*.java
Para: ia-core-security-model/src/main/java/com/ia/core/security/model/

Entidades a mover:
├── authentication/
│   ├── AuthenticationRequest.java
│   ├── AuthenticationResponse.java
│   ├── JwtToken.java          [NOVO - Wrapper para String]
│   └── JwtManager.java
├── user/
│   ├── User.java
│   ├── UserPrivilege.java
│   └── UserPasswordEncoder.java  [INTERFACE]
├── role/
│   ├── Role.java
│   ├── RolePrivilege.java
│   └── RoleFunctionality.java
├── privilege/
│   ├── Privilege.java
│   └── Functionality.java
└── authorization/
    ├── CoreSecurityAuthorizationManager.java
    └── JWTPrivilegeContext.java
```

**Refatoração Y1.2: Criar interfaces de serviço**
```java
// ia-core-security-service/src/main/java/com/ia/core/security/service/
public interface AuthenticationService<T extends AuthenticationRequest> {
    AuthenticationResponse login(T request, PasswordValidator validator);
    boolean initializeSecurity();
    User createFirstUser(T request);
}

public interface TokenService {
    JwtToken generateToken(UserDetails user);
    String getUserCodeFromToken(JwtToken token);
    boolean validateToken(JwtToken token);
    Claims getClaims(JwtToken token);
}
```

**Tipo de Refatoração:** Extract Module + Move Class  
**Resolves:** #1 - Arquitetura incompleta, #2 - Acoplamento alto

---

### **PASSO Y2: Criar Tipo Específico para JWT Token**

**Objetivo:** Aplicar Type-Driven Design para maior segurança de tipos

**Refatoração Y2.1: Criar classe `JwtToken`**
```java
/**
 * Wrapper type-safe para tokens JWT.
 * Garante que apenas tokens válidos são aceitos em operações sensíveis.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public final class JwtToken implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String value;
    private final Instant issuedAt;
    private final Instant expiration;

    private JwtToken(String value, Instant issuedAt, Instant expiration) {
        this.value = Objects.requireNonNull(value, "Token value cannot be null");
        this.issuedAt = issuedAt;
        this.expiration = expiration;
    }

    /**
     * Cria um JwtToken a partir de uma string raw do token.
     *
     * @param tokenValue string do token JWT
     * @return JwtToken instância encapsulada
     * @throws IllegalArgumentException se o token for nulo ou vazio
     */
    public static JwtToken from(String tokenValue) {
        if (tokenValue == null || tokenValue.isBlank()) {
            throw new IllegalArgumentException("Token JWT não pode ser nulo ou vazio");
        }
        return new JwtToken(tokenValue, Instant.now(), Instant.now().plusSeconds(3600));
    }

    /**
     * Cria um JwtToken a partir de um token existente com claims extraídas.
     *
     * @param tokenValue string do token JWT
     * @param claims claims extraídas do token
     * @return JwtToken com metadados
     */
    public static JwtToken withClaims(String tokenValue, Claims claims) {
        return new JwtToken(
            tokenValue,
            claims.getIssuedAt().toInstant(),
            claims.getExpiration().toInstant()
        );
    }

    public String getValue() { return value; }
    public Instant getIssuedAt() { return issuedAt; }
    public Instant getExpiration() { return expiration; }
    public boolean isExpired() { return Instant.now().isAfter(expiration); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JwtToken jwtToken = (JwtToken) o;
        return Objects.equals(value, jwtToken.value);
    }

    @Override
    public int hashCode() { return Objects.hash(value); }

    @Override
    public String toString() {
        return "JwtToken[prefix=" + value.substring(0, Math.min(10, value.length())) + "...]";
    }
}
```

**Refatoração Y2.2: Atualizar `CoreJwtAuthenticationFilter`**
```java
// ANTES
String token = getTokenFromRequest(request);
String userCode = JwtManager.get().getUserCodeFromJWT(token);

// DEPOIS
JwtToken jwtToken = extractToken(request)
    .orElseThrow(() -> new InvalidTokenException("Token não encontrado"));

UserDetails user = userDetailsService.loadUserByUsername(
    jwtToken.getClaims().getSubject()
);
```

**Refatoração Y2.3: Criar exceção específica `InvalidTokenException`**
```java
/**
 * Exceção lançada quando um token JWT é inválido, expirado ou malformado.
 *
 * @author Israel Araújo
 */
public class InvalidTokenException extends SecurityException {
    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

**Tipo de Refatoração:** Introduce Parameter Object + Introduce Null Object  
**Resolves:** #4 - Token JWT como String genérica

---

### **PASSO Y3: Aplicar SRP em CoreRestSecurityConfig**

**Objetivo:** Separar responsabilidades de configuração, logging e detalhes

**Refatoração Y3.1: Extrair `SecurityLoggingService`**
```java
/**
 * Serviço dedicado para logging de operações de segurança.
 *遵循 SRP - única responsabilidade é registrar eventos de segurança.
 *
 * @author Israel Araújo
 */
@Slf4j
@Service
public class SecurityLoggingService {

    /**
     * Registra tentativa de autenticação bem-sucedida.
     *
     * @param userCode código do usuário
     * @param ipAddress endereço IP
     */
    public void logSuccessfulAuthentication(String userCode, String ipAddress) {
        log.info("Autenticação bem-sucedida para usuário: {} - IP: {}", userCode, ipAddress);
    }

    /**
     * Registra tentativa de autenticação falhada.
     *
     * @param userCode código do usuário
     * @param ipAddress endereço IP
     * @param reason motivo da falha
     */
    public void logFailedAuthentication(String userCode, String ipAddress, String reason) {
        log.warn("Falha na autenticação para usuário: {} - IP: {} - Motivo: {}",
                 userCode, ipAddress, reason);
    }

    /**
     * Registra acesso autorizado a recurso protegido.
     *
     * @param userCode código do usuário
     * @param resource recurso acessado
     * @param method método HTTP
     */
    public void logAuthorizedAccess(String userCode, String resource, String method) {
        log.debug("Acesso autorizado - Usuário: {} - Recurso: {} - Método: {}",
                  userCode, resource, method);
    }

    /**
     * Registra acesso negado a recurso protegido.
     *
     * @param userCode código do usuário
     * @param resource recurso acessado
     * @param reason motivo da negação
     */
    public void logDeniedAccess(String userCode, String resource, String reason) {
        log.warn("Acesso negado - Usuário: {} - Recurso: {} - Motivo: {}",
                 userCode, resource, reason);
    }
}
```

**Refatoração Y3.2: Extrair `TokenDetailsExtractor`**
```java
/**
 * Serviço dedicado para extrair detalhes do usuário de tokens JWT.
 *遵循 SRP - única responsabilidade é processar tokens.
 *
 * @author Israel Araújo
 */
@Slf4j
@Component
public class TokenDetailsExtractor {

    private final JwtManager jwtManager;

    public TokenDetailsExtractor(JwtManager jwtManager) {
        this.jwtManager = jwtManager;
    }

    /**
     * Extrai detalhes do usuário de um token JWT.
     *
     * @param token token JWT
     * @return mapa com detalhes do usuário
     */
    public Map<String, Object> extractDetails(JwtToken token) {
        Map<String, Object> details = new HashMap<>();

        String userName = jwtManager.getUserNameFromJWT(token.getValue());
        String userCode = jwtManager.getUserCodeFromJWT(token.getValue());
        Collection<String> functionalities = jwtManager.getFunctionalitiesFromJWT(token.getValue());

        details.put("userName", userName);
        details.put("userCode", userCode);
        details.put("functionalities", functionalities);

        log.debug("Detalhes extraídos do token para usuário: {}", userCode);
        return details;
    }

    /**
     * Extrai o nome de usuário do token.
     *
     * @param token token JWT
     * @return nome do usuário
     */
    public String extractUserName(JwtToken token) {
        return jwtManager.getUserNameFromJWT(token.getValue());
    }

    /**
     * Extrai o código do usuário do token.
     *
     * @param token token JWT
     * @return código do usuário
     */
    public String extractUserCode(JwtToken token) {
        return jwtManager.getUserCodeFromJWT(token.getValue());
    }
}
```

**Refatoração Y3.3: Simplificar `CoreRestSecurityConfig`**
```java
/**
 * Configuração abstrata de segurança REST.
 *(Configuração pura, sem lógica de negócio)
 *
 * @author Israel Araújo
 * @version 2.0
 */
@Slf4j
public abstract class CoreRestSecurityConfig
  extends CoreSecurityServiceConfiguration {

    // Constantes de configuração (mantidas)
    public static final String AUTHORIZATION_ENDPOINT_PATTERN = "/api/${api.version}/authentication/**";
    public static final String PUBLIC_ENDPOINT_PATTERN = "/api/${api.version}/public/**";
    // ... outras constantes

    // Dependências extraídas
    private final SecurityLoggingService loggingService;
    private final TokenDetailsExtractor tokenDetailsExtractor;

    // Métodos de configuração (simplificados)
    @Bean
    static SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   UserDetailsService userDetailsService) {
        http.securityMatcher(PathPatternRequestMatcher.withDefaults().matcher("/**"))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
            .exceptionHandling(handling -> handling
                .authenticationEntryPoint(authenticationEntryPoint()))
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .httpBasic(Customizer.withDefaults())
            .addFilterBefore(authenticationFilter(userDetailsService),
                             UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // Métodos de conveniência removidos do CoreRestSecurityConfig
    // Passam a ser responsabilidade de TokenDetailsExtractor
}
```

**Tipo de Refatoração:** Extract Class + Move Method  
**Resolves:** #2 - SRP Violado em CoreRestSecurityConfig

---

### **PASSO Y4: Implementar Validação de Token Robusta**

**Objetivo:** Adicionar validações de segurança faltantes

**Refatoração Y4.1: Criar `TokenValidator` com validações**
```java
/**
 * Componente para validação completa de tokens JWT.
 * Implementa validações de segurança seguindo melhores práticas.
 *
 * @author Israel Araújo
 */
@Slf4j
@Component
public class TokenValidator {

    private final JwtManager jwtManager;

    public TokenValidator(JwtManager jwtManager) {
        this.jwtManager = jwtManager;
    }

    /**
     * Validação completa de um token JWT.
     *
     * @param token token a ser validado
     * @return TokenValidationResult resultado da validação
     */
    public TokenValidationResult validate(JwtToken token) {
        if (token == null) {
            return TokenValidationResult.failure("Token não pode ser nulo");
        }

        try {
            // Validação 1: Estrutura básica
            if (!isValidStructure(token.getValue())) {
                return TokenValidationResult.failure("Estrutura de token inválida");
            }

            // Validação 2: Expiração
            if (token.isExpired()) {
                log.warn("Token expirado para validação");
                return TokenValidationResult.failure("Token expirado");
            }

            // Validação 3: Issuer
            String issuer = jwtManager.getIssuerFromJWT(token.getValue());
            if (!isValidIssuer(issuer)) {
                return TokenValidationResult.failure("Issuer inválido");
            }

            // Validação 4: Audience (se configurado)
            if (!hasValidAudience(token.getValue())) {
                return TokenValidationResult.failure("Audience inválida");
            }

            return TokenValidationResult.success();

        } catch (ExpiredJwtException e) {
            log.warn("Token expirado durante validação: {}", e.getMessage());
            return TokenValidationResult.failure("Token expirado");
        } catch (JwtException e) {
            log.error("Erro na validação do token: {}", e.getMessage());
            return TokenValidationResult.failure("Token inválido");
        }
    }

    private boolean isValidStructure(String token) {
        String[] parts = token.split("\\.");
        return parts.length == 3; // header.payload.signature
    }

    private boolean isValidIssuer(String issuer) {
        // Validar contra issuers permitidos
        Set<String> allowedIssuers = Set.of("ia-core", "gestor-igreja");
        return allowedIssuers.contains(issuer);
    }

    private boolean hasValidAudience(String token) {
        // Implementar validação de audience se necessário
        return true;
    }
}

/**
 * Resultado de validação de token.
 *遵循 Value Object pattern - imutável e auto-validável.
 */
public final class TokenValidationResult {
    private final boolean valid;
    private final String errorMessage;

    private TokenValidationResult(boolean valid, String errorMessage) {
        this.valid = valid;
        this.errorMessage = errorMessage;
    }

    public static TokenValidationResult success() {
        return new TokenValidationResult(true, null);
    }

    public static TokenValidationResult failure(String message) {
        return new TokenValidationResult(false, message);
    }

    public boolean isValid() { return valid; }
    public Optional<String> getErrorMessage() { return Optional.ofNullable(errorMessage); }
}
```

**Refatoração Y4.2: Atualizar `CoreJwtAuthenticationFilter`**
```java
@Override
public Authentication getAuthentication(HttpServletRequest request)
    throws UserNotFoundException {

    JwtToken jwtToken = extractToken(request)
        .orElseThrow(() -> new InvalidTokenException("Token não encontrado na requisição"));

    TokenValidationResult validation = tokenValidator.validate(jwtToken);
    if (!validation.isValid()) {
        log.warn("Validação de token falhou: {}", validation.getErrorMessage());
        throw new InvalidTokenException(validation.getErrorMessage().orElse("Token inválido"));
    }

    UserDetails user = getUserByUserCode(
        jwtManager.getUserCodeFromJWT(jwtToken.getValue())
    );

    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(jwtToken, null, user.getAuthorities());
    authentication.setDetails(new WebAuthenticationDetails(request));

    return authentication;
}
```

**Tipo de Refatoração:** Extract Class + Introduce Guard Clauses  
**Resolves:** #3 - Falta de validações de segurança

---

### **PASSO Y5: Padronizar Tratamento de Exceções de Segurança**

**Objetivo:** Aplicar padrões de exception handling consistentes

**Refatoração Y5.1: Criar hierarquia de exceções de segurança**
```java
/**
 * Exceção base para erros de autenticação.
 *Todas as exceções de segurança estendem esta classe.
 *
 * @author Israel Araújo
 */
public abstract class SecurityException extends RuntimeException {
    protected SecurityException(String message) {
        super(message);
    }

    protected SecurityException(String message, Throwable cause) {
        super(message, cause);
    }
}

/**
 * Exceção para erros de autenticação (credenciais inválidas).
 */
public class AuthenticationException extends SecurityException {
    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}

/**
 * Exceção para acesso negado (autenticado mas sem permissão).
 */
public class AccessDeniedException extends SecurityException {
    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}

/**
 * Exceção para token inválido, expirado ou malformado.
 */
public class InvalidTokenException extends SecurityException {
    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}

/**
 * Exceção para token expirado.
 */
public class TokenExpiredException extends InvalidTokenException {
    private final Instant expirationTime;

    public TokenExpiredException(Instant expirationTime) {
        super("Token expirado em: " + expirationTime);
        this.expirationTime = expirationTime;
    }

    public Instant getExpirationTime() {
        return expirationTime;
    }
}

/**
 * Exceção para usuário não encontrado durante autenticação.
 */
public class UserNotFoundException extends SecurityException {
    private final String userIdentifier;

    public UserNotFoundException(String userIdentifier) {
        super("Usuário não encontrado: " + userIdentifier);
        this.userIdentifier = userIdentifier;
    }

    public String getUserIdentifier() {
        return userIdentifier;
    }
}
```

**Refatoração Y5.2: Atualizar `CoreRestControllerAdvice`**
```java
/**
 * Handler centralizado de exceções de segurança.
 *Garante respostas consistentes para erros de autenticação/autorização.
 *
 * @author Israel Araújo
 */
@RestControllerAdvice
@Slf4j
public class SecurityExceptionHandler {

    private final SecurityLoggingService loggingService;

    public SecurityExceptionHandler(SecurityLoggingService loggingService) {
        this.loggingService = loggingService;
    }

    /**
     * Trata exceções de autenticação (credenciais inválidas).
     *Retorna HTTP 401 Unauthorized.
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(
            AuthenticationException ex, WebRequest request) {

        loggingService.logFailedAuthentication(
            extractUserCode(request),
            getClientIp(request),
            ex.getMessage()
        );

        ErrorResponse error = ErrorResponse.of(
            HttpStatus.UNAUTHORIZED.value(),
            RestErrorCode.AUTHENTICATION_ERROR,
            getTranslatedMessage("error.authentication"),
            getPath(request),
            getTraceId()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .header("x-trace-id", getTraceId())
            .body(error);
    }

    /**
     * Trata exceções de acesso negado (permissão insuficiente).
     *Retorna HTTP 403 Forbidden.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(
            AccessDeniedException ex, WebRequest request) {

        loggingService.logDeniedAccess(
            extractUserCode(request),
            getPath(request),
            ex.getMessage()
        );

        ErrorResponse error = ErrorResponse.of(
            HttpStatus.FORBIDDEN.value(),
            RestErrorCode.ACCESS_DENIED,
            getTranslatedMessage("error.access.denied"),
            getPath(request),
            getTraceId()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .header("x-trace-id", getTraceId())
            .body(error);
    }

    /**
     * Trata exceções de token inválido.
     *Retorna HTTP 401 Unauthorized.
     */
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenException(
            InvalidTokenException ex, WebRequest request) {

        loggingService.logFailedAuthentication(
            "UNKNOWN",
            getClientIp(request),
            "Token inválido: " + ex.getMessage()
        );

        ErrorResponse error = ErrorResponse.of(
            HttpStatus.UNAUTHORIZED.value(),
            RestErrorCode.AUTHENTICATION_ERROR,
            getTranslatedMessage("error.authentication.token.invalid"),
            getPath(request),
            getTraceId()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .header("x-trace-id", getTraceId())
            .body(error);
    }

    /**
     * Trata exceções de token expirado.
     *Retorna HTTP 401 Unauthorized com mensagem específica.
     */
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleTokenExpiredException(
            TokenExpiredException ex, WebRequest request) {

        loggingService.logFailedAuthentication(
            "UNKNOWN",
            getClientIp(request),
            "Token expirado"
        );

        ErrorResponse error = ErrorResponse.builder()
            .timestamp(Instant.now())
            .status(HttpStatus.UNAUTHORIZED.value())
            .errorCode("TOKEN_EXPIRED")
            .message(getTranslatedMessage("error.authentication.token.expired"))
            .path(getPath(request))
            .traceId(getTraceId())
            .details(Map.of("expirationTime", ex.getExpirationTime().toString()))
            .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .header("x-trace-id", getTraceId())
            .header("x-token-expired", "true")
            .body(error);
    }

    // Métodos auxiliares
    private String extractUserCode(WebRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getName() : "UNKNOWN";
    }

    private String getClientIp(WebRequest request) {
        return request.getHeader("X-Forwarded-For");
    }

    private String getPath(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }

    private String getTraceId() {
        return MDC.get("traceId");
    }
}
```

**Tipo de Refatoração:** Extract Class + Introduce Strategy  
**Resolves:** #3 - Tratamento inconsistente de exceções de segurança

---

### **PASSO Y6: Implementar Testes de Segurança**

**Objetivo:** Garantir cobertura de testes para funcionalidades críticas

**Refatoração Y6.1: Criar testes unitários para `JwtToken`**
```java
/**
 * Testes unitários para JwtToken.
 *Garante que o wrapper de token funciona corretamente.
 *
 * @author Israel Araújo
 */
@ExtendWith(MockitoExtension.class)
class JwtTokenTest {

    @Test
    @DisplayName("Deve criar JwtToken a partir de string válida")
    void shouldCreateJwtTokenFromValidString() {
        // Given
        String validToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIn0.dozjgNryP4J3jVmNHl0w5N_XgL0n3I9PlFUP0THsR8U";

        // When
        JwtToken token = JwtToken.from(validToken);

        // Then
        assertThat(token.getValue()).isEqualTo(validToken);
        assertThat(token.getIssuedAt()).isNotNull();
        assertThat(token.getExpiration()).isNotNull();
    }

    @Test
    @DisplayName("Deve rejeitar token nulo")
    void shouldRejectNullToken() {
        // When/Then
        assertThatThrownBy(() -> JwtToken.from(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("não pode ser nulo");
    }

    @Test
    @DisplayName("Deve rejeitar token vazio")
    void shouldRejectEmptyToken() {
        // When/Then
        assertThatThrownBy(() -> JwtToken.from(""))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("não pode ser vazio");
    }

    @Test
    @DisplayName("Deve identificar token expirado")
    void shouldIdentifyExpiredToken() {
        // Given
        JwtToken expiredToken = new JwtToken("valid.token.here",
            Instant.now().minusSeconds(7200),  // issued 2 hours ago
            Instant.now().minusSeconds(3600)); // expired 1 hour ago

        // When/Then
        assertThat(expiredToken.isExpired()).isTrue();
    }

    @Test
    @DisplayName("Deve identificar token válido")
    void shouldIdentifyValidToken() {
        // Given
        JwtToken validToken = new JwtToken("valid.token.here",
            Instant.now().minusSeconds(100),
            Instant.now().plusSeconds(3500)); // expires in ~1 hour

        // When/Then
        assertThat(validToken.isExpired()).isFalse();
    }
}
```

**Refatoração Y6.2: Criar testes de integração para `TokenValidator`**
```java
/**
 * Testes de integração para TokenValidator.
 *Garante que validações de segurança funcionam corretamente.
 *
 * @author Israel Araújo
 */
@SpringBootTest
@AutoConfigureMockMvc
class TokenValidatorIntegrationTest {

    @Autowired
    private TokenValidator tokenValidator;

    @Autowired
    private JwtManager jwtManager;

    @Test
    @DisplayName("Deve validar token gerado corretamente")
    void shouldValidateCorrectlyGeneratedToken() {
        // Given
        UserDetails user = createTestUser();
        JwtToken token = jwtManager.generateToken(user);

        // When
        TokenValidationResult result = tokenValidator.validate(token);

        // Then
        assertThat(result.isValid()).isTrue();
    }

    @Test
    @DisplayName("Deve rejeitar token modificado")
    void shouldRejectModifiedToken() {
        // Given
        JwtToken token = createValidToken();
        String modifiedValue = token.getValue() + "x"; // Modify token

        JwtToken modifiedToken = JwtToken.from(modifiedValue);

        // When
        TokenValidationResult result = tokenValidator.validate(modifiedToken);

        // Then
        assertThat(result.isValid()).isFalse();
    }

    @Test
    @DisplayName("Deve rejeitar token expirado")
    void shouldRejectExpiredToken() {
        // Given
        JwtToken expiredToken = createExpiredToken();

        // When
        TokenValidationResult result = tokenValidator.validate(expiredToken);

        // Then
        assertThat(result.isValid()).isFalse();
        assertThat(result.getErrorMessage()).isPresent();
    }
}
```

**Refatoração Y6.3: Criar testes para filtros de segurança**
```java
/**
 * Testes para CoreJwtAuthenticationFilter.
 *Garante que extração e validação de tokens funcionam.
 *
 * @author Israel Araújo
 */
@WebMvcTest(controllers = TestController.class)
class CoreJwtAuthenticationFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private JwtManager jwtManager;

    @Test
    @DisplayName("Deve retornar 401 quando token é ausente")
    void shouldReturn401WhenTokenIsMissing() throws Exception {
        mockMvc.perform(get("/api/v1/protected"))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.errorCode").value("AUTHENTICATION_ERROR"));
    }

    @Test
    @DisplayName("Deve retornar 401 quando token é inválido")
    void shouldReturn401WhenTokenIsInvalid() throws Exception {
        mockMvc.perform(get("/api/v1/protected")
                .header("Authorization", "Bearer invalid.token.here"))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.errorCode").value("AUTHENTICATION_ERROR"));
    }

    @Test
    @DisplayName("Deve permitir acesso com token válido")
    void shouldAllowAccessWithValidToken() throws Exception {
        // Given
        String validToken = createValidMockToken();
        UserDetails user = createMockUserDetails();

        when(jwtManager.getUserCodeFromJWT(validToken)).thenReturn("USER001");
        when(userDetailsService.loadUserByUsername("USER001")).thenReturn(user);

        // When/Then
        mockMvc.perform(get("/api/v1/protected")
                .header("Authorization", "Bearer " + validToken))
            .andExpect(status().isOk());
    }
}
```

**Tipo de Refatoração:** Introduce Test + Extract Test Class  
**Resolves:** #3 - Falta de testes de segurança

---

### **PASSO Y7: Documentar Padrões de Segurança (ADR)**

**Objetivo:** Criar documentação arquitetural das decisões de segurança

**Refatoração Y7.1: Criar ADR-015: Padrões de Segurança JWT**
```markdown
# ADR-015: Padrões de Segurança JWT

## Título
Padrões de Autenticação e Autorização com JWT

## Status
Proposto

## Contexto
A aplicação requer uma estratégia padronizada para:
- Autenticação de usuários via JWT
- Autorização baseada em roles e funcionalidades
- Validação robusta de tokens
- Tratamento consistente de exceções de segurança

## Decisões

### Decisão 1: JWT como Token Padrão
**Escolhido:** Usar JWT (JSON Web Tokens) para autenticação stateless

**Justificativa:**
- Stateless: não requer sessão no servidor
- Escalável: pode ser usado em arquiteturas distribuídas
- Padronizado: RFC 7519
- Flexível: pode conter claims customizadas

### Decisão 2: Tipo Específico JwtToken
**Escolhido:** Criar wrapper type-safe `JwtToken`

**Estrutura:**
```java
public final class JwtToken implements Serializable {
    private final String value;
    private final Instant issuedAt;
    private final Instant expiration;

    public static JwtToken from(String tokenValue) { ... }
    public boolean isExpired() { ... }
}
```

**Justificativa:**
- Type safety: previne uso de String genérica
- Self-validating: pode validar própria estrutura
- Extensible: pode adicionar claims como campos

### Decisão 3: Claims Obrigatórios
**Escolhido:** Definir claims mínimos obrigatórios

```json
{
  "iss": "ia-core",           // Issuer
  "sub": "userCode",          // Subject (código do usuário)
  "iat": 1234567890,          // Issued At
  "exp": 1234567890,         // Expiration
  "roles": ["ADMIN", "USER"], // Roles
  "funcs": ["READ", "WRITE"]  // Functionalities
}
```

### Decisão 4: Tempo de Expiração
**Escolhido:** Token de acesso com 1 hora, refresh token com 7 dias

### Decisão 5: Hierarquia de Exceções
**Escolhido:** Exceções específicas para cada cenário

```
SecurityException (abstract)
├── AuthenticationException (401)
├── AccessDeniedException (403)
├── InvalidTokenException (401)
├── TokenExpiredException (401)
└── UserNotFoundException (401)
```

## Validações de Token

| Validação | Tratamento |
|-----------|------------|
| Estrutura inválida | 401 - Token malformado |
| Expirado | 401 - Token expirado + header x-token-expired |
| Issuer inválido | 401 - Issuer não autorizado |
| Assinatura inválida | 401 - Token comprometido |

## Exemplo de Uso

### Geração de Token
```java
UserDetails user = userDetailsService.loadUserByUsername("USER001");
JwtToken token = tokenService.generateToken(user);
return Response.ok(new AuthenticationResponse(token));
```

### Validação de Token
```java
TokenValidationResult result = tokenValidator.validate(jwtToken);
if (!result.isValid()) {
    throw new InvalidTokenException(result.getErrorMessage());
}
```

## Segurança

### Boas Práticas Implementadas
1. HTTPS obrigatório em produção
2. Claims sensíveis nunca no token (apenas IDs)
3. Validação de issuer
4. Expiração curta para access tokens
5. Refresh tokens para renovação

### Headers de Segurança
```
Authorization: Bearer <token>
x-token-expired: true (quando aplicável)
x-trace-id: <correlation-id>
```

## Referências

- [RFC 7519 - JSON Web Token](https://tools.ietf.org/html/rfc7519)
- [RFC 7515 - JSON Web Signature](https://tools.ietf.org/html/rfc7515)
- [OWASP JWT Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/JSON_Web_Token_Cheat_Sheet.html)
```

**Tipo de Refatoração:** Create Documentation  
**Resolves:** #5 - Falta de documentação de segurança

---

## 4. Resumo das Refatorações por Tipo

### 4.1 Por Tipo de Refatoração

| Tipo | Quantidade | Exemplos |
|------|------------|----------|
| Extract Module | 1 | Y1 - Módulos de segurança |
| Extract Class | 4 | Y3, Y4.1, Y5.1, Y5.2 |
| Move Class | 2 | Y1.1 - Mover modelos para security |
| Introduce Parameter Object | 1 | Y2.1 - JwtToken |
| Introduce Guard Clauses | 1 | Y4.2 - Validações |
| Introduce Null Object | 1 | Y2.1 - JwtToken.from() |
| Extract Interface | 2 | Y1.2 - AuthenticationService, TokenService |
| Introduce Test | 3 | Y6.1, Y6.2, Y6.3 |
| Create Documentation | 1 | ADR-015 |
| Rename | 2 | Atualizar nomes para convenções |

### 4.2 Por Problema Resolvido

| Problema | Passos | Status |
|----------|--------|--------|
| #1 - Arquitetura incompleta | Y1 | ⏳ Pending |
| #2 - Acoplamento alto | Y1, Y3 | ⏳ Pending |
| #3 - Falta de testes | Y6 | ⏳ Pending |
| #4 - Token como String | Y2 | ⏳ Pending |
| #5 - SRP Violado | Y3 | ⏳ Pending |
| #6 - Exceções inconsistentes | Y5 | ⏳ Pending |

---

## 5. Ordem de Execução Recomendada

```
Y1: Criar Módulos de Segurança
   ├── Y1.1: Criar estrutura de diretórios
   ├── Y1.2: Mover modelos existentes
   └── Y1.3: Criar interfaces de serviço

Y2: Criar Tipo JwtToken
   ├── Y2.1: Implementar classe JwtToken
   ├── Y2.2: Criar InvalidTokenException
   └── Y2.3: Atualizar filtros existentes

Y3: Aplicar SRP em CoreRestSecurityConfig
   ├── Y3.1: Extrair SecurityLoggingService
   ├── Y3.2: Extrair TokenDetailsExtractor
   └── Y3.3: Simplificar CoreRestSecurityConfig

Y4: Implementar Validação Robusta
   ├── Y4.1: Criar TokenValidator
   └── Y4.2: Atualizar CoreJwtAuthenticationFilter

Y5: Padronizar Exceções
   ├── Y5.1: Criar hierarquia de exceções
   └── Y5.2: Atualizar handlers

Y6: Implementar Testes
   ├── Y6.1: Testes para JwtToken
   ├── Y6.2: Testes para TokenValidator
   └── Y6.3: Testes para filtros

Y7: Documentação (pode ser并行)
   └── Y7.1: Criar ADR-015
```

---

## 6. Critérios de Aceitação

- [ ] Todos os módulos de segurança criados e populados
- [ ] Classe `JwtToken` implementada e usada em todo o sistema
- [ ] `CoreRestSecurityConfig` com SRP aplicado
- [ ] Validações de token robustas implementadas
- [ ] Hierarquia de exceções de segurança completa
- [ ] Testes unitários com cobertura > 80% para módulos de segurança
- [ ] Testes de integração para validação de tokens
- [ ] ADR-015 criado e aprovado
- [ ] Código compila sem warnings
- [ ] Análise estática (SonarQube) limpa

---

## 7. Riscos e Mitigações

| Risco | Probabilidade | Impacto | Mitigação |
|-------|---------------|---------|-----------|
| Breaking changes em consumidores | Média | Alto | Versionamento de API |
| Performance degradada com validações | Baixa | Médio | Cache de claims |
| Complexidade aumentada | Média | Baixo | Documentação e exemplos |
| Retrocompatibilidade perdida | Baixa | Alto | Implementação gradual |

---

## 8. Referências

### Documentação Existente
- [ADR-011: Exception Handling Patterns](./ADR/011-exception-handling-patterns.md)
- [ADR-013: Logging Monitoring Patterns](./ADR/013-logging-monitoring-patterns.md)
- [JAVADOC_STANDARDS.md](./JAVADOC_STANDARDS.md)

### Links Externos
- [RFC 7519: JSON Web Token](https://tools.ietf.org/html/rfc7519)
- [OWASP JWT Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/JSON_Web_Token_Cheat_Sheet.html)
- [Spring Security Documentation](https://docs.spring.io/spring-security/reference/index.html)

---

**Próximos Passos:** Aguardar aprovação para iniciar implementação.
