# ia-core-security-service-model

## 📋 Descrição

DTOs para serviço de segurança. Define estruturas para autenticação, autorização e gerenciamento de usuários.

## 🏗️ Estrutura

```
ia-core-security-service-model/
├── src/main/java/
│   └── com/ia/core/security/service/dto/
│       ├── request/               # LoginDTO, RegisterDTO
│       ├── response/              # JwtTokenDTO, UserDTO
│       └── util/
└── pom.xml
```

## 🔑 Responsabilidades

- **LoginDTO**: Credenciais de login
- **RegisterDTO**: Dados de registro
- **JwtTokenDTO**: Token JWT com expiração
- **UserDTO**: Dados públicos do usuário
- **PermissionDTO**: Permissões

## 💡 DTOs Principais

```java
@Data
public class LoginDTO {
    @NotBlank private String username;
    @NotBlank private String password;
}

@Data
@Builder
public class JwtTokenDTO {
    private String accessToken;
    private String tokenType;
    private Long expiresIn;
    private String refreshToken;
}

@Data
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private Set<String> roles;
    private LocalDateTime createdAt;
}
```


