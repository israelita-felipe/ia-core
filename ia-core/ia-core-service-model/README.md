# ia-core-service-model

## 📋 Descrição

Módulo que contém Data Transfer Objects (DTOs) e modelos de transferência de dados para a camada de serviço. Separação clara entre entidades internas e dados transferidos entre camadas.

## 🏗️ Estrutura

```
ia-core-service-model/
├── src/main/java/
│   └── com/ia/core/service/dto/
│       ├── request/                # DTOs de entrada (Request)
│       ├── response/               # DTOs de resposta (Response)
│       ├── filter/                 # DTOs de filtro
│       ├── page/                   # Paginação
│       └── util/                   # Utilitários
└── pom.xml
```

## 🔑 Responsabilidades

- **DTOs**: Modelos para transferência entre camadas
- **Request DTOs**: Dados recebidos do cliente
- **Response DTOs**: Dados retornados aos clientes
- **Filter Objects**: Critérios de busca
- **Mappers**: Conversão entre DTOs e entidades
- **Validations**: Regras de validação compartilhadas

## 🛠️ Tecnologias Utilizadas

- Jakarta Validation (JSR-303/380)
- Lombok (redução de boilerplate)
- MapStruct (mapeamento automático)
- Jackson (serialização JSON)

## 📦 Dependências

- `ia-core-model` - Referência às entidades
- `jakarta.validation:jakarta.validation-api`
- `org.projectlombok:lombok`
- `org.mapstruct:mapstruct`

## 🔗 Relacionamentos

Depende de:
- `ia-core-model` - Conhece as entidades

Utilizado por:
- `ia-core-service` - Mapeia DTOs para entidades
- `ia-core-rest` - Controllers usam DTOs
- Todos os módulos que transferem dados

## 💡 Padrões Implementados

- **Data Transfer Object (DTO)**: Separação de responsabilidades
- **Builder Pattern**: Construcción fluida
- **Validation Groups**: Validações por cenário
- **DTO Hierarchy**: Reutilização via herança

## 🚀 Como Usar

### Criar Request DTO

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "Username é obrigatório")
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username inválido")
    private String username;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    private String password;

    @NotBlank(message = "Nome completo é obrigatório")
    private String fullName;

    @NotNull(message = "Ativo é obrigatório")
    private Boolean enabled = true;
}
```

### Criar Response DTO

```java
@Data
@Builder
public class UserResponse {

    private Long id;

    private String username;

    private String email;

    private String fullName;

    private Boolean enabled;

    private Set<String> roles;

    private LocalDateTime createdAt;

    private String createdBy;
}
```

### Criar Mapper

```java
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UserMapper {

    /**
     * Mapeia request para entidade
     */
    User toEntity(CreateUserRequest dto);

    /**
     * Mapeia entidade para response
     */
    UserResponse toResponse(User entity);

    /**
     * Mapeia lista de entidades
     */
    List<UserResponse> toResponseList(List<User> entities);

    /**
     * Atualiza entidade dados do request (para PUT)
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    void updateEntity(UpdateUserRequest dto, @MappingTarget User entity);

    /**
     * Extrai roles para a resposta
     */
    @Named("roleNames")
    default Set<String> roleNames(Set<Role> roles) {
        return roles.stream()
            .map(Role::getName)
            .map(Enum::name)
            .collect(Collectors.toSet());
    }
}
```

### Usar Mapper em Controller

```java
@RestController
@RequestMapping("/api/${api.version}/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request) {

        User entity = userMapper.toEntity(request);
        User saved = userService.save(entity);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(userMapper.toResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        User user = userService.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {

        User user = userService.findById(id)
            .orElseThrow();

        userMapper.updateEntity(request, user);
        User updated = userService.save(user);

        return ResponseEntity.ok(userMapper.toResponse(updated));
    }
}
```

### Filtro DTO

```java
@Data
@Builder
public class UserFilter {

    @Size(max = 50)
    private String username;

    @Email
    private String email;

    private Boolean enabled;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createdAfter;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createdBefore;

    private List<String> roles;

    public Specification<User> toSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (username != null) {
                predicates.add(cb.like(root.get("username"), "%" + username + "%"));
            }

            if (email != null) {
                predicates.add(cb.equal(root.get("email"), email));
            }

            if (enabled != null) {
                predicates.add(cb.equal(root.get("enabled"), enabled));
            }

            if (createdAfter != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"),
                    createdAfter.atStartOfDay()));
            }

            if (createdBefore != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"),
                    createdBefore.atEndOfDay()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
```

### Page Response (Paginação)

```java
@Data
@Builder
public class PageResponse<T> {

    private List<T> content;

    private Long totalElements;

    private Integer totalPages;

    private Integer currentPage;

    private Integer pageSize;

    private Boolean hasNext;

    private Boolean hasPrevious;

    public static <T> PageResponse<T> from(Page<T> page) {
        return PageResponse.<T>builder()
            .content(page.getContent())
            .totalElements(page.getTotalElements())
            .totalPages(page.getTotalPages())
            .currentPage(page.getNumber())
            .pageSize(page.getSize())
            .hasNext(page.hasNext())
            .hasPrevious(page.hasPrevious())
            .build();
    }
}
```

## 🧪 Testes

```java
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testCreateUserRequest() {
        CreateUserRequest request = CreateUserRequest.builder()
            .username("testuser")
            .email("test@example.com")
            .password("password123")
            .fullName("Test User")
            .build();

        User entity = userMapper.toEntity(request);

        assertEquals("testuser", entity.getUsername());
        assertEquals("test@example.com", entity.getEmail());
    }

    @Test
    public void testUserResponse() {
        User user = User.builder()
            .id(1L)
            .username("testuser")
            .email("test@example.com")
            .build();

        UserResponse response = userMapper.toResponse(user);

        assertEquals(1L, response.getId());
        assertEquals("testuser", response.getUsername());
    }
}
```

## 📝 Convenções

### Naming

- Requests: `Create*Request`, `Update*Request`, `*Request`
- Responses: `*Response`, `*DTO`
- Filters: `*Filter`, `*Criteria`
- Pages: `Page*Request`, `PageResponse<T>`

### Validação

- Use grupos de validação para diferentes cenários
- Separe validação de negócio (service) de validação de input

```java
public interface CreateGroup {}
public interface UpdateGroup {}

@NotNull(groups = CreateGroup.class)
private String requiredOnCreate;
```

## 🤝 Contribuição

Ao adicionar novo DTOs:
1. Separe request/response
2. Use validações apropriadas
3. Implemente mapper
4. Documente com JavaDoc
5. Adicione testes

## 📝 Notas

- DTOs não devem ter lógica de negócio
- Não exponha entidades JPA nos endpoints
- Reutilize validações comuns
- Mantenha DTOs simples e focados

## 🔍 Referências

- [Jakarta Validation](https://jakarta.ee/specifications/bean-validation/)
- [MapStruct Documentation](https://mapstruct.org/)
- [DTO Pattern](https://martinfowler.com/eaaCatalog/dataTransferObject.html)


