# Plano de Refatoração Completo - ia-core e Biblia (gestor-igreja)

## 1. Análise dos Projetos

### 1.1 Projeto ia-core (já em refatoração)

**Estrutura atual:**
```
ia-core/
├── ia-core-model/          # Entidades base e utilitários
├── ia-core-service/        # Serviços JPA e monitoramento
├── ia-core-rest/           # Controllers REST e filtros
├── ia-core-security-model/ # Modelos de segurança (NOVO - FASE Y)
├── security-core-service/  # Serviços de segurança (NOVO - FASE Y)
├── ia-core-llm-*/          # Módulos LLM
├── ia-core-quartz-*/       # Módulos de agendamento
└── ia-core-view/           # Camada de apresentação
```

**Fases já concluídas:**
- FASE Q: Specification Pattern ✅
- FASE R: ADR Documentation ✅
- FASE S: Exception Handling ✅
- FASE U: Automated Tests ✅
- FASE V: Logging and Monitoring ✅
- FASE X: Javadoc Standards ✅
- **FASE Y: Segurança e Autenticação** (EM PROGRESSO)

### 1.2 Projeto Biblia (gestor-igreja)

**Estrutura atual:**
```
gestor-igreja/Biblia/
├── biblia-model/           # Modelos de domínio
├── biblia-service/         # Serviços de negócio
├── biblia-service-model/   # DTOs e翻译
├── biblia-rest/            # Controllers REST
├── biblia-view/            # Camada de apresentação
├── biblia-nlp/             # Processamento de linguagem natural
└── biblia-grammar/         # Gramáticas ANTLR
```

---

## 2. Problemas Identificados

### 2.1 SOLID Violations
| Problema | Módulo | Princípio Violado |
|----------|--------|-------------------|
| `CoreBaseService` faz too much | ia-core-service | SRP |
| Controllers com lógica de negócio | ia-core-rest, biblia-rest | SRP |
| `CoreJwtAuthenticationFilter` combina validação e logging | ia-core-rest | SRP |
| Classes de exceção genéricas | Ambos | ISP |

### 2.2 Clean Architecture Violations
| Problema | Solução |
|----------|---------|
| Dependência direta entre REST e Model | Inverter dependências via interfaces |
| Lógica de validação nos controllers | Mover para camada de serviço |
| DTOs misturados com entidades | Criar módulos separados |

### 2.3 Clean Code Violations
| Problema | Módulo |
|----------|--------|
| Nomes genéricos (ex: `CoreBaseController`) | Renomear para `AbstractBaseController` |
| Métodos longos (>30 linhas) | Extrair métodos |
| Comentários explicativos em vez de código claro | Refatorar |
| Variáveis com nomes ruins (ex: `temp`, `data1`) | Renomear |

---

## 3. Plano de Refatoração por Fases

### **FASE A: Correção de Dependências e Build** (IMEDIATA)

#### A1: Corrigir dependências do ia-core-service
- **Problema:** Faltam dependências do Spring Boot Actuator e Micrometer
- **Solução:** Adicionar dependências ao pom.xml
- **Arquivos afetados:**
  - `ia-core/ia-core-service/pom.xml`
- **Tipo de refatoração:** Adição de dependência
- **O que resolve:** Permite compilação do módulo de monitoramento

#### A2: Configurar módulos de segurança
- **Problema:** security-core-service depende de ia-core-service que tem problemas de build
- **Solução:** Ajustar dependências transitivas
- **Arquivos afetados:**
  - `security-core-service/pom.xml`
- **Tipo de refatoração:** Reorganização de dependências
- **O que resolve:** Permite compilação dos módulos de segurança

---

### **FASE B: Single Responsibility Principle (SRP)**

#### B1: Extrair `SecurityLoggingService` de `CoreRestSecurityConfig`
- **Problema:** `CoreRestSecurityConfig` faz logging + configuração de segurança
- **Solução:** Extrair lógica de logging para `SecurityLoggingService`
- **Arquivos afetados:**
  - `ia-core/ia-core-rest/src/main/java/com/ia/core/rest/security/CoreRestSecurityConfig.java`
  - Criar: `ia-core-security-service/src/main/java/com/ia/core/security/service/logging/SecurityLoggingService.java`
