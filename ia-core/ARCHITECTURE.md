# Arquitetura do Projeto IA-Core

## Visão Geral

Este documento descreve a arquitetura técnica do projeto **IA-Core**, um sistema de inteligência artificial modular para processamento de linguagem natural, integração com modelos de linguagem (LLM) e agendamento de tarefas.

---

## 🎯 Princípios de Design

### Clean Architecture

O projeto segue **Clean Architecture** com separação em camadas independentes de regras de negócio:

```
┌─────────────────────────────────────────────────────────────┐
│                    CAMADA DE APRESENTAÇÃO                    │
│                    (View / REST API)                        │
├─────────────────────────────────────────────────────────────┤
│                     CAMADA DE APLICAÇÃO                      │
│                  (ViewModels, DTOs, Services)               │
├─────────────────────────────────────────────────────────────┤
│                    CAMADA DE DOMÍNIO                         │
│              (Entidades, Repositórios, Events)               │
├─────────────────────────────────────────────────────────────┤
│                   CAMADA DE INFRAESTRUTURA                   │
│           (Frameworks, Databases, External APIs)             │
└─────────────────────────────────────────────────────────────┘
```

### SOLID Applied

| Princípio | Aplicação |
|-----------|-----------|
| **SRP** | Cada serviço tem responsabilidade única |
| **OCP** | Extensível via novos módulos e serviços |
| **LSP** | Interfaces consistentes entre implementações |
| **ISP** | Interfaces segregadas por funcionalidade |
| **DIP** | Dependências injetadas via construtor |

---

## 📁 Estrutura de Módulos

```
ia-core/
├── ia-core-model/              # Entidades e modelos de domínio
│   ├── BaseEntity.java          # Entidade base com auditoria
│   ├── TSID.java                # Identificadores distribuídos
│   ├── filter/                  # Filtros dinâmicos (Specification Pattern)
│   │   ├── FilterRequest.java
│   │   ├── Operator.java        # 8 operadores de filtro
│   │   └── FieldType.java       # 11 tipos de campo
│   └── util/                    # Utilitários compartilhados
│
├── ia-core-service/             # Lógica de negócio base
│   ├── AbstractBaseService.java # Classe base com publishEvent()
│   ├── SaveBaseService.java     # Callbacks: beforeSave(), afterSave()
│   ├── DeleteBaseService.java   # Callbacks: beforeDelete(), afterDelete()
│   ├── event/                   # Eventos de domínio
│   │   ├── BaseServiceEvent.java
│   │   └── CrudOperationType.java
│   └── validators/              # Validadores compartilhados
│
├── ia-core-rest/               # Controllers REST
│   ├── AbstractBaseController.java
│   └── CoreRestControllerAdvice.java
│
├── ia-core-view/               # Interface MVVM (Vaadin)
│   ├── FormViewModel.java
│   ├── FormViewModelConfig.java
│   └── components/
│
├── ia-core-llm-model/          # Modelos específicos de LLM
│   ├── LLMModel.java
│   ├── Template.java
│   └── comando/
│
├── ia-core-llm-service/        # Serviços de LLM
│   ├── ChatService.java
│   ├── TemplateService.java
│   ├── transform/
│   │   ├── LLMTransformationService.java
│   │   ├── ImageProcessingService.java
│   │   └── TextExtractionService.java
│   └── owl/
│       ├── CoreOWLService.java
│       └── LLMCommunicator.java
│
├── ia-core-llm-view/           # View de LLM
│   ├── ChatDialog.java
│   └── ChatClient.java
│
├── ia-core-quartz/             # Modelo de agendamento
│   ├── SchedulerConfig.java
│   └── periodicidade/
│
├── ia-core-quartz-service/     # Serviços de agendamento
│   ├── SchedulerConfigService.java
│   └── JobSchedulerChecker.java
│
├── ia-core-quartz-view/        # View de agendamento
│
├── ia-core-nlp/                # Processamento de linguagem natural
│   ├── CoreTokenizerService.java
│   └── model/
│
├── ia-core-grammar/            # Gramáticas ANTLR
│   └── Leitura.g4
│
├── ia-core-report/             # Relatórios Jasper
│   ├── AbstractJasperReport.java
│   └── DynamicJasperReport.java
│
├── ia-core-flyway/             # Migrações de banco
│   └── CoreFlywayConfiguration.java
│
└── security-core-service/       # Serviços de segurança
    ├── DefaultSecuredBaseService.java
    ├── AbstractSecuredBaseService.java
    └── authorization/
```

