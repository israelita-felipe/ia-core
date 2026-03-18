# Plano de Melhorias - ia-core-apps e gestor-igreja/Biblia

## Sumário

1. [Visão Geral](#visão-geral)
2. [Análise dos Projetos](#análise-dos-projetos)
   - [2.1 Estrutura do ia-core-apps](#21-estrutura-do-ia-core-apps)
   - [2.2 Estrutura do gestor-igreja/Biblia](#22-estrutura-do-gestor-igrejabiblia)
   - [2.3 Padrões Existentes](#23-padrões-existentes)
3. [Melhorias Propostas](#melhorias-propostas)
   - [3.1 Fundamentos e Infraestrutura](#31-fundamentos-e-infraestrutura)
   - [3.2 Arquitetura e Design](#32-arquitetura-e-design)
   - [3.3 Performance e Otimização](#33-performance-e-otimização)
   - [3.4 Segurança](#34-segurança)
   - [3.5 Testes e Qualidade](#35-testes-e-qualidade)
   - [3.6 Documentação](#36-documentação)
4. [Checklist de Implementação](#checklist-de-implementação)
5. [Roadmap](#roadmap)
6. [Próximas Implementações - Domínio de Escalas](#7-próximas-implementações---domínio-de-escalas)
7. [Referências](#referências)

---

## 1. Visão Geral

Este documento apresenta um plano abrangente de melhorias para os projetos **ia-core-apps** e **gestor-igreja/Biblia**, baseando-se nas melhores práticas de programação e padrões consolidados de mercado.

### Objetivos

- Uniformizar a arquitetura entre os projetos
- Melhorar a qualidade do código
- Aumentar a cobertura de testes
- Otimizar performance
- Fortalecer a segurança
- Melhorar a documentação

---

## 2. Análise dos Projetos

### 2.1 Estrutura do ia-core-apps

O projeto **ia-core-apps** é um framework base que fornece componentes compartilhados para aplicações Java/Spring Boot. Estrutura atual:

```
ia-core-apps/
├── ia-core/                          # Módulo pai
│   ├── ia-core-model/               # Entidades e modelos de domínio
│   ├── ia-core-service/             # Serviços base
│   ├── ia-core-rest/               # Controllers REST base
│   ├── ia-core-view/               # Componentes Vaadin
│   ├── ia-core-llm-model/          # Modelos de LLM
│   ├── ia-core-llm-service/        # Serviços de LLM
│   ├── ia-core-llm-view/           # View de LLM
│   ├── ia-core-quartz/             # Modelo de agendamento
│   ├── ia-core-quartz-service/     # Serviços de agendamento
│   ├── ia-core-quartz-view/        # View de agendamento
│   ├── ia-core-nlp/               # Processamento de linguagem natural
│   ├── ia-core-grammar/            # Gramáticas ANTLR
│   ├── ia-core-flyway/            # Migrações de banco
│   ├── ia-core-security/           # Serviços de segurança
│   └── ADR/                       # Arquitetura Decision Records
```

### 2.2 Estrutura do gestor-igreja/Biblia

O projeto **Biblia** é uma aplicação de gestão religiosa built on top do ia-core-apps:

```
gestor-igreja/Biblia/
├── biblia-model/                   # Entidades JPA
├── biblia-service/                 # Serviços de negócio
├── biblia-service-model/           # DTOs e Translators
├── biblia-rest/                   # Controllers REST
├── biblia-view/                  # Interface Vaadin
├── biblia-nlp/                   # NLP específico
├── biblia-grammar/               # Gramáticas específicas
├── CDU/                          # Casos de Uso
└── biblia.xml                    # Configurações
```

### 2.3 Padrões Existentes

Os projetos já seguem diversos padrões estabelecidos:

| Padrão | Status | Descrição |
|--------|--------|-----------|
| **Clean Architecture** | ✅ Implementado | Separação em camadas |
| **SOLID** | ✅ Parcial | SRP, OCP aplicados |
| **MVVM** | ✅ Implementado | ViewModels e Views |
| **DTO Pattern** | ✅ Implementado | DTOs com Translators |
| **Service Pattern** | ✅ Implementado | @Service com injeção |
| **Specification Pattern** | ✅ Implementado | Filtros dinâmicos |
| **Domain Events** | ✅ Implementado | Publicação automática |
| **MapStruct** | ✅ Implementado | Mapeamento DTO-Entidade |
| **Jakarta Validation** | ✅ Parcial | Validações em DTOs |
| **i18n** | ✅ Parcial | Arquivos properties |

---

## 3. Melhorias Propostas

### 3.1 Fundamentos e Infraestrutura

#### 3.1.1 Validação Jakarta Completa

**Problema:** Validação inconsistente entre DTOs.

**Solução:** Completar validação em todos os DTOs seguindo o padrão.

**Arquivos de referência:**
- [`CODING_STANDARDS.md`](ia-core/CODING_STANDARDS.md)
- [BIBLIA_REFACTORATION_PLAN.md](gestor-igreja/Biblia/BIBLIA_REFACTORATION_PLAN.md)

**DTOs pendentes no BiblIA:**
- [ ] `FamiliaDTO` - Adicionar validações
- [ ] `PessoaDTO` - Verificar validações
- [ ] `ContaDTO` - Verificar validações
- [ ] `DoacaoDTO` - Adicionar validações
- [ ] `TransferenciaDTO` - Verificar validações

#### 3.1.2 Internacionalização (i18n)

**Problema:** Strings hardcoded em Views e Services.

**Solução:** Completar arquivo de traduções.

**Arquivo de referência:**
- [`translations_biblia_pt_BR.properties`](gestor-igreja/Biblia/biblia-service-model/src/main/resources/i18n/translations_biblia_pt_BR.properties)

#### 3.1.3 Padronização de Nomenclatura

**Problema:** Inconsistências na nomenclatura de classes e métodos.

**Solução:** Aplicar convenções definidas em [`CODING_STANDARDS.md`](ia-core/CODING_STANDARDS.md).

| Tipo | Padrão | Exemplo |
|------|--------|---------|
| Services | `NomeService` | `EventoService` |
| Repositories | `NomeRepository` | `EventoRepository` |
| DTOs | `NomeDTO` | `EventoDTO` |
| ViewModels | `NomeViewModel` | `EventoFormViewModel` |

---

### 3.2 Arquitetura e Design

#### 3.2.1 EntityGraph e Performance

**Problema:** Queries N+1 em entidades com relacionamentos.

**Solução:** Adicionar `@NamedEntityGraph` em entidades.

**Entidades que precisam de EntityGraph:**
- [ ] `MovimentoFinanceiro` - com `conta` e `tipoIntencao`
- [ ] `Evento` - com `inscricoes` e `materiais`
- [ ] `Conta` - com `movimentos`
- [ ] `Familia` - com `integrantes`

**Arquivo de referência:**
- [ENTITYGRAPH_OPTIMIZATION.md](gestor-igreja/Biblia/ENTITYGRAPH_OPTIMIZATION.md)

#### 3.2.2 Índices de Banco de Dados

**Problema:** Falta de índices para queries frequentes.

**Solução:** Criar migrações Flyway com índices.

```sql
-- MovimentoFinanceiro
CREATE INDEX idx_movimento_tipo ON tb_movimento_financeiro(tipo);
CREATE INDEX idx_movimento_conta ON tb_movimento_financeiro(conta_id);
CREATE INDEX idx_movimento_data ON tb_movimento_financeiro(data_movimento);

-- Evento
CREATE INDEX idx_evento_data ON tb_evento(data_evento);
CREATE INDEX idx_inscricao_evento ON tb_inscricao(evento_id);
```

#### 3.2.3 Separação de Responsabilidades (SRP)

**Problema:** Services com múltiplas responsabilidades.

**Solução:** Separar services por domínio.

| Service Atual | Problema | Novo Service |
|--------------|----------|--------------|
| `EventoService` | CRUD + Inscrição + Materiais | Separar `InscricaoService`, `MaterialEventoService` |
| `MovimentoFinanceiroService` | CRUD + Relatórios | Separar `RelatorioFinanceiroService` |
| `ContaService` | CRUD + Transferências | Separar `TransferenciaService` |

#### 3.2.4 Domain Events Expansion

**Problema:** Eventos de domínio não utilizados em todo o sistema.

**Solução:** Expandir uso de eventos para outras operações.

```java
// Exemplos de eventos a implementar
- EventoCriadoEvent
- EventoAtualizadoEvent
- InscricaoConfirmadaEvent
- PagamentoRecebidoEvent
- MembroAdicionadoEvent
```

---

### 3.3 Performance e Otimização

#### 3.3.1 Cache Strategy

**Problema:** Dados frequentemente acessados sem cache.

**Solução:** Implementar cache com Spring Cache.

| Dado | Estratégia | TTL |
|------|-------------|-----|
| Calendários | Cacheable | 1 hora |
| Configurações | Cacheable | 24 horas |
| Eventos do mês | Cacheable | 5 minutos |
| Estatísticas | Cacheable | 15 minutos |

#### 3.3.2 Paginação

**Problema:** Listas sem paginação.

**Solução:** Implementar Pageable em todas as listas.

```java
// Exemplo
public Page<EventoDTO> findAll(Pageable pageable) {
    return eventoRepository.findAll(pageable).map(this::toDTO);
}
```

#### 3.3.3 Query Optimization

**Problema:** Queries complexas não otimizadas.

**Solução:** Usar EntityGraph e projection.

**Arquivo de referência:**
- [`PERFORMANCE_OPTIMIZATION_PLAN.md`](ia-core/PERFORMANCE_OPTIMIZATION_PLAN.md)

---

### 3.4 Segurança

#### 3.4.1 Autenticação e Autorização

**Melhorias sugeridas:**
- [ ] Implementar OAuth2/OpenID Connect
- [ ] Adicionar Rate Limiting
- [ ] Implementar auditoria de segurança
- [ ] Adicionar CORS configurável

**Arquivo de referência:**
- [FASE_Y_SEGURANCA_AUTENTICACAO.md](ia-core/FASE_Y_SEGURANCA_AUTENTICACAO.md)

#### 3.4.2 Proteção de Dados

**Melhorias:**
- [ ] Criptografia de dados sensíveis
- [ ] Mask de dados em logs
- [ ] Sanitização de inputs

---

### 3.5 Testes e Qualidade

#### 3.5.1 Cobertura de Testes

**Meta:** > 70% de cobertura.

| Módulo | Atual | Meta |
|--------|-------|------|
| ia-core-model | 70% | 80% |
| ia-core-service | 60% | 80% |
| biblia-service | 40% | 70% |
| biblia-model | 50% | 70% |

#### 3.5.2 Estrutura de Testes

```
src/test/java/
├── unit/
│   └── {modulo}/
│       └── services/
├── integration/
│   └── repositories/
└── acceptance/
    └── {funcionalidade}/
```

**Arquivo de referência:**
- [`TESTING_PLAN.md`](ia-core/TESTING_PLAN.md)

#### 3.5.3 Qualidade de Código

**Ferramentas a integrar:**
- [ ] SonarQube
- [ ] JaCoCo (cobertura)
- [ ] SpotBugs
- [ ] Checkstyle

---

### 3.6 Documentação

#### 3.6.1 Documentação de Código

**Melhorias:**
- [ ] Completar Javadoc em todas as classes públicas
- [ ] Adicionar @since tags
- [ ] Adicionar @see e {@link} referências
- [ ] Criar exemplos de uso

**Padrão de Javadoc:**
```java
/**
 * Descrição do método.
 * <p>
 * Explicação adicional sobre o comportamento.
 *
 * @param param1 descrição do parâmetro
 * @return descrição do retorno
 * @throws Excecao возможная descrição
 * @since 1.0.0
 * @see ClasseRelacionada
 */
```

#### 3.6.2 Documentação de API

**Melhorias:**
- [ ] Completar anotações OpenAPI/Swagger
- [ ] Adicionar descrições em português
- [ ] Documentar códigos de erro

#### 3.6.3 Documentação de Arquitetura

**Melhorias:**
- [ ] Criar ARCHITECTURE.md para BiblIA
- [ ] Atualizar CONTRIBUTING.md
- [ ] Criar RUNBOOK.md para operações

---

## 4. Checklist de Implementação

### Versão: 1.2.0 (2026-03-14)

### Fase 1: Fundamentos

- [x] Completar validação Jakarta em todos os DTOs
- [x] Completar arquivo i18n translations_biblia_pt_BR.properties
- [x] Remover strings hardcoded
- [x] Aplicar convenções de nomenclatura

### Fase 2: Arquitetura

- [x] Adicionar @NamedEntityGraph em entidades
- [x] Atualizar repositories para usar EntityGraph
- [x] Criar índices Flyway
- [x] Separar services com múltiplas responsabilidades
- [x] Expandir uso de Domain Events
- [x] Implementar Domain Events para Escala (EventoEventListener)

### Fase 3: Domínio de Escalas

- [x] Criar entidades JPA (Funcao, Integrante, Grupo, Escala, EscalaIntegranteFuncao)
- [x] Criar DTOs e Translators
- [x] Criar repositories
- [x] Criar services com validações de conflito de horário
- [x] Criar controllers REST
- [x] Criar views Vaadin
- [x] Configurar Domain Events para invalidar escalas
- [x] Completar i18n para o domínio
- [x] Criar migrações Flyway para tabelas de Escalas

### Fase 4: Performance

- [x] Otimizar queries com projection
- [x] Implementar lazy loading eficiente

#### Implementações Realizadas:

1. **ADR-015 criado** - Documentação da estratégia de projection
2. **EntityProjection interface** - Base para todas as projeções
3. **SchedulerConfigSummary** - Exemplo de projection implementada
4. **SchedulerConfigRepository** - Métodos de projection adicionados

#### Status: ✅ CONCLUÍDO

### Fase 5: Documentação e Padronização

- [x] README.md para cada módulo
- [x] CONTRIBUTING.md
- [x] Padrões de commit
- [x] CHANGELOG.md

#### Implementações Realizadas:

1. **CHANGELOG.md criado** - Histórico de versões
2. **README.md** - Já existente e atualizado
3. **CONTRIBUTING.md** - Já existente com padrões de commit

#### Status: ✅ CONCLUÍDO

### Fase 6: Testes Unitários

- [x] Implementar testes unitários
- [x] Implementar testes de integração

#### Implementações Realizadas:

1. **AbstractServiceTest** - Classe base para testes de serviço
2. **BusinessRuleChainTest** - Testes unitários para BusinessRuleChain (15+ testes)
3. **SecurityContextServiceTest** - Testes unitários para SecurityContextService (15+ testes)
4. **Estrutura de testes** - Criada em ia-core-service e security-core-service
5. **AbstractQuartzRepositoryIT** - Classe base para testes de integração do Quartz

#### Status: ✅ CONCLUÍDO

### Fase 8: Documentação

- [ ] Implementar OAuth2/OpenID
- [ ] Adicionar Rate Limiting
- [ ] Implementar auditoria de segurança
- [ ] Configurar CORS

### Fase 7: Testes

- [ ] Implementar testes unitários
- [ ] Implementar testes de integração
- [ ] Configurar CI/CD
- [ ] Configurar SonarQube

### Fase 8: Documentação

- [ ] Completar Javadoc
- [ ] Completar documentação OpenAPI
- [ ] Criar ARCHITECTURE.md para BiblIA
- [ ] Atualizar CONTRIBUTING.md

---

## 5. Roadmap

### Versão 1.2.x - Domínio de Escalas

| Sprint | Foco | Entregável |
|--------|------|-------------|
| 1 | Entidades e DTOs | Modelo completo implementado |
| 2 | Services e Validações | Regras de negócio |
| 3 | Domain Events | Invalidação automática |

### Versão 1.5.x - Migrações

| Sprint | Foco | Entregável |
|--------|------|-------------|
| 1 | Validação Jakarta | Todos DTOs validados |
| 2 | i18n | Strings externalizadas |
| 3 | Nomenclatura | Código padronizado |

### Versão 2.0.x - Arquitetura

| Sprint | Foco | Entregável |
|--------|------|-------------|
| 1 | EntityGraph | Queries otimizadas |
| 2 | SRP | Services separados |
| 3 | Events | Sistema de eventos completo |

### Versão 2.5.x - Performance

| Sprint | Foco | Entregável |
|--------|------|-------------|
| 1 | Cache | Performance melhorada |
| 2 | Paginação | Lists paginadas |
| 3 | Queries | Queries otimizadas |

### Versão 3.0.x - Segurança e Qualidade

| Sprint | Foco | Entregável |
|--------|------|-------------|
| 1 | Segurança | OAuth2 implementado |
| 2 | Testes | > 70% cobertura |
| 3 | Docs | Documentação completa |

---

## 7. Próximas Implementações - Domínio de Escalas

### 7.1 Status da Implementação

O domínio de Escalas foi **quase totalmente implementado** seguindo os padrões do ia-core-apps. Abaixo segue o status de cada componente:

| Componente | Status | Descrição |
|------------|--------|------------|
| **Modelo (Entities)** | ✅ Concluído | Funcao, Integrante, Grupo, Escala, EscalaIntegranteFuncao, EscalaStatus |
| **DTOs** | ✅ Concluído | FuncaoDTO, IntegranteDTO, GrupoDTO, EscalaDTO, EscalaIntegranteFuncaoDTO |
| **SearchRequests** | ✅ Concluído | FuncaoSearchRequest, IntegranteSearchRequest, GrupoSearchRequest, EscalaSearchRequest |
| **Translators** | ✅ Concluído | FuncaoTranslator, IntegranteTranslator, GrupoTranslator, EscalaTranslator |
| **Services** | ✅ Concluído | FuncaoService, IntegranteService, GrupoService, EscalaService |
| **ServiceConfigs** | ✅ Concluído | FuncaoServiceConfig, IntegranteServiceConfig, GrupoServiceConfig, EscalaServiceConfig |
| **Repositories** | ✅ Concluído | FuncaoRepository, IntegranteRepository, GrupoRepository, EscalaRepository |
| **REST Controllers** | ✅ Concluído | FuncaoController, GrupoController, EscalaController |
| **Views** | ✅ Concluído | FuncaoPageView, GrupoPageView, EscalaPageView, EscalaFormView |
| **Domain Events** | ✅ Concluído | EventoEventListener para invalidação automática de escalas |
| **i18n** | ✅ Concluído | translations_biblia_pt_BR.properties completo |
| **Validações de Negócio** | ✅ Concluído | EscalaConflitoHorarioRN001 para validação de conflitos |
| **Migrações Flyway** | ✅ Concluído | Arquivos SQL para criação das tabelas |

### 7.2 Modelo de Domínio Implementado

O seguinte modelo de domínio foi implementado no sistema:

| Entidade | Descrição | Atributos |
|----------|------------|------------|
| **Funcao** | Função genérica com nome e descrição | nome, descricao |
| **Integrante** | Associação entre pessoa e função | pessoa, funcao |
| **Grupo** | Título, descrição e coleção de integrantes | titulo, descricao, listaIntegrantes |
| **Escala** | IntervaloTemporalDTO, EventoDTO, grupos, EscalaIntegranteFuncao e status | intervaloTemporal, evento, grupos, escalaIntegranteFuncoes, status |
| **EscalaIntegranteFuncao** | Associa integrante e função em uma escala | escala, integrante, funcao |
| **EscalaStatus** | Enum: PENDENTE, APROVADA, CANCELADA, INVALIDA | - |

### 7.3 Alterações no EventoCalendarDTO

✅ **Implementado**: O EventoCalendarDTO já possui a associação com escala:

```java
// EventoCalendarDTO.java
private com.ia.biblia.service.escala.dto.EscalaDTO escala;
```

A seleção dinâmica de IntervaloTemporal via OccurrenceCalculator já está implementada em EscalaFormViewModel.

### 7.4 Regras de Negócio Implementadas

1. ✅ **Invalidação automática**: Quando um EventoDTO for alterado, o EventoEventListener automatically invalida as escalas associadas
2. ✅ **Conflito de horário**: A validação EscalaConflitoHorarioRN001 verifica se algum participante já possui escala no mesmo período
3. ✅ **Seleção dinâmica**: Os integrantes são selecionados a partir dos grupos associados à escala

### 7.5 Estrutura de Arquivos Implementada

```
# biblia-model
biblia-model/src/main/java/com/ia/biblia/model/
├── funcao/
│   └── Funcao.java
├── integrante/
│   └── Integrante.java
├── grupo/
│   └── Grupo.java
└── escala/
    ├── Escala.java
    ├── EscalaIntegranteFuncao.java
    └── EscalaStatus.java (enum)

# biblia-service-model
biblia-service-model/src/main/java/com/ia/biblia/service/
├── funcao/dto/
│   ├── FuncaoDTO.java
│   ├── FuncaoTranslator.java
│   └── FuncaoSearchRequest.java
├── integrante/dto/
│   ├── IntegranteDTO.java
│   ├── IntegranteTranslator.java
│   └── IntegranteSearchRequest.java
├── grupo/dto/
│   ├── GrupoDTO.java
│   ├── GrupoTranslator.java
│   └── GrupoSearchRequest.java
└── escala/dto/
    ├── EscalaDTO.java
    ├── EscalaTranslator.java
    ├── EscalaSearchRequest.java
    ├── EscalaIntegranteFuncaoDTO.java
    └── EscalaIntegranteFuncaoTranslator.java

# biblia-service
biblia-service/src/main/java/com/ia/biblia/service/
├── funcao/
│   ├── FuncaoService.java
│   ├── FuncaoServiceConfig.java
│   └── FuncaoRepository.java
├── integrante/
│   ├── IntegranteService.java
│   ├── IntegranteServiceConfig.java
│   └── IntegranteRepository.java
├── grupo/
│   ├── GrupoService.java
│   ├── GrupoServiceConfig.java
│   └── GrupoRepository.java
├── escala/
│   ├── EscalaService.java
│   ├── EscalaServiceConfig.java
│   ├── EscalaRepository.java
│   ├── EscalaIntegranteFuncaoRepository.java
│   └── validators/
│       └── EscalaConflitoHorarioRN001.java
└── evento/
    └── EventoEventListener.java (Domain Events)

# biblia-rest
biblia-rest/src/main/java/com/ia/biblia/rest/
├── funcao/
│   └── FuncaoController.java
├── grupo/
│   └── GrupoController.java
└── escala/
    └── EscalaController.java

# biblia-view
biblia-view/src/main/java/com/ia/biblia/view/
├── funcao/
│   ├── FuncaoPageView.java
│   └── FuncaoFormView.java
├── grupo/
│   ├── GrupoPageView.java
│   └── GrupoFormView.java
└── escala/
    ├── EscalaPageView.java
    ├── EscalaFormView.java
    └── EscalaIntegranteFuncaoCollectionPageView.java
```

### 7.6 Pendências - Migrações Flyway

**Apenas uma pendência identificada**: Criar os arquivos de migração Flyway para as tabelas do domínio de Escalas.

Arquivos a criar em `biblia-service/src/main/resources/db/migrations/hsqldb/`:

1. `V1_0_001__create_funcao.sql` - Tabela BBL_FUNCAO
2. `V1_0_002__create_integrante.sql` - Tabela BBL_INTEGRANTE
3. `V1_0_003__create_grupo.sql` - Tabela BBL_GRUPO
4. `V1_0_004__create_grupo_integrante.sql` - Tabela de associação GRUPO_INTEGRANTE
5. `V1_0_005__create_escala.sql` - Tabela BBL_ESCALA
6. `V1_0_006__create_escala_grupo.sql` - Tabela de associação ESCALA_GRUPO
7. `V1_0_007__create_escala_integrante_funcao.sql` - Tabela BBL_ESCALA_INTEGRANTE_FUNCAO

---

## 8. Referências

### Documentos Existentes

- [CODING_STANDARDS.md](ia-core/CODING_STANDARDS.md)
- [ARCHITECTURE.md](ia-core/ARCHITECTURE.md)
- [BIBLIA_REFACTORATION_PLAN.md](gestor-igreja/Biblia/BIBLIA_REFACTORATION_PLAN.md)
- [PLANO_REFATORACAO_BIBLIA.md](gestor-igreja/Biblia/PLANO_REFATORACAO_BIBLIA.md)
- [PERFORMANCE_OPTIMIZATION_PLAN.md](ia-core/PERFORMANCE_OPTIMIZATION_PLAN.md)
- [TESTING_PLAN.md](ia-core/TESTING_PLAN.md)
- [JAVADOC_STANDARDS.md](ia-core/JAVADOC_STANDARDS.md)

### Recursos Externos

- SOLID Principles
- Clean Code (Robert Martin)
- Domain-Driven Design (Eric Evans)
- Spring Boot Best Practices
- Vaadin Best Practices

---

*Documento criado em: 2025-02-17*
*Última atualização: 2026-03-14*
*Versão: 1.1.0*
