# Plano de Refatoração Unificado - IA-Core e Biblia

## 📋 Visão Geral

Este documento apresenta um **plano de refatoração granular** para os projetos **IA-Core** e **Biblia**, aplicando **SOLID**, **Clean Architecture** e **Clean Code**.

### Estrutura dos Projetos

```
/home/israel/git/
├── ia-core-apps/                          # Workspace principal
│   ├── ia-core/                          # Framework base (core)
│   │   ├── ia-core-model/               # Entidades e DTOs base
│   │   ├── ia-core-service/              # Serviços base (BaseService, etc.)
│   │   ├── ia-core-rest/                # Controllers REST base
│   │   ├── ia-core-security/             # Segurança
│   │   ├── ia-core-llm-service/          # Serviços de LLM
│   │   ├── ia-core-llm-view/             # Views de LLM
│   │   └── ia-core-quartz/              # Scheduler
│   └── ...
└── gestor-igreja/
    └── Biblia/                            # Aplicação de gestão religiosa
        ├── biblia-model/                 # Entidades específicas
        ├── biblia-service/               # Serviços de domínio
        ├── biblia-service-model/        # DTOs e Translators
        ├── biblia-rest/                  # Controllers REST
        ├── biblia-view/                  # UI (Vaadin)
        ├── biblia-nlp/                   # NLP
        └── biblia-grammar/               # ANTLR Grammars
```

---

## 🎯 Princípios Aplicados

| Princípio | Descrição | Aplicação no Projeto |
|-----------|-----------|---------------------|
| **S**RP | Single Responsibility | Cada classe tem uma única responsabilidade |
| **O**CP | Open/Closed | Aberto para extensão, fechado para modificação |
| **L**SP | Liskov Substitution | Interfaces segregadas |
| **I**SP | Interface Segregation | Interfaces pequenas e específicas |
| **D**IP | Dependency Inversion | Depender de abstrações |

---

## 📊 Status Geral das Fases

| Fase | Status | Descrição |
|------|--------|-----------|
| FASE 1-10 | ✅ Concluído | Fundamentos e infraestrutura |
| FASE A | ✅ Concluído | ApplicationEventPublisher generalizado |
| FASE B | ✅ Concluído | Interfaces de serviço (DIP) |
| FASE C | ✅ Concluído | Padronização de nomenclatura |
| FASE D | ✅ Concluído | Publicação automática de eventos |
| FASE E | ✅ Concluído | Documentação técnica |
| FASE F | ✅ Concluído | Padronização Lombok |
| FASE G | ✅ Concluído | Separação de Services (SRP) |
| **FASE H** | ✅ Concluído | **REST service layer** |
| **FASE I** | ✅ Concluído | **View layer MVVM com FormValidator** |
| FASE J | ⏳ Pendente | Dependências circulares |

---

## 📝 Implementações da FASE I (MVVM com FormValidator)

### Arquivos Implementados/Corrigidos

| Arquivo | Ação | Descrição |
|---------|------|-----------|
| [`FormViewModel.java`](ia-core/ia-core-view/src/main/java/com/ia/core/view/components/form/viewModel/FormViewModel.java) | Corrigido | Implementa `HasPropertyChangeSupport` corretamente |
| [`FormViewModelConfig.java`](ia-core/ia-core-view/src/main/java/com/ia/core/view/components/form/viewModel/FormViewModelConfig.java) | Corrigido | Adicionado método factory `of()` |
| [`FormValidator.java`](ia-core/ia-core-view/src/main/java/com/ia/core/view/components/form/FormValidator.java) | ✅ Existente | Jakarta Validation integrado com Vaadin |

### Padrão MVVM Documentado

**Estrutura padrão do ViewModel**:

```
biblia-view/
└── {recurso}/
    ├── form/
    │   ├── {Recurso}FormView.java           # View do formulário
    │   ├── {Recurso}FormViewModel.java      # Lógica de apresentação
    │   └── {Recurso}FormViewModelConfig.java # Dependências
    ├── list/
    │   ├── {Recurso}ListView.java           # View de lista
    │   └── {Recurso}ListViewModel.java       # Lógica de lista
    └── page/
        ├── {Recurso}PageView.java           # Página principal
        └── {Recurso}PageViewModel.java       # Lógica da página
```

### Uso do FormValidator