- **Tipo de refatoração:** Extract Class
- **O que resolve:** SRP - cada classe tem uma única responsabilidade

#### B2: Extrair `TokenDetailsExtractor` de `CoreRestSecurityConfig`
- **Problema:** Extração de claims do JWT misturada com configuração
- **Solução:** Criar `TokenDetailsExtractor` como componente separado
- **Arquivos afetados:**
  - Criar: `ia-core-security-service/src/main/java/com/ia/core/security/service/extractor/TokenDetailsExtractor.java`
- **Tipo de refatoração:** Extract Class
- **O que resolve:** Separação de concerns - extração de tokens

#### B3: Aplicar SRP em Controllers do Biblia
- **Problema:** Controllers misturam validação, transformação e lógica de negócio
- **Solução:** Criar `BaseControllerAdvice` para validação e mover lógica para serviços
- **Arquivos afetados:**
  - `gestor-igreja/Biblia/biblia-rest/src/main/java/com/ia/biblia/rest/control/**/*.java`
- **Tipo de refatoração:** Extract Method, Move Method
- **O que resolve:** Controllers só lidam com HTTP

#### B4: Extrair `ValidationService` genérico
- **Problema:** Validação duplicada em múltiplos serviços
- **Solução:** Criar `ValidationService` reutilizável
- **Arquivos afetados:**
  - Criar: `ia-core/ia-core-service/src/main/java/com/ia/core/service/validation/ValidationService.java`
- **Tipo de refatoração:** Extract Class
- **O que resolve:** DRY - Don't Repeat Yourself

---

### **FASE C: Interface Segregation Principle (ISP)**

#### C1: Separar interfaces de TokenService
- **Problema:** `TokenService` tem métodos demais para alguns usos
- **Solução:** Criar interfaces segregadas:
  - `TokenGenerator` (apenas generate)
  - `TokenValidator` (apenas validate)
  - `TokenParser` (apenas parse)
- **Arquivos afetados:**
  - `security-core-service/src/main/java/com/ia/core/security/service/token/TokenService.java`
- **Tipo de refatoração:** Extract Interface, Replace Type with Interface
- **O que resolve:** Clients não dependem de métodos que não usam

#### C2: Separar interfaces de Repository
- **Problema:** `BaseEntityRepository` tem métodos demais
- **Solução:** Criar interfaces granulares:
  - `ReadOnlyRepository` (find, exists)
  - `WritableRepository` (save, delete)
  - `CountableRepository` (count)
- **Arquivos afetados:**
  - `ia-core/ia-core-service/src/main/java/com/ia/core/service/repository/BaseEntityRepository.java`
- **Tipo de refatoração:** Extract Interface
- **O que resolve:** ISPs - interfaces específicas por necessidade

#### C3: Criar interfaces para Services do Biblia
- **Problema:** Serviços implementam interfaces grandes demais
- **Solução:** Criar interfaces específicas
- **Arquivos afetados:**
  - `gestor-igreja/Biblia/biblia-service/src/main/java/com/ia/biblia/service/**/*.java`
- **Tipo de refatoração:** Extract Interface
- **O que resolve:** Múltiplas interfaces pequenas

---

### **FASE D: Dependency Inversion Principle (DIP)**

#### D1: Inverter dependências na camada REST
- **Problema:** REST depende de implementações concretas
- **Solução:** Injetar interfaces abstratas
- **Arquivos afetados:**
  - `ia-core/ia-core-rest/src/main/java/com/ia/core/rest/control/**/*.java`
  - `gestor-igreja/Biblia/biblia-rest/src/main/java/com/ia/biblia/rest/control/**/*.java`
- **Tipo de refatoração:** Dependency Injection, Extract Interface
- **O que resolve:** Alto nível não depende de baixo nível

