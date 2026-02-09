# Plano de Refatoração Unificado - ia-core e Biblia

## Sumário

- [Visão Geral](#visão-geral)
- [Ordem de Execução](#ordem-de-execução)
- [Análise da Arquitetura Atual](#análise-da-arquitetura-atual)
- [Fases de Refatoração](#fases-de-refatoração)
- [Scripts Flyway](#scripts-flyway)
- [Padrões e Convenções](#padrões-e-convenções)
- [Métricas de Sucesso](#métricas-de-sucesso)

---

## Visão Geral

Este documento define o plano de refatoração completo para os projetos **ia-core** (genérico) e **Biblia** (específico), seguindo os princípios SOLID, Clean Architecture e Clean Code.

### Princípios Fundamentais

1. **SRP (Single Responsibility Principle)**: Cada classe tem uma única responsabilidade
2. **OCP (Open/Closed Principle)**: Aberto para extensão, fechado para modificação
3. **LSP (Liskov Substitution Principle)**: Subclasses substituíveis pela classe base
4. **ISP (Interface Segregation Principle)**: Interfaces segregadas por responsabilidade
5. **DIP (Dependency Inversion Principle)**: Depender de abstrações, não implementações

### Clean Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                       │
│  (Views, ViewModels, Controllers)                         │
├─────────────────────────────────────────────────────────────┤
│                     Application Layer                       │
│  (Services, Use Cases, DTOs, Translators)                 │
├─────────────────────────────────────────────────────────────┤
│                      Domain Layer                          │
│  (Entities, Value Objects, Domain Services)               │
├─────────────────────────────────────────────────────────────┤
│                   Infrastructure Layer                      │
│  (Repositories, Adapters, External Services)              │
└─────────────────────────────────────────────────────────────┘
```

---

## Ordem de Execução

### Fase 0: Fundamentos (ia-core) ✅ Já Implementado
**Objetivo**: Estabelecer infraestrutura base

1. **BaseEntity** - Entidade base com ID (TSID) e versionamento
2. **BaseRepository** - Repository pattern com Spring Data JPA
3. **BaseService** - Interfaces de serviço base (CRUD)
4. **DTO Pattern** - Classes DTO com validação Jakarta
5. **Translator Pattern** - Internacionalização (i18n)

### Fase 1: ia-core (Genérico) 🔄 Em Andamento
**Objetivo**: Refatorar módulos core antes de usar em Biblia

| Módulo | Prioridade | Status | Ações |
|--------|------------|--------|-------|
| ia-core-model | Alta | ✅ | Entidades base (BaseEntity, TSID, Attachments) |
| ia-core-security-model | Alta | ✅ | User, Role, Privilege, Authentication |
| ia-core-service | Alta | ✅ | SaveBaseService, ValidationBaseService |
| ia-core-service-model | Alta | ✅ | DTOs, Translators, Exceptions |
| ia-core-rest | Média | ✅ | Controllers base |
| ia-core-view | Média | ✅ | FormViewModel, MVVM |
| ia-core-quartz | Média | ✅ | Scheduler, Periodicidade |
| ia-core-llm-service | Baixa | 🔄 | Chat, Vector Store, OWL |
| ia-core-nlp | Baixa | ✅ | Tokenizer, NLP |

### Fase 2: Biblia (Específico) 📋 Planejado
**Objetivo**: Refatorar após ia-core estar estável

| Módulo | Prioridade | Dependências |
|--------|------------|--------------|
| biblia-model | Alta | ia-core-model |
| biblia-service | Alta | ia-core-service |
| biblia-service-model | Alta | ia-core-service-model |
| biblia-rest | Média | ia-core-rest |
| biblia-view | Média | ia-core-view |

---

## Análise da Arquitetura Atual

### Estrutura do ia-core

```
ia-core/
├── ia-core-model/                    # Camada de domínio
│   ├── BaseEntity.java              # Entidade base (ID TSID, Version)
│   ├── TSID.java                   # Generator de ID
│   ├── HasVersion.java             # Interface de versionamento
│   ├── attachment/Attachment.java   # Entidade de anexo
│   └── util/                       # Utilitários
│
├── ia-core-security-model/          # Segurança (dominio)
│   ├── user/User.java              # Entidade usuário
│   ├── role/Role.java              # Entidade função
│   ├── privilege/Privilege.java     # Entidade privilégio
│   ├── authentication/            # JWT, OAuth
│   └── functionality/              # Funcionalidades
│
├── ia-core-service/                 # Camada de aplicação
│   ├── SaveBaseService.java        # Interface save CRUD
│   ├── ValidationBaseService.java   # Interface validação
│   ├── contract/                   # Contratos (ISP)
│   │   ├── HasMapper.java
│   │   ├── HasRepository.java
│   │   └── HasTransaction.java
│   ├── validators/                 # Validadores
│   │   ├── IServiceValidator.java
│   │   └── JakartaValidator.java
│   └── mapper/                     # Mappers
│
├── ia-core-service-model/           # DTOs e Translators
│   ├── dto/
│   │   ├── AbstractDTO.java
│   │   └── DTO.java
│   ├── exception/ServiceException.java
│   └── translator/Translator.java
│
├── ia-core-view/                   # Camada de apresentação
│   ├── components/form/
│   │   ├── FormView.java          # Componente Vaadin
│   │   ├── FormViewModel.java     # MVVM ViewModel
│   │   └── FormViewModelConfig.java # Configuração
│   ├── client/                     # Clients REST
│   └── manager/                    # Managers de visualização
│
├── ia-core-quartz/                  # Agendamento
│   ├── model/scheduler/
│   │   └── SchedulerConfig.java
│   └── model/periodicidade/
│
├── ia-core-llm-service/             # Serviços LLM
│   ├── chat/ChatService.java
│   ├── vector/VectorStoreService.java
│   └── owl/CoreOWLService.java
│
└── ia-core-flyway/                 # Migrações
```

### Padrões Identificados

#### 1. Service Pattern (Já Implementado)
```java
@Service
public class EventoService extends DefaultSecuredBaseService<Evento, EventoDTO> {
    public EventoService(EventoServiceConfig config) {
        super(config);
    }
}
```

#### 2. Repository Pattern (Já Implementado)
```java
public interface EventoRepository extends BaseEntityRepository<Evento> {
}
```

#### 3. MVVM Pattern (Já Implementado)
```java
// View
public class EventoFormView extends FormView<EventoDTO> {
    public EventoFormView(IFormViewModel<EventoDTO> viewModel) {
        super(viewModel);
    }
}

// ViewModel
public class EventoFormViewModel extends FormViewModel<EventoDTO> {
    public EventoFormViewModel(EventoFormViewModelConfig config) {
        super(config);
    }
}

// Config
public class EventoFormViewModelConfig extends FormViewModelConfig<EventoDTO> {
    private final EventoManager eventoManager;
    
    public EventoFormViewModelConfig(boolean readOnly, EventoManager manager) {
        super(readOnly);
        this.eventoManager = manager;
    }
}
```

#### 4. Validator Pattern (Já Implementado)
```java
@Component
public class BeanValidator {
    private final jakarta.validation.Validator validator;
    private final Translator translator;
    
    public <T> List<String> validate(T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        return violations.stream()
            .map(v -> translator.translate(v.getMessage()))
            .collect(Collectors.toList());
    }
}
```

---

## Fases de Refatoração

### Fase 1: Fundamentos e Infraestrutura ✅

#### Passo 1.1: Padronizar Validação Jakarta ✅

**Status**: Implementado

**JakartaValidator** já existente em `ia-core-service/src/main/java/com/ia/core/service/validators/JakartaValidator.java`

**DTOs com Validação**:
```java
public class EventoDTO {
    @NotNull(message = "{validation.evento.nome.required}")
    @Size(min = 3, max = 200, message = "{validation.evento.nome.size}")
    private String nome;
    
    @NotNull(message = "{validation.evento.data.required}")
    private LocalDateTime dataEvento;
}
```

#### Passo 1.2: Internacionalização (i18n) ✅

**Status**: Implementado

**Arquivo de Tradução**:
```properties
# translations_service_model_pt_BR.properties
validation.evento.nome.required=Nome do evento é obrigatório
validation.evento.nome.size=Nome deve ter entre 3 e 200 caracteres
validation.evento.data.required=Data do evento é obrigatória
```

**Translator Pattern**:
```java
public class EventoTranslator {
    public static class VALIDATION {
        public static final String NOME_REQUIRED = "validation.evento.nome.required";
        public static final String NOME_SIZE = "validation.evento.nome.size";
    }
    
    public static final String NOME = "evento.nome";
    public static final String DATA = "evento.data";
}
```

### Fase 2: Separação de Responsabilidades 🔄

#### Passo 2.1: SRP em Services

**Objetivo**: Dividir serviços com múltiplas responsabilidades

**Serviços a Separar**:
| Serviço Original | Serviços Propostos | Motivação |
|-----------------|-------------------|-----------|
| ChatService | ChatSessionService, ChatMessageService | SRP |
| CoreOWLService | OWLParsingService, OWLReasoningService | SRP |
| LLMTransformationService | ImageProcessorService, TextTransformationService | SRP |

**Exemplo de Refatoração**:
```java
// ANTES: ChatService com múltiplas responsabilidades
@Service
public class ChatService {
    public void sendMessage(ChatMessage message);          // Chat
    public void storeVector(VectorStoreEntry entry);       // Vector Store
    public List<Template> getTemplates();                   // Templates
}

// DEPOIS: Serviços segregados
@Service
public class ChatSessionService {
    public void sendMessage(ChatMessage message);
    public ChatSession createSession(User user);
}

@Service
public class VectorStoreService {
    public void store(VectorStoreEntry entry);
    public List<SimilarEntry> findSimilar(String query);
}

@Service
public class TemplateService {
    public List<Template> findAll();
    public Template findById(Long id);
}
```

#### Passo 2.2: Interface Segregation

**Objetivo**: Segregar interfaces base

**Interfaces Existentes**:
- `SaveBaseService<T, D>` - Salvar
- `DeleteBaseService<T, D>` - Deletar
- `FindBaseService<T, D>` - Buscar
- `ListBaseService<T, D>` - Listar
- `CountBaseService<T, D>` - Contar

**Mantidas como estão** (já seguem ISP)

### Fase 3: Clean Code

#### Passo 3.1: Nomenclatura

**Correções Necessárias**:
| Arquivo | Problema | Correção |
|---------|----------|----------|
| LLMTransformationService.java:34 | `llmComminicator` typo | `llmCommunicator` |
| VectorStoreService | Nomenclatura inconsistente | Padronizar `*Service` |

#### Passo 3.2: Documentação

**Javadoc Necessário**:
- Todos os métodos públicos devem ter Javadoc
- Documentar exceções lançadas
- Documentar parâmetros de retorno

#### Passo 3.3: Utilitários

**Utilitários Identificados**:
| Classe | Responsabilidade | Status |
|--------|-----------------|--------|
| DateTimeUtils | Datas e horários | ✅ |
| EnumUtils | Operações com enums | ✅ |
| FormatUtils | Formatação | ✅ |
| Patterns | Expressões regulares | ✅ |
| ThreadUtils | Operações de thread | ✅ |

### Fase 4: Performance e Otimização

#### Passo 4.1: Consultas Otimizadas

**N+1 Problem**:
```java
// ANTES: N+1 Problem
@Query("SELECT e FROM Evento e")
List<Evento> findAll();

// DEPOIS: Fetch Join
@Query("SELECT DISTINCT e FROM Evento e LEFT JOIN FETCH e.participantes")
List<Evento> findAllWithParticipantes();
```

**Indexes**:
```sql
CREATE INDEX idx_evento_data ON evento(data_evento);
CREATE INDEX idx_evento_tipo ON evento(tipo_evento);
```

#### Passo 4.2: Caching

**@Cacheable**:
```java
@Cacheable(value = "eventos", key = "#id")
public Evento findById(Long id) {
    return repository.findById(id).orElse(null);
}
```

---

## Scripts Flyway

### Estrutura de Migrações

```
ia-core/
├── ia-core-flyway/src/main/resources/db/migrations/
│   ├── V10022025103000__LLM_SCHEMA.sql       # ✅ Existente
│   ├── V30092025163200__QUARTZ.sql           # ✅ Existente
│   └── V30092025163300__QUARTZ.sql           # ✅ Existente
│
Biblia/
├── biblia-flyway/src/main/resources/db/migrations/
│   ├── V10022025103000__USER_SCHEMA.sql     # 📋 Criar
│   ├── V10022025103001__ROLE_SCHEMA.sql      # 📋 Criar
│   ├── V10022025103002__PRIVILEGE_SCHEMA.sql # 📋 Criar
│   └── ...
```

### Scripts Propostos para ia-core-security

#### V10022025103000__USER_SCHEMA.sql
```sql
-- Schema: security
-- Tabela: core_user

CREATE TABLE IF NOT EXISTS core_user (
    id BIGINT NOT NULL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 1,
    user_name VARCHAR(500) NOT NULL,
    user_code VARCHAR(500) NOT NULL,
    password VARCHAR(500),
    enabled CHAR(1) NOT NULL DEFAULT 'S',
    account_not_expired CHAR(1) NOT NULL DEFAULT 'S',
    account_not_locked CHAR(1) NOT NULL DEFAULT 'S',
    credentials_not_expired CHAR(1) NOT NULL DEFAULT 'S',
    CONSTRAINT uk_core_user_user_code UNIQUE (user_code),
    CONSTRAINT uk_core_user_user_name UNIQUE (user_name)
);

CREATE INDEX IF NOT EXISTS idx_core_user_user_code ON core_user(user_code);
CREATE INDEX IF NOT EXISTS idx_core_user_enabled ON core_user(enabled);
```

#### V10022025103001__ROLE_SCHEMA.sql
```sql
-- Schema: security
-- Tabela: core_role

CREATE TABLE IF NOT EXISTS core_role (
    id BIGINT NOT NULL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 1,
    name VARCHAR(500) NOT NULL,
    description VARCHAR(1000),
    CONSTRAINT uk_core_role_name UNIQUE (name)
);

-- Tabela de associação: core_users_roles
CREATE TABLE IF NOT EXISTS core_users_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT pk_users_roles PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_users_roles_user FOREIGN KEY (user_id) 
        REFERENCES core_user(id) ON DELETE CASCADE,
    CONSTRAINT fk_users_roles_role FOREIGN KEY (role_id) 
        REFERENCES core_role(id) ON DELETE CASCADE,
    CONSTRAINT uk_users_roles_user_role UNIQUE (user_id, role_id)
);

CREATE INDEX IF NOT EXISTS idx_users_roles_role_id ON core_users_roles(role_id);
```

#### V10022025103002__PRIVILEGE_SCHEMA.sql
```sql
-- Schema: security
-- Tabela: core_privilege

CREATE TABLE IF NOT EXISTS core_privilege (
    id BIGINT NOT NULL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 1,
    name VARCHAR(500) NOT NULL,
    description VARCHAR(1000),
    operation VARCHAR(50) NOT NULL,
    resource VARCHAR(200) NOT NULL,
    CONSTRAINT uk_core_privilege_name UNIQUE (name),
    CONSTRAINT uk_core_privilege_resource_op UNIQUE (resource, operation)
);

-- Tabela: core_role_privilege
CREATE TABLE IF NOT EXISTS core_role_privilege (
    role_id BIGINT NOT NULL,
    privilege_id BIGINT NOT NULL,
    CONSTRAINT pk_role_privilege PRIMARY KEY (role_id, privilege_id),
    CONSTRAINT fk_role_privilege_role FOREIGN KEY (role_id) 
        REFERENCES core_role(id) ON DELETE CASCADE,
    CONSTRAINT fk_role_privilege_priv FOREIGN KEY (privilege_id) 
        REFERENCES core_privilege(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_role_privilege_privilege_id ON core_role_privilege(privilege_id);
```

### Scripts Propostos para ia-core-quartz

#### V30092025163200__QUARTZ.sql
```sql
-- Schema: scheduler
-- Tabela: core_scheduler_config

CREATE TABLE IF NOT EXISTS core_scheduler_config (
    id BIGINT NOT NULL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 1,
    job_class_name VARCHAR(500) NOT NULL UNIQUE,
    periodicidade BIGINT,
    enabled CHAR(1) NOT NULL DEFAULT 'S',
    description VARCHAR(1000),
    cron_expression VARCHAR(100),
    CONSTRAINT fk_scheduler_config_periodicidade 
        FOREIGN KEY (periodicidade) REFERENCES core_periodicidade(id) ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS idx_scheduler_job_class ON core_scheduler_config(job_class_name);
CREATE INDEX IF NOT EXISTS idx_scheduler_enabled ON core_scheduler_config(enabled);

-- Tabela: core_periodicidade
CREATE TABLE IF NOT EXISTS core_periodicidade (
    id BIGINT NOT NULL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 1,
    tipo VARCHAR(50) NOT NULL,
    dia_semana INTEGER,
    dia_mes INTEGER,
    hora INTEGER,
    minuto INTEGER,
    intervalo_dias INTEGER
);

CREATE INDEX IF NOT EXISTS idx_periodicidade_tipo ON core_periodicidade(tipo);
```

### Scripts Propostos para biblia

#### V40012025103000__BIBLIA_SCHEMA.sql
```sql
-- Schema: biblia
-- Tabela: biblia_livro

CREATE TABLE IF NOT EXISTS biblia_livro (
    id BIGINT NOT NULL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 1,
    nome VARCHAR(500) NOT NULL,
    abreviacao VARCHAR(50) NOT NULL,
    testamento VARCHAR(50) NOT NULL,
    ordem INTEGER NOT NULL,
    CONSTRAINT uk_biblia_livro_abreviacao UNIQUE (abreviacao)
);

-- Tabela: biblia_capitulo
CREATE TABLE IF NOT EXISTS biblia_capitulo (
    id BIGINT NOT NULL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 1,
    livro_id BIGINT NOT NULL,
    numero INTEGER NOT NULL,
    CONSTRAINT fk_capitulo_livro FOREIGN KEY (livro_id) 
        REFERENCES biblia_livro(id) ON DELETE CASCADE,
    CONSTRAINT uk_capitulo_livro_numero UNIQUE (livro_id, numero)
);

CREATE INDEX IF NOT EXISTS idx_capitulo_livro_id ON biblia_capitulo(livro_id);

-- Tabela: biblia_versiculo
CREATE TABLE IF NOT EXISTS biblia_versiculo (
    id BIGINT NOT NULL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 1,
    capitulo_id BIGINT NOT NULL,
    numero INTEGER NOT NULL,
    texto TEXT NOT NULL,
    CONSTRAINT fk_versiculo_capitulo FOREIGN KEY (capitulo_id) 
        REFERENCES biblia_capitulo(id) ON DELETE CASCADE,
    CONSTRAINT uk_versiculo_capitulo_numero UNIQUE (capitulo_id, numero)
);

CREATE INDEX IF NOT EXISTS idx_versiculo_capitulo_id ON biblia_versiculo(capitulo_id);
```

---

## Padrões e Convenções

### 1. Convenções de Nomenclatura

#### Classes
| Tipo | Prefixo | Exemplo |
|------|---------|---------|
| Entidade | (nenhum) | `User`, `Role` |
| DTO | (nenhum) | `UserDTO`, `RoleDTO` |
| Service | (nenhum) | `UserService` |
| Repository | (nenhum) | `UserRepository` |
| ViewModel | (nenhum) | `UserFormViewModel` |
| View | (nenhum) | `UserFormView` |
| Config | (nenhum) | `UserFormViewModelConfig` |
| Validator | (nenhum) | `UserValidator` |
| Translator | (nenhum) | `UserTranslator` |

#### Métodos
| Operação | Prefixo | Exemplo |
|----------|---------|---------|
| Buscar por ID | `findById` | `findById(Long id)` |
| Buscar todos | `findAll` | `findAll()` |
| Criar | `create` | `create(UserDTO dto)` |
| Atualizar | `update` | `update(UserDTO dto)` |
| Deletar | `delete` | `delete(Long id)` |
| Validar | `validate` | `validate(UserDTO dto)` |

#### Arquivos i18n
```
translations_{module}_{locale}.properties

Exemplos:
- translations_service_model_pt_BR.properties
- translations_biblia_pt_BR.properties
- translations_security_pt_BR.properties
```

### 2. Estrutura de Pacotes

```
com.ia.{module}.{layer}.{feature}

Exemplos:
- com.ia.core.model.user
- com.ia.core.service.evento
- com.ia.core.view.components.form.evento
- com.ia.biblia.rest.livro
```

### 3. Anotações Jakarta Validation

```java
// Obrigatoriedade
@NotNull(message = "{validation.field.required}")
@NotBlank(message = "{validation.field.notblank}")
@NotEmpty(message = "{validation.field.notempty}")

// Tamanho
@Size(min = 3, max = 100, message = "{validation.field.size}")
@Length(min = 3, max = 100, message = "{validation.field.size}")

// Formato
@Pattern(regexp = "[a-zA-Z]+", message = "{validation.field.pattern}")

// Números
@Min(value = 0, message = "{validation.field.min}")
@Max(value = 100, message = "{validation.field.max}")
@DecimalMin(value = "0.0", message = "{validation.field.decimalmin}")
@DecimalMax(value = "999.99", message = "{validation.field.decimalmax}")

// Datas
@Past(message = "{validation.field.past}")
@Future(message = "{validation.field.future}")
```

### 4. Estrutura MVVM

```
{feature}/
├── form/
│   ├── {Feature}FormView.java          # View (Vaadin)
│   ├── {Feature}FormViewModel.java    # ViewModel
│   └── {Feature}FormViewModelConfig.java # Config
├── list/
│   ├── {Feature}ListView.java
│   └── {Feature}ListViewModel.java
└── page/
    ├── {Feature}PageView.java
    └── {Feature}PageViewModel.java
```

---

## Métricas de Sucesso

| Métrica | Target | Atual | Status |
|---------|--------|-------|--------|
| Cobertura de Testes | 80% | 0% | 🔴 |
| Complexidade Ciclomática | < 10 | ? | ⚪ |
| DTOs com Validação Jakarta | 100% | 80% | 🟡 |
| Strings em i18n | 100% | 95% | 🟡 |
| Documentação Javadoc | 100% | 60% | 🟡 |
| Nomenclatura Padronizada | 100% | 90% | 🟡 |

---

## Estratégia de Migração

### Abordagem: Incremental

1. **Fase 0**: Infraestrutura base (ja implementada)
2. **Fase 1**: Validacao Jakarta completa (80%)
3. **Fase 2**: i18n completo (95%)
4. **Fase 3**: SRP em Services
5. **Fase 4**: Performance e otimizacao
6. **Fase 5**: Testes unitarios

### Versionamento

| Versão | Descrição | Status |
|--------|-----------|--------|
| v1.0 | Estado inicial | ✅ |
| v1.1 | Validacao Jakarta | 🔄 |
| v1.2 | i18n completo | 🔄 |
| v2.0 | SOLID completo | 📋 |

---

## O que NÃO Fazer

1. ❌ NÃO criar classes `*ServiceConfig` com `@Configuration` e `@Bean`
2. ❌ NÃO implementar Unit of Work manual (usar `@Transactional`)
3. ❌ NÃO criar novo Exception Handler (usar existente)
4. ❌ NÃO refatorar cache existente
5. ❌ NÃO usar `ObservableList` (usar `PropertyChangeSupport`)
6. ❌ NÃO criar nova estrutura de tradução (usar `Translator`)
7. ❌ NÃO criar novos Domain Services (usar existentes)

---

## Referências

- [SOLID Principles](https://solid principles.dev)
- [Clean Code](https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882)
- [Clean Architecture](https://www.amazon.com/Clean-Architecture-Craftsmans-Software-Structure/dp/0134494164)
- [Spring Boot Best Practices](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Vaadin Best Practices](https://vaadin.com/docs/latest/flow)
- [Jakarta Validation](https://jakarta.ee/specifications/validation/3.0/)
- [Flyway Documentation](https://flywaydb.org/documentation/)