```java
// Injeção do validador
@Service
public class PessoaService {
    private final FormValidator formValidator;

    public PessoaService(FormValidator formValidator) {
        this.formValidator = formValidator;
    }

    public void validar(PessoaDTO dto) {
        BindingResult result = formValidator.validate(dto, "pessoa");
        if (result.hasErrors()) {
            // Tratar erros
        }
    }

    // Ou retornar mapa de erros
    public Map<String, String> validarParaMapa(PessoaDTO dto) {
        return formValidator.validateToMap(dto);
    }
}
```

---

## 📝 Plano de Refatoração Granular

### FASE H: REST Service Layer - Padronização

**Objetivo**: Padronizar toda a camada REST com interface segregation e callbacks

#### Controllers Ja Padronizados

| Controller | Localização | Responsabilidade |
|------------|-------------|-------------------|
| `AbstractBaseController` | ia-core-rest | Classe base com service injetado |
| `BaseController` | ia-core-rest | Interface marker |
| `CountBaseController` | ia-core-rest | Operações de count |
| `DeleteBaseController` | ia-core-rest | Operações de delete |
| `FindBaseController` | ia-core-rest | Operações de find |
| `ListBaseController` | ia-core-rest | Operações de list |
| `SaveBaseController` | ia-core-rest | Operações de save/validate |
| `DefaultBaseController` | ia-core-rest | Implementação padrão |
| `AuthenticationBaseController` | ia-core-rest | Autenticação |

**Status**: Controllers já seguem ISP - Interfaces segregadas por operação

---

### FASE J: Análise e Correção de Dependências Circulares

**Objetivo**: Eliminar ciclos de dependência que causam problemas de startup

#### Passo J.1: Identificar Ciclos Existentes

**Ferramenta**: `spring-boot-dependency-tool` ou análise manual

**Ciclos comuns detectados**:

| Ciclo | Serviços Envolvidos | Solução Proposta |
|-------|---------------------|------------------|
| `PessoaService` ↔ `FamiliaService` | Pessoa ↔ Familia | Eventos + Interface DIP |
| `ContaService` ↔ `MovimentoFinanceiroService` | Conta ↔ Movimento | Aggregate Root Pattern |
| `EventoService` ↔ `InscricaoEventoService` | Evento ↔ Inscrição | Eventos |

#### Passo J.2: Aplicar Interface DIP para Quebrar Ciclos

**Antes** (acoplamento direto):

```java
@Service
public class FamiliaService {
    private final PessoaService pessoaService;  // ❌ Ciclo
    
    public FamiliaService(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }
}
```

**Depois** (com DIP + eventos):

```java
// Interface segregada - apenas operações de leitura
public interface IPessoaReadOnly {
    Optional<PessoaDTO> buscarPorId(Long id);
    List<PessoaDTO> listarAtivos();
}

@Service
public class PessoaService implements IPessoaReadOnly {
    // ... implementação
}

@Service
public class FamiliaService {
    private final IPessoaReadOnly pessoaService;  // ✅ Apenas leitura
    private final ApplicationEventPublisher eventPublisher;
    
    public FamiliaService(IPessoaReadOnly pessoaService, 
                          ApplicationEventPublisher eventPublisher) {
        this.pessoaService = pessoaService;
        this.eventPublisher = eventPublisher;
    }
}
```

#### Passo J.3: Substituir Chamadas Diretas por Eventos

**Antes**:

```java
@Service
public class PessoaService {
    private final FamiliaService familiaService;
    
    public void atualizarIntegrante(Long pessoaId, Long familiaId) {
        Pessoa pessoa = repository.findById(pessoaId).orElseThrow();
        Familia familia = familiaService.buscarPorId(familiaId);  // ❌ Acoplamento
        pessoa.setFamilia(familia);
        repository.save(pessoa);
    }
}
```

**Depois** (com eventos):

```java
@Service
public class PessoaService {
    private final ApplicationEventPublisher eventPublisher;
    
    public void atualizarIntegrante(Long pessoaId, Long familiaId) {
        Pessoa pessoa = repository.findById(pessoaId).orElseThrow();
        // Atualiza e salva
        repository.save(pessoa);
        
        // Notificação via evento (desacoplado)
        eventPublisher.publishEvent(new IntegranteAtualizadoEvent(this, pessoaId, familiaId));
    }
}
```