---

## 🔧 Padrões de Projeto Aplicados

### 1. Service Pattern

**Estrutura:**
```java
@Service
public class NomeService extends DefaultSecuredBaseService<Entidade, DTO> {
    public NomeService(NomeServiceConfig config) {
        super(config);
    }
}
```

**Exemplo:** [`TemplateService.java`](ia-core-llm-service/src/main/java/com/ia/core/llm/service/template/TemplateService.java)

### 2. ServiceConfig Pattern

**Estrutura:**
```java
@Component
public class NomeServiceConfig 
    extends DefaultSecuredBaseServiceConfig<Entidade, DTO> {
    
    @Getter
    private final RepositorioRepository repositorio;
    
    public NomeServiceConfig(/* dependências */) {
        super(/* dependências base */);
        this.repositorio = repositorio;
    }
}
```

**Benefícios:**
- Injeção via construtor
- Dependências explícitas
- Testabilidade

### 3. Mapper Pattern (MapStruct)

**Estrutura:**
```java
@Mapper(componentModel = "spring", uses = { EnderecoMapper.class })
public interface EntidadeMapper {
    @Mapping(target = "enderecos", source = "enderecos")
    DTO toDTO(Entidade entity);
    
    @Mapping(target = "enderecos", source = "enderecos")
    Entidade toEntity(DTO dto);
}
```

### 4. Specification Pattern

**Operadores disponíveis:**

| Operador | Descrição | Exemplo |
|----------|-----------|---------|
| `EQUAL` | Igualdade | `nome = "João"` |
| `NOT_EQUAL` | Diferente | `status != "INATIVO"` |
| `LIKE` | Like (case-insensitive) | `nome LIKE "%João%"` |
| `IN` | Em lista | `status IN ("A", "B")` |
| `GREATER_THAN` | Maior que | `idade > 18` |
| `LESS_THAN` | Menor que | `idade < 65` |
| `GREATER_THAN_OR_EQUAL_TO` | Maior ou igual | `salario >= 1000` |
| `LESS_THAN_OR_EQUAL_TO` | Menor ou igual | `idade <= 65` |

### 5. Domain Events Pattern

**Estrutura:**
```java
// Publicação automática em DefaultSecuredBaseService
@Override
public void afterSave(D original, D saved, CrudOperationType type) {
    publishEvent(saved, type); // CREATED ou UPDATED
}

@Override
public void afterDelete(Long id, D dto) {
    publishEvent(dto, CrudOperationType.DELETED);
}
```

**Tipos de evento:**
- `CrudOperationType.CREATED` - Entidade criada
- `CrudOperationType.UPDATED` - Entidade atualizada
- `CrudOperationType.DELETED` - Entidade deletada

### 6. MVVM Pattern (View Layer)

**Estrutura:**
```
View (FormView.java)
    ↓
ViewModel (FormViewModel.java)
    ↓
Config (FormViewModelConfig.java)
    ↓
Service → Repository → Database
```

---

## 🔌 Dependências Externas

### Frameworks

| Dependência | Versão | Propósito |
|-------------|--------|-----------|
| Spring Boot | 3.x | Framework principal |
| Spring Data JPA | - | Persistência |
| Spring Security | - | Autenticação/Autorização |
| Spring AI | - | Integração com LLMs |
| Quartz | - | Agendamento |
| Jakarta EE | - | Validação, Persistence |
| MapStruct | 1.5.x | Mapeamento DTO-Entidade |
| Lombok | - | Redução de boilerplate |
| Vaadin | - | Interface web |

