# Plano de Refatoração Unificado - ia-core e Biblia

## Visão Geral

Este documento define o plano de refatoração completo para harmonizar os projetos **ia-core** (framework) e **Biblia** (aplicação), aplicando SOLID, Clean Architecture e Clean Code.

---

## Parte 1: Análise Comparativa

### Estrutura de Módulos

| Camada | ia-core | Biblia |
|--------|---------|--------|
| **Model** | ia-core-model | biblia-model |
| **Service** | ia-core-service | biblia-service |
| **Service-Model** | ia-core-service-model | biblia-service-model |
| **REST** | ia-core-rest | biblia-rest |
| **View** | ia-core-view | biblia-view |
| **NLP** | ia-core-nlp | biblia-nlp |
| **Grammar** | ia-core-grammar | biblia-grammar |

### Padrões já Implementados ( Conformidade )

| Padrão | ia-core | Biblia | Status |
|--------|---------|--------|--------|
| **ServiceConfig (DIP)** | ✅ Completo | ✅ Implementado | Alinhado |
| **DefaultSecuredBaseService** | ✅ Base | ✅ Reutilizado | Alinhado |
| **Domain Events** | ✅ BaseServiceEvent | ⚠️ BibliaEvent próprio | Necessita padronização |
| **MVVM** | ✅ FormViewModel | ✅ Reutilizado | Alinhado |
| **REST Controllers** | ✅ DefaultBaseController | ✅ Estendido | Alinhado |
| **Specification Pattern** | ✅ Implementado | ⚠️ Parcial | Pendente |
| **EntityGraph** | ✅ Implementado | ⚠️ Parcial | Pendente |
| **Jakarta Validation** | ✅ Completo | ⚠️ 80% | Pendente |

---

## Parte 2: Refatorações Granulares

### FASE K: Padronização de Eventos de Domínio

#### K.1 - Migrar BibliaEvent para usar BaseServiceEvent

**Objetivo:** Uniformizar sistema de eventos entre ia-core e Biblia

**Problema:** Biblia possui `BibliaEvent` próprio que duplica funcionalidade de `BaseServiceEvent`

**Solução:** Remover BibliaEvent e usar BaseServiceEvent diretamente

**Arquivos afetados:**
```
biblia-service/src/main/java/com/ia/biblia/service/event/
├── BibliaEvent.java          ❌ REMOVER
├── BibliaEventType.java      ❌ REMOVER
├── EventPublisher.java       ❌ REMOVER
├── EventPublishable.java    ❌ REMOVER
```

**Refatoração K.1.1 - Remover classes de evento duplicadas:**
```java
// Antes: biblia-service/event/BibliaEvent.java
public class BibliaEvent<T> extends ApplicationEvent {
    // Duplicação de BaseServiceEvent
}

// Depois: Usar BaseServiceEvent diretamente
// DefaultSecuredBaseService já publica eventos automaticamente
```

**Refatoração K.1.2 - Remover FamiliaEventListener:**
```java
// Antes: biblia-service/familia/event/FamiliaEventListener.java
@Component
public class FamiliaEventListener {
    // Listener específico que usa BibliaEvent
}

// Depois: Remover, eventos são publicados automaticamente
// pelo DefaultSecuredBaseService
```

**Verificação:**
- [ ] Remover `BibliaEvent.java`
- [ ] Remover `BibliaEventType.java`
- [ ] Remover `EventPublisher.java`
- [ ] Remover `EventPublishable.java`
- [ ] Remover `FamiliaEventListener.java`
- [ ] Compilar projeto
- [ ] Verificar se eventos são publicados corretamente

---

### FASE L: Validação Jakarta Completa

#### L.1 - Completar validação em DTOs pendentes

**Status atual:** ~80% dos DTOs possuem validação

**DTOs pendentes de validação:**
```
biblia-service-model/src/main/java/com/ia/biblia/service/
├── familia/dto/FamiliaDTO.java
├── pessoa/dto/PessoaDTO.java
├── conta/dto/ContaDTO.java
├── doacao/dto/DoacaoDTO.java
├── despesa/dto/DespesaDTO.java
├── receita/dto/ReceitaDTO.java
├── transferencia/dto/TransferenciaDTO.java
├── movimentofinanceiro/dto/MovimentoFinanceiroDTO.java
```

**Refatoração L.1.1 - Adicionar validação a FamiliaDTO:**
```java
// Antes: FamiliaDTO.java (anêmico)
@Data
public class FamiliaDTO extends AbstractBaseEntityDTO<Familia> {
    private String nome;
    private List<IntegranteFamiliaDTO> integrantes;
}

// Depois: Com validação
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

**Refatoração L.1.2 - Adicionar validação a PessoaDTO:**
```java
// Antes: PessoaDTO.java
@Data
public class PessoaDTO extends AbstractBaseEntityDTO<Pessoa> {
    private String nome;
    private String cpf;
    private String email;
}

