# ADR-028: Usar JWT para Autenticação Stateless

## Status

✅ Aceito

## Contexto

O projeto precisa de um sistema de autenticação que:
- Seja stateless (sem sessão no servidor)
- Funcione em arquiteturas distribuídas
- Permita integração com múltiplos clientes
- Tenha tokens com expiração segura
- Suporte refresh tokens

## Decisão

Usar **JWT (JSON Web Tokens)** para autenticação, com tokens de acesso e refresh.

## Detalhes

### Fluxo de Autenticação

```
1. Login
   Client → POST /api/v1/auth/login → Server
   Server → Gera JWT (access) + JWT (refresh) → Client

2. Access Token (curto)
   Client → GET /api/v1/pessoas (com Bearer JWT) → Server
   Server → Valida token → Decodifica → Permite/Nega → Response

3. Refresh Token (longo)
   Client → POST /api/v1/auth/refresh → Server
   Server → Valida refresh → Gera novos tokens → Response
```

### Dependências Maven

```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.3</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>
```

### Configuração de JWT

```java
@Configuration
public class JwtConfig {
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.access-token-validity:3600000}") // 1 hora
    private long accessTokenValidity;
    
    @Value("${jwt.refresh-token-validity:86400000}") // 24 horas
    private long refreshTokenValidity;
    
    @Bean
    public JwtParser jwtParser() {
        return Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
            .build();
    }
}
```

### Payload do Token

```json
{
  "sub": "12345",           // ID do usuário
  "username": "joao@email.com",
  "roles": ["ADMIN", "USER"],
  "tenant": "ibav",
  "iat": 1704067200,        // Issued At
  "exp": 1704070800         // Expiration (1 hora)
}
```

### Gerador de Tokens

```java
@Service
@RequiredArgsConstructor
public class JwtService {
    
    private final JwtParser jwtParser;
    @Value("${jwt.secret}")
    private String secret;
    
    public String generateAccessToken(User user) {
        return Jwts.builder()
            .subject(user.getId().toString())
            .claim("username", user.getUsername())
            .claim("roles", user.getRoles())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 3600000))
            .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
            .compact();
    }
    
    public String generateRefreshToken(User user) {
        return Jwts.builder()
            .subject(user.getId().toString())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 86400000))
            .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
            .compact();
    }
    
    public boolean validateToken(String token) {
        try {
            jwtParser.parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
```

### JwtAuthenticationFilter

```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {
        
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            
            if (jwtService.validateToken(token)) {
                Claims claims = jwtService.parseToken(token);
                
                UserPrincipal principal = new UserPrincipal(
                    Long.parseLong(claims.getSubject()),
                    claims.get("username", String.class),
                    claims.get("roles", List.class)
                );
                
                UsernamePasswordAuthenticationToken auth = 
                    new UsernamePasswordAuthenticationToken(
                        principal, null, 
                        AuthorityUtils.createAuthorityList(
                            (List<String>)claims.get("roles")
                        )
                    );
                
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
```

## Consequências

### Positivas

- ✅ Stateless (sem sessão)
- ✅ Escalável
- ✅ Suporte a múltiplos clients
- ✅ Token com informações do usuário
- ✅ Fácil de invalidar (refresh)

### Negativas

- ❌ Token não pode ser revogado individualmente
- ❌ Armazenar em local seguro no cliente
- ❌ Tamanho do token pode crescer

## Status de Implementação

✅ **COMPLETO**

- JwtService implementado
- Filtro configurado
- Endpoints de login/refresh

## Data

2024-02-15

## Revisores

- Team Lead
- Architect

## Referências

1. **JWT.io**
   - URL: https://jwt.io/
   - Debugger e documentação

2. **RFC 7519 - JWT**
   - URL: https://datatracker.ietf.org/doc/html/rfc7519
   - Especificação oficial

3. **Baeldung - JWT Tutorial**
   - URL: https://www.baeldung.com/spring-security-jwt
   - Tutorial completo

4. **Spring Security - JWT**
   - URL: https://spring.io/guides/gs/securing-web/
   - Guia oficial

5. **OWASP - JWT Security**
   - URL: https://cheatsheetseries.owasp.org/cheatsheets/JSON_Web_Token_Cheat_Sheet.html
   - Boas práticas de segurança