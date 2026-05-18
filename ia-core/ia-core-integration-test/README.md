# ia-core-integration-test

## 📋 Descrição

Módulo de testes de integração que fornece base e utilitários para testar múltiplos módulos de forma integrada. Define setup, fixtures, e helpers para testes end-to-end.

## 🏗️ Estrutura

```
ia-core-integration-test/
├── src/main/java/
│   └── com/ia/core/test/
│       ├── base/                   # Classe base para testes
│       ├── fixture/                # Dados de teste (fixtures)
│       ├── container/              # Testcontainers
│       ├── assertion/              # Assertions customizados
│       └── util/                   # Utilitários de teste
└── pom.xml
```

## 🔑 Responsabilidades

- **Base Test Class**: Setup comum para todos os testes
- **Fixtures**: Dados de teste reutilizáveis
- **Test Containers**: BD dockerizado para testes
- **Custom Assertions**: Validações específicas do domínio
- **Security Context**: Autenticação simulada
- **DB Cleanup**: Limpeza entre testes

## 🛠️ Tecnologias Utilizadas

- JUnit 5
- Spring Test
- TestContainers (BD em containers)
- Mockito (mocks)
- REST Assured (testes de API)
- AssertJ (assertions fluidas)
- H2 (BD em memória para testes rápidos)

## 📦 Dependências

- `org.springframework.boot:spring-boot-starter-test`
- `io.rest-assured:rest-assured`
- `org.testcontainers:testcontainers`
- `org.testcontainers:mysql` (ou outro DB)
- `org.assertj:assertj-core`

## 🔗 Relacionamentos

Depende de:
- `ia-core-model`, `ia-core-service`, `ia-core-rest` - Módulos a testar

Utilizado por:
- `ia-core-security-test` - Testes específicos de segurança
- Qualquer módulo que necessite testes de integração

## 💡 Padrões Implementados

- **Test Fixture Pattern**: Dados reutilizáveis
- **Test Container Pattern**: Ambiente isolado
- **Builder Pattern**: Construção de dados de teste
- **Assertion Pattern**: Validações customizadas

## 🚀 Como Usar

### Base Test Class

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public abstract class IntegrationTestBase {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected TestRestTemplate restTemplate;

    protected String token;
    protected User currentUser;

    @BeforeEach
    public void setup() throws Exception {
        // Setup comum
        setupTestData();
        setupAuthentication();
    }

    protected void setupTestData() {
        // Dados padrão para testes
    }

    protected void setupAuthentication() throws Exception {
        // Login de usuário de testes
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("testuser");
        loginDTO.setPassword("password");

        // Fazer login e armazenar token
        String loginResponse = mockMvc.perform(
            post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO))
        )
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        JwtTokenDTO tokenDTO = objectMapper.readValue(loginResponse, JwtTokenDTO.class);
        this.token = tokenDTO.getAccessToken();
    }

    protected String asJson(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T fromJson(String json, Class<T> type) throws Exception {
        return objectMapper.readValue(json, type);
    }
}
```

### Fixture Builder

```java
public class UserFixture {

    public static User.UserBuilder validUser() {
        return User.builder()
            .username("testuser")
            .email("test@example.com")
            .password("hashedPassword")
            .fullName("Test User")
            .enabled(true)
            .accountNonLocked(true)
            .credentialsNonExpired(true)
            .accountNonExpired(true);
    }

    public static User.UserBuilder adminUser() {
        return validUser()
            .username("admin")
            .roles(Set.of(RoleFixture.adminRole()));
    }

    public static CreateUserRequest.CreateUserRequestBuilder validCreateRequest() {
        return CreateUserRequest.builder()
            .username("newuser")
            .email("new@example.com")
            .password("password123")
            .fullName("New User");
    }
}

public class RoleFixture {

    public static Role.RoleBuilder adminRole() {
        return Role.builder()
            .name(RoleType.ADMIN)
            .description("Administrator");
    }

    public static Role.RoleBuilder userRole() {
        return Role.builder()
            .name(RoleType.USER)
            .description("Regular User");
    }
}
```

### Test Container Setup

```java
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "spring.datasource.url=jdbc:mysql://localhost:3306/test_db",
        "spring.jpa.hibernate.ddl-auto=create-drop"
    }
)
public abstract class IntegrationTestWithContainer extends IntegrationTestBase {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
        .withDatabaseName("test_db")
        .withUsername("test")
        .withPassword("test");

    @DynamicPropertySource
    static void registerMysqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }
}
```

### Teste de API REST

```java
public class UserControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() throws Exception {
        CreateUserRequest request = UserFixture.validCreateRequest().build();

