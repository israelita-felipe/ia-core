# Guia de Módulos - ia-core

## 📋 Visão Geral

**ia-core** é um framework modular construído sobre Spring Boot 3/4 que fornece uma base robusta para aplicações Java. Segue princípios de **Clean Architecture** com separação clara em camadas e suporta múltiplos padrões de integração.

## 🏗️ Organização Modular

### 1️⃣ Camada de Domínio (Model)

**Responsabilidade**: Definir entidades e modelos de dados

| Módulo | Descrição | Heranç da |
|--------|-----------|---------|
| `ia-core-model` | Entidades base, TSID, Specifications | JPA base |
| `ia-core-security-model` | User, Role, Permission entities | ia-core-model |
| `ia-core-llm-model` | Conversation, ChatMessage, Prompt | ia-core-model |
| `ia-core-quartz` | JobDetail, JobExecution entities | ia-core-model |
| `ia-core-communication-model` | Email, Notification, Channel | ia-core-model |
| `ia-core-flyway-model` | MigrationHistory, SchemaVersion | ia-core-model |

### 2️⃣ Camada de Serviço (Service)

**Responsabilidade**: Lógica de negócio, orquestração, transações

| Módulo | Descrição | Depende de |
|--------|-----------|----------|
| `ia-core-service` | AbstractCrudService genérico | ia-core-model |
| `ia-core-security-service` | Autenticação, JWT, autorização | ia-core-security-model |
| `ia-core-llm-service` | Chat, Prompts, integração LLM | ia-core-llm-model |
| `ia-core-quartz-service` | Agendamento, jobs, scheduler | ia-core-quartz |
| `ia-core-communication-service` | Email, SMS, notificações | ia-core-communication-model |
| `ia-core-flyway-service` | Migrações, validação de BD | ia-core-flyway-model |

### 3️⃣ Camada de Transferência de Dados (Service Model)

**Responsabilidade**: DTOs, Requests, Responses, Mappers

| Módulo | DTOs | Mappers |
|--------|------|---------|
| `ia-core-service-model` | CRUD genéricos, Filter, Page | MapStruct |
| `ia-core-security-service-model` | LoginDTO, JwtTokenDTO, UserDTO | Mapeador User |
| `ia-core-llm-service-model` | ChatRequest, ChatResponse | Mapeador de conversa |
| `ia-core-quartz-service-model` | CreateJobRequest, JobDetailDTO | Mapeador de job |
| `ia-core-communication-service-model` | SendEmailRequest, SendResultDTO | Mapeador de comunicação |
| `ia-core-flyway-service-model` | MigrationResult, ValidationResult | Mapeador de migração |

### 4️⃣ Camada de Apresentação (REST)

**Responsabilidade**: Endpoints HTTP, validação, documentação OpenAPI

| Módulo | Controllers | Swagger |
|--------|------------|---------|
| `ia-core-rest` | Controllers base genéricos | @Operation, @Tag |
| Derivado pelo cliente | Extensão com Domain Controllers | Endpoint documentation |

### 5️⃣ Camada de Interface (View)

**Responsabilidade**: UI com Vaadin, componentes reutilizáveis

| Módulo | Componentes | Integração |
|--------|------------|-----------|
| `ia-core-view` | FormView, Grid, Dialog, Layout | Vaadin 25.x |
| `ia-core-security-view` | LoginView, UserListView, RoleMgmt | io-core-view |
| `ia-core-llm-view` | ChatView, ConversationList, Prompt | ia-core-view |
| `ia-core-quartz-view` | JobListView, ExecutionHistory | ia-core-view |
| `ia-core-communication-view` | EmailHistory, TemplateEditor | ia-core-view |
| `ia-core-flyway-view` | MigrationHistory, SchemaValidation | ia-core-view |

## 🔄 Fluxo Completo de Requisição

```
HTTP Request (GET /api/v1/users/1)
         │
         ▼
REST Controller (ia-core-rest)
├─ @GetMapping("/{id}")
├─ Validação (@Validated)
├─ Autenticação (JWT)
         │
         ▼
Service (ia-core-service)
├─ findById(id)
├─ Lógica de negócio
├─ @Transactional
         │
         ▼
JPA Repository
         │
         ▼
Database (via Hibernate ORM)
         │
         ◄─────────────────────────┐
         │                         │
         ▼                         │
Entity → DTO (Mapper) ───────────│
         │                       │
         ▼                       │
JSON Response ◄───────────────────
```

## 🎯 Padrões Implementados

### SOLID Principles

- **S**ingle Responsibility: Cada módulo com responsabilidade única
- **O**pen/Closed: Extensível via subclassing e interfaces
- **L**iskov Substitution: Interfaces JPA consistentes
- **I**nterface Segregation: DTOs específicos por operação
- **D**ependency Inversion: Spring DI container

### Architectural Patterns

- **Layered Architecture**: Model → Service → REST/View
- **Repository Pattern**: Abstração de acesso a dados
- **Service Locator**: Spring beans como serviços
- **DTO Pattern**: Transferência sem expor entidades
- **Builder Pattern**: Construção fluida de objetos
- **Strategy Pattern**: Diferentes provedores (LLM, SMS)
- **Template Method**: AbstractCrudService base

