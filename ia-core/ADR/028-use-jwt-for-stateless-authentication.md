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
   Client → POST /api/${api.version}/auth/login → Server
   Server → Gera JWT (access) + JWT (refresh) → Client

2. Access Token (curto)
   Client → GET /api/${api.version}/pessoas (com Bearer JWT) → Server
   Server → Valida token → Decodifica → Permite/Nega → Response

3. Refresh Token (longo)
   Client → POST /api/${api.version}/auth/refresh → Server
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

## Padrões de Padronização (Standardization)

Este ADR adere aos seguintes padrões técnicos:

- **RFC 7519** — JSON Web Token (JWT): estrutura de claims, serialização e validação básica.
- **RFC 6750** — The OAuth 2.0 Authorization Framework: Bearer Token Usage: envio de access tokens no cabeçalho `Authorization: Bearer <token>`.
- **RFC 6749** — OAuth 2.0 Authorization Framework: fluxo de refresh token para renovação de credenciais.
- **RFC 9068** — JWT Profile for OAuth 2.0 Access Tokens: usar como referência quando o access token JWT for consumido por APIs OAuth 2.0.

### Compatibilidade com ADRs Relacionados

- **ADR-042**: fluxo de refresh token conforme OAuth 2.0.
- **ADR-043**: padronização de JWT e claims essenciais.
- **ADR-052**: ADRs devem usar linguagem normativa RFC 2119/RFC 8174 e indicar referências técnicas vigentes.

## Referências

1. **JWT.io**
   - URL: https://jwt.io/
   - Debugger e documentação

2. **RFC 7519 - JWT**
   - URL: https://datatracker.ietf.org/doc/html/rfc7519
   - Especificação oficial

3. **RFC 6750 - OAuth 2.0 Bearer Token Usage**
   - URL: https://datatracker.ietf.org/doc/html/rfc6750
   - Uso de bearer tokens em requisições HTTP

4. **RFC 6749 - OAuth 2.0 Authorization Framework**
   - URL: https://datatracker.ietf.org/doc/html/rfc6749
   - Fluxo de refresh token e autorização OAuth 2.0

5. **RFC 9068 - JWT Profile for OAuth 2.0 Access Tokens**
   - URL: https://datatracker.ietf.org/doc/html/rfc9068
   - Perfil recomendado para access tokens JWT em APIs OAuth 2.0

6. **Baeldung - JWT Tutorial**
   - URL: https://www.baeldung.com/spring-security-jwt
   - Tutorial completo

4. **Spring Security - JWT**
   - URL: https://spring.io/guides/gs/securing-web/
   - Guia oficial

5. **OWASP - JWT Security**
   - URL: https://cheatsheetseries.owasp.org/cheatsheets/JSON_Web_Token_Cheat_Sheet.html
   - Boas práticas de segurança