// Depois: Com validação
@Data
@SuperBuilder(toBuilder = true)
public class PessoaDTO extends AbstractBaseEntityDTO<Pessoa> {
    @NotNull(message = PessoaTranslator.VALIDATION.NOME_NOT_NULL)
    @NotEmpty(message = PessoaTranslator.VALIDATION.NOME_NOT_NULL)
    @Size(max = 200)
    private String nome;
    
    @CPF(message = PessoaTranslator.VALIDATION.CPF_INVALID)
    private String cpf;
    
    @Email(message = PessoaTranslator.VALIDATION.EMAIL_INVALID)
    private String email;
}
```

**Verificação:**
- [ ] Adicionar Jakarta Validation annotations
- [ ] Adicionar mensagens no arquivo i18n
- [ ] Mapear chaves no Translator
- [ ] Executar testes de validação

---

### FASE M: Correção de Encoding i18n

#### M.1 - Corrigir problemas de encoding no arquivo properties

**Problema:** Arquivo `translations_biblia_pt_BR.properties` possui caracteres mal codificados

**Refatoração M.1.1 - Corrigir encoding:**
```properties
# Antes (incorreto):
CONTANTO=Contato

# Depois (correto):
validation.pessoa.nome.not_null=O nome é obrigatório
validation.pessoa.nome.size=O nome deve ter entre 3 e 200 caracteres
```

**Verificação:**
- [ ] Corrigir encoding de todas as chaves
- [ ] Padronizar nomenclatura (entidade.campo.tipo)
- [ ] Verificar consistência com ia-core

---

### FASE N: Padronização de Nomenclatura

#### N.1 - Corrigir inconsistências de nomenclatura

**Typos identificados:**
```
biblia-view/src/main/java/com/ia/biblia/view/config/
└── BibliaSecurityConfiguration.java
    └── registryAccess → registerAccess (JÁ CORRIGIDO)
```

**Refatoração N.1.1 - Verificar nomenclatura de métodos:**
```java
// Padrão ia-core: create{Entity}, findById, delete, save
// Verificar biblia-service se segue o padrão
```

**Verificação:**
- [ ] Auditoria de todos os métodos de serviço
- [ ] Corrigir inconsistências encontradas
- [ ] Documentar convenção de nomenclatura

---

### FASE O: Separação de Responsabilidades (SRP)

#### O.1 - Revisar serviços com responsabilidades múltiplas

**Serviços a serem analisados:**
```
biblia-service/src/main/java/com/ia/biblia/service/
├── evento/EventoService.java          ⚠️ Precisa revisão
├── pessoa/PessoaService.java          ⚠️ Precisa revisão
├── familia/FamiliaService.java        ⚠️ Precisa revisão
└── movimentofinanceiro/MovimentoFinanceiroService.java  ⚠️ Precisa revisão
```

**Refatoração O.1.1 - Separar validadores de serviços:**
```
// Antes: Validadores dentro do package de serviço
biblia-service/src/main/java/com/ia/biblia/service/
├── evento/validators/EventoServiceValidator.java
├── contato/validators/ContatoServiceValidator.java
├── inscricao/validators/InscricaoServiceValidator.java

// Depois: Mover para padrão ia-core
// Validadores são injetados via ServiceConfig
```

**Refatoração O.1.2 - Revisar MovimentoFinanceiroService:**
```java
// Antes: Responsabilidade múltipla
@Service
public class MovimentoFinanceiroService 
  extends DefaultSecuredBaseService<MovimentoFinanceiro, MovimentoFinanceiroDTO> {
    
    // Opera com: Despesa, Receita, Transferencia
    //violação SRP: um serviço para múltiplas entidades
}

// Depois: Separar em serviços específicos
// - DespesaService
// - ReceitaService
// - TransferenciaService (já existe)
```

**Verificação:**
- [ ] Analisar cada serviço
- [ ] Separar responsabilidades
- [ ] Manter compatibilidade com REST Controllers

---

### FASE P: EntityGraph - Performance N+1

#### P.1 - Adicionar EntityGraph para evitar N+1

**Status:** Implementado parcialmente (FamiliaRepositoryEntityGraphTest existe)

**Repositórios a serem otimizados:**
```
biblia-service/src/main/java/com/ia/biblia/service/
├── evento/EventoRepository.java
├── pessoa/PessoaRepository.java
├── familia/FamiliaRepository.java
├── inscricao/evento/InscricaoEventoRepository.java
├── movimentofinanceiro/MovimentoFinanceiroRepository.java
```

**Refatoração P.1.1 - Adicionar EntityGraph em EventoRepository:**
```java
// Antes: Repository padrão
public interface EventoRepository 
  extends BaseEntityRepository<Evento> {
}

