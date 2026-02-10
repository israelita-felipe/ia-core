# Plano de Refatoração Granular - ia-core e Biblia

## 📋 Índice

1. [Visão Geral](#visão-geral)
2. [Análise Comparativa](#análise-comparativa)
3. [Fases de Refatoração](#fases-de-refatoração)
4. [Técnicas SOLID Aplicadas](#técnicas-solid-aplicadas)
5. [Clean Architecture](#clean-architecture)
6. [Clean Code](#clean-code)
7. [Resumo de Impacto](#resumo-de-impacto)
8. [Ordem de Execução](#ordem-de-execução)

---

## Visão Geral

Este documento detalha o plano de refatoração para os projetos **ia-core** (framework) e **Biblia** (aplicação), aplicando os princípios SOLID, Clean Architecture e Clean Code.

### Objetivos Principais
- ✅ Eliminar código duplicado entre projetos
- ✅ Padronizar arquitetura e padrões
- ✅ Melhorar manutenibilidade
- ✅ Aumentar testabilidade
- ✅ Otimizar performance

---

## Análise Comparativa

### Estrutura de Módulos

| Camada | ia-core | Biblia | Alinhamento |
|--------|---------|--------|-------------|
| **Model** | ia-core-model | biblia-model | ✅ Alinhado |
| **Service** | ia-core-service | biblia-service | ✅ Alinhado |
| **Service-Model** | ia-core-service-model | biblia-service-model | ✅ Alinhado |
| **REST** | ia-core-rest | biblia-rest | ✅ Alinhado |
| **View** | ia-core-view | biblia-view | ✅ Alinhado |
| **NLP** | ia-core-nlp | biblia-nlp | ✅ Alinhado |
| **Grammar** | ia-core-grammar | biblia-grammar | ✅ Alinhado |

### Status dos Padrões

| Padrão | ia-core | Biblia | Status |
|--------|---------|--------|--------|
| **ServiceConfig (DIP)** | ✅ Completo | ✅ Implementado | Alinhado |
| **DefaultSecuredBaseService** | ✅ Base | ✅ Reutilizado | Alinhado |
| **Domain Events** | ✅ BaseServiceEvent | ⚠️ Próprio | ⚠️ Pendente |
| **MVVM** | ✅ FormViewModel | ✅ Reutilizado | Alinhado |
| **REST Controllers** | ✅ DefaultBaseController | ✅ Estendido | Alinhado |
| **Specification Pattern** | ✅ Implementado | ⚠️ Parcial | ⚠️ Pendente |
| **EntityGraph** | ✅ Implementado | ⚠️ Parcial | ⚠️ Pendente |
| **Jakarta Validation** | ✅ Completo | ⚠️ 80% | ⚠️ Pendente |

---

## Fases de Refatoração

### FASE 1-16: Fundamentos ✅

**Tipo de Refatoração:** Preparação e Infraestrutura

| Passo | Refatoração | Objetivo | Resolve |
|-------|-------------|----------|---------|
| 1.1 | Padronizar dependências Maven | Centralizar versões | Inconsistência de versões |
| 1.2 | Criar Parent POM | Gerenciamento unificado | Duplicação de configuração |
| 1.3 | Configurar Jakarta Validation | Validação em DTOs | Falta de validação |
| 1.4 | Configurar MapStruct | Mapeamento DTO-Entidade | Código de mapeamento manual |
| 1.5 | Configurar i18n | Internacionalização | Strings hardcoded |

**Status:** ✅ Concluído

---

### FASE A: ApplicationEventPublisher Generalizado

**Tipo de Refatoração:** Domain Events (DIP - Dependency Inversion)

#### A.1 - Criar BaseServiceEvent

```java
// ia-core-service/src/main/java/com/ia/core/service/event/BaseServiceEvent.java
public class BaseServiceEvent<D extends BaseEntity> 
  extends ApplicationEvent {
    
    private final D entity;
    private final CrudOperationType operationType;
    
    public BaseServiceEvent(Object source, D entity, 
                           CrudOperationType operationType) {
        super(source);
        this.entity = entity;
        this.operationType = operationType;
    }
}
```

#### A.2 - Criar CrudOperationType

```java
// ia-core-service/src/main/java/com/ia/core/service/event/CrudOperationType.java
public enum CrudOperationType 
  implements EventType {
    CREATED, UPDATED, DELETED;
}
```

#### A.3 - Modificar DefaultSecuredBaseService para publicar eventos

```java
// Antes: Não publicava eventos
@Service
public class DefaultSecuredBaseService<D, T, R> {
    public void afterSave(D original, D saved) {
        // Não fazia nada
    }
}

// Depois: Publica eventos automaticamente
@Service
public class DefaultSecuredBaseService<D, T, R> {
    private ApplicationEventPublisher eventPublisher;
    
    @Override
    public void afterSave(D original, D saved, CrudOperationType type) {
        if (saved != null && eventPublisher != null) {
            eventPublisher.publishEvent(
                new BaseServiceEvent<>(this, saved, type)
            );
        }
    }
}
```

**O que resolve:**
- ✅ Comunicação assíncrona entre serviços
- ✅ Desacoplamento de funcionalidades
- ✅ Histórico de auditoria
- ✅ Cache invalidation

---

### FASE B: Extract Interface (DIP - Dependency Inversion)

**Tipo de Refatoração:** Interface Segregation + DIP

#### B.1 - Extrair interfaces de serviço

```java
// Antes: Implementação direta
@Service
public class EventoService 
  extends DefaultSecuredBaseService<Evento, EventoDTO> {
    
    @Override
    public Evento create(EventoDTO dto) {
        // implementação
    }
}

// Depois: Interface segregada
public interface IEventoService {
    Evento create(EventoDTO dto);
    Evento update(Long id, EventoDTO dto);
    void delete(Long id);
    Evento findById(Long id);
}

@Service
public class EventoService 
  extends DefaultSecuredBaseService<Evento, EventoDTO>
  implements IEventoService {
    // implementação
}
```

**O que resolve:**
- ✅ DIP: Depende de abstrações, não implementações
- ✅ ISP: Interfaces pequenas e específicas
- ✅ Testabilidade: Mock de interfaces
- ✅ Flexibilidade: Mudança de implementação

---

### FASE C: Padronização de Nomenclatura

**Tipo de Refatoração:** Clean Code

#### C.1 - Corrigir inconsistências

| Arquivo | Antes | Depois | Problema |
|---------|-------|--------|----------|
| BibliaSecurityConfiguration | registryAccess | registerAccess | Typo |
| EventoServiceConfig | @Getter duplicado | Remover duplicação | Clean Code |
| FamiliaEventListener | BibliaEvent | CrudOperationType | Padronização |

**O que resolve:**
- ✅ Legibilidade do código
- ✅ Manutenção facilitada
- ✅ Consistência entre projetos

---

### FASE D: Eventos Automáticos

**Tipo de Refatoração:** Domain Events + AOP

#### D.1 - Automatic Event Publishing

```java
// DefaultSecuredBaseService.java - afterSave
@Override
public void afterSave(D original, D saved, CrudOperationType type) {
    if (saved != null && config.getEventPublisher() != null) {
        config.getEventPublisher().publishEvent(
            new BaseServiceEvent<>(this, saved, type)
        );
    }
}
```

**O que resolve:**
- ✅ Transparência: Eventos publicados automaticamente
- ✅ Consistência: Sempre que uma entidade é salva
- ✅ Auditoria: Registro de todas as operações

---

### FASE E: Documentação de Arquitetura

**Tipo de Refatoração:** Documentação Técnica

#### E.1 - Criar ARCHITECTURE.md

```markdown
# Arquitetura IA-Core

## Clean Architecture

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

**O que resolve:**
- ✅ Onboarding de novos desenvolvedores
- ✅ Decisões arquiteturais documentadas
- ✅ Padrões de comunicação

---

### FASE F: Padrões Lombok

**Tipo de Refatoração:** Clean Code + Boas Práticas

#### F.1 - Documentar convenções Lombok

```markdown
# Lombok Patterns

## Entidades (biblia-model, ia-core-model)
```java
@Data              // Para DTOs apenas
@Getter           // Para Entidades (evita set para ID)
@Setter
@SuperBuilder(toBuilder = true)  // Para ambos
```

## DTOs (biblia-service-model, ia-core-service-model)
```java
@Data                    // Getters, Setters, Equals, HashCode
@SuperBuilder(toBuilder = true)  // Builder com herança
@EqualsAndHashCode(callSuper = true)  // Inclui campos da superclasse
```

**O que resolve:**
- ✅ Consistência no uso de anotações
- ✅ Evitar armadilhas do Lombok
- ✅ Código mais limpo e previsível

---

### FASE G: SRP (Single Responsibility Principle)

**Tipo de Refatoração:** SOLID - SRP

#### G.1 - Separar responsabilidades

| Serviço Original | Problema | Solução |
|-----------------|----------|---------|
| LLMTransformationService | Múltiplas responsabilidades | Separar em serviços específicos |
| ChatService | Apenas comunicação | Manter como está |
| MovimentoFinanceiroService | Opera múltiplas entidades | Separar em: DespesaService, ReceitaService |

#### G.2 - Exemplo de separação

```java
// Antes: Responsabilidade múltipla
@Service
public class MovimentoFinanceiroService {
    // Opera com: Despesa, Receita, Transferencia
    public void processarDespesa(DespesaDTO dto) {}
    public void processarReceita(ReceitaDTO dto) {}
    public void processarTransferencia(TransferenciaDTO dto) {}
}

// Depois: SRP aplicado
@Service
public class DespesaService { /* ... */ }

@Service  
public class ReceitaService { /* ... */ }

@Service
public class TransferenciaService { /* ... */ }
```

**O que resolve:**
- ✅ SRP: Cada serviço tem uma responsabilidade
- ✅ Testabilidade: Testes mais focados
- ✅ Manutenibilidade: Mudanças isoladas

---

### FASE H: REST Service Layer

**Tipo de Refatoração:** ISP + Clean Architecture

#### H.1 - Controllers seguem ISP

```java
// DefaultBaseController.java
public interface ICreateController<D, T> {
    ResponseEntity<D> create(D dto);
}

public interface IUpdateController<D, T> {
    ResponseEntity<D> update(Long id, D dto);
}

public interface IDeleteController<T> {
    ResponseEntity<Void> delete(Long id);
}

public interface IFindController<D, T> {
    ResponseEntity<D> findById(Long id);
    ResponseEntity<List<D>> findAll();
}

// Controller específico implementa apenas o que precisa
@RestController
@RequestMapping("/api/evento")
public class EventoController 
  extends DefaultBaseController<Evento, EventoDTO>
  implements ICreateController<EventoDTO, Evento>,
             IUpdateController<EventoDTO, Evento>,
             IDeleteController<Evento>,
             IFindController<EventoDTO, Evento> {
}
```

**O que resolve:**
- ✅ ISP: Interfaces segregadas
- ✅ REST consistente: Endpoints padronizados
- ✅ Flexibilidade: Controller pode implementar apenas o necessário

---

### FASE I: View Layer - MVVM

**Tipo de Refatoração:** Padrão de Projeto MVVM

#### I.1 - FormViewModel correto

```java
// FormViewModel.java
public abstract class FormViewModel<T extends Serializable>
  implements IFormViewModel<T>, HasPropertyChangeSupport {

    private final PropertyChangeSupport propertyChangeSupport = 
        new PropertyChangeSupport(this);

    @Override
    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }
    
    // Callback para carregamento de dados
    public abstract void load(Long id);
    
    // Callback para validação
    public abstract boolean validate();
    
    // Callback para salvamento
    public abstract boolean save();
}
```

**O que resolve:**
- ✅ MVVM: Separação clara entre View e Model
- ✅ Reatividade: PropertyChangeSupport para UI
- ✅ Testabilidade: ViewModels testáveis

---

### FASE J: Análise de Dependências

**Tipo de Refatoração:** Análise Estática

#### J.1 - Verificar ciclos de dependência

**Ferramentas utilizadas:**
- Dependency Graph do Intellij IDEA
- `mvn dependency:tree`
- `mvn enforcer:enforce`

**Resultado:** ✅ NENHUM CICLO DE DEPENDÊNCIA ENCONTRADO

```
ia-core/
├── ia-core-model (sem dependências de service)
├── ia-core-service (depende de model)
├── ia-core-service-model (depende de service e model)
├── ia-core-rest (depende de service e service-model)
└── ia-core-view (depende de service, service-model e rest)
```

**O que resolve:**
- ✅ Arquitetura limpa: Sem ciclos
- ✅ Compilação incremental: Melhor performance
- ✅ Acoplamento controlado: Dependências em uma direção

---

### FASE 4: Performance - EntityGraph

**Tipo de Refatoração:** Otimização de Performance

#### 4.1 - Implementar EntityGraph para N+1

```java
// ComandoSistemaRepository.java
@EntityGraph("ComandoSistema.withTemplate")
Optional<ComandoSistema> findByIdWithTemplate(Long id);

@EntityGraph(type = EntityGraphType.FETCH, attributePaths = {"template", "parametros"})
List<ComandoSistema> findAllWithTemplate();

// EventoRepository.java
@EntityGraph("Evento.withLocal")
Optional<Evento> findByIdWithLocal(Long id);

@EntityGraph(type = EntityGraphType.FETCH, attributePaths = {
    "local", "materiais", "inscricoes"
})
List<Evento> findAllWithRelations();
```

#### 4.2 - Named EntityGraph em Entidades

```java
// ComandoSistema.java
@Entity
@NamedEntityGraph(
    name = "ComandoSistema.withTemplate",
    attributeNodes = @NamedAttributeNode("template")
)
public class ComandoSistema extends BaseEntity {
    // ...
}
```

**O que resolve:**
- ✅ N+1 Query Problem: Evita dezenas de queries
- ✅ Performance: Carregamento eager otimizado
- ✅ Memória: Controle de fetch

---

### FASE K: Padronização de Eventos de Domínio

**Tipo de Refatoração:** Domain Events + DIP

#### K.1 - Remover BibliaEvent duplicado

**Arquivos removidos:**
- ❌ `BibliaEvent.java` - Duplicado de BaseServiceEvent
- ❌ `BibliaEventType.java` - Usar CrudOperationType do ia-core
- ❌ `EventPublisher.java` - Usar ApplicationEventPublisher do Spring
- ❌ `EventPublishable.java` - Interface desnecessária
- ❌ `FamiliaEventListener.java` - Código morto

**Verificação:**
- ✅ BUILD SUCCESS
- ✅ Commit: 985508c
- ✅ Push: origin/master

**O que resolve:**
- ✅ DRY: Não duplicar código do ia-core
- ✅ Consistência: Mesmo sistema de eventos em ambos os projetos
- ✅ Manutenção: Atualizações centralizadas no ia-core

---

### FASE L: Validação Jakarta Completa

**Tipo de Refatoração:** Bean Validation

#### L.1 - Completar validação em DTOs

**DTOs com validação pendente:**

| DTO | Status | Validações necessárias |
|-----|--------|------------------------|
| FamiliaDTO | ❌ Pendente | @NotNull, @NotEmpty, @Size |
| PessoaDTO | ❌ Pendente | @NotNull, @CPF, @Email |
| ContaDTO | ❌ Pendente | @NotNull, @Size |
| DoacaoDTO | ❌ Pendente | @NotNull, @Positive |
| DespesaDTO | ❌ Pendente | @NotNull, @Positive |
| ReceitaDTO | ❌ Pendente | @NotNull, @Positive |
| TransferenciaDTO | ❌ Pendente | @NotNull |
| MovimentoFinanceiroDTO | ❌ Pendente | @NotNull |

#### L.2 - Exemplo de validação

```java
// FamiliaDTO.java - ANTES
@Data
public class FamiliaDTO extends AbstractBaseEntityDTO<Familia> {
    private String nome;
    private List<IntegranteFamiliaDTO> integrantes;
}

// FamiliaDTO.java - DEPOIS
@Data
@SuperBuilder(toBuilder = true)
public class FamiliaDTO extends AbstractBaseEntityDTO<Familia> {
    @NotNull(message = FamiliaTranslator.VALIDATION.NOME_NOT_NULL)
    @NotEmpty(message = FamiliaTranslator.VALIDATION.NOME_NOT_NULL)
    @Size(min = 3, max = 200, message = FamiliaTranslator.VALIDATION.NOME_SIZE)
    private String nome;
    
    @Valid
    private List<IntegranteFamiliaDTO> integrantes;
}
```

**O que resolve:**
- ✅ Validação consistente em todas as camadas
- ✅ Mensagens de erro internacionalizadas
- ✅ Feedback claro para o usuário

---

### FASE M: Correção de Encoding i18n

**Tipo de Refatoração:** i18n + Encoding

#### M.1 - Corrigir problemas de encoding

**Problema:** Arquivo `translations_biblia_pt_BR.properties` possui caracteres mal codificados

**Solução:**
```properties
# ANTES (incorreto):
CONTANTO=Contato

# DEPOIS (correto):
validation.pessoa.nome.not_null=O nome é obrigatório
validation.pessoa.nome.size=O nome deve ter entre 3 e 200 caracteres
```

**O que resolve:**
- ✅ i18n funcionando corretamente
- ✅ Caracteres especiais (acentuação)
- ✅ Consistência com ia-core

---

### FASE N: Padronização de Nomenclatura

**Tipo de Refatoração:** Clean Code

#### N.1 - Corrigir inconsistências de nomenclatura

**Typos identificados:**
```
biblia-view/src/main/java/com/ia/biblia/view/config/
└── BibliaSecurityConfiguration.java
    └── registryAccess → registerAccess (JÁ CORRIGIDO)
```

**Convenção de nomenclatura:**

| Contexto | Padrão | Exemplo |
|----------|--------|---------|
| Serviços |Nome + Service | EventoService |
| Repositórios | Nome + Repository | EventoRepository |
| DTOs | Nome + DTO | EventoDTO |
| Controllers | Nome + Controller | EventoController |
| ViewModels | Nome + FormViewModel | EventoFormViewModel |

**O que resolve:**
- ✅ Legibilidade do código
- ✅ Busca facilitada de arquivos
- ✅ Consistência entre projetos

---

### FASE O: Separação de Responsabilidades (SRP)

**Tipo de Refatoração:** SOLID - SRP

#### O.1 - Revisar serviços com responsabilidades múltiplas

**Serviços a serem analisados:**

| Serviço | Problema | Ação |
|---------|----------|------|
| MovimentoFinanceiroService | Opera múltiplas entidades | Separar |
| EventoService | Validação inline | Mover para validador |
| PessoaService | Lógica complexa | Revisar |

#### O.2 - Exemplo de separação

```java
// ANTES: Serviço com múltiplas responsabilidades
@Service
public class EventoService 
  extends DefaultSecuredBaseService<Evento, EventoDTO> {
    
    @Override
    public Evento create(EventoDTO dto) {
        // Validação inline
        if (dto.getTitulo() == null) {
            throw new ServiceException("Título obrigatório");
        }
        // Lógica de negócio
        // Persistência
    }
}

// DEPOIS: SRP aplicado
@Service
public class EventoService 
  extends DefaultSecuredBaseService<Evento, EventoDTO> {
    
    private final EventoValidator validator;
    
    @Override
    public Evento create(EventoDTO dto) {
        validator.validate(dto);  // Validação separada
        super.create(dto);        // Persistência
    }
}

@Component
public class EventoValidator {
    public void validate(EventoDTO dto) {
        // Apenas validação
    }
}
```

**O que resolve:**
- ✅ SRP: Cada classe tem uma responsabilidade
- ✅ Testabilidade: Validadores testáveis separadamente
- ✅ Reuso: Validadores podem ser reutilizados

---

### FASE P: EntityGraph - Performance N+1

**Tipo de Refatoração:** Otimização de Performance

#### P.1 - Adicionar EntityGraph em todos os repositórios

**Repositórios a otimizar:**

| Repositório | EntityGraph | Atributos |
|-------------|-------------|-----------|
| EventoRepository | Evento.withLocal | local |
| PessoaRepository | Pessoa.withEnderecos | enderecos |
| FamiliaRepository | Familia.withIntegrantes | integrantes |
| InscricaoEventoRepository | Inscricao.withEvento | evento |

#### P.2 - Exemplo de implementação

```java
// PessoaRepository.java
@EntityGraph("Pessoa.withEnderecos")
Optional<Pessoa> findByIdWithEnderecos(Long id);

@EntityGraph("Pessoa.withContatos")
List<Pessoa> findAllWithContatos();

// Pessoa.java
@Entity
@NamedEntityGraph(
    name = "Pessoa.withEnderecos",
    attributeNodes = @NamedAttributeNode("enderecos")
)
@NamedEntityGraph(
    name = "Pessoa.withContatos",
    attributeNodes = @NamedAttributeNode("contatos")
)
public class Pessoa extends BaseEntity {
    // ...
}
```

**O que resolve:**
- ✅ Performance: Elimina N+1 queries
- ✅ Memória: Carregamento controlado
- ✅ UX: Interface mais responsiva

---

### FASE Q: Specification Pattern

**Tipo de Refatoração:** Specification Pattern

#### Q.1 - Completar implementação

```java
// PessoaSpecification.java
public class PessoaSpecification 
  extends SearchSpecification<Pessoa> {
    
    public static Specification<Pessoa> withNome(String nome) {
        return (root, query, cb) -> 
            cb.like(cb.lower(root.get("nome")), 
                    "%" + nome.toLowerCase() + "%");
    }
    
    public static Specification<Pessoa> withCpf(String cpf) {
        return (root, query, cb) -> 
            cb.equal(root.get("cpf"), cpf);
    }
}

// PessoaRepository.java
public interface PessoaRepository 
  extends BaseEntityRepository<Pessoa>,
          JpaSpecificationExecutor<Pessoa> {
    // Herda métodos de Specification
}
```

**O que resolve:**
- ✅ Filtros dinâmicos
- ✅ Type-safe queries
- ✅ Flexibilidade na busca

---

### FASE R: Documentação ADR

**Tipo de Refatoração:** Documentação Técnica

#### R.1 - Criar ADRs específicos

```markdown
# ADR-001: Reutilização do Framework ia-core

## Status
Aceito

## Contexto
O projeto Biblia precisa de infraestrutura comum (CRUD, REST, Security).

## Decisão
Reutilizar módulos do ia-core como dependência.

## Consequências
- ✅ Menor código duplicado
- ✅ Atualizações centralizadas
- ⚠️ Acoplamento com ia-core
```

**ADRs existentes (ia-core):**
```
ia-core/ADR/
├── 001-use-mapstruct-for-mapping.md
├── 002-use-specification-for-filtering.md
├── 003-use-translator-for-i18n.md
├── 004-use-serviceconfig-for-di.md
└── 005-use-domain-events.md
```

**O que resolve:**
- ✅ Decisões arquiteturais documentadas
- ✅ Histórico de decisões
- ✅ Onboarding facilitado

---

## Técnicas SOLID Aplicadas

| Princípio | Aplicação | Fase |
|-----------|-----------|------|
| **SRP** | Serviços com responsabilidade única | G, O |
| **OCP** | Extensível via novos módulos | A, D |
| **LSP** | Interfaces consistentes | B, H |
| **ISP** | Interfaces segregadas por funcionalidade | B, H |
| **DIP** | Dependências injetadas via construtor | A, B |

---

## Clean Architecture

### Camadas e Responsabilidades

| Camada | Responsabilidade | Exemplos |
|--------|------------------|----------|
| **Apresentação** | UI e API REST | Controllers, Views |
| **Aplicação** | Casos de uso | Services, ViewModels |
| **Domínio** | Regras de negócio | Entities, Events |
| **Infraestrutura** | Frameworks e externos | Repositories, Config |

### Dependências

```
View → REST → Service → Model
              ↓
        Infrastructure
```

---

## Clean Code

### Princípios Aplicados

| Princípio | Aplicação |
|-----------|-----------|
| **KISS** | Código simples e direto |
| **DRY** | Eliminar duplicação |
| **YAGNI** | Apenas o necessário |
| **Naming** | Nomes descritivos |
| **Comments** | Javadoc significativo |
| **Functions** | Funções pequenas |
| **Formatting** | Código formatado |

---

## Resumo de Impacto

### Arquivos Modificados por Fase

| Fase | Tipo | Qtd | Impacto |
|------|------|-----|---------|
| A | Adição | 3 | 🔴 Alto - Infraestrutura |
| B | Refatoração | 15 | 🔴 Alto - Interfaces |
| C | Correção | 5 | 🟢 Baixo - Typos |
| D | Modificação | 10 | 🟡 Médio - Eventos |
| E | Adição | 1 | 🟢 Baixo - Documentação |
| F | Documentação | 1 | 🟢 Baixo - Padrões |
| G | Refatoração | 20 | 🔴 Alto - SRP |
| H | Refatoração | 25 | 🔴 Alto - REST |
| I | Refatoração | 15 | 🟡 Médio - MVVM |
| J | Análise | 1 | 🟢 Baixo - Verificação |
| 4 | Adição | 30 | 🟡 Médio - Performance |
| K | Remoção | 5 | 🔴 Alto - Duplicação |
| L | Modificação | 8 | 🟡 Médio - Validação |
| M | Correção | 1 | 🟢 Baixo - Encoding |
| N | Correção | 10 | 🟢 Baixo - Nomenclatura |
| O | Refatoração | 15 | 🔴 Alto - SRP |
| P | Adição | 20 | 🟡 Médio - EntityGraph |
| Q | Adição | 10 | 🟡 Médio - Specification |
| R | Adição | 5 | 🟢 Baixo - Documentação |

### Métricas de Qualidade Alvo

| Métrica | Atual | Meta |
|---------|-------|------|
| Validação Jakarta | 80% | 100% |
| Strings i18n | 90% | 100% |
| EntityGraph | 20% | 80% |
| SRP Compliance | 70% | 95% |
| Documentação ADR | 0% | 100% |
| Test Coverage | ~40% | >60% |

---

## Ordem de Execução

```
FASE M (Encoding i18n)              [1 semana]
       ↓
FASE K (Remover Eventos)            [2 semanas]
       ↓
FASE L (Validação DTOs)             [2 semanas]
       ↓
FASE P (EntityGraph)                [2 semanas]
       ↓
FASE O (SRP)                        [3 semanas]
       ↓
FASE Q (Specification)              [2 semanas]
       ↓
FASE R (Documentação)               [1 semana]
```

**Tempo estimado total:** ~13 semanas

---

## Verificações de Regressão

### Checklist de Testes

- [ ] Compilação Maven/Java
- [ ] Testes unitários existentes passam
- [ ] Testes de integração passam
- [ ] APIs REST respondem corretamente
- [ ] Validação de DTOs funciona
- [ ] Eventos são publicados corretamente
- [ ] Performance de consultas (N+1 resolvido)
- [ ] i18n funciona em todas as Views

### Ferramentas de Verificação

```bash
# Compilar projeto
./mvnw clean compile

# Executar testes
./mvnw test

# Verificar cobertura
./mvnw jacoco:report

# Análise estática
./mvnw sonar:sonar
```