#### D2: Criar abstrações para serviços externos
- **Problema:** Serviços dependem de implementações concretas de HTTP clients
- **Solução:** Criar interfaces para serviços externos
- **Arquivos afetados:**
  - Criar: `ia-core/ia-core-service/src/main/java/com/ia/core/service/http/HttpClient.java`
  - Criar: `ia-core/ia-core-service/src/main/java/com/ia/core/service/http/RestClient.java`
- **Tipo de refatoração:** Extract Interface, Introduce Parameter
- **O que resolve:** Facilita testes e substituição de implementações

#### D3: Configurar injeção via @Configuration
- **Problema:** Instanciação direta de dependências
- **Solução:** Usar @Bean e @Configuration
- **Arquivos afetados:**
  - `ia-core/ia-core-rest/src/main/java/com/ia/core/rest/security/**/*.java`
  - `gestor-igreja/Biblia/biblia-rest/src/main/java/com/ia/biblia/rest/config/**/*.java`
- **Tipo de refatoração:** Introduce Configuration
- **O que resolve:** Inversão de controle completa

---

### **FASE E: Open/Closed Principle (OCP)**

#### E1: Criar AbstractFactory para DTOs
- **Problema:** Adicionar novos DTOs requer modificar código existente
- **Solução:** Criar factory extensível
- **Arquivos afetados:**
  - Criar: `ia-core/ia-core-service-model/src/main/java/com/ia/core/service/model/factory/DtoFactory.java`
- **Tipo de refatoração:** Extract Class, Introduce Factory
- **O que resolve:** Aberto para extensão, fechado para modificação

#### E2: Strategy Pattern para validação
- **Problema:** Validação hardcoded
- **Solução:** Usar Strategy Pattern
- **Arquivos afetados:**
  - Criar: `ia-core/ia-core-service/src/main/java/com/ia/core/service/validation/ValidationStrategy.java`
  - Criar: `ia-core/ia-core-service/src/main/java/com/ia/core/service/validation/ValidationContext.java`
- **Tipo de refatoração:** Replace Conditional with Strategy
- **O que resolve:** Novos tipos de validação sem modificar código

#### E3: Template Method para controllers CRUD
- **Problema:** Cada controller reimplementa lógica CRUD
- **Solução:** Usar Template Method
- **Arquivos afetados:**
  - `ia-core/ia-core-rest/src/main/java/com/ia/core/rest/control/AbstractBaseController.java`
- **Tipo de refatoração:** Extract Superclass
- **O que resolve:** Reutilização de lógica comum

---

### **FASE F: Clean Code - Nomenclatura e Estrutura**

#### F1: Renomear classes com nomes ruins
- **Problema:** Nomes genéricos como `temp`, `data`, `handler1`
- **Solução:** Renomear para nomes descritivos
- **Arquivos afetados:**
  - Revisar: `ia-core/ia-core-*/src/main/java/com/ia/core/**/*.java`
  - Revisar: `gestor-igreja/Biblia/biblia-*/src/main/java/com/ia/biblia/**/*.java`
- **Tipo de refatoração:** Rename
- **O que resolve:** Código autoexplicativo

#### F2: Extrair métodos longos
- **Problema:** Métodos com mais de 30 linhas
- **Solução:** Extrair em métodos menores
- **Critérios:**
  - Métodos > 30 linhas → extrair
  - Parâmetros > 3 → criar parâmetro object
  - Ifs aninhados > 2 → extrair métodos
- **Arquivos afetados:**
  - Revisar todos os arquivos Java
- **Tipo de refatoração:** Extract Method, Replace Method with Method Object
- **O que resolve:** Legibilidade e testabilidade

#### F3: Substituir comentários por código claro
- **Problema:** Comentários que explicam código confuso
- **Solução:** Refatorar código para ser autoexplicativo
- **Exemplo:**
  ```java
  // Se o usuário está ativo, retorna true
  if (user.status == UserStatus.ACTIVE) {
  ```
  Menjadi:
  ```java
  if (user.isActive()) {
  ```
- **Arquivos afetados:** Todos
- **Tipo de refatoração:** Rename Method, Extract Method
- **O que resolve:** Código documenta a si mesmo

