# ia-core-security-model

## 📋 Descrição

Módulo que define as entidades e modelos de domínio para o sistema de segurança. Inclui usuários, papéis (roles), permissões e autenticação.

## 🏗️ Estrutura

```
ia-core-security-model/
├── src/main/java/
│   └── com/ia/core/security/model/
│       ├── User.java               # Entidade de usuário
│       ├── Role.java               # Entidade de papel/role
│       ├── Permission.java         # Entidade de permissão
│       ├── Authority.java          # Autoridade do Spring Security
│       └── dto/                    # DTOs de segurança
└── pom.xml
```

## 🔑 Responsabilidades

- **Entidade User**: Representa usuários do sistema com dados de autenticação
- **Entidade Role**: Define papéis (ADMIN, USER, MODERADOR, etc.)
- **Entidade Permission**: Define permissões granulares
- **Authority': Adaptação para Spring Security
- **DTOs**: LoginDTO, TokenDTO, UserDTO, etc.

## 🛠️ Tecnologias Utilizadas

- Spring Security
- JWT (JSON Web Tokens)
- Jakarta Persistence (JPA)
- Lombok
- Spring Data

## 📦 Dependências

- `ia-core-model` - Herda de AbstractEntity
- Spring Security Core
- JJWT (JWT library)
- Jackson (JSON serialization)

## 🔗 Relacionamentos

Depende de:
- `ia-core-model` - Entidades base

Utilizado por:
- `ia-core-security-service` - Lógica de autenticação/autorização
- `ia-core-rest` - Proteção de endpoints

## 💡 Padrões Implementados

- **Entity Pattern**: Entidades JPA para BD relacional
- **GrantedAuthority**: Interface do Spring Security
- **Data Transfer Object (DTO)**: Para transferência segura de dados

## 🚀 Como Usar

### Entidade User

```java
@Entity
@Table(name = "users")
@Data
@Builder
public class User extends AbstractEntity {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // Sempre criptografado

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private Boolean enabled = true;

    @Column(nullable = false)
    private Boolean accountNonLocked = true;

    @Column(nullable = false)
    private Boolean accountNonExpired = true;

    @Column(nullable = false)
    private Boolean credentialsNonExpired = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toSet());
    }
}
```

### Entidade Role

```java
@Entity
@Table(name = "roles")
@Data
@Builder
public class Role extends AbstractEntity {

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType name; // ADMIN, USER, MODERATOR

    @Column
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "role_permissions",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();
}

public enum RoleType {
    ADMIN("Administrador"),
    USER("Usuário"),
    MODERATOR("Moderador");

    private final String description;

    RoleType(String description) {
        this.description = description;
    }
}
```

### Entidade Permission

```java
@Entity
@Table(name = "permissions")
@Data
@Builder
public class Permission extends AbstractEntity {

    @Column(unique = true, nullable = false)
    private String name; // ex: "USER_READ", "USER_WRITE"

    @Column
    private String description;

    @Column
    private String category; // "USER", "ADMIN", etc.
}
```

### DTOs

```java
// Login Request
@Data
public class LoginDTO {

    @NotBlank(message = "Username é obrigatório")
    private String username;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    private String password;
}

// JWT Response
@Data
@Builder
public class JwtTokenDTO {

    private String accessToken;

    private String tokenType = "Bearer";

    private Long expiresIn;

    private String refreshToken;
}

// User Response
@Data
@Builder
public class UserDTO {

    private Long id;

    private String username;

    private String email;

    private String fullName;

    private Set<String> roles;

    private LocalDateTime criadoEm;
}
```

## 🔐 Configuração Padrão

### application.yml

```yaml
security:
  jwt:
    secret: sua-chave-secreta-muito-longa-com-pelo-menos-256-bits
    expiration: 86400000 # 24 horas em ms
    refresh-expiration: 604800000 # 7 dias em ms
```

## 🧪 Testes

```java
@SpringBootTest
public class UserModelTest {

    @Test
    public void testUserCreation() {
        User user = User.builder()
            .username("testuser")
            .email("test@example.com")
            .password("hashedPassword")
            .fullName("Test User")
            .build();

        assertNotNull(user.getId());
        assertTrue(user.getEnabled());
    }

    @Test
    public void testUserRoles() {
        Role adminRole = Role.builder()
            .name(RoleType.ADMIN)
            .build();

        User user = User.builder()
            .username("admin")
            .email("admin@example.com")
            .roles(Set.of(adminRole))
            .build();

        assertTrue(user.getRoles().contains(adminRole));
    }
}
```

## 📋 Checklist de Dados Sensíveis

- ❌ Nunca armazene senhas em texto plano
- ✅ Use bcrypt ou argon2 para código de hash de senha
- ❌ Não exponha senhas em DTOs
- ✅ Use HTTPS em produção
- ✅ Implemente proteção contra CSRF
- ✅ Defina tokens JWT com expiração

## 🤝 Contribuição

Ao adicionar novas entidades de segurança:
1. Estenda `AbstractEntity`
2. Implemente GrantedAuthority se necessário
3. Use Roles e Permissions para controle granular
4. Adicione validações com JSR-303
5. Documente com JavaDoc

## 📝 Notas

- Senhas devem ser SEMPRE criptografadas
- Use roles e permissões para controle de acesso
- JWTs são consumidos por frontend e APIs externas
- Sempre valide tokens no backend

## 🔍 Referências

- [Spring Security](https://spring.io/projects/spring-security)
- [JWT Best Practices](https://tools.ietf.org/html/rfc8725)
- [OWASP Authentication Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Authentication_Cheat_Sheet.html)