### Banco de Dados

- **HSQLDB** (desenvolvimento)
- **PostgreSQL** (produção)
- **MySQL** (produção)

### LLMs Suportados

- OpenAI GPT
- Anthropic Claude
- Modelos locais (via Ollama)

---

## 📊 Decisões de Arquitetura (ADR)

| ADR | Decisão | Status |
|-----|---------|--------|
| ADR-001 | Usar MapStruct para mapeamento DTO-Entidade | ✅ Implementado |
| ADR-002 | Usar Specification Pattern para filtros | ✅ Implementado |
| ADR-003 | Usar Translator Pattern para i18n | ✅ Implementado |
| ADR-004 | Usar ServiceConfig para injeção de dependências | ✅ Implementado |
| ADR-005 | Publicação automática de eventos de domínio | ✅ Implementado |

---

## 🧪 Testes

### Estrutura de Testes

```
src/test/java/
├── unit/                    # Testes unitários
│   └── services/
├── integration/            # Testes de integração
│   └── repositories/
└── acceptance/            # Testes de aceitação
```

### Cobertura Atual

| Módulo | Cobertura | Meta |
|--------|-----------|------|
| ia-core-model | > 70% | 80% |
| ia-core-service | > 60% | 80% |
| ia-core-rest | > 50% | 70% |

---

## 📈 Performance

### Otimizações Aplicadas

1. **EntityGraph** - Carregamento eager de relacionamentos
2. **Paginação** - Pageable para listas grandes
3. **Índices** - Índices de banco otimizados
4. **Cache** - Cache de entidades频繁 acessadas

### Plano de Performance

Consulte: [`PERFORMANCE_OPTIMIZATION_PLAN.md`](PERFORMANCE_OPTIMIZATION_PLAN.md)

---

## 🔒 Segurança

### Autorização

- **Funcionalidades** - Controle por funcionalidade
- **Contexto** - Contexto de segurança dinâmico
- **Logs** - Auditoria de operações

### Autenticação

- JWT (configurável)
- Session-based

---

## 📝 Convenções de Código

### Nomenclatura

| Elemento | Padrão | Exemplo |
|----------|--------|---------|
| Serviço | `NomeService` | `TemplateService` |
| Config | `NomeServiceConfig` | `TemplateServiceConfig` |
| DTO | `NomeDTO` | `TemplateDTO` |
| Mapper | `NomeMapper` | `TemplateMapper` |
| Repository | `NomeRepository` | `TemplateRepository` |
| Translator | `NomeTranslator` | `TemplateTranslator` |

### Commits

```
feat: Nova funcionalidade
fix: Correção de bug
docs: Documentação
refactor: Refatoração
perf: Performance
test: Testes
chore: Manutenção
```

---

## 🚀 Deploy

### Builds

```bash
# Compilar todos os módulos
mvn clean install

# Compilar módulo específico
mvn clean install -pl ia-core/ia-core-llm-service
```

### Docker

```bash
docker build -t ia-core .
docker run -p 8080:8080 ia-core
```

---

## 📚 Referências

- [README.md](README.md)
- [PLANO_REFATORACAO_COMPLETO.md](PLANO_REFATORACAO_COMPLETO.md)
- [REFACTORING_STATUS.md](REFACTORING_STATUS.md)
- [PERFORMANCE_OPTIMIZATION_PLAN.md](PERFORMANCE_OPTIMIZATION_PLAN.md)
- [CODING_STANDARDS.md](CODING_STANDARDS.md)

---

## 🤝 Contribuição

1. Leia [CONTRIBUTING.md](CONTRIBUTING.md)
2. Siga as convenções de código
3. Adicione testes para novas funcionalidades
4. Atualize a documentação
5. Abra um Pull Request