#### F4: Padronizar estrutura de pacotes
- **Estrutura alvo:**
  ```
  com.ia.{module}.domain.entity      # Entidades
  com.ia.{module}.domain.valueobject # Value Objects
  com.ia.{module}.domain.repository  # Interfaces Repository
  com.ia.{module}.domain.service    # Interfaces Service
  com.ia.{module}.application.usecase # Casos de uso
  com.ia.{module}.application.dto   # DTOs
  com.ia.{module}.infrastructure.persistence # Implementações
  com.ia.{module}.infrastructure.web  # Controllers
  com.ia.{module}.config            # Configurações
  ```
- **Tipo de refatoração:** Move Class, Rename Package
- **O que resolve:** Arquitetura clara e previsível

---

### **FASE G: Clean Architecture - Camadas**

#### G1: Separar Domain de Application
- **Problema:** DTOs e entidades no mesmo pacote
- **Solução:** Separar em camadas
- **Arquivos afetados:**
  - `ia-core/ia-core-model/` → mover entidades para `domain/`
  - `ia-core/ia-core-service-model/` → manter em `application/`
  - `gestor-igreja/Biblia/biblia-model/` → separar
- **Tipo de refatoração:** Move Class, Create Package
- **O que resolve:** Dependências direcionadas corretamente

#### G2: Criar casos de uso explícitos
- **Problema:** Lógica de negócio nos controllers
- **Solução:** Criar classes UseCase
- **Exemplo:**
  ```java
  // Antes
  @RestController
  public class UserController {
      @PostMapping("/users")
      public User createUser(CreateUserRequest req) {
          // Lógica de negócio aqui
      }
  }

  // Depois
  @RestController
  public class UserController {
      private final CreateUserUseCase useCase;
      public UserController(CreateUserUseCase useCase) {
          this.useCase = useCase;
      }
      @PostMapping("/users")
      public User createUser(CreateUserRequest req) {
          return useCase.execute(req);
      }
  }

  @Component
  public class CreateUserUseCase {
      private final UserRepository repo;
      public User execute(CreateUserRequest req) {
          // Lógica de negócio
      }
  }
  ```
- **Arquivos afetados:**
  - Criar: `ia-core/ia-core-service/src/main/java/com/ia/core/service/usecase/**/*.java`
- **Tipo de refatoração:** Extract Method, Move Method
- **O que resolve:** Controllers thin, lógica de negócio isolada

#### G3: Implementar Domain Events
- **Problema:** Acoplamento temporal entre serviços
- **Solução:** Usar Domain Events
- **Arquivos afetados:**
  - Criar: `ia-core/ia-core-model/src/main/java/com/ia/core/model/event/DomainEvent.java`
  - Criar: `ia-core/ia-core-model/src/main/java/com/ia/core/model/event/DomainEventPublisher.java`
- **Tipo de refatoração:** Introduce Domain Event
- **O que resolve:** Desacoplamento temporal

---

### **FASE H: Testes Automatizados**

#### H1: Configurar cobertura de testes
- **Problema:** Baixa cobertura de testes
- **Solução:** Configurar JaCoCo e escrever testes
- **Meta:** 80% cobertura em domain e application
- **Arquivos afetados:**
  - `pom.xml` (adicionar JaCoCo)
  - `src/test/java/` (novos testes)
- **Tipo de refatoração:** Add Test
- **O que resolve:** Confiança em refatorações futuras

#### H2: Testes de unidade para domain
- **Problema:** Lógica de domínio sem testes
- **Solução:** Testar Value Objects, Entities, Use Cases
- **Prioridade:**
  1. Value Objects (invariantes)
  2. Entities (comportamentos)
  3. Use Cases (cenários principais)
- **Tipo de refatoração:** Add Test
- **O que resolve:** Validação de regras de negócio

#### H3: Testes de integração para repositories
- **Problema:** Repositories sem testes de integração
- **Solução:** Usar @DataJpaTest
- **Tipo de refatoração:** Add Integration Test
- **O que resolve:** Validação de persistência

---

## 4. Priorização e Dependências