### Cross-Cutting Concerns

| Concern | Module | Technology |
|---------|--------|-----------|
| Security | ia-core-security | Spring Security, JWT |
| DB Migration | ia-core-flyway | Flyway |
| Reporting | ia-core-report | JasperReports |
| NLP | ia-core-nlp | OpenNLP, Stanford Core |
| Grammar | ia-core-grammar | ANTLR 4 |
| LLM Integration | ia-core-llm | Spring AI |
| Scheduling | ia-core-quartz | Quartz Scheduler |
| Communication | ia-core-communication | Spring Mail, SMS APIs |
| Resilience | ia-core-resilience4j | Resilience4j |
| Testing | ia-core-integration-test | JUnit, TestContainers |

## 📦 Dependência Mínima para Começar

```xml
<!-- Mínimo para Application Monolítica -->
<dependencies>
    <dependency>
        <groupId>com.ia</groupId>
        <artifactId>ia-core</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
</dependencies>

<!-- Inclui automaticamente:

ia-core -> ia-core-parent
├── ia-core-model (base entities)
├── ia-core-service (CRUD base)
├── ia-core-security-model/service (auth)
├── ia-core-rest (controllers)
├── ia-core-view (Vaadin UI)
├── ia-core-llm-service (optional LLM)
├── ia-core-quartz-service (optional scheduling)
└── ... outros conforme necessário
```

## 🚀 Como Estender ia-core

### 1. Criar Novo Módulo de Domínio

```bash
mkdir myproject-model
# Dentro: extends AbstractEntity para entidades
```

### 2. Criar Serviço

```bash
mkdir myproject-service
# Dentro: extends AbstractCrudService
```

### 3. Criar DTOs

```bash
mkdir myproject-service-model
# Dentro: Request/Response/Filter DTOs + Mappers
```

### 4. Criar REST API

```bash
mkdir myproject-rest
# Dentro: @RestController que usa myproject-service
```

### 5. Criar UI (opcional)

```bash
mkdir myproject-view
# Dentro: @Route views com Vaadin
```

## 📊 Matriz de Dependências

```
ia-core-view ← ia-core-security-view, ia-core-llm-view, ...
ia-core-service-model ← ia-core-service
ia-core-security-service ← ia-core-security-model
ia-core-llm-service ← ia-core-llm-model
ia-core-quartz-service ← ia-core-quartz
ia-core-communication-service ← ia-core-communication-model
ia-core-flyway-service ← ia-core-flyway-model
ia-core-rest ← ia-core-service, ia-core-security-service
ia-core-nlp ← ia-core-service
ia-core-grammar ← (standalone)
ia-core-report ← (standalone, parametrizado)
ia-core-resilience4j ← (standalone, via AOP)
ia-core-integration-test ← todos (test scope)
```

## 🔐 Segurança

### Camadas de Proteção

1. **HTTP Layer**: HTTPS, CORS
2. **Authentication**: JWT tokens
3. **Authorization**: Role-based (RBAC)
4. **Method-level**: @PreAuthorize
5. **Data-level**: Soft delete, row-level security

### Configuração

```yaml
security:
  jwt:
    secret: ${JWT_SECRET:change-in-prod}
    expiration: 3600000 # 1h
  cors:
    allowed-origins: http://localhost:3000
  password:
    encoder: bcrypt # BCryptPasswordEncoder
    strength: 12 # rounds
```

## 🧪 Estratégia de Testes

```
Unit Tests (Serviços)
    ↓
Integration Tests (Service + Repo)
    ↓
Controller Tests (REST with MockMvc)
    ↓
End-to-End Tests (Full stack)
```

Base class fornecida: `IntegrationTestBase` com:
- MockMvc
- TestRestTemplate
- ObjectMapper
- Fixtures de dados
- Custom assertions

## 📈 Escalabilidade

### Por Design Suporta:

- **Vertical**: Múltiplas threads, conexões DB
- **Horizontal**: Stateless services, cache distribuído
- **Messaging**: Para desacoplamento (futuro)
- **Async**: CompletableFuture, @Async
- **Microservices**: Separar por módulo

### Recomendações:

- Cache: Redis/Memcached
- Logging: ELK Stack, Splunk
- Monitoring: Prometheus, Grafana
- CI/CD: GitHub Actions, Jenkins

## 🔍 Documentação por Módulo

Cada módulo tem:
- `README.md` - Guia específico
- `pom.xml` - Dependências e versões
- `CHANGELOG.md` - Histórico de mudanças (root)
- JavaDoc no código

## 📚 Recursos Adicionais

- [CONTRIBUTING.md](./CONTRIBUTING.md) - Guia de contribuição
- [HELP.md](./HELP.md) - Perguntas frequentes
- [Arquivos de Decisão (ADR)](./ADR/) - Decisões arquiteturais
- [Casos de Uso (CDU)](./CDU/) - Especificação funcional
- [Regras de Negócio (RN)](./RN/) - Validações

## 🎓 Próximos Passos

1. **Entender estrutura**: Ler este MODULES.md
2. **Explorar módulos**: Abrir READMEs específicos
3. **Executar exemplo**: Ver projeto Biblia
4. **Customizar**: Criar novo módulo aplicação


