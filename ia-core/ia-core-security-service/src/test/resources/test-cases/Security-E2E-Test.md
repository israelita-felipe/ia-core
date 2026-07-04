# Caso de Teste: Security - End-to-End (E2E)

## Descrição
Caso de teste end-to-end para validar fluxos completos de segurança, desde autenticação até autorização e auditoria, simulando cenários reais de uso em produção.

## Componentes Testados
- `com.ia.core.security.service.authentication.JwtAuthenticationService`
- `com.ia.core.security.service.authorization.AuthorizationManager`
- `com.ia.core.security.service.UserService`
- `com.ia.core.security.service.RoleService`
- `com.ia.core.security.service.PrivilegeService`
- `com.ia.core.security.service.log.operation.LogOperationService`
- `com.ia.core.security.service.model.authentication.JwtAuthenticationResponseDTO`
- `com.ia.core.security.service.model.log.operation.LogOperationDTO`

## Stack do DTO
| Camada | Componente | Status |
|--------|------------|--------|
| Model | User, Role, Privilege, JwtToken, LogOperation | Implementado |
| Repository | UserRepository, RoleRepository, PrivilegeRepository, LogOperationRepository | Implementado |
| Mapper | LoginTranslator, UserMapper, LogOperationMapper | Implementado |
| ServiceModel | JwtAuthenticationResponseDTO, UserDTO, RoleDTO, PrivilegeDTO, LogOperationDTO | Implementado |
| Service | JwtAuthenticationService, UserService, RoleService, PrivilegeService, LogOperationService | Implementado |
| API/REST | Não implementado no módulo ia-core-security-rest | Não implementado |
| View/Client | DefaultAuthenticationManager, TokenRefreshManager, LogOperationManager | Implementado |

## Objetivo
Documentar e validar fluxos end-to-end de segurança, testando cenários completos que simulam uso real em produção, incluindo autenticação, autorização, auditoria e tratamento de erros.

## Fluxo do Teste
1. Dado o contexto de segurança completo no domínio `Manter Security`.
2. Quando fluxos end-to-end são executados simulando cenários reais.
3. Então o comportamento deve validar a interação correta entre todos os componentes da stack.
4. E deve manter rastreabilidade completa com [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md).
5. E deve registrar todas as operações de auditoria corretamente.

## Cenários

### Cenário 1: Fluxo completo de login e acesso a recursos
**Given**: Um usuário válido com roles e privilégios configurados.
**When**: O usuário faz login e tenta acessar recursos protegidos.
**Then**: Deve autenticar com sucesso e gerar token JWT.
**And**: Deve autorizar acesso com base nos privilégios.
**And**: Deve registrar todas as operações no LogOperationService.
**And**: O token deve ser válido para múltiplas requisições durante o período de expiração.

### Cenário 2: Fluxo completo de criação de usuário com roles
**Given**: Um administrador autenticado com privilégios de gerenciamento de usuários.
**When**: O administrador cria um novo usuário e atribui roles.
**Then**: Deve criar o usuário com sucesso no banco de dados.
**And**: Deve associar as roles corretamente.
**And**: Deve registrar a operação de criação no LogOperationService.
**And**: O novo usuário deve poder autenticar imediatamente.

### Cenário 3: Fluxo completo de alteração de senha
**Given**: Um usuário autenticado.
**When**: O usuário solicita alteração de senha fornecendo senha atual e nova.
**Then**: Deve validar a senha atual.
**And**: Deve atualizar a senha no banco de dados.
**And**: Deve invalidar tokens existentes do usuário.
**And**: Deve registrar a operação no LogOperationService.
**And**: O usuário deve poder autenticar com a nova senha.

### Cenário 4: Fluxo completo de revogação de acesso
**Given**: Um usuário com acesso ativo.
**When**: Um administrador revoga o acesso do usuário (desabilita usuário).
**Then**: Deve desabilitar o usuário no banco de dados.
**And**: Deve invalidar todos os tokens existentes do usuário.
**And**: Deve registrar a operação no LogOperationService.
**And**: O usuário não deve mais conseguir autenticar.

### Cenário 5: Fluxo completo de auditoria de operações
**Given**: Múltiplas operações de segurança executadas.
**When**: É solicitado um relatório de auditoria com filtros.
**Then**: Deve retornar todas as operações que atendem aos filtros.
**And**: Deve incluir usuário, tipo de operação, timestamp e valores.
**And**: Deve permitir paginação dos resultados.
**And**: Os dados devem estar consistentes com o banco de dados.