### Ordem de Execução Recomendada

```
┌─────────────────────────────────────────────────────────────┐
│ FASE A: Correção de Dependências                            │
│   → Pré-requisito para todas as outras fases                 │
├─────────────────────────────────────────────────────────────┤
│ FASE B: SRP                                                  │
│   → B1, B2 (dependem de A)                                  │
│   → B3, B4 (independentes)                                  │
├─────────────────────────────────────────────────────────────┤
│ FASE C: ISP                                                  │
│   → C1 (depende de B1-B2)                                   │
│   → C2, C3 (independentes)                                  │
├─────────────────────────────────────────────────────────────┤
│ FASE D: DIP                                                  │
│   → D1 (depende de C1-C2)                                   │
│   → D2, D3 (independentes)                                  │
├─────────────────────────────────────────────────────────────┤
│ FASE E: OCP                                                  │
│   → E1, E2, E3 (dependem de B, C, D)                        │
├─────────────────────────────────────────────────────────────┤
│ FASE F: Clean Code                                           │
│   → Pode ser executado em paralelo                           │
├─────────────────────────────────────────────────────────────┤
│ FASE G: Clean Architecture                                   │
│   → G1 (depende de F)                                        │
│   → G2, G3 (dependem de E, F)                               │
├─────────────────────────────────────────────────────────────┤
│ FASE H: Testes                                               │
│   → Depende das fases anteriores                            │
└─────────────────────────────────────────────────────────────┘
```

### Estimativa de Esforço

| Fase | Esforço | Prioridade | Risco |
|------|---------|------------|-------|
| A | Baixo | Crítica | Baixo |
| B | Médio | Alta | Médio |
| C | Médio | Alta | Médio |
| D | Alto | Alta | Alto |
| E | Médio | Média | Médio |
| F | Alto | Média | Alto |
| G | Alto | Média | Alto |
| H | Muito Alto | Baixa | Alto |

---

## 5. Critérios de Sucesso

### 5.1 Métricas de Qualidade
- **Cobertura de testes:** ≥ 80% em domain
- **Complexidade ciclomática:** ≤ 10 por método
- **Acoplamento:** ≤ 3 dependências por classe
- **Coesão:** ≥ 70% de métodos acessados

### 5.2 Verificações Automáticas
- SonarQube: Zero code smells críticos
- Checkstyle: Zero violações
- PMD: Zero violações de regras principais
- SpotBugs: Zero bugs potenciais

### 5.3 Validação Manual
- Code review de cada fase
- Testes de regressão completos
- Documentação atualizada

---

## 6. Riscos e Mitigações

| Risco | Probabilidade | Impacto | Mitigação |
|-------|---------------|---------|-----------|
| Dependências quebradas | Alta | Alto | FASE A primeiro |
| Regressões em funcionalidades | Média | Alto | Testes automatizados |
| Tempo de desenvolvimento | Alta | Médio | Fases paralelas |
| Resistência da equipe | Baixa | Médio | Documentação clara |

---

## 7. Documentação Relacionada

- [ADR-001: Specification Pattern](ia-core/docs/adr/ADR-001_SPECIFICATION_PATTERN.md)
- [ADR-002: Exception Handling](ia-core/docs/adr/ADR-002_EXCEPTION_HANDLING.md)
- [ADR-003: Automated Tests](ia-core/docs/adr/ADR-003_AUTOMATED_TESTS.md)
- [ADR-004: Logging and Monitoring](ia-core/docs/adr/ADR-004_LOGGING_MONITORING.md)
- [ADR-005: Javadoc Standards](ia-core/docs/adr/ADR-005_JAVADOC_STANDARDS.md)
- [ADR-006: JWT Authentication](ia-core/docs/adr/ADR-006_JWT_AUTHENTICATION.md) (pendente)

---

## 8. Próximos Passos Imediatos

1. **Executar FASE A** - Corrigir dependências do ia-core-service
2. **Compilar projeto** - Verificar se build passa
3. **Iniciar FASE Y** - Completar implementação de segurança
4. **Criar ADR-006** - Documentar decisões de segurança