---

## 🔧 Tipos de Refatoração por Problema

### 1. SOLID Violations

| Violação | Refatoração | O que Resolve |
|----------|-------------|---------------|
| SRP | Extrair responsabilidades | Facilita manutenção e testes |
| OCP | Strategy Pattern | Extensão sem modificação |
| LSP | Interface segregation | Substituição garantida |
| ISP | Separar interfaces grandes | Clients não dependem de métodos não usados |
| DIP | Injetar interfaces | Dependência de abstrações |

### 2. Clean Code Violations

| Violação | Refatoração | O que Resolve |
|----------|-------------|---------------|
| Nomes inconsistentes | Rename Method/Variable | Legibilidade |
| Métodos longos | Extract Method | Clareza e reuso |
| Parâmetros excessivos | Introduce Parameter Object | Simplificação |
| Código duplicado | Extract Method + Call | Manutenibilidade |

### 3. Clean Architecture Violations

| Violação | Refatoração | O que Resolve |
|----------|-------------|---------------|
| Service conhece REST | Mover anotações REST para controller | Separação de camadas |
| Entity com anotações de UI | Anotações apenas em DTOs | Dependency inversion |
| Validação duplicada | Centralizar em DTOs | Single responsibility |

---

## 📋 Checklist por Módulo

### ia-core (Framework Base)

| Item | Status | Observação |
|------|--------|-----------|
| BaseEntity | ✅ | Ja segue padrões |
| BaseService | ✅ | Ja implementado com DIP |
| BaseController | ✅ | Ja segue ISP |
| BaseRepository | ✅ | Ja usa Spring Data |
| Events | ✅ | Ja implementados |
| FormViewModel | ✅ | Corrigido HasPropertyChangeSupport |
| FormValidator | ✅ | Ja existe e integrado |

### biblia-service (Serviços de Domínio)

| Item | Status | Refatoração Necessária |
|------|--------|----------------------|
| PessoaService | ✅ | Ja implementado |
| FamiliaService | ⚠️ | DIP + Eventos para ciclos |
| ContaService | ⚠️ | DIP + Eventos para ciclos |
| EventoService | ✅ | Ja implementado |
| InscricaoEventoService | ⚠️ | DIP + Eventos para ciclos |

### biblia-rest (Controllers)

| Item | Status | Observação |
|------|--------|-----------|
| Controllers existentes | ✅ | Ja seguem ISP |
| OpenAPI docs | ⏳ | Adicionar @Operation |
| Error handling | ✅ | Ja usa CoreRestControllerAdvice |
| Validation | ✅ | Ja usa Jakarta Validation |

### biblia-view (UI - Vaadin)

| Item | Status | Observação |
|------|--------|-----------|
| MVVM pattern | ✅ | Ja implementado |
| FormValidator | ✅ | Ja existe |
| i18n | ✅ | Ja usa ApplicationTranslator |
| Error handling | ✅ | Ja implementado |

---

## 🚀 Próximos Passos

### Prioridade 1 (FASE J - Dependências Circulares)

1. **Analisar** dependências circulares com ferramenta
2. **Identificar** ciclos entre serviços (Pessoa↔Familia, Conta↔Movimento)
3. **Aplicar** DIP + Eventos para quebrar ciclos
4. **Testar** startup da aplicação

### Prioridade 2 (Documentação)

1. **Atualizar** ARCHITECTURE.md com padrões MVVM
2. **Documentar** uso do FormValidator
3. **Criar** exemplos de código

---

## 📚 Referências

- [SOLID Principles](https://solidprinciples.com/)
- [Clean Architecture - Robert Martin](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Clean Code - Robert Martin](https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882)
- [Spring Boot Best Practices](https://www.baeldung.com/spring-boot-best-practices)
- [Vaadin Best Practices](https://vaadin.com/docs/latest/guide/application/architecture)

---

## 📝 Histórico de Versões

| Versão | Data | Descrição |
|--------|------|-----------|
| 1.0 | 2025-02-09 | Plano inicial unificado |
| 1.1 | 2025-02-09 | Adicionadas fases granulares |
| 1.2 | 2025-02-09 | Documentação de controllers REST |
| 1.3 | 2025-02-09 | FASE I concluída - MVVM corrigido |