// Depois: Com EntityGraph
@EntityGraph("Evento.withLocal")
public interface EventoRepository 
  extends BaseEntityRepository<Evento> {
    
    @EntityGraph("Evento.withLocal")
    Optional<Evento> findByIdWithLocal(Long id);
    
    @EntityGraph(type = EntityGraphType.FETCH, attributePaths = {
        "local", "materiais", "inscricoes"
    })
    List<Evento> findAllWithRelations();
}
```

**Refatoração P.1.2 - Adicionar EntityGraph em PessoaRepository:**
```java
public interface PessoaRepository 
  extends BaseEntityRepository<Pessoa> {
    
    @EntityGraph("Pessoa.withEnderecos")
    Optional<Pessoa> findByIdWithEnderecos(Long id);
    
    @EntityGraph("Pessoa.withContatos")
    List<Pessoa> findAllWithContatos();
}
```

**Verificação:**
- [ ] Adicionar @NamedEntityGraph em entidades
- [ ] Criar métodos de consulta com EntityGraph
- [ ] Criar testes de performance (FamiliaRepositoryEntityGraphTest como参考)
- [ ] Executar benchmarks

---

### FASE Q: Specification Pattern Completo

#### Q.1 - Completar implementação de Specification

**Status:** Implementado no ia-core, parcial no Biblia

**Arquivos de Specification já existentes:**
```
ia-core-model/src/main/java/com/ia/core/model/specification/
└── SearchSpecification.java

biblia-service/src/main/java/com/ia/biblia/service/request/
├── BibliaFilterRequestMapper.java
├── BibliaSearchRequestMapper.java
└── BibliaSortRequestMapper.java
```

**Refatoração Q.1.1 - Criar specification para Biblia:**
```java
// biblia-model/src/main/java/com/ia/biblia/model/specification/
├── EventoSpecification.java
├── PessoaSpecification.java
└── FamiliaSpecification.java
```

**Refatoração Q.1.2 - Adicionar métodos de busca dinâmica:**
```java
public interface EventoRepository 
  extends BaseEntityRepository<Evento>, 
          JpaSpecificationExecutor<Evento> {
    // Herda métodos de busca dinâmica
}
```

**Verificação:**
- [ ] Adicionar JpaSpecificationExecutor em todos os repositórios
- [ ] Criar Specification classes para entidades principais
- [ ] Testar filtros dinâmicos

---

### FASE R: Documentação ADR

#### R.1 - Criar ADRs específicos do Biblia

**ADRs já existentes (ia-core):**
```
ia-core/ADR/
├── 001-use-mapstruct-for-mapping.md
├── 002-use-specification-for-filtering.md
├── 003-use-translator-for-i18n.md
├── 004-use-serviceconfig-for-di.md
└── 005-use-domain-events.md
```

**ADRs a criar para Biblia:**
```
biblia/ADR/
├── 001-reuse-ia-core-framework.md      (Decisão: Reutilizar ia-core)
├── 002-event-integration-pattern.md    (Integração com ia-core events)
└── 003-mvvm-pattern-for-vaadin.md      (Padrão MVVM implementado)
```

**Refatoração R.1.1 - Documentar decisões de arquitetura:**
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

**Verificação:**
- [ ] Criar pasta ADR no Biblia
- [ ] Documentar decisões principais
- [ ] Referenciar ADRs do ia-core quando aplicável

---

## Parte 3: Resumo de Impacto

### Arquivos Modificados por Fase

| Fase | Tipo | Qtd | Impacto |
|------|------|-----|---------|
| K | Remoção | 5 | 🔴 Alto - Remove código duplicado |
| L | Modificação | 8 | 🟡 Médio - Validação |
| M | Modificação | 1 | 🟢 Baixo - i18n |
| N | Correção | 2 | 🟢 Baixo - Typos |
| O | Refatoração | 10 | 🔴 Alto - SRP |
| P | Adição | 15 | 🟡 Médio - Performance |
| Q | Adição | 8 | 🟡 Médio - Filtros |
| R | Adição | 3 | 🟢 Baixo - Documentação |

### Métricas de Qualidade Alvo

| Métrica | Atual | Meta |
|---------|-------|------|
| Validação Jakarta | 80% | 100% |
| Strings i18n | 90% | 100% |
| EntityGraph | 20% | 80% |
| SRP Compliance | 70% | 95% |
| Documentação ADR | 0% | 100% |

---

## Parte 4: Ordem de Execução Recomendada

```
FASE M (Encoding i18n)        [1 semana]
       ↓
FASE K (Remover Eventos)       [2 semanas]
       ↓
FASE L (Validação DTOs)       [2 semanas]
       ↓
FASE P (EntityGraph)          [2 semanas]
       ↓
FASE O (SRP)                  [3 semanas]
       ↓
FASE Q (Specification)         [2 semanas]
       ↓
FASE R (Documentação)         [1 semana]
```

**Tempo estimado total:** ~13 semanas

---

## Parte 5: Verificações de Regressão

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

# Análise estática (se disponível)
./mvnw sonar:sonar
```
