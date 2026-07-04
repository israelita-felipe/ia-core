# Caso de Teste: Autenticação e Autorização - Integração

## Descrição
Caso de teste de integração para validar fluxos completos de autenticação e autorização no módulo de segurança.

## Componentes Testados
- `com.ia.core.security.service.authentication.JwtAuthenticationService`
- `com.ia.core.security.service.authorization.AuthorizationManager`
- `com.ia.core.security.service.UserService`
- `com.ia.core.security.service.RoleService`
- `com.ia.core.security.service.PrivilegeService`

## Stack do DTO
| Camada | Componente | Status |
|--------|------------|--------|
| Model | User, Role, Privilege, JwtToken | Implementado |
| Repository | UserRepository, RoleRepository, PrivilegeRepository | Implementado |
| Mapper | LoginTranslator, UserMapper | Implementado |
| ServiceModel | JwtAuthenticationResponseDTO, UserDTO, RoleDTO, PrivilegeDTO | Implementado |
| Service | JwtAuthenticationService, UserService, RoleService, PrivilegeService | Implementado |
| API/REST | Não implementado no módulo ia-core-security-rest | Não implementado |
| View/Client | DefaultAuthenticationManager, TokenRefreshManager | Implementado |

## Objetivo
Documentar e validar fluxos de integração de autenticação e autorização, testando a interação entre múltiplos serviços e componentes da stack de segurança do `ia-core-security-*`.

## Fluxo do Teste
1. Dado o contexto de segurança `Autenticação e Autorização` no domínio `Manter Security`.
2. Quando fluxos de integração são executados envolvendo múltiplos serviços.
3. Então o comportamento deve validar a interação correta entre UserService, RoleService, PrivilegeService e JwtAuthenticationService.
4. E deve manter rastreabilidade com [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md).
5. E deve registrar falhas, exceções ou lacunas de implementação sem expor dados sensíveis.

## Cenários

### Cenário 1: Fluxo completo de autenticação com roles e privilégios
**Given**: Um usuário válido com roles e privilégios configurados no banco de dados.
**When**: O usuário é autenticado e suas permissões são verificadas.
**Then**: Deve retornar token JWT válido com as claims corretas de roles e privilégios.
**And**: Deve permitir acesso a recursos protegidos com base nas permissões.

### Cenário 2: Fluxo de autorização com múltiplos roles
**Given**: Um usuário com múltiplos roles associados.
**When**: É verificado se o usuário possui privilégios específicos.
**Then**: Deve consolidar todos os privilégios de todos os roles.
**And**: Deve retornar true se o privilégio estiver presente em qualquer role.

### Cenário 3: Fluxo de refresh de token
**Given**: Um token JWT expirado mas válido.
**When**: O token é enviado para refresh.
**Then**: Deve validar o token expirado.
**And**: Deve gerar um novo token JWT válido com as mesmas claims.

### Cenário 4: Fluxo de autenticação com credenciais inválidas
**Given**: Credenciais inválidas (usuário inexistente ou senha incorreta).
**When**: É tentada a autenticação.
**Then**: Deve lançar SecurityException com mensagem apropriada.
**And**: Não deve gerar token JWT.

### Cenário 5: Fluxo de autorização com usuário sem privilégios
**Given**: Um usuário autenticado mas sem privilégios específicos.
**When**: É verificado se o usuário possui um privilégio específico.
**Then**: Deve retornar false.
**And**: Não deve lançar exceção.

### Cenário 6: Integração com LogOperationService
**Given**: Uma operação de autenticação ou autorização é executada.
**When**: A operação é concluída.
**Then**: Deve registrar a operação no LogOperationService.
**And**: O log deve conter usuário, tipo de operação, e timestamp.

## Dependências
- JUnit 5
- AssertJ
- Spring Boot Test
- TestContainers para banco de dados real (PostgreSQL)
- Spring Security Test
- Dados de teste sem informação sensível