### Cenário 6: Fluxo completo de refresh de token
**Given**: Um usuário autenticado com token próximo à expiração.
**When**: O usuário solicita refresh do token.
**Then**: Deve validar o token expirado mas assinado corretamente.
**And**: Deve verificar se o usuário ainda está ativo.
**And**: Deve gerar novo token com as mesmas claims.
**And**: Deve registrar a operação de refresh no LogOperationService.

### Cenário 7: Fluxo completo de tratamento de erro de autenticação
**Given**: Tentativas de autenticação com credenciais inválidas.
**When**: Múltiplas tentativas falhas são realizadas.
**Then**: Deve registrar cada tentativa falha no LogOperationService.
**And**: Deve não revelar se o usuário existe (segurança).
**And**: Deve aplicar rate limiting se configurado.
**And**: Deve bloquear temporariamente após muitas tentativas.

### Cenário 8: Fluxo completo de alteração de roles
**Given**: Um usuário com roles existentes.
**When**: Um administrador altera as roles do usuário.
**Then**: Deve atualizar as associações de roles no banco de dados.
**And**: Deve invalidar tokens existentes do usuário.
**And**: Deve registrar a operação no LogOperationService.
**And**: O usuário deve ter os novos privilégios no próximo login.

## Dependências
- JUnit 5
- AssertJ
- Spring Boot Test
- TestContainers para banco de dados real (PostgreSQL)
- Spring Security Test
- Dados de teste sem informação sensível
- Ambiente de teste configurado para simular produção

## Configuração de Teste
```java
@SpringBootTest
@Testcontainers
@ActiveProfiles("testcontainers")
class SecurityE2ETest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private JwtAuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private LogOperationService logOperationService;
}
```

## Estratégia de Execução
- Testes E2E devem ser executados manualmente ou em pipeline de CI/CD
- Devem usar banco de dados real via TestContainers
- Devem limpar o banco de dados antes de cada teste
- Devem validar estado final do banco de dados
- Devem verificar logs de auditoria

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
| Componente | `Security - End-to-End` |
| Camada | E2E |
| Tipo de teste | End-to-End |
| Domínio | Autenticação, Autorização e Auditoria |
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
- [ ] O fluxo cobre cenários completos simulando uso real em produção.
- [ ] Os nomes de classes, métodos, arquivos e mensagens seguem ADR-010.
- [ ] Os asserts são explícitos, legíveis e preferencialmente usam AssertJ/JUnit 5 conforme ADR-012.
- [ ] Teste usa TestContainers para banco de dados real conforme ADR-012.
- [ ] Teste valida estado final do banco de dados.
- [ ] Teste verifica logs de auditoria.
- [ ] O documento está em UTF-8 e usa linguagem clara e consistente com ADR-050/ADR-052.

### Evidências esperadas

- Cenários completos documentados com Given/When/Then.
- Cenários negativos e exceções documentados quando aplicáveis.
- Dependências e pré-condições explicitados.
- Resultados esperados verificáveis por teste automatizado.
- Rastreabilidade com CDU, domínio, componentes e ADRs aplicáveis.
- Configuração de TestContainers documentada.
- Estratégia de execução definida.

### Referências ADR

- [ADR-010 - Padrões de Nomenclatura](../../../../ADR/010-nomenclature-standards.md)
- [ADR-011 - Exception Handling Patterns](../../../../ADR/011-exception-handling-patterns.md)
- [ADR-012 - Padrões de Teste Automatizado](../../../../ADR/012-testing-patterns.md)
- [ADR-050 - Diretrizes Gerais de Padronização](../../../../ADR/050-standardization-guidelines.md)
- [ADR-052 - MADR e Linguagem Normativa](../../../../ADR/052-adopt-madr-and-rfc-normative-language-for-adrs.md)

### Referências do projeto

- CDU: [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md)
- Classes: [`JwtAuthenticationService`](../../../../ia-core-security-service/src/main/java/com/ia/core/security/service/authentication/JwtAuthenticationService.java), [`UserService`](../../../../ia-core-security-service/src/main/java/com/ia/core/security/service/UserService.java), [`LogOperationService`](../../../../ia-core-security-service/src/main/java/com/ia/core/security/service/log/operation/LogOperationService.java)
