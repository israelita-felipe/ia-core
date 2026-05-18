# ia-core-security-service

## 📋 Descrição

Módulo que implementa serviços de autenticação e autorização. Gerencia login, geração de tokens JWT, validação de credenciais e controle de acesso baseado em roles.

## 🏗️ Estrutura

```
ia-core-security-service/
├── src/main/java/
│   └── com/ia/core/security/service/
│       ├── AuthService.java        # Serviço de autenticação
│       ├── JwtService.java         # Gerenciamento de JWT
│       ├── UserService.java        # Gerenciamento de usuários
│       ├── RoleService.java        # Gerenciamento de roles
│       ├── impl/                   # Implementações
│       └── util/                   # Utilitários de segurança
└── pom.xml
```

## 🔑 Responsabilidades

- **AuthService**: Autentica usuários e gera tokens JWT
- **JwtService**: Cria, valida e decodifica tokens JWT
- **UserService**: CRUD de usuários, hash de senhas
- **RoleService**: Gerenciamento de roles e permissões
- **Validação de Token**: Verifica tokens em requisições
- **Criptografia de Senha**: BCrypt para segurança

## 🛠️ Tecnologias Utilizadas

- Spring Security
- JWT (JJWT library)
- BCrypt (password encoding)
- Spring Data JPA
- Lombok

## 📦 Dependências

- `ia-core-security-model` - Entidades de segurança
- `ia-core-model` - Modelos base
- Spring Security
- JJWT
- Spring Data JPA

## 🔗 Relacionamentos

Depende de:
- `ia-core-security-model` - Entidades User, Role, Permission
- `ia-core-model` - Entidades base

Utilizado por:
- `ia-core-rest` - Proteção de endpoints REST
- `ia-core-security-view` - UI de segurança
- Todos os módulos que precisam de autenticação

## 💡 Padrões Implementados

- **Service Layer**: Lógica de segurança centralizada
- **Authentication Provider**: Spring Security
- **Token-Based Authentication**: JWT
- **Strategy Pattern**: Diferentes estratégias de autenticação

## 🚀 Como Usar

### Implementar AuthService

```java
@Service
@Transactional
public class AuthServiceImpl extends AbstractCrudService<User, Long> implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RoleService roleService;

    /**
     * Autentica um usuário e retorna um token JWT
     */
    public JwtTokenDTO login(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername())
            .orElseThrow(() -> new BadCredentialsException("Usuário não encontrado"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Senha incorreta");
        }

        if (!user.getEnabled()) {
            throw new DisabledException("Usuário desabilitado");
        }

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return JwtTokenDTO.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .tokenType("Bearer")
            .expiresIn(jwtService.getExpirationTime())
            .build();
    }

    /**
     * Registra um novo usuário
     */
    public UserDTO register(RegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new IllegalArgumentException("Username já existe");
        }

        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new IllegalArgumentException("Email já existe");
        }

        User user = User.builder()
            .username(registerDTO.getUsername())
            .email(registerDTO.getEmail())
            .password(passwordEncoder.encode(registerDTO.getPassword()))
            .fullName(registerDTO.getFullName())
            .roles(Set.of(roleService.findByName(RoleType.USER)))
            .build();

        user = userRepository.save(user);
        return mapToDTO(user);
    }

    /**
     * Valida um token e retorna o usuário
     */
    public User validateToken(String token) {
        String username = jwtService.extractUsername(token);

        if (!jwtService.validateToken(token)) {
            throw new TokenExpiredException("Token expirado ou inválido");
        }

        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
```

### Implementar JwtService

```java
@Service
public class JwtServiceImpl implements JwtService {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.expiration}")
    private long jwtExpirationMs;

    @Value("${security.jwt.refresh-expiration}")
    private long refreshExpirationMs;

    /**
     * Gera um novo JWT Access Token
     */
    public String generateToken(User user) {
        return Jwts.builder()
            .setSubject(user.getUsername())
            .claim("email", user.getEmail())
            .claim("roles", user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList()))
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(getSigningKey(), SignatureAlgorithm.HS512)
            .compact();
    }

    /**
     * Gera um JWT Refresh Token
     */
    public String generateRefreshToken(User user) {
        return Jwts.builder()
            .setSubject(user.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationMs))
            .signWith(getSigningKey(), SignatureAlgorithm.HS512)
            .compact();
    }

    /**
     * Extrai o username de um token
     */
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    /**
     * Valida um token
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Obtém a chave de assinatura
     */
    private Key getSigningKey() {
        byte[] decodedKey = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public long getExpirationTime() {
        return jwtExpirationMs;
    }
}
```

### Usar Authorization em Controllers

```java
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        JwtTokenDTO token = authService.login(loginDTO);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterDTO registerDTO) {
        UserDTO userDTO = authService.register(registerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> getProfile(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(authService.mapToDTO(currentUser));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        authService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

### Configurar Spring Security

```java
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(req -> req
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Retornar implementação que carrega usuários do BD
        return new CustomUserDetailsService();
    }
}
```

## 🔐 JWT Filter

```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtService.validateToken(token)) {
                String username = jwtService.extractUsername(token);
                User user = userService.findByUsername(username);

                UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities()
                    );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
```

## 🧪 Testes

```java
@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testLogin() {
        User user = User.builder()
            .username("testuser")
            .password("hashedPassword")
            .enabled(true)
            .build();

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("testuser");
        loginDTO.setPassword("password");

        // Teste de login
        // JwtTokenDTO token = authService.login(loginDTO);
        // assertNotNull(token.getAccessToken());
    }
}
```

## 🔐 Segurança

- ✅ Senhas com BCrypt (mínimo 12 rounds)
- ✅ JWTs com expiração curta (15-30 min access tokens)
- ✅ Refresh tokens com expiração longa (7-30 dias)
- ✅ HTTPS obrigatório em produção
- ✅ Proteger contra CSRF

## 🤝 Contribuição

Ao adicionar novos serviços de segurança:
1. Implemente a interface apropriada
2. Use PasswordEncoder para senhas
3. Adicione validações
4. Implemente testes
5. Documente com JavaDoc

## 📝 Notas

- Sempre criptografe senhas
- JWTs devem ser verificados em cada requisição
- Use HTTPS em produção
- Implemente rate limiting no login

## 🔍 Referências

- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [JWT Handbook](https://auth0.com/resources/ebooks/jwt-handbook)
- [OWASP Authentication](https://cheatsheetseries.owasp.org/)