## Configuração de Teste
```java
@SpringBootTest
@Testcontainers
@ActiveProfiles("testcontainers")
class AuthenticationAuthorizationIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
}
```

## Referências
- [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md)
- ADR 010: Padrões de Nomenclatura
- ADR 012: Padrões de Teste Automatizado
- ADR 011: Exception Handling Patterns
- ADR 050: Diretrizes Gerais de Padronização
- ADR 052: MADR e Linguagem Normativa

## Aderência a ADRs

Este caso de teste foi gerado como documento vivo de rastreabilidade entre teste, CDU, domínio e decisões arquiteturais do `ia-core`.

### Metadados de contexto

| Campo | Valor |
|-------|-------|
| Componente | `Autenticação e Autorização - Integração` |
| Camada | Integração |
| Tipo de teste | Integração |
| Domínio | Autenticação e Autorização |
| CDU relacionada | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) |
| Status da camada | Implementado |

### Matriz de conformidade

| ADR | Tema | Aplicabilidade | Critério de conformidade |
|-----|------|----------------|--------------------------|
| ADR-010 | Padrões de Nomenclatura | Obrigatório | Nomes de arquivos, classes, métodos e campos seguem ADR-010. |
| ADR-012 | Padrões de Teste Automatizado | Obrigatório | Cenário feliz, negativo, dependências, mocks e rastreabilidade documentados. |
| ADR-050 | Diretrizes Gerais de Padronização | Obrigatório | Documento UTF-8, claro e alinhado à padronização do ia-core. |
| ADR-052 | MADR e Linguagem Normativa | Obrigatório | Critérios objetivos e termos normativos sem ambiguidade. |
| ADR-011 | Exception Handling | Aplicável | Validar exceções de domínio, validação, códigos de erro e mensagens i18n. |

### Critérios de aceitação obrigatórios

- [ ] O caso informa objetivo, componentes testados, tipo de teste, domínio e CDU relacionado.
- [ ] O fluxo cobre cenário feliz, entradas inválidas, exceções e dependências relevantes.
- [ ] Os nomes de classes, métodos, arquivos e mensagens seguem ADR-010.
- [ ] Os asserts são explícitos, legíveis e preferencialmente usam AssertJ/JUnit 5 conforme ADR-012.
- [ ] Mocks, stubs e verificações de interação são documentados sem expor dados sensíveis.
- [ ] O documento está em UTF-8 e usa linguagem clara e consistente com ADR-050/ADR-052.
- [ ] Teste usa TestContainers para banco de dados real conforme ADR-012.
- [ ] Teste valida interação entre múltiplos serviços.

### Evidências esperadas

- Cenário feliz documentado com Given/When/Then ou fluxo equivalente.
- Cenários negativos e exceções documentados quando aplicáveis.
- Dependências, mocks, stubs e pré-condições explicitados.
- Resultados esperados verificáveis por teste automatizado.
- Rastreabilidade com CDU, domínio, componentes e ADRs aplicáveis.
- Configuração de TestContainers documentada.

### Referências ADR

- [ADR-010 - Padrões de Nomenclatura](../../../../ADR/010-nomenclature-standards.md)
- [ADR-011 - Exception Handling Patterns](../../../../ADR/011-exception-handling-patterns.md)
- [ADR-012 - Padrões de Teste Automatizado](../../../../ADR/012-testing-patterns.md)
- [ADR-050 - Diretrizes Gerais de Padronização](../../../../ADR/050-standardization-guidelines.md)
- [ADR-052 - MADR e Linguagem Normativa](../../../../ADR/052-adopt-madr-and-rfc-normative-language-for-adrs.md)

### Referências do projeto

- CDU: [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md)
- Classes: [`JwtAuthenticationService`](../../../../ia-core-security-service/src/main/java/com/ia/core/security/service/authentication/JwtAuthenticationService.java), [`UserService`](../../../../ia-core-security-service/src/main/java/com/ia/core/security/service/UserService.java)