        mockMvc.perform(
            post("/api/v1/users")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(request))
        )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.username").value(request.getUsername()))
            .andExpect(jsonPath("$.email").value(request.getEmail()));

        // Verificar que usuário foi salvo
        User savedUser = userRepository.findByUsername(request.getUsername())
            .orElseThrow();
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo(request.getEmail());
    }

    @Test
    public void testGetUser() throws Exception {
        // Arrange
        User user = userRepository.save(UserFixture.validUser().build());

        // Act & Assert
        mockMvc.perform(
            get("/api/v1/users/{id}", user.getId())
                .header("Authorization", "Bearer " + token)
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(user.getId()))
            .andExpect(jsonPath("$.username").value(user.getUsername()));
    }

    @Test
    public void testUnauthorizedAccess() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
            .andExpect(status().isUnauthorized());
    }
}
```

### Custom Assertions

```java
public class UserAssertions {

    public static UserAssert assertThat(User user) {
        return new UserAssert(user);
    }

    public static class UserAssert extends AbstractAssert<UserAssert, User> {

        public UserAssert(User actual) {
            super(actual, UserAssert.class);
        }

        public UserAssert hasUsername(String username) {
            isNotNull();
            if (!actual.getUsername().equals(username)) {
                failWithMessage("Expected username <%s> but was <%s>",
                    username, actual.getUsername());
            }
            return this;
        }

        public UserAssert isEnabled() {
            isNotNull();
            if (!actual.getEnabled()) {
                failWithMessage("Expected user to be enabled but was disabled");
            }
            return this;
        }

        public UserAssert hasRole(RoleType role) {
            isNotNull();
            boolean hasRole = actual.getRoles().stream()
                .anyMatch(r -> r.getName().equals(role));
            if (!hasRole) {
                failWithMessage("Expected user to have role <%s>", role);
            }
            return this;
        }
    }
}
```

### Uso de REST Assured

```java
public class UserApiIntegrationTest extends IntegrationTestBase {

    @Test
    public void testListUsers() {
        given()
            .header("Authorization", "Bearer " + token)
            .contentType(ContentType.JSON)
        .when()
            .get("/api/v1/users")
        .then()
            .statusCode(200)
            .body("totalElements", greaterThan(0))
            .body("content[0].username", notNullValue());
    }
}
```

## 📋 Configuração para Testes

### application-test.yml

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password:

  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: create-drop
    show-sql: false

  security:
    jwt:
      secret: test-secret-key-that-is-long-enough
      expiration: 3600000

logging:
  level:
    com.ia: DEBUG
```

## 🧪 Exemplo Completo de Teste

```java
@RunWith(SpringRunner.class)
public class UserServiceIntegrationTest extends IntegrationTestBase {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void testUserLifecycle() {
        // 1. Criar
        CreateUserRequest createRequest = UserFixture.validCreateRequest().build();
        User user = UserFixture.validUser().build();
        User saved = userService.save(user);

        // 2. Buscar
        Optional<User> retrieved = userService.findById(saved.getId());
        assertThat(retrieved).isPresent();
        assertThat(retrieved.get()).hasUsername(saved.getUsername());

        // 3. Atualizar
        saved.setEmail("newemail@example.com");
        User updated = userService.save(saved);
        assertThat(updated.getEmail()).isEqualTo("newemail@example.com");

        // 4. Deletar
        userService.delete(saved.getId());
        Optional<User> deleted = userService.findById(saved.getId());
        assertThat(deleted).isEmpty();
    }
}
```

## 🔐 Setup de Segurança em Testes

```java
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTestBase {

    @Autowired
    protected MockMvc mockMvc;

    protected void performAsUser() {
        // Configure SecurityContext com user anônimo
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken("testuser", null,
                List.of(new SimpleGrantedAuthority("ROLE_USER")))
        );
    }

    protected void performAsAdmin() {
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken("admin", null,
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")))
        );
    }
}
```

## 🤝 Contribuição

Ao adicionar testes de integração:
1. Herde de `IntegrationTestBase`
2. Use `Fixture` para dados
3. Limpe BD após testes
4. Use `@Transactional` quando apropriado
5. Teste fluxos completos

## 📝 Notas

- H2 é mais rápido que containers para testes locais
- Use TestContainers para ambiente produção-like
- Fixtures tornam testes mais legíveis
- Setup/Teardown deve ser automático

## 🔍 Referências

- [Spring Test Reference](https://docs.spring.io/spring-framework/reference/testing.html)
- [TestContainers Documentation](https://www.testcontainers.org/)
- [REST Assured](https://rest-assured.io/)


